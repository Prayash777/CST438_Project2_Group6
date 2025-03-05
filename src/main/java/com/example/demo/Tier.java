package com.example.demo;

public class Tier {
    private int tierId;
    private String tier;

    public Tier (int i, String t){
        this.tierId = i;
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


