package Category;

import com.jensen.DatabaseHandler;

import Product.Product;
import Product.ProductService;

import java.sql.*;
import java.util.ArrayList;

public class CategoryRepository {
    private ProductService productService;

    public CategoryRepository() throws SQLException {
        productService = new ProductService();
    }

    public ArrayList<Category> getAllCategoryNames() throws SQLException {
        ArrayList<Category> categories = new ArrayList<Category>();
        String query = "SELECT * FROM categories";
        try (Connection mysql = new DatabaseHandler().getConnection();) {
            Statement stmt = mysql.createStatement();
            ResultSet rs = stmt.executeQuery(query);


            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("category_id"),
                        rs.getString("name"))
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return categories;
    }

    public Category getCategory(int id) {
        String categoryName = "";
        ArrayList<Product> products = new ArrayList<Product>();

        String query = "SELECT * FROM categories WHERE category_id = ?";
        try (Connection mysql = new DatabaseHandler().getConnection();) {
            PreparedStatement stmt = mysql.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.isBeforeFirst()) {
                //hämta kategori namn
                if(rs.next()) {
                    categoryName = rs.getString("name");
                }

                //Hämta produkter i kategori;
                query = "SELECT * FROM products_categories WHERE category_id = ?";
                stmt = mysql.prepareStatement(query);
                stmt.setInt(1, id);
                rs = stmt.executeQuery();

                while(rs.next()) {
                    products.add(productService.getProduct(rs.getInt("product_id")));
                }
                return new Category(id, categoryName, products);
            } else {
                return null; //kategori existerar inte
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
