package majja.org.goaldigger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Jonas on 29/04/15.
 */
public class Friend implements Serializable{
    private String name, email;

    public Friend(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return "Name: " + name + " Email: " + email;
    }

    public static void add(User user, String friendEmail){
        DB db = DB.getInstance();
        db.createFriend(friendEmail, user.email(), user.password());
        db.getReturnData();
    }

    public static Friend[]  search(User user, String searchPhrase){

        DB db = DB.getInstance();
        JSONArray ja = null;

        db.searchFriend(user.email(), user.password(), searchPhrase);

        try {
            ja = new JSONArray(db.getReturnData());
            Helper.pelle(ja.toString());
        } catch (JSONException e) {
            Helper.pelle("Error making jsonarray from returndata: " + e.getMessage());
        }

        Friend[] friends= null;

        if(ja != null) {
            JSONObject jo;

            friends = new Friend[ja.length()];
            for (int i = 0; i < ja.length(); i++) {
                try {
                    jo = ja.getJSONObject(i);
                    friends[i] = new Friend(jo.getString("name"), jo.getString("email"));
                } catch (JSONException e){}

            }
        }
        return friends;
    }
}
