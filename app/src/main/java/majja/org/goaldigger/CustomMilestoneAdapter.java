package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

        ListView milestoneList = (ListView) customView.findViewById(R.id.milestoneListView);

        milestoneName.setText(getItem(position).name());

        List<Item> itemsList = getItem(position).items();
        Item[] itemArray = itemsList.toArray(new Item[itemsList.size()]);
        ListAdapter itemAdapter = new CustomItemAdapter(this.getContext(), itemArray);
        ListView itemListView = (ListView)customView.findViewById(R.id.milestoneListView);
        itemListView.setAdapter(itemAdapter);

        return customView;
    }
}