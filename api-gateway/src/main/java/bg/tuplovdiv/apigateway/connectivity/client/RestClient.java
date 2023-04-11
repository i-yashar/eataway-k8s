package bg.tuplovdiv.apigateway.connectivity.client;

import bg.tuplovdiv.apigateway.connectivity.handler.ResponseHandler;
import bg.tuplovdiv.apigateway.exception.BadRequestException;
import bg.tuplovdiv.apigateway.exception.ResponseBodyParsingException;
import bg.tuplovdiv.apigateway.exception.UnableToReachHostException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class RestClient {

    private final HttpClient client;
    private final ObjectMapper mapper;

    public RestClient() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected <T> T get(HttpRequest request, ResponseHandler<T> handler) {
        HttpResponse<String> response = sendRequest(request);

        verifyResponse(response);

        return handler.handle(response);
    }

    private HttpResponse<String> sendRequest(HttpRequest request) {
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new UnableToReachHostException("Unsuccessful request attempt to: " + request.uri());
        }

        return response;
    }

    protected void verifyResponse(HttpResponse<String> response) {
        boolean isValid = response != null && response.statusCode() >= 200 && response.statusCode() < 300;

        if(!isValid) {
            throw new BadRequestException("Request: " + (response != null ? response.request().uri() : null)
                            + " was unsuccessful. Status code returned: " + (response != null ? response.statusCode() : ""));
        }
    }

    protected <T> T mapJsonToObject(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new ResponseBodyParsingException("Error message: " + e.getMessage());
        }
    }

    public HttpRequest post() {
        return null;
    }
}
