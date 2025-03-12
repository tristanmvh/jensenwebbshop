package Products;

import com.jensen.Menu;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    private Menu menu;
    private ProductService service = new ProductService();

    public ProductController() throws SQLException {
    }

    public void run() {

        Scanner scanner = new Scanner(System.in);
        String choice;

        menu = new Menu(new String[] {
                "Sök produkt",
                "Bläddra efter kategori",
                "Visa alla produkter",
                "Tillbaka till huvudmeny"
        });
        System.out.print("Val: ");
        choice = scanner.nextLine();

        menu.clearCommandLine();
        switch(choice) {
            //Sök
            case "1":
                this.searchProducts(scanner);
            break;
        }
    }

    private void searchProducts(Scanner scanner) {
        String choice;
        System.out.print("Sök: ");
        choice = scanner.nextLine();
        ArrayList<Product> result = service.SearchProduct(choice);

        if(!result.isEmpty()) {
            for(int i = 0; i<result.size(); i++) {
                Product product = result.get(i);
                System.out.printf("%s (%s)", product.getName(), product.getPrice());
                System.out.println("Beskrivning: " + product.getDescription());
                System.out.println("Tillverkare: " + product.getManufacturer());
                System.out.println("Artikelnummer: " + product.getId());
            }
            if(result.size() == 1) {

                System.out.println("Tryck på ENTER för att fortsätta ELLER skriv Lägg till [antal] om du vill lägga till varan i varukorgen");
                choice = scanner.nextLine();
                if(choice.trim().isEmpty()) {
                    //input tom, användaren vill fortsätta.
                    menu.clearCommandLine();
                    this.run();
                } else {
                    //inputen va inte tom användaren vill eventuellt lägga till varan i varukorgen
                    String[] command = choice.toLowerCase().split(" ");
                    if(command[0] + " " + command[1] == "lägg till") {
                        try {
                            int quantity = Integer.parseInt(command[2]);

                        } catch(IllegalArgumentException e) {
                            System.out.println("Hoppsan! Ogiltigt svar. Ignoreras \n");
                        }
                    }
                }
            } else {

            }
        }
    }
}
