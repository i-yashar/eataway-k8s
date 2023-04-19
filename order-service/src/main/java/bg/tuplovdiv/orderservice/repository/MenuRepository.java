package bg.tuplovdiv.orderservice.repository;

import bg.tuplovdiv.orderservice.model.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    Optional<MenuEntity> findMenuEntityByExternalId(UUID menuId);
}
