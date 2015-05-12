package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by antonnilsson on 2015-05-06.
 */
public class AddedFriendAdapter extends ArrayAdapter<Friend> {
    private Friend[] friends;
    private Project project;
    private String[] temp;

    public AddedFriendAdapter(Context context, Friend[] friends, Project project) {
        super(context, R.layout.custom_milestone_item, friends);
        this.friends = friends;
        this.project = project;
        this.temp = project.getParticipants();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.custom_milestone_item, parent, false);
        CheckBox friendName = (CheckBox) customView.findViewById(R.id.itemCheckBox);

        if(Arrays.asList(temp).contains(getItem(position).getEmail())){
            friendName.setChecked(true);
        }

        /*for(String temp : project.getParticipants()){
            if (getItem(position).getEmail().equals(temp)) {
                friendName.setChecked(true);
            }
        }*/
        friendName.setText(getItem(position).getName());

        return customView;
    }
}
