package com.jensen;

import Admin.Admin;
import Customer.Customer;
import Customer.CustomerRepository;

import java.sql.SQLException;

public class LoginService {

    // Repositories för att hämta data från databasen
    private final CustomerRepository customerRepository;
    //private final Admin adminRepository;

    // Konstruktor som initierar repositories
    public LoginService() throws SQLException {
        this.customerRepository = new CustomerRepository();
        //this.adminRepository = new Admin();
    }

    /**
     * Loggar in en kund med e-post och lösenord.
     * Returnerar den inloggade kunden om lyckat, annars null.
     */
    public Customer loginAsCustomer(String email, String password) throws SQLException {
        // Hämta kunden från databasen baserat på e-post
        Customer customer = customerRepository.getCustomer(email);

        if (customer == null) {
            System.out.println("No customer found"); // Ingen kund hittades
        } else if (customer.getPassword().equals(password)) {
            System.out.println("Congrats you've logged in"); // Lyckad inloggning
            return customer;
        } else {
            System.out.println("Wrong password"); // Felaktigt lösenord
        }

        return null; // Returnera null om inloggningen misslyckas
    }

    /**
     * Loggar in en admin med namn och lösenord.
     * (Ej implementerad ännu)
     */
/*    public void loginAsAdmin(String name, String password) {
        System.out.println("Admin login not implemented yet."); // Admin-inloggning saknas
    }*/
}