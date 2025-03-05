package com.example.demo;

public class Categories {
    private int categoryId;
    private String categoryName;

    public Categories(int i, String n) {
        this.categoryId = i;
        this.categoryName = n;
    }

    public int getCategoryId() {
        return categoryId;
    } 

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
