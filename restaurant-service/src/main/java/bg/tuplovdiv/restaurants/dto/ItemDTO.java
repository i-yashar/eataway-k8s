package bg.tuplovdiv.restaurants.dto;

import java.util.UUID;

public class ItemDTO {

    private UUID itemId;
    private String name;
    private Integer quantity;

    public UUID getItemId() {
        return itemId;
    }

    public ItemDTO setItemId(UUID itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ItemDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
