package spring.project.securityservice.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.project.securityservice.secService.AccountService;
import spring.project.securityservice.secuEntities.AppRole;
import spring.project.securityservice.secuEntities.AppUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {
    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/users")
    public List<AppUser> appUsers(){
        return accountService.listUsers();
    }
    @GetMapping(path = "/roles")
    public List<AppRole> appRoles(){
        return accountService.listRoles();
    }

    @PostMapping(path = "/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return accountService.addNewUser(appUser);
    }

    @PostMapping(path = "/roles")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return accountService.addNewRole(appRole);
    }
    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
         accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRolename());
    }

    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authToken=request.getHeader("Authorization");
        if(authToken!=null && authToken.startsWith("Bearer ")){
            try {
                String jwt=authToken.substring(7);
                Algorithm algorithm=Algorithm.HMAC256("mySecret123");
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username=decodedJWT.getSubject();
                AppUser appUser=accountService.loadUserByUsername(username);
                String jwtAccessToken= JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+1*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",appUser.getAppRoles().stream().map(ga->ga.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> idToken = new HashMap<>();
                idToken.put("access_token",jwtAccessToken);
                idToken.put("refresh-token",jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);
            }catch (Exception e){
                throw e;
            }
        }else {
            throw new RuntimeException("Refresh token required!!");
        }
    }
}
@Data
class RoleUserForm{
    private String username;
    private String rolename;
}