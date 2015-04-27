package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by xeronic on 2015-04-24.
 */
public class MilestoneAdapter extends ArrayAdapter<Milestone> {


    public MilestoneAdapter(Context context, Milestone[] resource) {
        super(context, R.layout.milestone_adapter, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View milestoneView = inflater.inflate(R.layout.milestone_adapter, parent, false);

        Milestone milestone = getItem(position);
        TextView milestoneTextView = (TextView) milestoneView.findViewById(R.id.milestoneTextView);
        ListView itemsListView = (ListView) milestoneView.findViewById(R.id.itemsListView);

        milestoneTextView.setText(milestone.name());

        String[] items = new String[milestone.items().size()];
        Item[] mItems = milestone.getItems();
        for (int i = 0; i < milestone.items().size(); i++) {
            items[i] = mItems[i].name();
        }

        ListAdapter itemsAdapter = new CustomItemAdapter(getContext(), mItems);
        itemsListView.setAdapter(itemsAdapter);

        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        Utils.setListViewHeightBasedOnChildren(itemsListView);

        return milestoneView;
    }
}
