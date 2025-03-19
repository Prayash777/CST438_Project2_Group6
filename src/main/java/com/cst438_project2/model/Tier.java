package com.cst438_project2.model;

import javax.persistence.*;

@Entity
@Table(name = "Tier")
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private int tierId;

    @Column(nullable = false, unique = true)
    private String tier;

    public Tier() {}

    public Tier(String t){
        this.tier = t;
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
    
    @Override
    public String toString() {
        return "Tier{" +
                "tierId=" + tierId +
                ", tier='" + tier + '\'' +
                '}';
    }
}


