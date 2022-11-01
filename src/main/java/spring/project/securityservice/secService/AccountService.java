package spring.project.securityservice.secService;

import spring.project.securityservice.secuEntities.AppRole;
import spring.project.securityservice.secuEntities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
    List<AppRole> listRoles();
}
