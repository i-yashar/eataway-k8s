package bg.tuplovdiv.orderservice.repository;

import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findOrderEntityByExternalId(UUID orderId);
    Page<OrderEntity> findAllByClientId(String clientId, Pageable pageable);
}
