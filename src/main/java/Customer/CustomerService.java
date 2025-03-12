package Customer;

import com.jensen.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerService {

    //Registrera kund
    String addCustomer(Customer customer) throws SQLException {

        //1. Kolla först om angiven e-post address redan används
        //2. Om den inte redan används, skapa kunden.
        CustomerRepository repository = new CustomerRepository();

        Customer customerExist = repository.getCustomer(customer.getUsername());

        if(customerExist == null) {
                repository.addCustomer(customer);
                return "success";
        } else {
            return "email-in-use";
        }
    }

}
