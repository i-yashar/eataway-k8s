package bg.tuplovdiv.orderservice.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "basket_items")
public class BasketItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID menuId;

    private Integer count;

    public Long getId() {
        return id;
    }

    public BasketItemEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BasketItemEntity setMenuId(UUID menuId) {
        this.menuId = menuId;
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
