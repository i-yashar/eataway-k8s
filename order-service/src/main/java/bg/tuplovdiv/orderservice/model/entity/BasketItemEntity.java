package bg.tuplovdiv.orderservice.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "basket_items")
public class BasketItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MenuEntity menu;

    private Integer count;

    public Long getId() {
        return id;
    }

    public BasketItemEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public BasketItemEntity setMenu(MenuEntity menu) {
        this.menu = menu;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public BasketItemEntity setCount(Integer count) {
        this.count = count;
        return this;
    }
}
