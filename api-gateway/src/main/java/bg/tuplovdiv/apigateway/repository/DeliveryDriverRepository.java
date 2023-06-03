package bg.tuplovdiv.apigateway.repository;

import bg.tuplovdiv.apigateway.model.entity.DeliveryDriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryDriverRepository extends JpaRepository<DeliveryDriverEntity, Long> {
    Optional<DeliveryDriverEntity> findByDeliveryDriverId(String driverId);
}
