package bg.tuplovdiv.apigateway.security.validation;

import bg.tuplovdiv.apigateway.exception.DeliveryDriverNotFreeException;
import bg.tuplovdiv.apigateway.model.entity.DeliveryDriverEntity;
import bg.tuplovdiv.apigateway.repository.DeliveryDriverRepository;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeliveryValidator {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final DeliveryDriverRepository deliveryDriverRepository;

    public DeliveryValidator(AuthenticatedUserProvider authenticatedUserProvider, DeliveryDriverRepository deliveryDriverRepository) {
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.deliveryDriverRepository = deliveryDriverRepository;
    }

    public boolean isDeliveryDriverValid() {
        return getDeliveryDriver().isPresent();
    }

    public boolean isDeliveryDriverFree() {
        Optional<DeliveryDriverEntity> driver = getDeliveryDriver();

        if (driver.isEmpty()) {
            return false;
        }

        if(!driver.get().isFree()) {
            throw new DeliveryDriverNotFreeException();
        }

        return true;
    }

    public boolean isDeliveryDriverCorrect(UUID orderId) {
        Optional<DeliveryDriverEntity> driver = getDeliveryDriver();

        if (driver.isEmpty()) {
            return false;
        }

        return orderId.equals(driver.get().getCurrentOrderId());
    }

    private Optional<DeliveryDriverEntity> getDeliveryDriver() {
        AuthenticatedUser principal = authenticatedUserProvider.provide();
        return deliveryDriverRepository.findByDeliveryDriverId(principal.getUserId());
    }
}
