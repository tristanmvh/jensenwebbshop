package Cart;

import Order.OrderService;
import Product.Product;
import Customer.Customer;
import com.jensen.MainMenu;
import com.jensen.Menu;

import java.sql.SQLException;
import java.util.Scanner;

public class CartController {
    private CartService cartService;
    private Menu menu;
    private MainMenu mainMenu;
    private Scanner scanner;
    private OrderService orderService;
    private Cart cart;

    public CartController() throws SQLException {
        this.cartService = new CartService();
        this.scanner = new Scanner(System.in);
        this.mainMenu = new MainMenu();
        this.orderService = new OrderService();
        this.cart = new Cart();
    }

    public void run(Customer customer) throws SQLException {
        this.printCart();
        menu = new Menu(new String[] {"Slutför beställning", "Tillbaka"});
        System.out.print("Val: ");
        String choice = scanner.nextLine();

        switch(choice) {
            //Lägg beställning
            case "1":
                orderService.createOrder(cart, customer);
                System.out.println("Din beställning har lagts! Återgår till huvudmeny.");
                mainMenu.run();
                break;

            //Tillbaka till huvudmeny.
            case "2":
                mainMenu.run();
                break;
            default:
                System.out.println("Ogiltigt val. Återgår till huvudmeny.");
                mainMenu.run();
                break;
        }
    }
    private void printCart() {
        for(int i=0; i<cartService.getCart().size(); i++) {
            Object[] row = cartService.getCart().get(i);
            Product product = (Product) row[0];
            int quantity = (int) row[1];
            System.out.printf("%s %d SEK (antal: %d) \n", product.getName(), product.getPrice(), quantity);
        }
        System.out.println("Totalt: " + cartService.getTotal() + " SEK");
    }
}
