package bg.tuplovdiv.orderservice.dto;

import java.util.UUID;

public class BasketItemDTO {

    private UUID menuId;
    private Integer count;
    private String menuName;
    private UUID restaurantId;
    private String restaurantName;
    private String restaurantAddress;

    public UUID getMenuId() {
        return menuId;
    }

    public BasketItemDTO setMenuId(UUID menuId) {
        this.menuId = menuId;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public BasketItemDTO setCount(Integer count) {
        this.count = count;
        return this;
    }

    public String getMenuName() {
        return menuName;
    }

    public BasketItemDTO setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public BasketItemDTO setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public BasketItemDTO setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
        return this;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public BasketItemDTO setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
        return this;
    }
}
