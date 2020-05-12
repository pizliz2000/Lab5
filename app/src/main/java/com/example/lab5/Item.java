package com.example.lab5;


public class Item {
    private String mImageUrl;
    private boolean isNiceSelected=false;
    private boolean isBadSelected=false;
    public Item(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public boolean isNiceSelected() {
        return isNiceSelected;
    }

    public void setNiceSelected(boolean selected) {
        isNiceSelected = selected;
    }

    public boolean isBadSelected() {
        return isBadSelected;
    }

    public void setBadSelected(boolean badSelected) {
        isBadSelected = badSelected;
    }
}
