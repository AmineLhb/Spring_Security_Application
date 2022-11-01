package spring.project.securityservice.secuRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.securityservice.secuEntities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String roleName);
}
