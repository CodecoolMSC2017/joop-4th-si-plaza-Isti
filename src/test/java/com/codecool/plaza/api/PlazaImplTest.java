package com.codecool.plaza.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlazaImplTest {

    private PlazaImpl plaza;

    @BeforeEach
    void setUp() {
        plaza = new PlazaImpl("testName");
    }

    @Test
    void addShop() throws ShopAlreadyExistsException, PlazaIsClosedException {
        ShopImpl shop = new ShopImpl("testName", "testOwner");

        plaza.close();
        assertThrows(PlazaIsClosedException.class, () -> plaza.addShop(shop));
        plaza.open();

        assertEquals(0, plaza.getShops().size());
        plaza.addShop(shop);
        assertEquals(shop, plaza.getShops().get(0));
        assertEquals(1, plaza.getShops().size());

        assertThrows(ShopAlreadyExistsException.class, () -> plaza.addShop(shop));
    }

    @Test
    void removeShop() throws ShopAlreadyExistsException, PlazaIsClosedException, NoSuchShopException {
        ShopImpl shop1 = new ShopImpl("testName1", "testOwner1");
        ShopImpl shop2 = new ShopImpl("testName2", "testOwner2");
        plaza.addShop(shop1);

        plaza.close();
        assertThrows(PlazaIsClosedException.class, () -> plaza.removeShop(shop1));
        plaza.open();

        assertEquals(1, plaza.getShops().size());
        plaza.removeShop(shop1);
        assertEquals(0, plaza.getShops().size());

        assertThrows(NoSuchShopException.class, () -> plaza.removeShop(shop1));

        assertEquals(0, plaza.getShops().size());
        plaza.addShop(shop1);
        plaza.addShop(shop2);
        assertEquals(2, plaza.getShops().size());

        plaza.removeShop(shop1);
        assertEquals(1, plaza.getShops().size());
        assertEquals(shop2, plaza.getShops().get(0));
    }

    @Test
    void findShopByName() throws ShopAlreadyExistsException, PlazaIsClosedException, NoSuchShopException {
        ShopImpl shop1 = new ShopImpl("testName1", "testOwner1");
        ShopImpl shop2 = new ShopImpl("testName2", "testOwner2");

        plaza.addShop(shop1);
        plaza.addShop(shop2);

        assertEquals(shop1, plaza.findShopByName("testName1"));
        assertThrows(NoSuchShopException.class, () -> plaza.findShopByName("totallyRandomName"));
        assertEquals(shop2, plaza.findShopByName("testName2"));
    }

    @Test
    void testOpenClose() {
        assertTrue(plaza.isOpen());

        plaza.close();
        assertFalse(plaza.isOpen());

        plaza.open();
        assertTrue(plaza.isOpen());
    }

}