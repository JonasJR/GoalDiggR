package majja.org.goaldigger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by xeronic on 2015-04-15.
 */
public class LoginModel implements Serializable {

    private DB db;
    private String message;

    public LoginModel(DB db) {
        this.db = db;
    }

    public boolean login(String email, String password) {
        db.login(email, password);
        JSONObject jsonObj = db.getReturnData();

        boolean login = false;
        try {
            this.message = jsonObj.getString("message");
            login = jsonObj.getBoolean("success");
        } catch (JSONException e) {}

        return login;
    }

    public String getMessage() {
        return this.message;
    }
}
