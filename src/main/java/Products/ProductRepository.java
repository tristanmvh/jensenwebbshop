package Products;

import Customer.Customer;
import com.jensen.DatabaseHandler;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {

    public ProductRepository() throws SQLException {
    }

    //Hämta alla produkter i tabellen products
    public ArrayList<Product> getAllProducts() {
        try(Connection mysql = new DatabaseHandler().getConnection();) {
            ArrayList<Product> products = new ArrayList<Product>();

            String query = """
                    SELECT products.product_id,
                    products.manufacturer_id,
                    products.name,
                    products.description,
                    products.price, products.stock_quantity,
                    manufacturers.name
                    FROM products
                    INNER JOIN manufacturers
                    ON products.manufacturer_id = manufacturers.manufacturer_id
                    """;
            ResultSet rs = mysql.createStatement().executeQuery(query);

            while(rs.next()) {
                products.add(new Product(
                    rs.getInt("products.product_id"),
                    rs.getInt("products.manufacturer_id"),
                    rs.getString("products.name"),
                    rs.getString("products.description"),
                    rs.getString("manufacturers.name"),
                    rs.getInt("products.price"),
                    rs.getInt("products.stock_quantity")
                ));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> searchProduct(String searchQuery) {
        try(Connection mysql = new DatabaseHandler().getConnection();) {
            ArrayList<Product> products = new ArrayList<Product>();

            String query = """
                    SELECT products.product_id,
                    products.manufacturer_id,
                    products.name,
                    products.description,
                    products.price, products.stock_quantity,
                    manufacturers.name
                    FROM products
                    INNER JOIN manufacturers
                    ON products.manufacturer_id = manufacturers.manufacturer_id
                    WHERE products.name LIKE ?;
                    """;

            PreparedStatement stmt =  mysql.prepareStatement(query);
            stmt.setString(1, "%" + searchQuery + "%");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                products.add(new Product(
                        rs.getInt("products.product_id"),
                        rs.getInt("products.manufacturer_id"),
                        rs.getString("products.name"),
                        rs.getString("products.description"),
                        rs.getString("manufacturers.name"),
                        rs.getInt("products.price"),
                        rs.getInt("products.stock_quantity")
                ));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
