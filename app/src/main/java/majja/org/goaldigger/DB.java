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
import java.util.concurrent.ExecutionException;

/**
 * Created by Goaldigger on 2015-04-16.
 */
public class DB implements Serializable {

    private static DB db;
    private static final long TIMEOUT = 10000000000L;
    private HttpClient httpClient = new DefaultHttpClient();
    private String returnData;
    private JSONObject jsonObject;

    private DB() {}
    public static DB getInstance() {
        if (db == null) {
            db = new DB();
            return db;
        } else {
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
        } catch (JSONException e ) {Helper.log("Login: " + e.getMessage());}

        action("login");
    }

    public void shareProject(String shareFriends, int projectId, String email, String password) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("share_friends", shareFriends);
            jsonObject.put("project_id", projectId);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e ) {Helper.log("Share project: " + e.getMessage());}

        action("share_project");
    }

    public void getProjects(String email, String password) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e ) {Helper.log("Get projects: " + e.getMessage());}

        action("projects");
    }

    public void createUser(String username, String email, String password, String passwordConfirmation) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("name", username);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("password_confirmation", passwordConfirmation);
        } catch (JSONException e ) {Helper.log("Create user: " + e.getMessage());}

        action("signup");
    }

    public void createFriend(String friendEmail, String userEmail, String password) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("friend_email", friendEmail);
            jsonObject.put("email", userEmail);
            jsonObject.put("password", password);
        } catch (JSONException e ) {Helper.log("Create friend: " + e.getMessage());}

        action("create_friend");
    }
    
    public void showFriends(String userEmail, String password) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("email", userEmail);
            jsonObject.put("password", password);
        } catch (JSONException e ) {Helper.log("Couldn't fetch friendlist: " + e.getMessage());}

        action("show_friends");
    }

    public void searchFriend(String email, String password, String searchPhrase) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("search_phrase", searchPhrase);
        } catch (JSONException e ) { Helper.log("search friends: " + e.getMessage()); }

        action("search_friends");
    }

    public void getJSON(String url, JSONObject jsonObj) {

        HttpPost request = new HttpPost(url);
        BufferedReader bufferedReader;
        StringBuilder stringBuilder = new StringBuilder("");

        try {
            StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
            entity.setContentType("application/json");
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);
            bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line;
            String LineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + LineSeparator);
            }
            bufferedReader.close();

        } catch (IOException e) {
            Helper.log("IOException: " + e.getMessage());
        } catch (Exception e) {
            Helper.log("Exception nu: " + e.getMessage());
        }

        Helper.log("Return mess: " + stringBuilder.toString());

        this.returnData = stringBuilder.toString();
    }

    public void createProject(String name, String email, String password) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("project_name", name);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e ) {Helper.log("Create project: " + e.getMessage());}

       action("add_project");
    }

    public void deleteProject(int id, String email, String password){
        jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) { Helper.log("Couldn't delete project" + e.getMessage());}

        action("delete_project");
    }

    public void leaveProject(int id, String email, String password) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("project_id", id);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) { Helper.log("Couldn't leave project" + e.getMessage());}

        action("leave_project");
    }

    public void deleteMilestone(int id, int projectId, String email, String password ){
        jsonObject = new JSONObject();
        try {
            jsonObject.put("milestone_id", id);
            jsonObject.put("project_id", projectId);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {Helper.log("Couldn't delete milestone" + e.getMessage());}

        action("delete_milestone");
    }

    public void deleteItem(int id, String email, String password) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", id);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) { Helper.log("Couldn't delete item" + e.getMessage()); }

        action("delete_item");
    }

    public void toggleItem(int itemId, String email, String password){
        jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", itemId);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) { Helper.log("Couldn't toggle item: " + e.getMessage()); }

        action("toggle_item");
    }

    public void createMilestone(String milestoneName, int projectID, String email, String password) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("milestone_name", milestoneName);
            jsonObject.put("project_id", projectID);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) { Helper.log("Couldn't create milestone: " + e.getMessage()); }

        action("create_milestone");
    }

    public void createItem(String itemName, int milestoneId, String email, String password) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("item_name", itemName);
            jsonObject.put("milestone_id", milestoneId);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) { Helper.log("Couldn't create item: " + e.getMessage()); }

        action("create_item");
    }

    public void resetPassword(String email) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
        } catch (JSONException e) { Helper.log("Couldn't reset password: " + e.getMessage()); }

        action("reset_password");
    }

    public void changePassword( String email, String password, String oldPassword, String newPassword) {
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("old_password", oldPassword);
            jsonObject.put("new_password", newPassword);
        } catch (JSONException e) { Helper.log("Couldn't change password: " + e.getMessage()); }
        action("change_password");
    }

    private void action(String action) {
        try {
            Object obj = new Networking(urlFor(action), jsonObject).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void setRegId(String regId, String email, String password){
        try {
            jsonObject.put("reg_id", regId);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) { Helper.log("Couldn't set regid: " + e.getMessage()); }
        action("set_reg_id");
    }

    private String urlFor(String action) {
        // Address to web server
        final String URL = "http://goaldigger.herokuapp.com/api/v1/";
        return URL + action + ".json";
    }

    private class Networking extends AsyncTask implements Serializable {

        private String url;
        private JSONObject obj;

        public Networking(String url, JSONObject obj) {
            this.url = url;
            this.obj = obj;
            Helper.log(url + " : " + obj.toString());
        }
        protected Object doInBackground(Object[] params) {
            getJSON(url, obj);
            return null;
        }
    }
}