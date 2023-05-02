package bg.tuplovdiv.orderservice.service.security.authorization;

import java.util.UUID;

public abstract class BaseAuthorizationService {

    public boolean isOwner(String userId, UUID resourceId) {
        String resourceOwnerId = getResourceOwnerId(resourceId);

        return resourceOwnerId.equals(userId);
    }

    protected abstract String getResourceOwnerId(UUID resourceId);
}
