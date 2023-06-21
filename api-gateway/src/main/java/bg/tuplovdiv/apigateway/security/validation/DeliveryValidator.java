package bg.tuplovdiv.apigateway.security.validation;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.exception.DeliveryDriverNotFreeException;
import bg.tuplovdiv.apigateway.model.entity.DeliveryDriverEntity;
import bg.tuplovdiv.apigateway.repository.DeliveryDriverRepository;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import bg.tuplovdiv.apigateway.service.OrderService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeliveryValidator {

    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;
    private final OrderService orderService;
    private final DeliveryDriverRepository deliveryDriverRepository;

    public DeliveryValidator(AuthenticatedUserProviderFactory authenticatedUserProviderFactory, OrderService orderService, DeliveryDriverRepository deliveryDriverRepository) {
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
        this.orderService = orderService;
        this.deliveryDriverRepository = deliveryDriverRepository;
    }

    public boolean isDeliveryDriverValid() {
        return getDeliveryDriver() != null;
    }

    public boolean isDeliveryDriverFree() {
        DeliveryDriverEntity driver = getDeliveryDriver();
        return driver != null && isDeliveryDriverFree(driver);
    }

    public boolean isDeliveryDriverValidForInfo(UUID orderId) {
        DeliveryDriverEntity driver = getDeliveryDriver();
        return driver != null && orderContainsDriverRestaurant(driver, orderId);
    }

    public boolean isDeliveryDriverValidForTake(UUID orderId) {
        DeliveryDriverEntity driver = getDeliveryDriver();
        return driver != null && isDeliveryDriverFree(driver) && orderContainsDriverRestaurant(driver, orderId);
    }

    private boolean isDeliveryDriverFree(DeliveryDriverEntity driver) {
        if(!driver.isFree()) {
            throw new DeliveryDriverNotFreeException();
        }
        return true;
    }

    private boolean orderContainsDriverRestaurant(DeliveryDriverEntity driver, UUID orderId) {
        OrderDTO order = orderService.getOrderInfo(orderId);
        return order.getItems()
                .stream()
                .anyMatch(i -> i.getMenu().getRestaurantId().equals(driver.getRestaurantId()));
    }

    public boolean isDeliveryDriverValidForUpdate(UUID orderId) {
        DeliveryDriverEntity driver = getDeliveryDriver();
        return driver != null && orderId.equals(driver.getCurrentOrderId());
    }

    private DeliveryDriverEntity getDeliveryDriver() {
        AuthenticatedUser principal = authenticatedUserProviderFactory.getProvider().provide();
        return deliveryDriverRepository.findByDeliveryDriverId(principal.getUserId()).orElse(null);
    }
}
