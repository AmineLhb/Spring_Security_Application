package spring.project.securityservice.secuRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.securityservice.secuEntities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
}
