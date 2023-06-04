package bg.tuplovdiv.apigateway.security.authentication.impl;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import bg.tuplovdiv.apigateway.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;

    public OAuth2LoginSuccessHandler(UserService userService, AuthenticatedUserProviderFactory authenticatedUserProviderFactory) {
        this.userService = userService;
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        AuthenticatedUserProvider authenticatedUserProvider =
                authenticatedUserProviderFactory.getProvider(authentication);

        userService.registerUser((EatawayUser) authenticatedUserProvider.provide());

        response.sendRedirect("/home");
    }
}
