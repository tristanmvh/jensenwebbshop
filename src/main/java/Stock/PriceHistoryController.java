/*
package Stock;

import Products.Product;
import Products.ProductService;

import java.util.Scanner;

public class PriceHistoryController {
    private final PriceHistoryService priceHistoryService;
    private final ProductService productService;
    private final Scanner scanner;

    public PriceHistoryController(PriceHistoryService priceHistoryService,
                                  ProductService productService,
                                  Scanner scanner) {
        this.priceHistoryService = priceHistoryService;
        this.productService = productService;
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== PRISHISTORIK ===");
            System.out.println("1. Visa produktens prishistorik");
            System.out.println("2. Tillbaka");
            System.out.print("Val: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> showProductPriceHistory();
                case 2 -> back = true;
            }
        }
    }

    private void showProductPriceHistory() {
        try {
            System.out.print("Ange produkt ID: ");
            Long productId = scanner.nextLong();

            Product product = productService.getProduct(productId);
            if (product == null) {
                System.out.println("Ogiltigt produkt-ID");
                return;
            }
            public class PriceHistoryController {
                private final PriceHistoryService priceHistoryService;
                private final ProductService productService;
                private final Scanner scanner;

                public PriceHistoryController(PriceHistoryService priceHistoryService,
                                              ProductService productService,
                                              Scanner scanner) {
                    this.priceHistoryService = priceHistoryService;
                    this.productService = productService;
                    this.scanner = scanner;
                }

                public void showMenu() {
                    boolean back = false;
                    while (!back) {
                        System.out.println("\n=== PRISHISTORIK ===");
                        System.out.println("1. Visa produktens prishistorik");
                        System.out.println("2. Tillbaka");
                        System.out.print("Val: ");

                        int choice = scanner.nextInt();
                        switch (choice) {
                            case 1 -> showProductPriceHistory();
                            case 2 -> back = true;
                        }
                    }
                }

                private void showProductPriceHistory() {
                    try {
                        System.out.print("Ange produkt ID: ");
                        Long productId = scanner.nextLong();

                        Product product = productService.getProduct(productId);
                        if (product == null) {
                            System.out.println("Ogiltigt produkt-ID");
                            return;
                        }

                        List<PriceHistory> history = priceHistoryService.getPriceHistory(productId);

                        System.out.printf("\nPrisutveckling för %s:%n", product.getName());
                        history.forEach(h -> System.out.printf(
                                "[%s] %.2f kr → %.2f kr%n",
                                h.getChangeDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                                h.getOldPrice(),
                                h.getNewPrice()
                        ));

                    } catch (Exception e) {
                        System.out.println("Fel: " + e.getMessage());
                    }
                }
            }*/
