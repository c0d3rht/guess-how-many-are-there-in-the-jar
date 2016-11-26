package com.hariidaran;

import java.util.Random;

public class Jar {
    private String mItemName;
    private Random mGenerator;
    private int mItems;
    private int mMaxItems;

    public Jar() {
        mGenerator = new Random();
    }

    // -HELPER-METHODS--------------------------------------------

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public int getItems() {
        return mItems;
    }

    public int getMaxItems() {
        return mMaxItems;
    }

    public void setMaxItems(int maxItems) {
        mMaxItems = maxItems;
    }

    // -MAIN-METHODS----------------------------------------------

    // Fills the jar with a random number of items between 1 and the maximum number of items
    public void fill() {
        mItems = mGenerator.nextInt(mMaxItems) + 1;
    }
}