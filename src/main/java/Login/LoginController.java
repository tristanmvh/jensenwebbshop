package Login;

import java.rmi.UnexpectedException;
import java.sql.SQLException;
import java.util.Scanner;
import Customer.Customer;
import Customer.CustomerService;

public class LoginController {

    LoginService loginService;
    Customer customer;
    CustomerService customerService;

    // Scanner för användarinput
    Scanner scanner;

    public LoginController() throws SQLException {
        // Skapa instanser av nödvändiga objekt
        this.loginService = new LoginService();
        this.scanner = new Scanner(System.in);
        this.customerService = new CustomerService();
    }

    public Customer run() {
        while (true) {
            try {
                System.out.println("\n=== Logga in ===");
                System.out.println("1. Logga in som kund");
                System.out.println("2. Registrera kund");
                System.out.println("0. Avsluta");
                System.out.print("Val: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    //Logga in
                    case "1":
                        System.out.print("E-postadress: ");
                        String email = scanner.nextLine();
                        System.out.print("Lösenord: ");
                        String password = scanner.nextLine();
                        customer = loginService.loginAsCustomer(email, password);
                        return customer;
                    //Registrera
                    case "2":
                        //Personuppgifter
                        System.out.print("För- och efternamn: ");
                        String customerName = scanner.nextLine();
                        System.out.print("Mejladress: ");
                        String customerEmail = scanner.nextLine();
                        System.out.print("Telefonnummer: ");
                        String customerPhone = scanner.nextLine();
                        System.out.print("Lösenord: ");
                        String customerPassword = scanner.nextLine();

                        //Adress
                        System.out.print("Gata: ");
                        String customerStreet = scanner.nextLine();
                        System.out.print("Postnummer: ");
                        String customerPostalCode = scanner.nextLine();
                        System.out.print("Stad: ");
                        String customerCity = scanner.nextLine();
                        //Fullständig adress
                        String customerAddress = String.format("%s %s %s", customerStreet, customerPostalCode, customerCity);
                        customer = new Customer(0, customerName, customerEmail, customerPassword, customerPhone, customerAddress);
                        String registrationResult = customerService.addCustomer(customer);
                        if(registrationResult.equals("success")) {
                            System.out.println("Kontot har registrerats.");
                            return customer;
                        } else if(registrationResult.equals("email-in-use")) {
                            System.out.println("E-postadressen används redan.");
                        } else {
                            throw new UnexpectedException("Okänt fel har inträffat");
                        }
                        break;
                    case "0":
                        System.exit(0);
                    default:
                        System.out.println("Ogiltigt val, försök igen");
                }
            } catch (SQLException e) {
                // Hanterar databasfel
                System.out.println("Ett fel uppstod vid databasanrop: " + e.getMessage());
            } catch (Exception e) {
                // Hanterar övriga fel (t.ex. felaktig input)
                System.out.println("Ett oväntat fel uppstod: " + e.getMessage());
                scanner.nextLine(); // Rensa scanner-bufferten vid felinmatning
            }
        }
    }
}