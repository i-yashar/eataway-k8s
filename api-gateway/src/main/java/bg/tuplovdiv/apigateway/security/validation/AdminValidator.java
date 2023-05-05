package bg.tuplovdiv.apigateway.security.validation;

import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.repository.UserRepository;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.apigateway.model.UserRoleEnum.ADMIN;

@Component
public class AdminValidator {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final UserRepository userRepository;

    public AdminValidator(AuthenticatedUserProvider authenticatedUserProvider, UserRepository userRepository) {
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.userRepository = userRepository;
    }

    public boolean isAdmin() {
        AuthenticatedUser principal = authenticatedUserProvider.provide();
        UserEntity user = getUser(principal.getUserId());

        return user.getUserRoles().contains(ADMIN);
    }

    private UserEntity getUser(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
