package majja.org.goaldigger;

/**
 * Created by Xeronic on 15-04-21.
 */
public class Item {
    boolean done;
    String name;

    public Item(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

    public String name() {
        return this.name;
    }

    public boolean done() {
        return this.done;
    }
}
