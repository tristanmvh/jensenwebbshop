package Cart;

import Product.Product;

import java.util.ArrayList;

public class Cart {

    private static ArrayList<Object[]> cart = new ArrayList<>();

    /*
        cart är av typen Object därför att man ska kunna lagra produkten (Product)
        och antalet (int) tillsammans på följande sett:
        [Product, antal].
     */

    public Cart() {
    }

    public ArrayList<Object[]> getCart() {
        return cart;
    }

    public void addToCart(Product product, int quantity) {
        cart.add(new Object[]{product, quantity});
    }

    //Hämta totalpriset för varukorgen

}
