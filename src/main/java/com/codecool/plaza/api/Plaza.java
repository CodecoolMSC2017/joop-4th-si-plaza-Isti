package com.codecool.plaza.api;

import java.util.List;

public interface Plaza {

    public List<ShopImpl> getShops() throws PlazaIsClosedException;

    public void addShop(ShopImpl shop) throws ShopAlreadyExistsException, PlazaIsClosedException;

    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException;

    public Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException;

    public boolean isOpen();

    public void open();

    public void close();

    public String toString();
}
