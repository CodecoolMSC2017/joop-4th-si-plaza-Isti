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

    public Map<Long, ShopEntry> getProducts() {
        return products;
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
        checkIfProductExists(product);
        ShopEntry entry = new ShopEntry(product, quantity, price);
        long barcode = product.getBarcode();
        products.put(barcode, entry);
    }

    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException {
        checkIfOpen();
        checkIfProductExists(barcode);
        products.get(barcode).increaseQuantity(quantity);
    }

    public Product buyProduct(long barcode) throws NoSuchProductException, ShopIsClosedException, OutOfStockException {
        checkIfOpen();
        checkIfProductExists(barcode);
        checkIfInStock(barcode);
        ShopEntry entry = products.get(barcode);
        entry.decreaseQuantity(1);
        return entry.getProduct();
    }

    public List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        checkIfOpen();
        checkIfProductExists(barcode);
        checkIfInStock(barcode, quantity);
        List<Product> result = new ArrayList<>();
        ShopEntry entry = products.get(barcode);
        for (int i = 0; i < quantity; i++) {
            result.add(entry.getProduct());
        }
        entry.decreaseQuantity(quantity);
        return result;
    }

    private void checkIfOpen() throws ShopIsClosedException {
        if (!isOpen) {
            throw new ShopIsClosedException();
        }
    }

    private void checkIfInStock(long barcode, int quantity) throws OutOfStockException {
        Product product = products.get(barcode).getProduct();
        if (product == null || products.get(barcode).getQuantity() < quantity) {
            throw new OutOfStockException();
        }
    }

    private void checkIfInStock(long barcode) throws OutOfStockException {
        Product product = products.get(barcode).getProduct();
        if (product == null || products.get(barcode).getQuantity() == 0) {
            throw new OutOfStockException();
        }
    }

    private void checkIfProductExists(long barcode) throws NoSuchProductException {
        if (products.get(barcode) == null) {
            throw new NoSuchProductException();
        }
    }

    private void checkIfProductExists(Product product) throws ProductAlreadyExistsException {
        Product currentProduct;
        for (Long barcode : products.keySet()) {
            currentProduct = products.get(barcode).getProduct();
            if (currentProduct.equals(product)) {
                throw new ProductAlreadyExistsException();
            }
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
