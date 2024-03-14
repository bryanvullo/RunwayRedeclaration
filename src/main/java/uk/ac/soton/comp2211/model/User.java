package uk.ac.soton.comp2211.model;

public class User {
    private String username;
    private String password;
    private String access_level;

    public User(String username, String password, String access_level) {
        this.username = username;
        this.password = password;
        this.access_level = access_level;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccess_level() {
        return access_level;
    }
}
