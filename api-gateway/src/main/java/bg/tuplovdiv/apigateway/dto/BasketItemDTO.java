package bg.tuplovdiv.apigateway.dto;

import java.util.UUID;

public class BasketItemDTO {

    private UUID menuId;
    private Integer count;

    public UUID getMenuId() {
        return menuId;
    }

    public Integer getCount() {
        return count;
    }

}
