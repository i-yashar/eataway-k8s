package bg.tuplovdiv.apigateway.dto;

import java.util.Set;
import java.util.UUID;

public class BasketDTO {
    private UUID basketId;
    private String ownerId;
    private Set<ItemDTO> items;
    private Double totalCost;

    public UUID getBasketId() {
        return basketId;
    }

    public BasketDTO setBasketId(UUID basketId) {
        this.basketId = basketId;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public BasketDTO setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public Set<ItemDTO> getItems() {
        return items;
    }

    public BasketDTO setItems(Set<ItemDTO> items) {
        this.items = items;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public BasketDTO setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }
}
