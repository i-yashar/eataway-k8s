package bg.tuplovdiv.orderservice.repository;

import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
    Optional<BasketEntity> findBasketEntityByOwner(String ownerId);
}
