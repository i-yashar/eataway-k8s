package bg.tuplovdiv.apigateway.security.handler;

import bg.tuplovdiv.apigateway.security.user.impl.EatawayUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private final AuthenticatedUserProvider userProvider;

    public OAuth2LoginSuccessHandler(UserService userService, AuthenticatedUserProvider userProvider) {
        this.userService = userService;
        this.userProvider = userProvider;
        setDefaultTargetUrl("/home");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        if(authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {
            userService.registerUser((EatawayUser) userProvider.provide());
        }

        response.sendRedirect("/home");
    }
}
