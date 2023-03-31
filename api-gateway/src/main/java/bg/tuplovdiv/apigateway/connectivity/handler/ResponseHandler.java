package bg.tuplovdiv.apigateway.connectivity.handler;

import java.net.http.HttpResponse;

@FunctionalInterface
public interface ResponseHandler<T> {
    T handle(HttpResponse<String> response);
}
