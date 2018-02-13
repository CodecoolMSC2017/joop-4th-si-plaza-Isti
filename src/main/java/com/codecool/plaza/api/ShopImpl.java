package com.codecool.plaza.api;

import java.util.*;

public class ShopImpl implements Shop {

    private String name;
    private String owner;
    private boolean isOpen = true;
    private Map<Long, ShopEntry> products = new HashMap<>();

    public ShopImpl(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

    public List<Product> findByName(String name) throws ShopIsClosedException {
        checkIfOpen();
        List<Product> result = new ArrayList<>();
        Product product;
        for (Long barcode : products.keySet()) {
            product = products.get(barcode).getProduct();
            if (product.getName().equals(name)) {
                result.add(product);
            }
        }
        return result;
    }

    public boolean hasProduct(long barcode) throws ShopIsClosedException {
        checkIfOpen();
        Set<Long> barcodes = products.keySet();
        for (Long bCode : barcodes) {
            if (bCode.equals(barcode)) {
                return true;
            }
        }
        return false;
    }

    public void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException {
        checkIfOpen();

    }

    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException {
        checkIfOpen();

    }

    public Product buyProduct(long barcode) throws NoSuchProductException, ShopIsClosedException {
        checkIfOpen();
        return null;
    }

    public List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        checkIfOpen();
        return null;
    }

    private void checkIfOpen() throws ShopIsClosedException {
        if (!isOpen) {
            throw new ShopIsClosedException();
        }
    }

    @Override
    public String toString() {
        return "ShopImpl{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", products=" + products +
                '}';
    }

    class ShopEntry {

        private Product product;
        private int quantity;
        private float price;

        ShopEntry(Product product, int quantity, float price) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void increaseQuantity(int amount) {
            quantity += amount;
        }

        public void decreaseQuantity(int amount) {
            quantity -= amount;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ShopEntry{" +
                    "product=" + product +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    '}';
        }
    }
}
