package bg.tuplovdiv.orderservice.repository;

import bg.tuplovdiv.orderservice.model.entity.OrderStatusInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface OrderStatusInfoRepository extends JpaRepository<OrderStatusInfoEntity, Long> {
    Collection<OrderStatusInfoEntity> findAllByOrderIdOrderByTimeAsc(UUID orderId);
}
