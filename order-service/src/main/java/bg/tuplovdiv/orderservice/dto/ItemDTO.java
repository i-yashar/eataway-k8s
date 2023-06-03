package bg.tuplovdiv.orderservice.dto;

public class ItemDTO {

    private MenuDTO menu;
    private Integer count;

    public MenuDTO getMenu() {
        return menu;
    }

    public ItemDTO setMenu(MenuDTO menu) {
        this.menu = menu;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public ItemDTO setCount(Integer count) {
        this.count = count;
        return this;
    }
}
