package Stock;

import java.sql.SQLException;
import java.util.Scanner;
import Product.ProductRepository;
import Product.ProductService;
import com.jensen.MainMenu;

public class StockController {

    private final StockService stockService;
    private final Scanner scanner;
    private ProductService productService;

        public StockController() {
            this.stockService = new StockService();
            this.scanner = new Scanner(System.in);
        }

        public void run() {
            while (true) {
                try {
                    System.out.println("=== Lagerhantering ===");
                    System.out.println("1. Kontrollera Lagersaldo");
                    System.out.println("2. Uppdatera Lagersaldo");
                    System.out.println("0. Tillbaka till huvudmeny");
                    System.out.println("Välj ett alternativ: ");

                    String select = scanner.nextLine();

                    switch (select) {
                        case "1":
                            System.out.println("Ange Produkt ID: ");
                            int productId = scanner.nextInt();

                            //kolla först om produkten existerar
                            if(productService.getProduct(productId) != null) {

                                System.out.print(productService.getProduct(productId).getName());
                                System.out.printf("%d st i lager \n", productService.getProduct(productId).getStockQuantity());
                            } else {
                                System.out.println("Produkt med angivet ID hittades inte. Försök igen.");
                            }

                            System.out.println("Ange önskad kvantitet: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine(); // Rensa bufferten
                            this.run();
                            break;

                        case "2":
                            System.out.println("Ange Produkt ID för uppdatering: ");
                            int updateId = scanner.nextInt();
                            if(productService.getProduct(updateId) != null) {
                                System.out.println("Ange nytt lagersaldo: ");
                                int newQuantity = scanner.nextInt();
                                scanner.nextLine(); // Rensa bufferten
                                stockService.setStock(productService.getProduct(updateId), newQuantity);
                                System.out.printf("Lagersaldo för %s har uppdaterats. Nytt värde: %d", productService.getProduct(updateId).getName(), productService.getProduct(updateId).getId());
                            } else {
                                System.out.println("Produkt med angivet ID hittades inte. Försök igen.");
                            }
                            this.run();
                            break;

                        case "0":
                            new MainMenu().run();
                            break;
                        default:
                            System.out.println("Ogiltigt val, försök igen.");
                            this.run();
                    }
                } catch (SQLException e) {
                    System.out.println("Databasfel: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Ett fel uppstod: " + e.getMessage());
                    scanner.nextLine(); // Rensa bufferten vid fel
                }
            }
        }
    }


































