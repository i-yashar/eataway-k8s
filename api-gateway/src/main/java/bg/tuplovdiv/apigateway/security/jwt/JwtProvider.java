package bg.tuplovdiv.apigateway.security.jwt;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtProvider {

    private String token;
    private Instant issuedAt;

    private final TokenService tokenService;

    public JwtProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String provideToken() {

        if (token == null || isExpired()) {
            issuedAt = Instant.now();
            token = tokenService.generateToken();
        }

        return token;
    }

    private boolean isExpired() {
        Instant validUntil = issuedAt.plus(55, ChronoUnit.HOURS);
        return Instant.now().isAfter(validUntil);
    }
}
