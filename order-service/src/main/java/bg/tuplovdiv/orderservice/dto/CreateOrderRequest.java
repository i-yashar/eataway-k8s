package bg.tuplovdiv.orderservice.dto;

import bg.tuplovdiv.orderservice.validation.order.ValidCreateOrderRequest;

@ValidCreateOrderRequest
public class CreateOrderRequest {
    public static final String CLIENT_ID_JSON_PROPERTY = "clientId";
    public static final String CLIENT_PHONE_NUMBER_JSON_PROPERTY = "clientPhoneNumber";
    public static final String DELIVERY_DRIVER_ID_JSON_PROPERTY = "deliveryDriverId";
    public static final String ADDRESS_JSON_PROPERTY = "address";
    public static final String BASKET_ID_JSON_PROPERTY = "basketId";

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
