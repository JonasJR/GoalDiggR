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

    public void toggleDone() {
        this.done = !this.done;
    }

    public String toString() {
        return this.name;
    }

    public static void toggle(int itemId, UserModel user) {
        DB db = DB.getInstance();

        db.toggleItem(itemId, user.email(), user.password());
    }
}
