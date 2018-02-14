package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.util.List;
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
            ShopImpl shop = selectShop(plaza);
            shopMenu(shop);
        } catch (PlazaIsClosedException e) {
            System.out.println(texts.plazaIsClosed);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(texts.noSuchShop);
        }
    }

    private void shopMenu(ShopImpl shop) {
        displayMenu(texts.shopMenuTitle(shop), texts.shopMenuOptions);
    }

    private void removeShop(PlazaImpl plaza) {
        try {
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
        final String noTitle = "";
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
                "Enter a shop by name",
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
    }
}
