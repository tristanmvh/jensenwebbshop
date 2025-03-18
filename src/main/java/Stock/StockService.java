package Stock;

import Product.Product;

import java.sql.*;

public class StockService {
    private StockRepository repository = new StockRepository();

    public int getStock(Product product) throws SQLException {
        return repository.getStockQuantity(product);
    }

    public void setStock(Product product, int quantity) throws SQLException {
        int stock = repository.getStockQuantity(product);


        if (stock + quantity > 0) {
            repository.setStockQuantity(product, stock + quantity);
        }
    }
}

