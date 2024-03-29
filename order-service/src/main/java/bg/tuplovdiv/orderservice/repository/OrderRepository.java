package bg.tuplovdiv.orderservice.repository;

import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findOrderEntityByExternalId(UUID orderId);
}
