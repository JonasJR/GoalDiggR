package majja.org.goaldigger;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * Created by Xeronic on 15-04-15.
 */
public class DB implements Serializable {

    // Address to web server
    private final String URL = "http://goaldigger.herokuapp.com/api/v1/";

    private static DB db;
    private static final long TIMEOUT = 10000000000L;
    private HttpClient httpClient = new DefaultHttpClient();
    private HttpPost request;
    private String returnData;
    private JSONObject jsonObject;

    private DB() {}
    public static DB getInstance() {
        if (db == null) {
            return new DB();
        }
        else
        {
            return DB.db;
        }
    }

    public String getReturnData() {
        long endTime = System.nanoTime() + TIMEOUT;
        while (returnData == null) {
            if (System.nanoTime() > endTime) {
                return "Request timed out";
            }
        }
        String returndata = this.returnData;
        this.returnData = null;
        return returndata;
    }

    public void login(String email, String password) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e ) {}

        action("login");
    }

    public void getProjects(String email, String password) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("email", email);
            jsonObj.put("password", password);
        } catch (JSONException e ) {}

        action("projects");
    }

    public void createUser(String username, String email, String password, String passwordConfirmation) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("name", username);
            jsonObj.put("email", email);
            jsonObj.put("password", password);
            jsonObj.put("password_confirmation", passwordConfirmation);
        } catch (JSONException e ) {}

        action("signup");
    }

    public void createFriend(String userEmail, String friendEmail) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("email", userEmail);
            jsonObj.put("friend_email", friendEmail);
        } catch (JSONException e ) {}

        action("create_friend");
    }

    public void searchFriend(String searchPhrase) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("search_phrase", searchPhrase);
        } catch (JSONException e ) {}

        action("search_friends");
    }

    public void getJSON(String url, JSONObject jsonObj) {
        request = new HttpPost(url);

        BufferedReader bufferedReader = null;

        StringBuffer stringBuffer = new StringBuffer("");

        try {

            //UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParameters);
            StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
            entity.setContentType("application/json");
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);
            bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            String LineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + LineSeparator);
            }
            bufferedReader.close();

        } catch (IOException e) {
            Helper.pelle("IOException: " + e.getMessage());
        } catch (Exception e) {
            Helper.pelle("Exception: " + e.getMessage());
        }

        Helper.pelle("Return mess: " + stringBuffer.toString());

        this.returnData = stringBuffer.toString();
    }

    public void createProject(String name, String email, String password) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("project_name", name);
            jsonObj.put("email", email);
            jsonObj.put("password", password);
        } catch (JSONException e ) {}

       action("add_project");
    }

    public void deleteProject(int id, String email, String password){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            Helper.pelle("Couldn't delete project" + e.getMessage());
        }

        action("delete_project");
    }

    public void deleteMilestone(int id, String email, String password ){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("milestone_id", id);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            Helper.pelle("Couldn't delete milestone" + e.getMessage());
        }

        action("delete_milestone");
    }

    public void deleteItem(int id, String email, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", id);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            Helper.pelle("Couldn't delete item" + e.getMessage());
        }

        action("delete_item");
    }

    public void toggleItem(int itemId, String email, String password){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", itemId);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            Helper.pelle("Couldn't toggle item: " + e.getMessage());
        }

        action("toggle_item");
    }

    public void createMilestone(String milestoneName, int projectID, String email, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("milestone_name", milestoneName);
            jsonObject.put("project_id", projectID);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            Helper.pelle("Couldn't create milestone: " + e.getMessage());
        }

        action("create_milestone");
    }

    public void createItem(String itemName, int milestoneId, String email, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_name", itemName);
            jsonObject.put("milestone_id", milestoneId);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            Helper.pelle("Couldn't create item: " + e.getMessage());
        }

        action("create_item");
    }

    public void resetPassword(String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
        } catch (JSONException e) {
            Helper.pelle("Couldn't reset password: " + e.getMessage());
        }

        action("reset_password");
    }

    public void changePassword(String newPassword, String oldPassword) {
        try {
            jsonObject.put("new_password", newPassword);
            jsonObject.put("old_password", oldPassword);
        } catch (JSONException e) {
            Helper.pelle("Couldn't change password: " + e.getMessage());
        }
        action("change_password");
    }

    private void action(String action) {
        new Networking(urlFor(action), jsonObject).execute();
    }
    private String urlFor(String action) {
        return URL + action + ".json";
    }

    public class Networking extends AsyncTask implements Serializable {

        private String url;
        private JSONObject obj;

        public Networking(String url, JSONObject obj) {
            this.url = url;
            this.obj = obj;
        }
        @Override
        protected Object doInBackground(Object[] params) {
            getJSON(url, obj);
            return null;
        }
    }
}


