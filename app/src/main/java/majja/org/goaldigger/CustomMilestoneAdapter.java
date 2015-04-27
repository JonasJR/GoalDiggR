package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

class CustomMilestoneAdapter extends ArrayAdapter<Milestone> {

    CustomMilestoneAdapter(Context context, Milestone[] milestones) {
        super(context, R.layout.custom_milestone, milestones);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.custom_milestone, parent, false);

        TextView milestoneName = (TextView) customView.findViewById(R.id.milestoneName);

        milestoneName.setText(getItem(position).name());


        //För att nästla CustomItemAdapter (funkar men man ser bara ett item)
        ListAdapter itemAdapter = new CustomItemAdapter(CustomMilestoneAdapter.this.getContext(), getItem(position).getItems());
        ListView milestoneList = (ListView)customView.findViewById(R.id.milestoneListView);
        milestoneList.setAdapter(itemAdapter);

        return customView;
    }
}