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

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private UserEntity owner;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<BasketItemEntity> items;

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

    public UserEntity getOwner() {
        return owner;
    }

    public BasketEntity setOwner(UserEntity owner) {
        this.owner = owner;
        return this;
    }

    public Set<BasketItemEntity> getItems() {
        return items;
    }

    public BasketEntity setItems(Set<BasketItemEntity> items) {
        this.items = items;
        return this;
    }
}
