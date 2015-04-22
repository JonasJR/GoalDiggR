package majja.org.goaldigger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xeronic on 15-04-21.
 */
public class Milestone implements Serializable {
    private String name;
    private List<Item> items = new ArrayList<Item>();
    private int id;


    public Milestone(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public Item[] getItems() {
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
