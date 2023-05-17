package bg.tuplovdiv.apigateway.security.validation;

import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.order.OrderQueue;
import bg.tuplovdiv.apigateway.repository.UserRepository;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.apigateway.model.UserRoleEnum.EMPLOYEE;

@Component
public class DeliveryValidator {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final OrderQueue orderQueue;
    private final UserRepository userRepository;

    public DeliveryValidator(AuthenticatedUserProvider authenticatedUserProvider, OrderQueue orderQueue, UserRepository userRepository) {
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.orderQueue = orderQueue;
        this.userRepository = userRepository;
    }

    public boolean isDeliveryDriverFree() {
        UserEntity user = getUser();

        boolean isDriverBusy = orderQueue.getActiveOrders()
                .stream()
                .anyMatch(order -> order.getDeliveryDriverId().equals(user.getUserId()));

        return isDeliveryDriverValid() && !isDriverBusy;
    }

    public boolean isDeliveryDriverValid() {
        UserEntity user = getUser();

        return user.getUserRoles().stream()
                .anyMatch(role -> role.getUserRole().equals(EMPLOYEE));
    }

    private UserEntity getUser() {
        AuthenticatedUser principal = authenticatedUserProvider.provide();
        return userRepository.findByUserId(principal.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
