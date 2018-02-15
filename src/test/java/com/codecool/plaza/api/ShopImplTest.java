package com.codecool.plaza.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopImplTest {

    private ShopImpl shop;
    private ClothingProduct product;

    @BeforeEach
    void setUp() {
        shop = new ShopImpl("testName", "testOwner");
        product = new ClothingProduct("cloth", 987123654L, "manu", "material", "shirt");
    }

    @Test
    void testOpenClose() {
        assertTrue(shop.isOpen());

        shop.close();
        assertFalse(shop.isOpen());

        shop.open();
        assertTrue(shop.isOpen());
    }

    @Test
    void findByName() throws ShopIsClosedException, ProductAlreadyExistsException {
        List<Product> products = shop.findByName("test");
        assertEquals(0, products.size());

        shop.addNewProduct(product, 2, 150);
        products = shop.findByName("cloth");
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));

        shop.close();
        assertThrows(ShopIsClosedException.class, () -> {
            shop.findByName("");
        });
    }

    @Test
    void hasProduct() throws ProductAlreadyExistsException, ShopIsClosedException {
        shop.addNewProduct(product, 2, 150);

        assertTrue(shop.hasProduct(987123654L));

        shop.close();
        assertThrows(ShopIsClosedException.class, () -> {
            shop.hasProduct(987123654L);
        });
    }

    @Test
    void addNewProduct() throws ProductAlreadyExistsException, ShopIsClosedException {
        shop.addNewProduct(product, 2, 150);

        assertThrows(ProductAlreadyExistsException.class, () -> {
            shop.addNewProduct(product, 50, 2000);
        });

        shop.close();
        assertThrows(ShopIsClosedException.class, () -> {
            shop.addNewProduct(product, 2, 150);
        });
    }

    @Test
    void addProduct() throws ProductAlreadyExistsException, ShopIsClosedException, NoSuchProductException {
        shop.addNewProduct(product, 2, 150);
        assertEquals(1, shop.findByName("cloth").size());
        assertThrows(NoSuchProductException.class, () -> {
            shop.addProduct(123456789L, 1);
        });
        assertEquals(2, shop.getProductsEntry().get(987123654L).getQuantity());

        shop.addProduct(987123654L, 3);
        assertEquals(5, shop.getProductsEntry().get(987123654L).getQuantity());

        shop.close();
        assertThrows(ShopIsClosedException.class, () -> {
            shop.addProduct(123456789L, 1);
        });
    }

    @Test
    void buyProduct() throws ProductAlreadyExistsException, ShopIsClosedException, NoSuchProductException, OutOfStockException {
        shop.addNewProduct(product, 1, 130);
        assertEquals(1, shop.getProductsEntry().get(987123654L).getQuantity());

        Product boughtProduct = null;
        boughtProduct = shop.buyProduct(987123654L);
        assertEquals(product, boughtProduct);
        assertEquals(0, shop.getProductsEntry().get(987123654L).getQuantity());

        assertThrows(OutOfStockException.class, () -> {
            shop.buyProduct(987123654L);
        });

        assertThrows(NoSuchProductException.class, () -> {
            shop.buyProduct(123456789L);
        });

        shop.close();
        assertThrows(ShopIsClosedException.class, () -> {
            shop.buyProduct(987123654L);
        });
    }

    @Test
    void buyProducts() throws ProductAlreadyExistsException, ShopIsClosedException, NoSuchProductException, OutOfStockException {
        shop.addNewProduct(product, 3, 110);

        List<Product> boughtProducts = shop.buyProducts(987123654L, 1);
        assertEquals(product, boughtProducts.get(0));
        assertEquals(1, boughtProducts.size());

        assertThrows(OutOfStockException.class, () -> {
            shop.buyProducts(987123654L, 3);
        });

        boughtProducts = shop.buyProducts(987123654L, 2);
        assertEquals(product, boughtProducts.get(0));
        assertEquals(product, boughtProducts.get(1));
        assertEquals(2, boughtProducts.size());

        assertThrows(OutOfStockException.class, () -> {
            shop.buyProducts(987123654L, 1);
        });

        assertThrows(NoSuchProductException.class, () -> {
            shop.buyProducts(123456789L, 2);
        });

        shop.close();
        assertThrows(ShopIsClosedException.class, () -> {
            shop.buyProducts(987123654L, 2);
        });
    }

}