package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CmdProgram {

    private List<Product> cart;
    private Texts texts = new Texts();
    private Scanner userInput = new Scanner(System.in);

    public CmdProgram(String[] args) {
    }

    public void run() {
        label:
        while (true) {
            displayMenu(texts.mainMenuTitle, texts.mainMenuOptions);
            String choice = userInput.nextLine();
            switch (choice) {
                case "0":
                    break label;
                case "1":
                    PlazaImpl plaza = createNewPlaza();
                    plazaMenu(plaza);
                    break label;
                default:
                    System.out.println(texts.unknownCommandMessage);
                    break;
            }
        }
        System.out.println(texts.exitMessage);
        System.exit(0);
    }

    private void plazaMenu(PlazaImpl plaza) {
        label:
        while (true) {
            displayMenu(texts.plazaMenuTitle(plaza), texts.plazaMenuOptions);
            String choice = userInput.nextLine();
            switch (choice) {
                case "0":
                    break label;
                case "1":
                    listShops(plaza);
                    break;
                case "2":
                    addNewShop(plaza);
                    break;
                case "3":
                    removeShop(plaza);
                    break;
                case "4":
                    enterShop(plaza);
                    break;
                case "5":
                    openPlaza(plaza);
                    break;
                case "6":
                    closePlaza(plaza);
                    break;
                case "7":
                    checkIfOpen(plaza);
                    break;
                default:
                    System.out.println(texts.unknownCommandMessage);
                    break;
            }
        }
    }

    private void enterShop(PlazaImpl plaza) {
        try {
            if (plaza.getShops().size() == 0) {
                System.out.println(texts.noShops);
                return;
            }
            ShopImpl shop = selectShop(plaza);
            shopMenu(shop);
        } catch (PlazaIsClosedException e) {
            System.out.println(texts.plazaIsClosed);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(texts.noSuchShop);
        }
    }

    private void shopMenu(ShopImpl shop) {
        label:
        while (true) {
            displayMenu(texts.shopMenuTitle(shop), texts.shopMenuOptions);
            String choice = userInput.nextLine();
            switch (choice) {
                case "0":
                    break label;
                case "1":
                    listProducts(shop);
                    break;
                case "2":
                    findProductByName(shop);
                    break;
                case "3":
                    displayShopOwner(shop);
                    break;
                case "4":
                    openShop(shop);
                    break;
                case "5":
                    closeShop(shop);
                    break;
                case "6":
                    addNewProduct(shop);
                    break;
                case "7":
                    addExistingProduct(shop);
                    break;
                case "8":
                    buyProduct(shop);
                    break;
                default:
                    System.out.println(texts.unknownCommandMessage);
                    break;
            }
        }
    }

    private void buyProduct(ShopImpl shop) {
        System.out.println(texts.enterBarcode);
        Long barcode = Long.parseLong(userInput.nextLine());

        System.out.println(texts.enterQuantity);
        int quantity = Integer.parseInt(userInput.nextLine());

        try {
            if (quantity == 1) {
                cart.add(shop.buyProduct(barcode));
            } else if (quantity == 2) {
                cart.addAll(shop.buyProducts(barcode, quantity));
            }
        } catch (NoSuchProductException e) {
            System.out.println(texts.noSuchProduct);
        } catch (ShopIsClosedException e) {
            System.out.println(texts.shopIsClosed);
        } catch (OutOfStockException e) {
            System.out.println(texts.outOfStock);
        }
    }

    private void addExistingProduct(ShopImpl shop) {
        System.out.println(texts.enterBarcode);
        Long barcode = Long.parseLong(userInput.nextLine());

        System.out.println(texts.enterQuantity);
        int quantity = Integer.parseInt(userInput.nextLine());

        try {
            shop.addProduct(barcode, quantity);
        } catch (NoSuchProductException e) {
            System.out.println(texts.noSuchProduct);
        } catch (ShopIsClosedException e) {
            System.out.println(texts.shopIsClosed);
        }
    }

    private void addNewProduct(ShopImpl shop) {
        Product product;
        while (true) {
            System.out.println(texts.chooseProductType);
            String choice = userInput.nextLine();
            switch (choice) {
                case "1":
                    product = createFoodProduct();
                    break;
                case "2":
                    product = createClothingProduct();
                    break;
                default:
                    System.out.println(texts.unknownCommandMessage);
                    continue;
            }
            if (product != null) {
                try {
                    addProduct(shop, product);
                } catch (ProductAlreadyExistsException e) {
                    System.out.println(texts.productAlreadyExists);
                } catch (ShopIsClosedException e) {
                    System.out.println(texts.shopIsClosed);
                }
            }
            break;
        }

    }

    private Product createClothingProduct() {
        System.out.println(texts.enterProductName);
        String name = userInput.nextLine();

        System.out.println(texts.enterBarcode);
        Long barcode = Long.parseLong(userInput.nextLine());

        System.out.println(texts.enterManufacturer);
        String manufacturer = userInput.nextLine();

        System.out.println(texts.enterMaterial);
        String material = userInput.nextLine();

        System.out.println(texts.enterType);
        String type = userInput.nextLine();

        return new ClothingProduct(name, barcode, manufacturer, material, type);
    }

    private void addProduct(ShopImpl shop, Product product) throws ProductAlreadyExistsException, ShopIsClosedException {
        System.out.println(texts.enterQuantity);
        int quantity = Integer.parseInt(userInput.nextLine());

        System.out.println(texts.enterPrice);
        float price = Float.parseFloat(userInput.nextLine());

        shop.addNewProduct(product, quantity, price);
    }

    private Product createFoodProduct() {
        System.out.println(texts.enterProductName);
        String name = userInput.nextLine();

        System.out.println(texts.enterBarcode);
        Long barcode = Long.parseLong(userInput.nextLine());

        System.out.println(texts.enterManufacturer);
        String manufacturer = userInput.nextLine();

        System.out.println(texts.enterCalories);
        int calories = Integer.parseInt(userInput.nextLine());

        System.out.println(texts.enterBestBefore);
        String date = userInput.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date bestBefore;
        try {
            bestBefore = sdf.parse(date);
        } catch (ParseException e) {
            System.out.println(texts.wrongInput);
            return null;
        }

        return new FoodProduct(name, barcode, manufacturer, calories, bestBefore);
    }

    private void findProductByName(ShopImpl shop) {
        System.out.println(texts.enterProductName);
        List<Product> products;
        try {
            products = shop.findByName(userInput.nextLine());
        } catch (ShopIsClosedException e) {
            System.out.println(texts.shopIsClosed);
            return;
        }
        if (products.size() == 0) {
            System.out.println(texts.noProducts);
            return;
        }
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private void displayShopOwner(ShopImpl shop) {
        System.out.println("Owner: " + shop.getOwner());
    }

    private void closeShop(ShopImpl shop) {
        shop.close();
        System.out.println(texts.shopIsClosed);
    }

    private void openShop(ShopImpl shop) {
        shop.close();
        System.out.println(texts.shopIsOpen);
    }

    private void listProducts(ShopImpl shop) {
        Map<Long, Product> products = shop.getProducts();
        for (Long barcode : products.keySet()) {
            System.out.println("Barcode: " + barcode + products.get(barcode));
        }
    }

    private void removeShop(PlazaImpl plaza) {
        try {
            if (plaza.getShops().size() == 0) {
                System.out.println(texts.noShops);
                return;
            }
            Shop shop = selectShop(plaza);
            plaza.removeShop(shop);
            System.out.println(texts.shopRemoved);
        } catch (PlazaIsClosedException e) {
            System.out.println(texts.plazaIsClosed);
        } catch (NoSuchShopException | IndexOutOfBoundsException e) {
            System.out.println(texts.noSuchShop);
        }
    }

    private ShopImpl selectShop(PlazaImpl plaza) throws PlazaIsClosedException, IndexOutOfBoundsException {
        List<ShopImpl> shops = plaza.getShops();
        String[] shopArray = new String[shops.size()];
        int counter = 0;
        for (Shop shop : shops) {
            shopArray[counter] = shop.toString();
            counter++;
        }
        while (true) {
            try {
                displayMenu(texts.selectShop, shopArray);
                String choice = userInput.nextLine();
                int choiceNumber = Integer.parseInt(choice);
                return shops.get(choiceNumber);
            } catch (NumberFormatException e) {
                System.out.println(texts.notANumber);
            }
        }
    }

    private void addNewShop(PlazaImpl plaza) {
        System.out.println(texts.enterShopName);
        String name = userInput.nextLine();
        System.out.println(texts.enterShopOwner);
        String owner = userInput.nextLine();
        ShopImpl shop = new ShopImpl(name, owner);
        try {
            plaza.addShop(shop);
        } catch (ShopAlreadyExistsException e) {
            System.out.println(texts.shopAlreadyExists);
            return;
        } catch (PlazaIsClosedException e) {
            System.out.println(texts.plazaIsClosed);
            return;
        }
        System.out.println(texts.shopAdded);
    }

    private void openPlaza(PlazaImpl plaza) {
        plaza.open();
        System.out.println(texts.plazaIsOpen);
    }

    private void closePlaza(PlazaImpl plaza) {
        plaza.close();
        System.out.println(texts.plazaIsClosed);
    }

    private void checkIfOpen(PlazaImpl plaza) {
        if (plaza.isOpen()) {
            System.out.println(texts.plazaIsOpen);
        } else {
            System.out.println(texts.plazaIsClosed);
        }
    }

    private void listShops(PlazaImpl plaza) {
        List<ShopImpl> shops;
        try {
            shops = plaza.getShops();
        } catch (PlazaIsClosedException e) {
            System.out.println(texts.plazaIsClosed);
            return;
        }
        if (shops.size() == 0) {
            System.out.println(texts.noShops);
        }
        for (Shop shop : shops) {
            System.out.println(shop);
        }
    }

    private PlazaImpl createNewPlaza() {
        displayMenu(texts.createPlazaTitle, texts.noOptions);
        String plazaName = userInput.nextLine();
        return new PlazaImpl(plazaName);
    }

    private void displayMenu(String title, String[] options) {
        System.out.println(title);
        int counter = 0;
        for (String option : options) {
            System.out.println(counter + ") " + option);
            counter++;
        }
    }

    class Texts {
        final String[] noOptions = new String[0];

        final String mainMenuTitle = "Welcome! You have to create a plaza to start.";
        final String[] mainMenuOptions = new String[]{
                "Exit",
                "Create new plaza"
        };

        final String exitMessage = "See you next time!";
        final String unknownCommandMessage = "Unknown command!";

        final String createPlazaTitle = "Name your plaza!";

        String plazaMenuTitle(PlazaImpl plaza) {
            return "You are in " + plaza.getName() + " plaza! Choose a command.";
        }

        final String[] plazaMenuOptions = new String[]{
                "Exit",
                "List all shops",
                "Add a new shop",
                "Remove an existing shop",
                "Enter a shop",
                "Open the plaza",
                "Close the plaza",
                "Check if the plaza is open or not"
        };

        String shopMenuTitle(ShopImpl shop) {
            return "You are in " + shop.getName() + " shop! Choose a command.";
        }

        final String[] shopMenuOptions = new String[]{
                "Back to plaza",
                "List available products",
                "Find products by name",
                "Display shop's owner",
                "Open shop",
                "Close shop",
                "Add new product to the shop",
                "Add existing products to the shop",
                "Buy a product by barcode"
        };

        final String plazaIsClosed = "Plaza is closed!";
        final String plazaIsOpen = "Plaza is open!";
        final String noShops = "There are no shops in the plaza.";
        final String shopAlreadyExists = "Shop already exists!";
        final String enterShopName = "Enter the name of the shop:";
        final String enterShopOwner = "Enter the owner of the shop:";
        final String shopAdded = "Shop added!";

        final String selectShop = "Select a shop:";
        final String notANumber = "Not a number!";
        final String noSuchShop = "There is no such shop!";
        final String shopRemoved = "Shop removed!";

        final String shopIsClosed = "Shop is closed!";
        final String shopIsOpen = "Shop is open!";
        final String enterProductName = "Enter the name of the product:";
        final String noProducts = "No products found";

        final String chooseProductType = "Choose a type: Food (1), Clothes (2)";

        final String enterBarcode = "Enter barcode:";
        final String enterManufacturer = "Enter manufacturer:";
        final String enterCalories = "Enter calories:";
        final String enterBestBefore = "Best before: (yyyy/mm/dd)";
        final String wrongInput = "Wrong input!";

        final String enterQuantity = "Enter quantity:";
        final String enterPrice = "Enter price:";

        final String productAlreadyExists = "Product already exists!";

        final String enterMaterial = "Enter material:";
        final String enterType = "Enter the type:";

        final String noSuchProduct = "There is no such product!";
        final String outOfStock = "Out of stock!";
    }
}
