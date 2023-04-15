package bg.tuplovdiv.apigateway.dto;

import java.util.UUID;

public class CreateOrderRequest {
    public static final String CLIENT_ID_JSON_PROPERTY = "clientId";
    public static final String CLIENT_PHONE_NUMBER_JSON_PROPERTY = "clientPhoneNumber";
    public static final String ADDRESS_JSON_PROPERTY = "address";

    private UUID clientId;
    private String clientPhoneNumber;
    private String address;

    public UUID getClientId() {
        return clientId;
    }

    public CreateOrderRequest setClientId(UUID clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public CreateOrderRequest setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CreateOrderRequest setAddress(String address) {
        this.address = address;
        return this;
    }

}
