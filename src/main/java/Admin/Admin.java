package Admin;

import User.User;

public class Admin extends User {

    public Admin( int id, String name, String username, String password) {
        super(id, name, username, password, true);
    }
}
