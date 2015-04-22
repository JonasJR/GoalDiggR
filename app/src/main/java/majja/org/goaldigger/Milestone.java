package majja.org.goaldigger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xeronic on 15-04-21.
 */
public class Milestone {
    private String name;
    private List<Item> items = new ArrayList<Item>();

    public Milestone(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> items() {
        return items;
    }
}
