package majja.org.goaldigger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xeronic on 2015-04-16.
 */
public class Project {

    private List<Milestone> milestones = new ArrayList<Milestone>();
    private List<Item> items = new ArrayList<Item>();
    private String name;
    private int id;

    public Project(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String name() {
        return this.name;
    }

    public int percent() {
        return 0;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addMilestone(Milestone milestone) {
        milestones.add(milestone);
    }

    public Milestone milestone(int index) {
        return this.milestones.get(index);
    }

    public static void create(UserModel user, String name) {
        DB db = DB.getInstance();
        db.createProject(name, user.email(), user.password());
    }

    public static Project[] all(UserModel user) {
        DB db = DB.getInstance();
        JSONArray ja = null;

        db.getProjects(user.email(), user.password());

        try {
            ja = new JSONArray(db.getReturnData());
            Helper._(ja.toString());
        } catch (JSONException e) {
            Helper._("Error making jsonarray from returndata: " + e.getMessage());
        }

        Project[] projects = null;
        if (ja != null) {
            JSONObject jo;
            JSONArray milestones;
            JSONObject milestone;
            JSONArray items;
            JSONObject item;

            projects = new Project[ja.length()];
            for (int i = 0; i < ja.length(); i++) {
                try {
                    // Skapa projekt
                    jo = ja.getJSONObject(i);
                    projects[i] = new Project(jo.getInt("id"), jo.getString("name"));

                    // Skapa milestone och items
                    milestones = jo.getJSONArray("milestones");

                    if (milestones.length() > 0) {

                        for (int j = 0; j < milestones.length(); j++) {
                            milestone = milestones.getJSONObject(j);
                            projects[i].addMilestone(new Milestone(milestone.getInt("id"), milestone.getString("name")));
                            items = milestone.getJSONArray("items");
                            if (items.length() > 0) {
                                for (int k = 0; k < items.length(); k++) {
                                    item = items.getJSONObject(k);
                                    projects[i].milestone(j).addItem(new Item(item.getInt("id"), item.getString("name"), false));
                                }
                            }
                        }
                    }

                } catch (JSONException e) {
                    Helper._("Error getting json from jsonarray: " + e.getMessage());
                }
            }
        }
        return projects;
    }

    public String toString() {
        String result;

        result = "\nProject: " + name + "\n";

        for (Milestone milestone : milestones) {
            result += "\tmilestone: " + milestone.name() + "\n";
            for (Item item : milestone.items()) {
                result += "\t \tItem: " + item.name() + "\n";
            }
        }

        return result;
    }
}
