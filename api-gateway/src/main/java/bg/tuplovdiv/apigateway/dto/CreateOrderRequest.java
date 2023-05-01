package bg.tuplovdiv.apigateway.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateOrderRequest {
    public static final String CLIENT_ID_JSON_PROPERTY = "clientId";
    public static final String CLIENT_PHONE_NUMBER_JSON_PROPERTY = "clientPhoneNumber";
    public static final String ADDRESS_JSON_PROPERTY = "address";

    private String clientId;

    @NotBlank
    @Min(5)
    private String clientPhoneNumber;

    @NotBlank
    @Size(min = 5)
    private String address;

    public String getClientId() {
        return clientId;
    }

    public CreateOrderRequest setClientId(String clientId) {
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
