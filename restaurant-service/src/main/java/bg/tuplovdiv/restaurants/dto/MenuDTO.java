package bg.tuplovdiv.restaurants.dto;

import java.util.Set;
import java.util.UUID;

public class MenuDTO {

    private UUID menuId;
    private String name;
    private String description;
    private Double price;
    private Set<ItemDTO> items;
    private UUID restaurantId;

    public UUID getMenuId() {
        return menuId;
    }

    public MenuDTO setMenuId(UUID menuId) {
        this.menuId = menuId;
        return this;
    }

    public String getName() {
        return name;
    }

    public MenuDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MenuDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public MenuDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Set<ItemDTO> getItems() {
        return items;
    }

    public MenuDTO setItems(Set<ItemDTO> items) {
        this.items = items;
        return this;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public MenuDTO setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }
}
