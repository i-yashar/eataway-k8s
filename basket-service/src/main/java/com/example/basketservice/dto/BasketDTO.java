package com.example.basketservice.dto;

import java.util.Set;
import java.util.UUID;

public class BasketDTO {
    private UUID basketId;
    private String ownerId;
    private Set<ItemDTO> items;

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
}
