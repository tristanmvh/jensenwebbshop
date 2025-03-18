package Order;
    import Product.Product;
    import Product.ProductService;
    import com.jensen.DatabaseHandler;

    import java.sql.*;
    import java.time.LocalDate;
    import java.util.ArrayList;

    import Customer.Customer;
    import Customer.CustomerService;

public class OrderRepository {

    private CustomerService customerService;
    private ProductService productService;


    public OrderRepository () throws SQLException {
        this.customerService = new CustomerService();
        this.productService = new ProductService();
    }

    public Order getOrder(int id) {
        Order order;

        try(Connection mysql = new DatabaseHandler().getConnection()) {
            Customer customer = null;
            LocalDate orderDate = null;
            ArrayList<Object[]> products = new ArrayList<Object[]>();
            int total = 0;

            String query = "SELECT * FROM orders WHERE order_id = ?";
            PreparedStatement stmt = mysql.prepareStatement(query);

            stmt.setInt(1, id);
            ResultSet rsO = stmt.executeQuery();

            if(rsO.isBeforeFirst()) {
                while(rsO.next()) {
                    customer = customerService.getCustomer(rsO.getInt("customer_id"));
                    orderDate = rsO.getDate("order_date").toLocalDate();
                }

                //hämta produkter
                query = "SELECT * FROM orders_products WHERE order_id = ?";
                stmt = mysql.prepareStatement(query);
                stmt.setInt(1, id);
                ResultSet rsP = stmt.executeQuery();

                while(rsP.next()) {
                    int quantity = rsP.getInt("quantity");
                    int unitPrice = rsP.getInt("unit_price");
                    total += quantity*unitPrice;

                    products.add( new Object[]{
                            productService.getProduct(rsP.getInt("product_id")),
                            quantity,
                            unitPrice
                    });
                }
                order = new Order(id, customer, products, orderDate);
            } else {
                order = null; //ordern hittades inte
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;
    }

    public ArrayList<Order> getCustomerOrders(Customer customer) {
        ArrayList<Order> orders = new ArrayList<Order>();
        LocalDate orderDate = null;


        try(Connection mysql = new DatabaseHandler().getConnection()) {

            String query = "SELECT * FROM orders WHERE customer_id = ?";
            PreparedStatement stmt = mysql.prepareStatement(query);

            stmt.setInt(1, customer.getId());
            ResultSet rsO = stmt.executeQuery();

            if(rsO.isBeforeFirst()) {
                while(rsO.next()) {
                    int orderId = rsO.getInt("order_id");
                    orderDate = rsO.getDate("order_date").toLocalDate();

                    //hämta produkter för nuvarande order
                    query = "SELECT * FROM orders_products WHERE order_id = ?";
                    stmt = mysql.prepareStatement(query);
                    stmt.setInt(1, orderId);
                    ResultSet rsP = stmt.executeQuery();

                    while(rsP.next()) {
                        ArrayList<Object[]> products = new ArrayList<Object[]>();

                        products.add( new Object[]{
                                productService.getProduct(rsP.getInt("product_id")),
                                rsP.getInt("quantity"),
                                rsP.getInt("unit_price")
                        });
                        orders.add(new Order(orderId, customer, products, orderDate));
                    }

                }
            } else {
                return null; //inga ordrar hittades
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    public void createOrder(Order order) throws SQLException {
        int orderId = 0;

        try(Connection mysql = new DatabaseHandler().getConnection()) {
            //1. Lägg till rad i orders
            String query = "INSERT INTO orders VALUES(NULL, ?, NOW());";
            PreparedStatement stmt = mysql.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            //Statement.RETURN_GENERATED_KEYS gör det möjligt att hämta radens id.
            stmt.setInt(1, order.getCustomer().getId());
            stmt.executeUpdate();

            //2. Hämta radens id. Varför? För att vi behöver det när vi lägger in rader i orders_products
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                orderId = rs.getInt(1);
            }

            //3. lägg till produkterna som kunden vill beställa i tabellen products_orders
            query = "INSERT INTO orders_products(order_product_id, order_id, product_id, quantity, unit_price) VALUES(NULL, ?, ?, ?, ?)";

            for(int i = 0; i < order.getProducts().size(); i++) {
                Object[] row = order.getProducts().get(i);
                Product product = (Product) row[0];
                int quantity = (int) row[1];
                int unitPrice = (int) row[2];

                stmt = mysql.prepareStatement(query);
                stmt.setInt(1, orderId);
                stmt.setInt(2, product.getId());
                stmt.setInt(3, quantity);
                stmt.setInt(4, unitPrice);
                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
