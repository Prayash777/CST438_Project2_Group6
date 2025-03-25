package com.cst438_project2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tier_entries")
public class TierEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user; // Foreign key to User entity

    @Column(name = "item_name", nullable = false, length = 255)
    private String itemName;

    @Column(name = "tier", nullable = false, length = 50)
    private String tier;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Categories category; // Foreign key to Categories entity

    public TierEntry() {}

    public TierEntry(User user, String itemName, String tier, Categories category) {
        this.user = user;
        this.itemName = itemName;
        this.tier = tier;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "TierEntry{" +
                "id=" + id +
                ", user=" + user +
                ", itemName='" + itemName + '\'' +
                ", tier='" + tier + '\'' +
                ", category=" + category +
                '}';
    }
}
