package com.codecool.plaza.api;

public class FoodProduct extends Product {

    private String material;
    private String type;

    public FoodProduct(long barcode, String manufacturer, String material, String type) {
        super(barcode, manufacturer);
        this.material = material;
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "FoodProduct{" +
                "material='" + material + '\'' +
                ", type='" + type + '\'' +
                ", barcode=" + barcode +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}