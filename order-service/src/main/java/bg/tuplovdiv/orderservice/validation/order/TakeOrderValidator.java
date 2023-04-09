package bg.tuplovdiv.orderservice.validation.order;

import bg.tuplovdiv.orderservice.dto.TakeOrderRequest;
import bg.tuplovdiv.orderservice.repository.OrderRepository;
import bg.tuplovdiv.orderservice.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static bg.tuplovdiv.orderservice.dto.TakeOrderRequest.*;

@Component
public class TakeOrderValidator implements ConstraintValidator<ValidTakeOrderRequest, TakeOrderRequest> {

    private static final String INVALID_TAKE_ORDER_REQUEST_MESSAGE = "Invalid create order request. Please provide a valid value for field '%s'.";

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public TakeOrderValidator(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(TakeOrderRequest orderRequest, ConstraintValidatorContext context) {
        UUID orderId = orderRequest.getOrderId();
        UUID deliveryDriverId = orderRequest.getDeliveryDriverId();

        if(hasInvalidOrder(orderId)) {
            addConstraintViolation(context, ORDER_ID_JSON_PROPERTY);
            return false;
        }

        if(hasInvalidDeliveryDriver(deliveryDriverId)) {
            addConstraintViolation(context, DELIVERY_DRIVER_ID_JSON_PROPERTY);
            return false;
        }

        return true;
    }

    private boolean hasInvalidOrder(UUID orderId) {
        return orderRepository.findOrderEntityByExternalId(orderId).isEmpty();
    }

    private boolean hasInvalidDeliveryDriver(UUID deliveryDriverId) {
        return userRepository.findByExternalId(deliveryDriverId).isEmpty();
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String property) {
        context
                .buildConstraintViolationWithTemplate(
                        String.format(INVALID_TAKE_ORDER_REQUEST_MESSAGE, property))
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
