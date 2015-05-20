package majja.org.goaldigger;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Goaldigger on 2015-04-16.
 */
public class Milestone implements Serializable {
    private String name;
    private List<Item> items = new ArrayList<>();
    private int id;


    public Milestone(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public static Milestone create(String name, int projectID, User user) {
        DB db = DB.getInstance();
        db.createMilestone(name, projectID, user.email(), user.password());
        String data = db.getReturnData();

        Milestone milestone = null;

        try {
            JSONObject jo = new JSONObject(data);
            milestone = new Milestone(jo.getInt("milestone_id"), jo.getString("milestone_name"));
        } catch (JSONException e) {
            Helper.log("Couldn't create milestone from json" + e.getMessage());
        }
        return milestone;
    }

    public static void delete (int id, int projectId, User user) {
        DB db = DB.getInstance();
        db.deleteMilestone(id, projectId, user.email(), user.password());
        db.getReturnData();
    }

    public int percent() {
        int totalItems = 0;
        int itemsDone = 0;

        for (Item item : items) {
            if (item.done()) {
                itemsDone++;
            }
            totalItems++;
        }
        double percent = (double)itemsDone / (double) totalItems;

        return (int)(percent*100);
    }

    public Item[] Items() {
        return items.toArray(new Item[items.size()]);
    }

    public int id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> items(){
        return items;
    }
}