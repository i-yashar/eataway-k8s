package bg.tuplovdiv.orderservice.dto;

import bg.tuplovdiv.orderservice.validation.order.ValidCreateOrderRequest;

@ValidCreateOrderRequest
public class CreateOrderRequest {

    private String clientId;
    private String clientPhoneNumber;
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
