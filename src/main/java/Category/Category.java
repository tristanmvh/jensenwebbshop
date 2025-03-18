package Category;

import java.util.ArrayList;

import Product.Product;

public class Category {
    private int id;
    private String name;
    private ArrayList<Product> products;

    /* Denna constructor används om man bara vill ha namnet och id, exempelvis när vi hämtar listan av alla kategorier.
    För annars måste vi hämta alla produkter och det är onödigt och inte optimalt*/
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        products = new ArrayList<Product>();
    }
    //denna constructor används om man vill hämta produkter också
    public Category(int id, String name, ArrayList<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
