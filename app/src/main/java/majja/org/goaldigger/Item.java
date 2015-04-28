package majja.org.goaldigger;

import java.io.Serializable;

/**
 * Created by Xeronic on 15-04-21.
 */
public class Item implements Serializable{
    private int id;
    private String name;
    private boolean done;

    public Item(int id, String name, boolean done) {
        this.name = name;
        this.done = done;
        this.id = id;
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

    public static void create (String name, int mileStoneID, User user){
        DB db = DB.getInstance();
        db.createItem(name, mileStoneID, user.email(), user.password());
        db.getReturnData();
    }

    public static void delete (int id, User user){
        DB db = DB.getInstance();
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
        DB db = DB.getInstance();

        db.toggleItem(itemId, user.email(), user.password());
    }
}
