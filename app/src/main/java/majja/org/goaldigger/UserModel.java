package majja.org.goaldigger;

import org.json.JSONObject;

/**
 * Created by xeronic on 2015-04-16.
 */
public class UserModel {
    private String email, password, username;

    public UserModel(String username, String email, String password) {
        this.email = email;
        this.password = password;
        this.username = username;
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
            user = new UserModel(jo.getString("username"), jo.getString("email"), password);
        } catch (Exception e) {
            Helper._("Couldn't create user: " + e.getMessage());
        }

        return user;
    }
}
