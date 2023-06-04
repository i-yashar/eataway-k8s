package bg.tuplovdiv.apigateway.connectivity.client;

import bg.tuplovdiv.apigateway.connectivity.handler.ResponseHandler;
import bg.tuplovdiv.apigateway.exception.*;
import bg.tuplovdiv.apigateway.security.jwt.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class RestClient {

    private final HttpClient client;
    private final ObjectMapper mapper;
    private final JwtProvider jwtProvider;

    public RestClient(JwtProvider jwtProvider) {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.jwtProvider = jwtProvider;
    }

    protected <T> T get(HttpRequest request, ResponseHandler<T> handler) {
        return execute(request, handler);
    }

    protected <T> T post(HttpRequest request, ResponseHandler<T> handler) {
        return execute(request, handler);
    }

    protected <T> T put(HttpRequest request, ResponseHandler<T> handler) {
        return execute(request, handler);
    }

    protected <T> T delete(HttpRequest request, ResponseHandler<T> handler) {
        return execute(request, handler);
    }

    private <T> T execute(HttpRequest request, ResponseHandler<T> handler) {
        HttpResponse<String> response = sendRequest(request);

        verifyResponse(response);

        return handler.handle(response);
    }

    private HttpResponse<String> sendRequest(HttpRequest request) {
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RemoteHostException("Unsuccessful request attempt to: " + request.uri());
        }

        return response;
    }

    protected void verifyResponse(HttpResponse<String> response) {
        boolean isValid = response != null && response.statusCode() >= 200 && response.statusCode() < 300;

        if (!isValid) {
            throw new BadRequestException("Request: " + (response != null ? response.request().uri() : null)
                    + " was unsuccessful. Status code returned: " + (response != null ? response.statusCode() : ""));
        }
    }

    protected HttpRequest.BodyPublisher createRequestBody(Object object) {
        return HttpRequest.BodyPublishers.ofString(mapObjectToJson(object));
    }

    protected URI buildURI(String path, String... components) {
        return URI.create(String.format(path, (Object[]) components));
    }

    protected <T> T mapJsonToObject(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new ResponseBodyParsingException("Error message: " + e.getMessage());
        }
    }

    protected String mapObjectToJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ConversionToJsonException("Error message: " + e.getMessage());
        }
    }

    protected String extractLocation(HttpResponse<String> response) {
        return response.headers().firstValue("Location")
                .orElseThrow(() -> new LocationHeaderNotFoundException("Response does not contain mandatory 'Location' header"));
    }

    protected String getBearerToken() {
        return "Bearer " + jwtProvider.provideToken();
    }
}
