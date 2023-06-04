package bg.tuplovdiv.apigateway.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateOrderRequestDTO {

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

    public CreateOrderRequestDTO setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public CreateOrderRequestDTO setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CreateOrderRequestDTO setAddress(String address) {
        this.address = address;
        return this;
    }
}
