package bg.tuplovdiv.apigateway.connectivity.client;

import bg.tuplovdiv.apigateway.exception.RemoteHostException;
import bg.tuplovdiv.apigateway.security.jwt.JwtProvider;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;

@Component
public class OrderManagementClient extends RestClient {

    private static final String HOST = "http://localhost:8081";
    private static final String ORDER_MANAGEMENT_API_BASE_PATH = HOST + "/orders/api/v1/management";
    private static final String DISPATCH_REGISTERED_ORDERS_PATH = ORDER_MANAGEMENT_API_BASE_PATH + "/dispatch";

    public OrderManagementClient(JwtProvider jwtProvider) {
        super(jwtProvider);
    }

    public void triggerRegisteredOrderDispatch() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(DISPATCH_REGISTERED_ORDERS_PATH))
                .header("accept", "application/json")
                .header("Authorization", getBearerToken())
                .build();

        try {
            get(request, response -> null);
        } catch (RemoteHostException ignore) {

        }
    }
}
