package Customer;

import com.jensen.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerService {

    private CustomerRepository repository;

    public CustomerService() throws SQLException {
        repository = new CustomerRepository();
    }

    //Registrera kund
    public String addCustomer(Customer customer) throws SQLException {

        //1. Kolla först om angiven e-post address redan används
        //2. Om den inte redan används, skapa kunden.


        Customer customerExist = repository.getCustomer(customer.getUsername());

        if(customerExist == null) {
                repository.addCustomer(customer);
                return "success";
        } else {
            return "email-in-use";
        }
    }

    public Customer getCustomer(String email) throws SQLException {
        return repository.getCustomer(email);
    }

    public Customer getCustomer(int id) throws SQLException {
        return repository.getCustomer(id);
    }

    public String updateCustomerInformation(Customer customer, String username, String phone, String address) throws SQLException {

        //Kolla så att åtminstone ett fält har fyllts i
        if (!username.isEmpty() || !phone.isEmpty() || !address.isEmpty()) {
            //Uppdatera informationen först i instansen.
            if (!username.isEmpty()) {
                customer.setUsername(username);
            }

            if (!phone.isEmpty()) {
                customer.setPhoneNumber(phone);
            }
            if (!address.isEmpty()) {
                customer.setAddress(address);
            }
            //sedan skicka instansen till repository för att uppdatera i DB
            if (repository.updateCustomerInformation(customer)) {
                return "success";
            } else {
                return "fail";
            }
        } else {
            return "nothing-changed";
        }
    }
}
