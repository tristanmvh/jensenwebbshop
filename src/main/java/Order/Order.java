package Order;

import Customer.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;



public class Order {

    private int id;
    private Customer customer;
    private ArrayList<Object[]> products;
    //Format:   produkt (Product) - antal (int) - styckpris (int)
    private LocalDate orderDate;


    public Order(int id, Customer customer, ArrayList<Object[]> products, LocalDate orderDate) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.orderDate = orderDate;
    }

    Customer getCustomer() {
        return customer;
    }

    ArrayList<Object[]> getProducts() {
        return products;
    }

    LocalDate getOrderDate() {
        return orderDate;
    }

    //Beräkna orderns totalkostnad
    int getTotal() throws SQLException {
        return new OrderService().getOrderTotal(this);
    }

    public int getId() {
        return id;
    }
}
