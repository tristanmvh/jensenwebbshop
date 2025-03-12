package User;

public abstract class User {
    private String name;
    private String username; //användarnamn eller e-post
    private String password;
    private final int id;
    private final boolean isAdmin;

    public User(int id, String name, String username, String password, boolean isAdmin) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
