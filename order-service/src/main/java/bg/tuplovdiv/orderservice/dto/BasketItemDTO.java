package bg.tuplovdiv.orderservice.dto;

public class BasketItemDTO {

    private MenuDTO menu;
    private Integer count;

    public MenuDTO getMenu() {
        return menu;
    }

    public BasketItemDTO setMenu(MenuDTO menu) {
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
