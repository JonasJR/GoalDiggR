package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import java.util.Arrays;

/**
 * Created by Goaldigger on 2015-04-16.
 */
public class FriendAdapter extends ArrayAdapter<Friend> {

    private String[] participants;

    public FriendAdapter(Context context, Friend[] friends, Project project) {
        super(context, R.layout.custom_milestone_item, friends);
        this.participants = project.participants();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.custom_milestone_item, parent, false);
        CheckBox friendName = (CheckBox) customView.findViewById(R.id.itemCheckBox);

        if(Arrays.asList(participants).contains(getItem(position).getEmail())){
            friendName.setChecked(true);
        }

        friendName.setText(getItem(position).getName());

        return customView;
    }
}