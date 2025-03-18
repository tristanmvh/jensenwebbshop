package Category;

import Product.ProductController;
import com.jensen.Menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CategoryController {
    private Menu menu;
    private Scanner scanner;
    private CategoryService service;
    private ProductController productController;
    public CategoryController() throws SQLException {
        scanner = new Scanner(System.in);
        service = new CategoryService();

    }

    public void run() throws SQLException {
        menu = new Menu(new String[] {"Lista alla kategorier", "Titta i kategori"});
        System.out.println("0. Tillbaka");

        String choice = scanner.nextLine();

        switch(choice.trim()) {
            case "1":
                ArrayList<Category> categories = service.getAllCategoryNames();
                for(int i = 0; i<categories.size(); i++) {
                    System.out.printf("%d. %s \n", categories.get(i).getId(), categories.get(i).getName());
                }
                this.run();
                break;
            case "2":
                System.out.println("Ange ID:t för kategorin du vill titta i.");
                System.out.print("ID: ");
                Category category = service.getCategory(Integer.parseInt(scanner.nextLine()));

                //Kolla om en kategori hittades
                if(category != null) {
                    //printa produkt(er)
                    for(int i = 0; i<category.getProducts().size(); i++) {
                        productController = new ProductController();
                        productController.printProduct(category.getProducts().get(i));
                    }
                } else {
                    System.out.println("Kategorin existerar inte. Försök igen.");
                }
                this.run();
                break;
            //Tillbaka till ProductController
            case "0":
                productController = new ProductController();
                productController.run();
                break;
            default:
                System.out.println("Ogiltigt val. Försök igen");
                this.run();
        }
    }
}
