package bg.tuplovdiv.restaurants.repository;

import bg.tuplovdiv.restaurants.model.entity.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    Optional<MenuEntity> findByExternalId(UUID menuId);
    Page<MenuEntity> findAllByRestaurantExternalId(UUID restaurantId, Pageable pageable);
}
