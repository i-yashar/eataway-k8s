package bg.tuplovdiv.orderservice.order;

import bg.tuplovdiv.orderservice.dto.BasketDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderContext {
    private final UUID orderId;
    private final UUID clientId;
    private final String clientPhoneNumber;
    private final String address;
    private final BasketDTO basket;
    private final Double totalCost;
    private final LocalDateTime createdAt;

    private OrderContext(UUID orderId, UUID clientId, String clientPhoneNumber, String address, BasketDTO basket, Double totalCost, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.clientPhoneNumber = clientPhoneNumber;
        this.address = address;
        this.basket = basket;
        this.totalCost = totalCost;
        this.createdAt = createdAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public BasketDTO getBasket() {
        return basket;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private UUID orderId;
        private UUID clientId;
        private String clientPhoneNumber;
        private String address;
        private BasketDTO basket;
        private Double totalCost;
        private LocalDateTime createdAt;

        public Builder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder clientId(UUID clientId) {
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

        public Builder basket(BasketDTO basket) {
            this.basket = basket;
            return this;
        }

        public Builder totalCost(Double totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderContext build() {
            return new OrderContext(
                    this.orderId,
                    this.clientId,
                    this.clientPhoneNumber,
                    this.address,
                    this.basket,
                    this.totalCost,
                    this.createdAt
            );
        }
    }
}
