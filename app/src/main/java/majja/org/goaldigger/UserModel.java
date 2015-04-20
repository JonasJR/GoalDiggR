package majja.org.goaldigger;

/**
 * Created by xeronic on 2015-04-16.
 */
public class UserModel {
    private String email, password, username;

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String email() {
        return this.email;
    }

    public String password() {
        return this.password;
    }

    public String toString() {
        return "Email: " + email + " Password: " + password;
    }
}
