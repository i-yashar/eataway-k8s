package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.model.entity.DeliveryDriverEntity;
import bg.tuplovdiv.apigateway.repository.DeliveryDriverRepository;
import bg.tuplovdiv.apigateway.service.DeliveryDriverService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class DeliveryDriverServiceImpl implements DeliveryDriverService {

    private final DeliveryDriverRepository deliveryDriverRepository;

    DeliveryDriverServiceImpl(DeliveryDriverRepository deliveryDriverRepository) {
        this.deliveryDriverRepository = deliveryDriverRepository;
    }

    @Override
    public UUID getDeliveryDriverRestaurant(String driverId) {
        return getDeliveryDriver(driverId).getRestaurantId();
    }

    @Override
    public void registerDriver(String driverId, UUID orderId) {
        DeliveryDriverEntity deliveryDriver = getDeliveryDriver(driverId);

        deliveryDriver.setFree(false);
        deliveryDriver.setCurrentOrderId(orderId);
        deliveryDriverRepository.save(deliveryDriver);
    }

    @Override
    public void setDriverFreeIfOrderIsDelivered(OrderDTO order) {
        if (order.getStatus().equals("DELIVERED")) {
            DeliveryDriverEntity deliveryDriver = getDeliveryDriver(order.getDeliveryDriverId());

            deliveryDriver.setFree(true);
            deliveryDriver.setCurrentOrderId(null);
            deliveryDriverRepository.save(deliveryDriver);
        }
    }

    private DeliveryDriverEntity getDeliveryDriver(String driverId) {
        return deliveryDriverRepository.findByDeliveryDriverId(driverId)
                .orElseThrow(() -> new UsernameNotFoundException("Delivery driver not found."));
    }
}
