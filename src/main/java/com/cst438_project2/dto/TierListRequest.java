package com.cst438_project2.dto;

public class TierListRequest {
    private int categoryId;
    private String sTier;
    private String aTier;
    private String bTier;
    private String cTier;
    private String dTier;

    // Getters and setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSTier() {
        return sTier;
    }

    public void setSTier(String sTier) {
        this.sTier = sTier;
    }

    public String getATier() {
        return aTier;
    }

    public void setATier(String aTier) {
        this.aTier = aTier;
    }

    public String getBTier() {
        return bTier;
    }

    public void setBTier(String bTier) {
        this.bTier = bTier;
    }

    public String getCTier() {
        return cTier;
    }

    public void setCTier(String cTier) {
        this.cTier = cTier;
    }

    public String getDTier() {
        return dTier;
    }

    public void setDTier(String dTier) {
        this.dTier = dTier;
    }
}
