package bg.tuplovdiv.apigateway.security.validation;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.exception.DeliveryDriverNotFreeException;
import bg.tuplovdiv.apigateway.model.entity.DeliveryDriverEntity;
import bg.tuplovdiv.apigateway.repository.DeliveryDriverRepository;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import bg.tuplovdiv.apigateway.service.OrderService;
import org.springframework.stereotype.Component;

import java.util.Optional;
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
        return getDeliveryDriver().isPresent();
    }

    public boolean isDeliveryDriverFree() {
        DeliveryDriverEntity driver = getDeliveryDriver().get();

        if(!driver.isFree()) {
            throw new DeliveryDriverNotFreeException();
        }

        return true;
    }

    public boolean isDeliveryDriverCorrect(UUID orderId) {
        DeliveryDriverEntity driver = getDeliveryDriver().get();

        return orderId.equals(driver.getCurrentOrderId());
    }

    public boolean isDeliveryDriverEligible(UUID orderId) {
        UUID driverRestaurantId = getDeliveryDriver().get().getRestaurantId();
        OrderDTO order = orderService.getOrderInfo(orderId);

        return order.getItems()
                .stream()
                .anyMatch(i -> i.getMenu().getRestaurantId().equals(driverRestaurantId));
    }

    private Optional<DeliveryDriverEntity> getDeliveryDriver() {
        AuthenticatedUser principal = authenticatedUserProviderFactory.getProvider().provide();
        return deliveryDriverRepository.findByDeliveryDriverId(principal.getUserId());
    }
}
