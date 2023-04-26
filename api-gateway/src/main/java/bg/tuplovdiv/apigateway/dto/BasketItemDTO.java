package bg.tuplovdiv.apigateway.dto;

import java.util.UUID;

public class BasketItemDTO {

    private UUID menuId;
    private Integer count;

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
}
