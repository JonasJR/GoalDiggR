package majja.org.goaldigger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by xeronic on 2015-04-15.
 */
public class LoginModel implements Serializable {

    private DB db;

    public LoginModel() {
        this.db = DB.getInstance();
    }

    public boolean login(String email, String password) {
        db.login(email, password);
        JSONObject jsonObj;

        boolean login = false;
        try {
            jsonObj = new JSONObject(db.getReturnData());
            String returnName = jsonObj.getString("name");
            String returnEmail = jsonObj.getString("email");
            User.create(returnName, returnEmail, password);

            login = true;
        } catch (JSONException e) {
            Helper.pelle("Couldn't log in: " + e.getMessage());
        }

        return login;
    }
}
