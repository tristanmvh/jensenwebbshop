package Product;


import Category.CategoryController;
import com.jensen.MainMenu;
import com.jensen.Menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import Cart.CartService;

public class ProductController {
    private Menu menu;
    private final ProductService service;
    private final CartService cartService;
    private CategoryController categoryController;

    public ProductController() throws SQLException {
        service = new ProductService();
        cartService = new CartService();
    }

    public void run() throws SQLException {

        Scanner scanner = new Scanner(System.in);
        String choice;

        menu = new Menu(new String[] {
                "Sök produkt",
                "Bläddra efter kategori",
                "Visa alla produkter",
                "Visa alla tillgängliga produkter",
                "Tillbaka till huvudmeny"
        });
        System.out.print("Val: ");
        choice = scanner.nextLine();

        switch(choice) {
            //Sök
            case "1":
                this.searchProducts(scanner);
            break;

            //Bläddra efter kategori
            case "2":
                categoryController = new CategoryController();
                categoryController.run();
            break;

            //Visa alla produkter
            case "3":
                ArrayList<Product> products = service.getAllProducts();
                for(int i = 0; i<products.size(); i++) {
                    Product product = products.get(i);
                    //Printa namn, pris och antal i lager
                    System.out.printf("%s %d SEK (%d i lager) \n",
                            product.getName(),
                            product.getPrice(),
                            product.getStockQuantity()
                    );
                }
                System.out.println("Tryck på ENTER för att fortsätta");
                scanner.nextLine();
                this.run();
            break;

            case "4":
                products = service.getAllAvailableProducts();
                if(!products.isEmpty()) {
                    for(int i = 0; i<products.size(); i++) {
                        //Printa produkter
                        printProduct(products.get(i));
                    }
                } else {
                    System.out.println("Inga produkter är tillgängliga för tillfället.");
                }

                System.out.println("Tryck på ENTER för att fortsätta");
                scanner.nextLine();
                this.run();
                break;

            //Återgå till huvudmeny
            case "5":
                MainMenu mainMenu = new MainMenu();
                mainMenu.run();
            break;

            //Ogiltigt svar
            default:
                System.out.println("Ogiltigt svar, försök igen.");
                this.run();
        }
    }

    private void searchProducts(Scanner scanner) throws SQLException {
        String choice;
        System.out.print("Sök: ");
        choice = scanner.nextLine();
        ArrayList<Product> result = service.SearchProduct(choice);

        //produkt(er) hittades
        if(!result.isEmpty()) {
            //Printa funna produkter
            for(int i = 0; i<result.size(); i++) {
                printProduct(result.get(i));
            }
            //Om bara en produkt hittades och den finns i lager
            if(result.size() == 1 && result.getFirst().getStockQuantity() > 0) {
                Product product = result.getFirst();
                System.out.println("Tryck på ENTER för att fortsätta ELLER skriv Lägg till [antal] om du vill lägga till varan i varukorgen");
                choice = scanner.nextLine();
                if(choice.trim().isEmpty()) {
                    //input tom, användaren vill fortsätta.
                    this.run(); //Återgå till menyn
                } else {
                    //inputen va inte tom användaren vill eventuellt lägga till varan i varukorgen
                    String[] command = choice.toLowerCase().split(" ");
                    if("lägg till".equals(command[0] + " " + command[1])) {
                        try {
                            this.tryAddToCart(product, Integer.parseInt(command[2]));
                            this.run();
                        } catch(IllegalArgumentException e) {
                            System.out.println("Hoppsan! Ogiltigt svar. Ignoreras \n");
                            this.run();
                        }
                    }
                }
            //Om flera produkter hittades
            } else {
                System.out.println("Tryck på ENTER för att fortsätta");
                scanner.nextLine();
                this.run(); //Återgå till menyn
            }
        } else {
            System.out.println("Ingen produkt hittades. ");
            this.run();
        }
    }

    public void addProduct() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vänligen ange önskad produkts artikelnummer.");
        System.out.print("Artikelnummer: ");
        try {
            int productId = Integer.parseInt(scanner.nextLine());
            Product product = service.getProduct(productId);

            if(product != null) {
                if(product.getStockQuantity() > 0) {
                    //Produkt funnen
                    System.out.printf("Artikelnumret %d tillhör %s (%d SEK). Vill du lägga till produkten i varukorgen? JA/NEJ \n", productId, product.getName().toUpperCase(), product.getPrice());
                    System.out.print("Val: ");
                    String choice = scanner.nextLine();
                    switch (choice.trim().toUpperCase()) {
                        //trim() är en metod i String klassen som tar bort alla blanksteg som är i början och i slutet av en string.
                        case "JA":
                            System.out.printf("Ange hur många du vill lägga till i varukorgen (%d finns i lager) \n", product.getStockQuantity());
                            System.out.print("Val: ");
                            choice = scanner.nextLine();
                            try {
                                int quantity = Integer.parseInt(choice);
                                tryAddToCart(product, quantity);
                                this.run();
                            } catch (IllegalArgumentException e) {
                                System.out.println("Ogiltig inmatning. Avbryter.");
                            }
                            break;

                        case "NEJ":
                            System.out.print("Abryter.");
                            break;

                        default:
                            System.out.println("Ogiltigt svar, avbryter.");
                    }
                } else {
                    System.out.printf("Artikelnumret %d tillhör %s (%d SEK). Produkten finns dessvärre inte i lager för tillfället.", productId, product.getName().toUpperCase(), product.getPrice());
                    System.out.println("Tryck på ENTER för att fortsätta");
                    scanner.nextLine();
                }
            } else {
                //Ingen produkt funnen
                System.out.println("Ingen produkt hittades. Återgår till huvudmeny.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Ogiltig inmatning. Avbryter.");
        }

    }

    //Denna metod försöker lägga till x antal av angiven produkt och sedan berättar för användaren vad för svar den fick från CartService.
    private void tryAddToCart(Product product, int quantity) {
        String result = cartService.addToCart(product, quantity);
        switch(result) {
            case "success":
                System.out.println("Tillagt i varukorgen.");

                break;

            case "too-few":
                System.out.println("Fel! Antalet får inte lov att vara under 1. Avbryter");
                break;

            case "too-many":
                System.out.println("Fel! Det är inte tillåtet att ange ett större antal än vad där är i lager.");
                break;
            default:
                System.out.println("Ett oväntat fel har uppstått. Avbryter.");
        }
    }

    public void printProduct(Product product) {
        System.out.printf("%s (%s)", product.getName(), product.getPrice());
        System.out.println("Beskrivning: " + product.getDescription());
        System.out.println("Tillverkare: " + product.getManufacturer());
        System.out.println("Artikelnummer: " + product.getId());
        System.out.println("Antal i lager: " + product.getStockQuantity());
    }
}
