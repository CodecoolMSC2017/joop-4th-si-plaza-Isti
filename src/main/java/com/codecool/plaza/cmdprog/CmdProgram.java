package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.PlazaImpl;
import com.codecool.plaza.api.Product;

import java.util.List;
import java.util.Scanner;

public class CmdProgram {

    private List<Product> cart;
    private Texts texts = new Texts();
    private Scanner userInput = new Scanner(System.in);

    public CmdProgram(String[] args) {
    }

    public void run() {
        while (true) {
            displayMenu(texts.mainMenuTitle, texts.mainMenuOptions);
            String choice = userInput.nextLine();
            if (choice.equals("0")) {
                break;
            } else if (choice.equals("1")) {
                PlazaImpl plaza = createNewPlaza();
                plazaMenu(plaza);
                break;
            } else {
                System.out.println(texts.unknownCommandMessage);
            }
        }
        System.out.println(texts.exitMessage);
        System.exit(0);
    }

    private void plazaMenu(PlazaImpl plaza) {
        while (true) {
            displayMenu(texts.plazaMenuTitle(plaza), texts.plazaMenuOptions);
            String choice = userInput.nextLine();
            if (choice.equals("0")) {
                break;
            } else if (choice.equals("1")) {
                break;
            } else if (choice.equals("2")) {
                break;
            } else if (choice.equals("3")) {
                break;
            } else if (choice.equals("4")) {
                break;
            } else if (choice.equals("5")) {
                break;
            } else if (choice.equals("6")) {
                break;
            } else if (choice.equals("7")) {
                break;
            } else {
                System.out.println(texts.unknownCommandMessage);
            }
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
            StringBuilder sb = new StringBuilder("You are in ");
            sb.append(plaza.getName());
            sb.append(" plaza! Choose a command.");
            return sb.toString();
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
    }
}
