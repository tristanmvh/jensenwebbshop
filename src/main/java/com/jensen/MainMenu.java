package com.jensen;

import Cart.CartController;
import Customer.Customer;
import Login.LoginController;
import Products.ProductController;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {

    private Menu menu;
    private LoginController loginController;
    private static Customer customer;
    private Menu mainMenu;
    private Scanner scanner;

    //Controllers
    private ProductController productController;
    private CartController cartController;

    public MainMenu(Customer customer) throws SQLException {
        productController = new ProductController();
        scanner = new Scanner(System.in);
        this.customer = customer;
    }
    public MainMenu() throws SQLException {
        productController = new ProductController();
        scanner = new Scanner(System.in);
    }

    public void run() throws SQLException {

        menu = new Menu("Huvudmeny", new String[] {
                "Bläddra produkter",
                "Lägg till vara i varukorg",
                "Visa varukorg / Lägg beställning",
        });
        System.out.print("val: ");
        String choice = scanner.nextLine();

        switch(choice) {
            case "1":
                productController.run();
            break;

            //Lägg till produkt via artikelnummer
            case "2":
                productController.addProduct();
                this.run();
            break;

            //Varukorg
            case "3":
                cartController = new CartController();
                cartController.run(customer);
            break;

            default:
                System.out.println("Ogiltigt val. Försök igen");
                this.run();
        }
    }
}
