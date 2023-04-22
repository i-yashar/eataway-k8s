package bg.tuplovdiv.orderservice.repository;

import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
    Optional<BasketEntity> findBasketEntityByExternalId(UUID basketId);
    Optional<BasketEntity> findBasketEntityByOwnerUserId(String ownerId);
}
