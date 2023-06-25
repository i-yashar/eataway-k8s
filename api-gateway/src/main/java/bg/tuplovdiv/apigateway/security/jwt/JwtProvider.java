package bg.tuplovdiv.apigateway.security.jwt;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component("jwtProvider")
@SessionScope
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
        Instant validUntil = issuedAt.plus(55, ChronoUnit.MINUTES);
        return Instant.now().isAfter(validUntil);
    }
}
