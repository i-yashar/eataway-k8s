package bg.tuplovdiv.apigateway.security.jwt;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.impl.EatawayUser;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    private static final String API_GATEWAY_SERVICE = "api_gateway_service";

    private final JwtEncoder encoder;
    private final AuthenticatedUserProvider userProvider;

    public TokenService(JwtEncoder encoder, AuthenticatedUserProvider userProvider) {
        this.encoder = encoder;
        this.userProvider = userProvider;
    }

    public String generateToken() {
        AuthenticatedUser user = userProvider.provide();
        Instant now = Instant.now();

        String scope = String.join(" ", ((EatawayUser) user).getRoles());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(API_GATEWAY_SERVICE)
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(user.getUserId())
                .claim("scope", scope)
                .build();

        return encoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

}