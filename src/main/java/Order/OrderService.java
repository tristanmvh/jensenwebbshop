package Order;

import java.sql.SQLException;
import java.util.ArrayList;

import Cart.Cart;
import Customer.Customer;
import Product.Product;
import Stock.StockService;

public class OrderService {

    private OrderRepository repository;
    private StockService stockService;

    public OrderService() throws SQLException {
        repository = new OrderRepository();
        stockService = new StockService();
    }

    public Order getOrder(int id) {
        return repository.getOrder(id);
    }

    public ArrayList<Order> getCustomerOrders(Customer customer) {
        return repository.getCustomerOrders(customer);
    }

    //Beräkna en orders totalkostnad
    public int getOrderTotal(Order order) {
        int total = 0;
        for(int i = 0; i<order.getProducts().size(); i++) {
            int unitPrice = (int) order.getProducts().get(i)[2];
            int quantity = (int) order.getProducts().get(i)[1];
            total += quantity*unitPrice;
        }
        return total;
    }

    //Hämta beställningar som tillhör en specifik kund
    /*ArrayList<Order> getUserOrders() {

    }*/

    public void createOrder(Cart cart, Customer customer) throws SQLException {
        ArrayList<Object[]> products = new ArrayList<Object[]>();

        //kolla så att varukorgen inte är tom
        if(!cart.getCart().isEmpty()) {
            //loopa genom varukorg
            for(int i = 0; i<cart.getCart().size(); i++) {
                Object[] row = cart.getCart().get(i);
                Product product = (Product) row[0];
                int productPrice = product.getPrice();
                int quantity = (int) row[1];

                //lägg till produkt i order
                products.add(new Object[] {product, quantity, productPrice});

                //uppdatera lagersaldot
                stockService.setStock(product, -quantity);
            }
            repository.createOrder(new Order(
                    0,
                    customer,
                    products,
                    null
            ));
        }
    }
}