package Order;
import Customer.Customer;
import com.jensen.MainMenu;
import com.jensen.Menu;
import Product.Product;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class OrderController {
    private Menu menu;
    private Scanner scanner;
    private OrderService service;
    private Customer customer;

    public OrderController(Customer customer) throws SQLException {
        this.customer = customer;
        service = new OrderService();
        scanner = new Scanner(System.in);

    }

    public void run() throws SQLException {
        menu = new Menu(new String[] {"Visa alla mina ordrar", "Sök     order (ordernummer)"});
        System.out.println("0. Tillbaka till huvudmeny");
        System.out.print("Val: ");
        String choice = scanner.nextLine();

        switch(choice.trim()) {
            //Visa alla mina ordrar
            case "1":

                ArrayList<Order> orders = service.getCustomerOrders(customer);
                if (orders != null) {


                    for (int i = 0; i < orders.size(); i++) {
                        Order order = orders.get(i);
                        int orderId = order.getId();
                        int orderTotal = order.getTotal();
                        LocalDate date = order.getOrderDate();
                        ArrayList<Object[]> products = order.getProducts();

                        System.out.printf("Order #%d %tF (%d SEK) \n", orderId, date, orderTotal);

                        //printa produkter
                        for (int j = 0; j < products.size(); j++) {
                            Object[] row = products.get(j);
                            Product product = (Product) row[0];
                            int quantity = (int) row[1];
                            int unitPrice = (int) row[2];
                            System.out.printf("%s %d SEK (Antal: %d)\n\n", product.getName(), unitPrice, quantity);
                        }
                        System.out.printf("Visar %d ordrar", orders.size());
                    }
                } else {
                    System.out.println("Du har inte gjort några odrar.");
                }
                System.out.println("Tryck på ENTER för att fortsätta.");
                choice = scanner.nextLine();
                this.run();
                break;
            //Visa specifik order
            case "2":
                Order order;

                System.out.println("Ange ditt ordernummer");
                System.out.print("Ordernummer: ");
                int orderId = Integer.parseInt(scanner.nextLine());

                order = service.getOrder(orderId);
                if(order.getTotal() > 0) {
                    int orderTotal = order.getTotal();
                    LocalDate date = order.getOrderDate();
                    ArrayList<Object[]> products = order.getProducts();
                    System.out.println(products.size());

                    System.out.printf("Order #%d %tF (%d SEK) \n", orderId, date, orderTotal);
                    //Printa produkter
                    for(int i = 0; i< products.size(); i++) {
                        Object[] row = products.get(i);
                        Product product = (Product) row[0];
                        int quantity = (int) row[1];
                        int unitPrice = (int) row[2];
                        System.out.printf("%s %d SEK (Antal: %d)\n\n", product.getName(), unitPrice, quantity);
                    }
                } else {
                    System.out.println("Ingen order hittades. Kontrollera ordernummer");
                }
                System.out.println("Tryck på ENTER för att fortsätta.");
                choice = scanner.next();
                this.run();
                break;
            case "0":
                new MainMenu().run();
                break;
            default:
                System.out.println("Ogiltigt val. Försök igen");
                this.run();
        }
    }
}
