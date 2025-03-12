package Customer;

import com.jensen.DatabaseHandler;

import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {
    private Connection mysql = new DatabaseHandler().getConnection();

    public CustomerRepository() throws SQLException {

    }

    //hämtar en specifik kund från tabellen customers med hjälp av e-post
    public Customer getCustomer(String email) throws SQLException {
        String query = "SELECT * FROM customers WHERE email=?";
        try (Connection mysql = new DatabaseHandler().getConnection();) {
            PreparedStatement stmt = mysql.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            //Avgör om kunden hittades
            if(!rs.isBeforeFirst()) {
                //Kunden finns inte
                return null;
            } else {
                //Kund hittad
                while (rs.next()) {
                    int id = rs.getInt("customer_id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");

                    return new Customer(id, name, email, password, phone, address);
                }
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //hämtar en specifik kund från tabellen customers med hjälp av id
    public Customer getCustomer(int id) throws SQLException {
        String query = "SELECT * FROM customers WHERE customer_id=?";
        try (Connection mysql = new DatabaseHandler().getConnection();) {
            PreparedStatement stmt = mysql.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(!rs.isBeforeFirst()) {
                //Kunden finns inte
                return null;
            } else {
                //Kund hittad
                while (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");

                    return new Customer(id, name, email, password, phone, address);
                }
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //Hämtar alla kunder i tabellen customers
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        String query = "SELECT * FROM customers";

        try(Connection mysql = new DatabaseHandler().getConnection();) {
            Statement stmt = mysql.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                customers.add(new Customer(id, name, email, password, phone, address));
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Skapa kund
    public void addCustomer(Customer customer) throws SQLException {

        String query = "INSERT INTO customers VALUES(null, ?, ?, ?, ?, ?";

        try(Connection mysql = new DatabaseHandler().getConnection();) {
            PreparedStatement stmt = mysql.prepareStatement(query);
            stmt.setInt(1, customer.getId());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getUsername());
            stmt.setString(4, customer.getPhoneNumber());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


