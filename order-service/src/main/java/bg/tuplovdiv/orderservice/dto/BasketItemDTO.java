package bg.tuplovdiv.orderservice.dto;

import bg.tuplovdiv.orderservice.model.entity.MenuEntity;

public class BasketItemDTO {

    private MenuEntity menu;
    private Integer count;

    public MenuEntity getMenu() {
        return menu;
    }

    public BasketItemDTO setMenu(MenuEntity menu) {
        this.menu = menu;
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
