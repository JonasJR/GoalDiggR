package majja.org.goaldigger;

import org.json.JSONObject;
import java.io.Serializable;

/**
 * Created by xeronic on 2015-04-16.
 */
public class UserModel implements Serializable{
    private String email, password, username;

    private static UserModel user;

    private UserModel(String username, String email, String password) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static void create(String username, String email, String password) {
        UserModel.user = new UserModel(username, email, password);
    }

    public static UserModel getInstance() {
        return UserModel.user;
    }

    public String username() {
        return this.username;
    }

    public String email() {
        return this.email;
    }

    public String password() {
        return this.password;
    }

    public String toString() {
        return "Username: " + username + "Email: " + email + " Password: " + password;
    }

    public static UserModel createUser(String username, String email, String password, String passwordConfirmation) {
        DB db = DB.getInstance();
        db.createUser(username, email, password, passwordConfirmation);
        String returnData = db.getReturnData();
        JSONObject jo = null;
        UserModel user = null;

        try {
            jo = new JSONObject(returnData);
            user = new UserModel(jo.getString("name"), jo.getString("email"), password);
        } catch (Exception e) {
            Helper._("Couldn't create user: " + e.getMessage());
        }

        return user;
    }
}
