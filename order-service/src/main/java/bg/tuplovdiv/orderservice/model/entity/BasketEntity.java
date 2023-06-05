package bg.tuplovdiv.orderservice.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "baskets")
public class BasketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID externalId;

    @Column(unique = true)
    private String owner;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<ItemEntity> items;

    public Long getId() {
        return id;
    }

    public BasketEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public BasketEntity setExternalId(UUID externalId) {
        this.externalId = externalId;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public BasketEntity setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public BasketEntity setItems(Set<ItemEntity> items) {
        this.items = items;
        return this;
    }
}
