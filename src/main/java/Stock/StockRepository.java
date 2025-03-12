package Stock;

import java.sql.*;

public class StockRepository {

    // Hämtar lagersaldot för en produkt baserat på produkt-ID
    public int Stockquantity(int productID) throws SQLException {
        String sql = "SELECT stock_quantity FROM products WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/schema_name");){
             PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getInt("stock_quantity") : 0; // Returnerar lagersaldot eller 0 om produkten inte hittas
        }}
    }

    // Uppdaterar lagersaldot för en produkt
    public void uppdateradStockQuantity(int productID, int newQuantity) throws SQLException {
        String sql = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:webbutiken.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, productID);
            stmt.executeUpdate();
        }
    }
}

