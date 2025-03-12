package Stock;

import java.sql.*;

public class StockService {
    private StockRepository lagerRepo = new StockRepository();

    public void getStock(int productId, int requiredQuantity) throws SQLException {
        if (lagerRepo.Stockquantity(productId) >= requiredQuantity) {
            System.out.println("Finns i lager");
        } else {
            System.out.println("Produkten finns inte tillräckligt i lagret");
        }
    }

    public void setStock(int productId, int orderedQuantity) throws SQLException {
        int stock = lagerRepo.Stockquantity(productId);

        if (stock >= orderedQuantity) {
            lagerRepo.uppdateradStockQuantity(productId, stock - orderedQuantity);
            System.out.println("LagerSaldo uppdaterad");
        } else {
            System.out.println("Inte tillräckligt produkter i Lagret");
        }
    }
}

