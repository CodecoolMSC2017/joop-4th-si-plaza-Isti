package com.codecool.plaza.api;

import java.util.ArrayList;
import java.util.List;

public class PlazaImpl implements Plaza {

    private List<Shop> shops;
    private boolean isOpen;

    public PlazaImpl() {
        shops = new ArrayList<Shop>();
        isOpen = true;
    }

    public List<Shop> getShops() throws PlazaIsClosedException {
        if (!isOpen) {
            throw new PlazaIsClosedException();
        }
        return shops;
    }

    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {
        if (!isOpen) {
            throw new PlazaIsClosedException();
        }
        for (Shop sShop : shops) {
            if (sShop.equals(shop)) {
                throw new ShopAlreadyExistsException();
            }
        }
        shops.add(shop);
    }

    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {
        if (!isOpen) {
            throw new PlazaIsClosedException();
        }
        int index = 0;
        for (Shop sShop : shops) {
            if (sShop.equals(shop)) {
                shops.remove(index);
            }
            index++;
        }
        throw new NoSuchShopException();
    }

    public Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException {
        if (!isOpen) {
            throw new PlazaIsClosedException();
        }
        for (Shop shop : shops) {
            if (shop.getName().equals(name)) {
                return shop;
            }
        }
        throw new NoSuchShopException();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Shop shop : shops) {
            sb.append(shop.getName());
            sb.append(", ");
        }
        return "PlazaImpl{" +
                "shops=" + sb.toString() +
                "isOpen=" + isOpen +
                '}';
    }
}
