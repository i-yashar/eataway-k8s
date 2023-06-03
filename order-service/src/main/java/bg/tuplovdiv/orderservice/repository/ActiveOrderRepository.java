package bg.tuplovdiv.orderservice.repository;

import bg.tuplovdiv.orderservice.model.entity.ActiveOrderEntity;
import bg.tuplovdiv.orderservice.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActiveOrderRepository extends JpaRepository<ActiveOrderEntity, Long> {
    Optional<ActiveOrderEntity> findActiveOrderEntityByExternalId(UUID orderId);
    Page<ActiveOrderEntity> findAllByClientIdAndStatusInOrderByUpdatedAtDesc(String clientId, Collection<OrderStatus> statuses, Pageable pageable);
    Collection<ActiveOrderEntity> findAllByDeliveryDriverIdAndStatusIn(String driverId, Collection<OrderStatus> statuses);
}
