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

    private static final long TIMEOUT = 10000000000L;
    private HttpClient httpClient = new DefaultHttpClient();
    private HttpPost request;
    private JSONObject returnData;

    public JSONObject getReturnData() {
        long endTime = System.nanoTime() + TIMEOUT;
        while (returnData == null) {
            if (System.nanoTime() > endTime) {
                try {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("message", "Request timed out");
                    jsonObj.put("success", false);
                    return jsonObj;
                } catch (JSONException e) {}
            }
        }
        return this.returnData;
    }

    public void login(String email, String password) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("email", email);
            jsonObj.put("password", password);
        } catch (JSONException e ) {}

        new Networking("http://goaldigger.herokuapp.com/api/v1/login.json", jsonObj).execute();
    }

    public void createUser(String username, String email, String password) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("username", username);
            jsonObj.put("email", email);
            jsonObj.put("password", password);
        } catch (JSONException e ) {}

        new Networking("http://goaldigger.herokuapp.com/api/v1/create.json", jsonObj).execute();
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
            Helper._("IOException: " + e.getMessage());
        } catch (Exception e) {
            Helper._("Exception: " + e.getMessage());
        }

        JSONObject returnJSON = null;
        try {
            returnJSON = new JSONObject(stringBuffer.toString());

        } catch (JSONException e) {}

        Helper._("Return mess: " + stringBuffer.toString());

        this.returnData = returnJSON;
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
            returnData = null;
            getJSON(url, obj);
            return null;
        }
    }
}
