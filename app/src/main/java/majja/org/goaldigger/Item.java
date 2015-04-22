package majja.org.goaldigger;

import java.io.Serializable;

/**
 * Created by Xeronic on 15-04-21.
 */
public class Item implements Serializable{
    private boolean done;
    private String name;
    private int id;

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
}
