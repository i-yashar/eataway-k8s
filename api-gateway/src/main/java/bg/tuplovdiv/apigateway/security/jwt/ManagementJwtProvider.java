package bg.tuplovdiv.apigateway.security.jwt;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ManagementJwtProvider extends JwtProvider {

    public ManagementJwtProvider(TokenService tokenService) {
        super(tokenService);
    }
}
