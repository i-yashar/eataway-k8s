package bg.tuplovdiv.apigateway.security.validation;

import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.repository.UserRepository;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.apigateway.model.UserRoleEnum.ADMIN;

@Component
public class AdminValidator {

    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;
    private final UserRepository userRepository;

    public AdminValidator(AuthenticatedUserProviderFactory authenticatedUserProviderFactory, UserRepository userRepository) {
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
        this.userRepository = userRepository;
    }


    public boolean isAdmin() {
        AuthenticatedUser principal = authenticatedUserProviderFactory.getProvider().provide();
        UserEntity user = getUser(principal.getUserId());

        return user.getUserRoles().stream()
                .anyMatch(role -> role.getUserRole().equals(ADMIN));
    }

    private UserEntity getUser(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
