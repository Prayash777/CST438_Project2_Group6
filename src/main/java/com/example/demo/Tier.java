package com.example.demo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tierId;
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
}


