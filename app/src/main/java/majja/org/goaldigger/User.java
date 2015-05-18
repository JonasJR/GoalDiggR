package majja.org.goaldigger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

/**
 * Created by Goaldigger on 2015-04-16.
 */
public class User implements Serializable{

    private String email, password, username;
    private DB db;
    private static User user;
    private static String errorMessage;

    private User(String username, String email, String password) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.db = DB.getInstance();
    }

    public void share(String shareFriends, int projectId) {
        db.shareProject(shareFriends, projectId, email, password);
    }

    public Friend[] getFriends() {
        DB db = DB.getInstance();
        JSONArray ja = null;

        db.showFriends(email, password);

        try {
            ja = new JSONArray(db.getReturnData());
        } catch (JSONException e) {
            Helper.log("Error making jsonarray from returndata: " + e.getMessage());
        }

        Friend[] friends= null;

        if(ja != null) {
            JSONObject jo;

            friends = new Friend[ja.length()];
            for (int i = 0; i < ja.length(); i++) {
                try {
                    jo = ja.getJSONObject(i);
                    friends[i] = new Friend(jo.getInt("id"), jo.getString("name"), jo.getString("email"));
                } catch (JSONException e){
                    e.getMessage();
                }
            }
        }
        return friends;
    }

    public static void create(String username, String email, String password) {
        User.user = new User(username, email, password);
    }

    public static User getInstance() {
        return User.user;
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
        String str = User.errorMessage;
        str = str.replace("{", "").replace("}", "").replace("[", "").replace("]", "").replace("\"", "");
        str = str.replace(",", "\n");

        return str;
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
                User.create(jo.getString("name"), jo.getString("email"), password);
                created = true;
            }
        } catch (Exception e) {
            if(jo != null) {
                User.errorMessage = jo.toString();
                Helper.log("SUCCESS == FALSE: " + User.errorMessage);
                Helper.log("Couldn't create user: " + e.getMessage());
            }
        }
        return created;
    }
}