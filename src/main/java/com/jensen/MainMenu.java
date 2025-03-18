package com.jensen;

import Cart.CartController;
import Customer.Customer;
import Customer.CustomerController;
import Login.LoginController;
import Order.Order;
import Order.OrderController;
import Product.ProductController;

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
    private CustomerController customerController;
    private OrderController orderController;

    /*När en instans av menyn skapas för första gången måste vi använda denna constructor
    för att ange vilken användare som är inloggad,
    då ett användare objekt behövs i vissa klasser för att kunna utföra vissa uppgifter. */
    public MainMenu(Customer customer) throws SQLException {
        productController = new ProductController();
        scanner = new Scanner(System.in);
        this.customer = customer;
    }
    public MainMenu() throws SQLException {
        scanner = new Scanner(System.in);
    }

    public void run() throws SQLException {

        menu = new Menu("Huvudmeny", new String[] {
                "Bläddra produkter",
                "Lägg till vara i varukorg",
                "Visa varukorg / Lägg order",
                "Mina ordrar",
                "Min profil"
        });
        System.out.print("val: ");
        String choice = scanner.nextLine();

        switch(choice.trim()) {
            case "1":
                productController = new ProductController();
                productController.run();
                break;

            //Lägg till produkt via artikelnummer
            case "2":
                productController = new ProductController();
                productController.addProduct();
                this.run();
                break;

            //Varukorg
            case "3":
                cartController = new CartController();
                cartController.run(customer);
                break;

            //Ordrar
            case "4":
                orderController = new OrderController(customer);
                orderController.run();
                break;

            //Kund profil
            case "5":
                customerController = new CustomerController(customer);
                customerController.run();
                break;


            default:
                System.out.println("Ogiltigt val. Försök igen");
                this.run();
        }
    }
}
