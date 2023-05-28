package bg.tuplovdiv.orderservice.repository;

import bg.tuplovdiv.orderservice.model.OrderStatus;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findOrderEntityByExternalId(UUID orderId);
    Page<OrderEntity> findAllByClientIdAndStatusInOrderByUpdatedAtDesc(String clientId, Collection<OrderStatus> statuses, Pageable pageable);
    Collection<OrderEntity> findAllByDeliveryDriverIdAndStatusIn(String driverId, Collection<OrderStatus> statuses);
}
