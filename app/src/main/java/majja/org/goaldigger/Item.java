package majja.org.goaldigger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Item implements Serializable{
    private int id;
    private String name;
    private boolean done = false;
    private static DB db = DB.getInstance();

    public Item(int id, String name, boolean done) {
        this.name = name;
        this.id = id;
        this.done = done;
    }

    public String name() {
        return this.name;
    }

    public boolean done() {
        return this.done;
    }

    public int id() {
        return this.id;
    }

    public static Item create (String name, int mileStoneID, User user){
        db.createItem(name, mileStoneID, user.email(), user.password());
        String data = db.getReturnData();

        Item item = null;

        try {
            JSONObject jo = new JSONObject(data);
            item = new Item(jo.getInt("item_id"), jo.getString("item_name"), false);
        } catch (JSONException e) {
            Helper.pelle("Couldn't create item from json" + e.getMessage());
        }
        return item;
    }

    public static void delete (int id, User user){
        db.deleteItem(id, user.email(), user.password());
        db.getReturnData();
    }

    public void toggleDone() {
        this.done = !this.done;
    }

    public String toString() {
        return this.name;
    }

    public static void toggle(int itemId, User user) {
        db.toggleItem(itemId, user.email(), user.password());
    }
}
