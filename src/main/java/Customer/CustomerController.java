package Customer;

import com.jensen.MainMenu;
import com.jensen.Menu;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerController {
    private Customer customer;
    private CustomerService service;
    private Menu menu;
    private Scanner scanner;


    public CustomerController(Customer customer) throws SQLException {
        this.customer = customer;
        scanner = new Scanner(System.in);
        service = new CustomerService();
    }

    public void run() throws SQLException {
        menu = new Menu(new String[] {"Visa mina kunduppgifter", "Ändra mina kunduppgifter"});
        System.out.println("0. Tillbaka till huvudmeny");
        System.out.print("Val: ");
        String choice = scanner.nextLine();

        switch(choice.trim()) {
            //Visa kunduppgifter
            case "1":
                System.out.println("Kundnummer: " + customer.getId());
                System.out.println("Namn:" + customer.getName());
                System.out.println("Adress: " + customer.getAddress());
                System.out.println("Mejladress: " + customer.getUsername());
                System.out.println("Telefonnummer: " + customer.getPhoneNumber());
                this.run();
                break;

            //Uppdatera kunduppgifter
            case "2":
                System.out.println("Lämna fälten tomma om du inte vill ändra dom!!");
                System.out.print("E-postadress: ");
                String username = scanner.nextLine().trim();

                System.out.print("Telefonnummer: ");
                String phone = scanner.nextLine().trim();

                System.out.print("Fullständigt adress: ");
                String address = scanner.nextLine().trim();

                String result = service.updateCustomerInformation(customer, username, phone, address);

                switch(result) {
                    case "success":
                        System.out.println("Dina uppgifter har uppdaterats");
                        break;

                    case "nothing-changed":
                        System.out.println("Ingenting har ändrats");
                        break;
                    default:
                        System.out.println("Ett fel uppstod.");
                }
                this.run();
                break;
            //Huvudmeny
            case "0":
                new MainMenu().run();
                break;
            default:
                System.out.println("Ogiltigt val. Försök igen");
                this.run();
        }
    }
}
