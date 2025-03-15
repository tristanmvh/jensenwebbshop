package com.jensen;

import Login.LoginController;
import Customer.Customer;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static LoginController loginController;
    static Customer customer;
    static MainMenu mainMenu;
    static Scanner scanner;
    public static void main(String[] args) throws SQLException {
        scanner = new Scanner(System.in);

        loginController = new LoginController();


        customer = loginController.run();

        if(customer != null) {
            mainMenu = new MainMenu(customer);
            mainMenu.run();
        }
    }
}