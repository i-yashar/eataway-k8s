package bg.tuplovdiv.apigateway.security.validation;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StatusValidator {

    private static final Set<String> validStatuses = Set.of("ABOUT_TO_BE_DELIVERED", "DELIVERED");

    public boolean isStatusUpdateValid(String status) {
        return validStatuses.contains(status);
    }
}
