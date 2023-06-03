package bg.tuplovdiv.orderservice.messaging;

import bg.tuplovdiv.orderservice.dto.ItemDTO;

import java.util.Set;
import java.util.UUID;

public class OrderContext extends Message {
    private final UUID orderId;
    private final String clientId;
    private final String clientPhoneNumber;
    private final String address;
    private final Set<ItemDTO> items;
    private final Double totalCost;

    private OrderContext(UUID orderId, String clientId, String clientPhoneNumber, String address, Set<ItemDTO> items, Double totalCost) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.clientPhoneNumber = clientPhoneNumber;
        this.address = address;
        this.items = items;
        this.totalCost = totalCost;
    }

    public UUID getOrderId() {
        return this.orderId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public Set<ItemDTO> getItems() {
        return items;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private UUID orderId;
        private String clientId;
        private String clientPhoneNumber;
        private String address;
        private Set<ItemDTO> items;
        private Double totalCost;

        public Builder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientPhone(String clientPhone) {
            this.clientPhoneNumber = clientPhone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder items(Set<ItemDTO> items) {
            this.items = items;
            return this;
        }

        public Builder totalCost(Double totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public OrderContext build() {
            return new OrderContext(
                    this.orderId,
                    this.clientId,
                    this.clientPhoneNumber,
                    this.address,
                    this.items,
                    this.totalCost
            );
        }
    }
}
