package bg.tuplovdiv.apigateway.dto;

import jakarta.validation.constraints.NotBlank;

public class DeliveryInfoDTO {

    @NotBlank
    private String clientPhoneNumber;

    @NotBlank
    private String address;

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public DeliveryInfoDTO setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public DeliveryInfoDTO setAddress(String address) {
        this.address = address;
        return this;
    }
}
