package bg.tuplovdiv.apigateway.security.jwt;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class TokenService {

    private static final String API_GATEWAY_SERVICE = "api_gateway_service";
    private static final List<String> AUDIENCE;

    static {
        AUDIENCE = List.of("api_gateway_backend_services");
    }

    private final JwtEncoder encoder;
    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;

    public TokenService(JwtEncoder encoder, AuthenticatedUserProviderFactory authenticatedUserProviderFactory) {
        this.encoder = encoder;
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
    }


    public String generateToken() {
        AuthenticatedUser user = authenticatedUserProviderFactory.getProvider().provide();
        Instant now = Instant.now();

        String scope = String.join(" ", user.getRoles());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(API_GATEWAY_SERVICE)
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(user.getUserId())
                .audience(AUDIENCE)
                .claim("scope", scope)
                .build();

        System.out.println(encoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue());

        return encoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

}