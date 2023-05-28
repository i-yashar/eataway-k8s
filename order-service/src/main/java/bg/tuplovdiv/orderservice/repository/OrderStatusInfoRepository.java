package bg.tuplovdiv.orderservice.repository;

import bg.tuplovdiv.orderservice.model.entity.OrderStatusInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusInfoRepository extends JpaRepository<OrderStatusInfoEntity, Long> {
}
