package bg.tuplovdiv.orderservice.validation.order;

import bg.tuplovdiv.orderservice.dto.CreateOrderRequest;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import bg.tuplovdiv.orderservice.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderValidator implements ConstraintValidator<ValidCreateOrderRequest, CreateOrderRequest> {

    private static final String CLIENT_ID_JSON_PROPERTY = "clientId";
    private static final String BASKET_ID_JSON_PROPERTY = "basketId";

    private static final String INVALID_CREATE_ORDER_REQUEST_MESSAGE = "Invalid create order request. Please provide a valid value for field '%s'.";

    private final UserRepository userRepository;
    private final BasketRepository basketRepository;

    public CreateOrderValidator(UserRepository userRepository, BasketRepository basketRepository) {
        this.userRepository = userRepository;
        this.basketRepository = basketRepository;
    }

    @Override
    public boolean isValid(CreateOrderRequest orderRequest, ConstraintValidatorContext context) {
        String clientId = orderRequest.getClientId();

        if (hasInvalidClient(clientId)) {
            addConstraintViolation(context, CLIENT_ID_JSON_PROPERTY);
            return false;
        }

        if (hasInvalidBasket(clientId)) {
            addConstraintViolation(context, BASKET_ID_JSON_PROPERTY);
            return false;
        }

        return true;
    }

    private boolean hasInvalidClient(String clientId) {
        return userRepository.findByUserId(clientId).isEmpty();
    }

    private boolean hasInvalidBasket(String clientId) {
        return basketRepository.findBasketEntityByOwnerUserId(clientId).isEmpty();
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String property) {
        context
                .buildConstraintViolationWithTemplate(
                        String.format(INVALID_CREATE_ORDER_REQUEST_MESSAGE, property))
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
