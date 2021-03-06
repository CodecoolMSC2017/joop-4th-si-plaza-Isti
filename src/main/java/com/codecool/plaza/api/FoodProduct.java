package com.codecool.plaza.api;

import java.util.Date;

public class FoodProduct extends Product {

    private int calories;
    private Date bestBefore;

    public FoodProduct(String name, long barcode, String manufacturer, int calories, Date bestBefore) {
        super(name, barcode, manufacturer);
        this.calories = calories;
        this.bestBefore = bestBefore;
    }

    public boolean isStillConsumable() {
        return new Date().before(bestBefore);
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return "FoodProduct{" +
                "calories=" + calories +
                ", bestBefore=" + bestBefore +
                ", barcode=" + barcode +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}
