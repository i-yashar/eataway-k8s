package bg.tuplovdiv.orderservice.validation.order;

import bg.tuplovdiv.orderservice.dto.OrderRequest;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import bg.tuplovdiv.orderservice.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static bg.tuplovdiv.orderservice.dto.OrderRequest.*;

@Component
public class OrderValidator implements ConstraintValidator<ValidOrder, OrderRequest> {

    private static final String INVALID_ORDER_REQUEST_MESSAGE = "Invalid order request.";

    private final UserRepository userRepository;
    private final BasketRepository basketRepository;

    public OrderValidator(UserRepository userRepository, BasketRepository basketRepository) {
        this.userRepository = userRepository;
        this.basketRepository = basketRepository;
    }

    @Override
    public boolean isValid(OrderRequest orderRequest, ConstraintValidatorContext context) {
        UUID clientId = orderRequest.getClientId();
        UUID deliveryDriverId = orderRequest.getDeliveryDriverId();
        UUID basketId = orderRequest.getBasketId();
        Double totalCost = orderRequest.getTotalCost();

        if (hasInvalidClient(clientId)) {
            addConstraintViolation(context, CLIENT_ID_JSON_PROPERTY);
            return false;
        }

        if (hasInvalidDeliveryDriver(deliveryDriverId)) {
            addConstraintViolation(context, DELIVERY_DRIVER_ID_JSON_PROPERTY);
            return false;
        }

        if (hasInvalidBasket(basketId)) {
            addConstraintViolation(context, BASKET_ID_JSON_PROPERTY);
            return false;
        }

        return false;
    }


    private boolean hasInvalidDeliveryDriver(UUID deliveryDriverId) {
        return userRepository.findByExternalId(deliveryDriverId).isEmpty();
    }

    private boolean hasInvalidClient(UUID clientId) {
        return userRepository.findByExternalId(clientId).isEmpty();
    }

    private boolean hasInvalidBasket(UUID basketId) {
        return basketRepository.findBasketEntityByExternalId(basketId).isEmpty();
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String... properties) {
        ConstraintValidatorContext.ConstraintViolationBuilder builder = context
                .buildConstraintViolationWithTemplate(INVALID_ORDER_REQUEST_MESSAGE);

        for (String property : properties) {
            builder.addPropertyNode(property);
        }

        builder.addConstraintViolation().disableDefaultConstraintViolation();
    }
}
