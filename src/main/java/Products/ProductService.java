package Products;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {

    private final ProductRepository repository = new ProductRepository();

    public ProductService() throws SQLException {
    }

    public ArrayList<Product> SearchProduct(String searchQuery) {
        return repository.searchProduct(searchQuery);
    }

    public ArrayList<Product> getAllProducts() {
        return repository.getAllProducts();
    }

    public Product getProduct(int id) throws SQLException {
        return repository.getProduct(id);
    }
}
