package Category;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryService {
    private CategoryRepository repository;

    public CategoryService() throws SQLException {
        repository = new CategoryRepository();
    }

    public ArrayList<Category> getAllCategoryNames() throws SQLException {
        return repository.getAllCategoryNames();
    }

    public Category getCategory(int id) {
        return repository.getCategory(id);
    }
}
