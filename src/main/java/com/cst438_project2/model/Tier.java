package com.cst438_project2.model;

import jakarta.persistence.*;


import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tier")
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private int tierId;

    @Column(name = "tier", nullable = false, unique = true)
    private String tier;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Tier() {}

    public Tier(String tier, User user, Item item){
        this.tier = tier;
        this.user = user;
        this.item = item;
    }

    public int getTierId() {
        return tierId;
    }

    public void setTierId(int id) {
        this.tierId = id;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}