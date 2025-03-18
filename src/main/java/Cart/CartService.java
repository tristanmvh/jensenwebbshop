package Cart;

import Product.Product;

import java.util.ArrayList;

public class CartService {
    private Cart cart;

    public CartService() {
        cart = new Cart();
    }

    public ArrayList<Object[]> getCart() {
        return cart.getCart();
    }

    public String addToCart(Product product, int quantity) {

        //Kolla så att kunden anger ett värde som är större än 0 men mindre eller lika antalet i lager.
        if(product.getStockQuantity() > 0) {
            if (quantity > 0 && quantity <= product.getStockQuantity()) {
                cart.addToCart(product, quantity);
                return "success";
            } else if (quantity < 1) {
                //Kunden har angett ett värde som är mindre än 1, Lägg inte till produkten i varukorgen.
                return "too-few";
            } else if (quantity > product.getStockQuantity()) {
                //Kunden försöker lägga till fler än vad det finns i lager. Lägg inte till något i varukorgen.
                return "too-many";
            } else {
                //Oväntat fel
                return "unexpected-error";
            }
        } else {
            return "not-in-stock";
        }
    }

    public int getTotal() {
        int total = 0;

        for(int i = 0; i<cart.getCart().size(); i++) {
            Product product = (Product) cart.getCart().get(i)[0];
            int productPrice = product.getPrice();
            int quantity = (int) cart.getCart().get(i)[1];
            total += productPrice*quantity;
        }
        return total;
    }
}
