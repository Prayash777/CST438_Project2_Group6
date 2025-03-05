package com.example.demo;

public class Item {
    private int itemId;
    private String itemName;

    public Item(int i, String n) {
        this.itemId = i;
        this.itemName = n;
    }

    public int getId() {
        return itemId;
    }

    public void setId(int id) {
        this.itemId = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
