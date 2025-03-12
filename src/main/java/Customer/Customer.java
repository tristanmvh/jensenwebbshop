package Customer;

import User.User;

public class Customer extends User {
    private String phone;
    private String address;
    public Customer(int id, String name, String email, String password, String phone, String address) {
        super(id, name, email, password, false);
        this.phone = phone;
        this.address = address;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
