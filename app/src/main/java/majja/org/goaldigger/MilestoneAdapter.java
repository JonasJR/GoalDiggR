package majja.org.goaldigger;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xeronic on 2015-04-24.
 */
public class MilestoneAdapter extends BaseExpandableListAdapter {

    private Context context;
    private HashMap<Milestone, List<Item>> hashMap;
    private List<Milestone> listDataHeader;
    public MilestoneAdapter(Context context, Milestone[] resource) {
        this.context = context;
        hashMap = createHashMap(resource);
    }

    public HashMap<Milestone, List<Item>> createHashMap(Milestone[] resource){
        HashMap<Milestone, List<Item>> newHashMap = new HashMap<Milestone, List<Item>>();
        listDataHeader = new ArrayList<Milestone>();
        int i = 0;
        for (Milestone milestone : resource){
            listDataHeader.add(milestone);
            List<Item> temp = new ArrayList<Item>();
            for(Item item : milestone.getItems()){
                temp.add(item);
            }
            newHashMap.put(listDataHeader.get(i), temp);
            i++;
        }
        return newHashMap;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final Milestone headerMilestone = (Milestone) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.milestone_adapter, null);
        }

        ImageButton addItem = (ImageButton) convertView.findViewById(R.id.addItemButton);
        addItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Helper.popup(new PromptRunnable(){
                    @Override
                    public void run(){
                        //Item newItem = Item.create(this.getValue(), headerMilestone.id(), User.getInstance());
                        //headerMilestone.items().add(newItem);
                        notifyDataSetChanged();
                    }
                }, context, "name of Item");
            }
        });
        addItem.setFocusable(false);

        TextView textHead = (TextView) convertView
                .findViewById(R.id.milestoneTextView);
        textHead.setTypeface(null, Typeface.BOLD);
        textHead.setText(headerMilestone.name());

        ProgressBar milestoneProgressBar = (ProgressBar) convertView.findViewById(R.id.milestoneProgressBar);
        TextView milestoneProgressPercent = (TextView) convertView.findViewById(R.id.milestoneProgressPercent);

        if(headerMilestone.getItems().length != 0) {
            milestoneProgressBar.setProgress(headerMilestone.percent());
            milestoneProgressPercent.setText(headerMilestone.percent() + "%");
        }else{
            milestoneProgressBar.setProgress(0);
            milestoneProgressPercent.setText("No items");
        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

            final Item childItem = (Item) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.custom_milestone_item, null);
            }

            CheckBox itemCheckBox = (CheckBox) convertView
                    .findViewById(R.id.itemCheckBox);

            itemCheckBox.setText(childItem.name());
            itemCheckBox.setChecked(childItem.done());

            itemCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    childItem.toggleDone();
                    Item.toggle(childItem.id(), User.getInstance());
                    notifyDataSetChanged();
                }
            });

            itemCheckBox.setOnLongClickListener(
                    new View.OnLongClickListener(){
                        @Override
                        public boolean onLongClick(View v) {
                            Helper.delete(new PromptRunnable(){
                                @Override
                                public void run() {
                                   Item.delete(childItem.id(), User.getInstance());
                                   Helper.toast(childItem.name() + " removed from items", context);
                                    notifyDataSetChanged();
                                }
                            }, context, childItem.name());
                            return true;
                        }
                    }
            );

            return convertView;
        }


    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.hashMap.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.hashMap.get(this.listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
