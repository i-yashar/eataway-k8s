package com.example.basketservice.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MenuEntity menu;

    private Integer count;

    public Long getId() {
        return id;
    }

    public ItemEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public ItemEntity setMenu(MenuEntity menu) {
        this.menu = menu;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public ItemEntity setCount(Integer count) {
        this.count = count;
        return this;
    }
}
