package majja.org.goaldigger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xeronic on 2015-04-16.
 */
public class Project {

    String name;

    public Project(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public static Project[] all(UserModel user) {
        DB db = DB.getInstance();
        JSONArray ja = null;

        db.getProjects(user.email(), user.password());

        try {
            ja = new JSONArray(db.getReturnData());
            Helper._(ja.toString());
        } catch (JSONException e) {
            Helper._("Error making jsonarray from returndata: " + e.getMessage());
        }

        Project[] projects = null;
        if (ja != null) {
            JSONObject jo;
            projects = new Project[ja.length()];
            for (int i = 0; i < ja.length(); i++) {
                try {
                    jo = ja.getJSONObject(i);
                    projects[i] = new Project(jo.getString("name"));
                } catch (JSONException e) {
                    Helper._("Error getting json from jsonarray: " + e.getMessage());
                }
            }
        }
        return projects;
    }
}
