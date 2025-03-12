package Stock;

import java.sql.SQLException;
import java.util.Scanner;

public class StockController {

    private StockService stockService;
    private Scanner scanner;

        public StockController() {
            this.stockService = new StockService();
            this.scanner = new Scanner(System.in);
        }

        public void run() {
            while (true) {
                try {
                    System.out.println("=== Lagerhantering ===");
                    System.out.println("1. Kontrollera LagerSaldo");
                    System.out.println("2. Uppdatera LagerSaldo");
                    System.out.println("0. Avsluta");
                    System.out.println("Välj ett alternativ:");

                    String select = scanner.nextLine();

                    switch (select) {
                        case "1":
                            System.out.println("Ange Produkt ID: ");
                            int productId = scanner.nextInt();
                            System.out.println("Ange önskad kvantitet: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine(); // Rensa bufferten
                            stockService.getStock(productId, quantity);
                            break;

                        case "2":
                            System.out.println("Ange Produkt ID för uppdatering: ");
                            int updateId = scanner.nextInt();
                            System.out.println("Ange nytt lagersaldo: ");
                            int newQuantity = scanner.nextInt();
                            scanner.nextLine(); // Rensa bufferten
                            stockService.setStock(updateId, newQuantity);
                            break;

                        case "0":
                            System.out.println("Avslutar lagerhantering...");
                            return;

                        default:
                            System.out.println("Ogiltigt val, försök igen.");
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


































