package spring.project.securityservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.project.securityservice.secService.AccountService;
import spring.project.securityservice.secuEntities.AppRole;
import spring.project.securityservice.secuEntities.AppUser;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityServiceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	CommandLineRunner start(AccountService accountService){
//		return args -> {
//			accountService.addNewRole(new AppRole(null, "USER"));
//			accountService.addNewRole(new AppRole(null, "ADMIN"));
//			accountService.addNewRole(new AppRole(null, "MANAGER"));
//
//			accountService.addNewUser(new AppUser(null,"user1","123",new ArrayList<>()));
//			accountService.addNewUser(new AppUser(null,"user2","123",new ArrayList<>()));
//			accountService.addNewUser(new AppUser(null,"user3","123",new ArrayList<>()));
//
//			accountService.addRoleToUser("user1","USER");
//			accountService.addRoleToUser("user1","ADMIN");
//			accountService.addRoleToUser("user2","ADMIN");
//			accountService.addRoleToUser("user3","MANAGER");
//
//		};
//	}
}
