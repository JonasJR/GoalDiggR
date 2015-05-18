package majja.org.goaldigger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Goaldigger on 2015-04-16.
 */
public class Project implements Serializable{

    private List<Milestone> milestones = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private String name, owner;
    private int id;
    private String[] participants;
    private static DB db = DB.getInstance();

    public Project(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public Project(int id, String name, String owner, String[] participants) {
        this(id, name);
        this.participants = participants;
        this.owner = owner;
    }

    public Milestone[] milestonesArray() {
        return milestones.toArray(new Milestone[milestones.size()]);
    }

    public String owner(){
        return this.owner;
    }

    public String name() {
        return this.name;
    }

    public int id() {
        return this.id;
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

    public void addItem(Item item) {
        items.add(item);
    }

    public void addMilestone(Milestone milestone) {
        milestones.add(milestone);
    }

    public Milestone milestone(int index) {
        return this.milestones.get(index);
    }

    public List<Milestone> milestones() {
        return this.milestones;
    }

    public static void create(User user, String name) {
        db.createProject(name, user.email(), user.password());
        db.getReturnData();
    }

    public static void delete (int id, User user) {
        db.deleteProject(id, user.email(), user.password());
        db.getReturnData();
    }

    public static Project[] all(User user) {
        JSONArray ja = null;
        db.getProjects(user.email(), user.password());

        try {
            ja = new JSONArray(db.getReturnData());
            Helper.log(ja.toString());
        } catch (JSONException e) {
            Helper.log("Error making jsonarray from returndata: " + e.getMessage());
        }

        Project[] projects = null;
        if (ja != null) {
            JSONObject jo;
            JSONArray milestones;
            JSONObject milestone;
            JSONArray items;
            JSONObject item;
            JSONArray json_participants;
            String[] participants;

            projects = new Project[ja.length()];
            for (int i = 0; i < ja.length(); i++) {
                try {
                    // Skapa projekt
                    jo = ja.getJSONObject(i);

                    json_participants = jo.getJSONArray("participants");
                    participants = new String[json_participants.length()];
                    for (int ii = 0; ii < participants.length; ii++) {
                        participants[ii] = json_participants.getJSONObject(ii).getString("email");
                    }

                    projects[i] = new Project(jo.getInt("id"), jo.getString("name"), jo.getString("owner"), participants);

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
                                    projects[i].addItem(new Item(item.getInt("id"), item.getString("name"),
                                                                 item.getBoolean("done"), item.getString("done_by")));
                                    projects[i].milestone(j).addItem(new Item(item.getInt("id"), item.getString("name"),
                                                                              item.getBoolean("done"), item.getString("done_by")));
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    Helper.log("Error getting json from jsonarray: " + e.getMessage());
                }
            }
        }
        return projects;
    }

    public String[] participants(){
        return participants;
    }

    public String toString() {
        String result;

        result = "\nProject: " + name + "\n";

        for (Item item : items) {
            result += "Item: " + item.name() + "\n";
        }

        for (Milestone milestone : milestones) {
            result += "\tmilestone: " + milestone.name() + "\n";
            for (Item item : milestone.items()) {
                result += "\t \tItem: " + item.name() + "\n";
            }
        }
        return result;
    }
}