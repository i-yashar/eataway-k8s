package bg.tuplovdiv.apigateway.dto;

public class DetailedBasketItemDTO {

    private MenuDTO menu;
    private Integer count;

    public MenuDTO getMenu() {
        return menu;
    }

    public DetailedBasketItemDTO setMenu(MenuDTO menu) {
        this.menu = menu;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public DetailedBasketItemDTO setCount(Integer count) {
        this.count = count;
        return this;
    }
}
