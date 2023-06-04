package bg.tuplovdiv.orderservice.dto;

public class CreateOrderRequestDTO {
    private String clientId;
    private String clientPhoneNumber;
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
