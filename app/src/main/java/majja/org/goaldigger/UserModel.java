package majja.org.goaldigger;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by xeronic on 2015-04-16.
 */
public class UserModel implements Serializable{
    private String email, password, username;

    private static UserModel user;
    private static String errorMessage;

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

    public static String errorMessage() {
        String[] array = UserModel.errorMessage.split(",");
        for (int i = 0; i < array.length; i++) {
            array[i] += "\n";
        }
        return Arrays.toString(array);
    }

    public static boolean createUser(String username, String email, String password, String passwordConfirmation) {
        DB db = DB.getInstance();
        db.createUser(username, email, password, passwordConfirmation);
        String returnData = db.getReturnData();
        JSONObject jo = null;
        boolean created = false;

        try {
            jo = new JSONObject(returnData);
            if (jo.getBoolean("success")) {
                UserModel.create(jo.getString("name"), jo.getString("email"), password);
                created = true;
            }
        } catch (Exception e) {
            UserModel.errorMessage = jo.toString();
            Helper._("SUCCESS == FALSE: " + UserModel.errorMessage);
            Helper._("Couldn't create user: " + e.getMessage());
        }

        return created;
    }
}
