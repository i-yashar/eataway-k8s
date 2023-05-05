package bg.tuplovdiv.apigateway.repository;

import bg.tuplovdiv.apigateway.model.UserRoleEnum;
import bg.tuplovdiv.apigateway.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserRole(UserRoleEnum userRole);
}
