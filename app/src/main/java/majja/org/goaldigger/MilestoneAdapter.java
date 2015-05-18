package majja.org.goaldigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Goaldigger on 2015-04-16.
 */
public class MilestoneAdapter extends BaseExpandableListAdapter {

    private Milestone[] resource;
    private Context context;
    private HashMap<Milestone, List<Item>> hashMap;
    private List<Milestone> listDataHeader;

    public MilestoneAdapter(Context context, Milestone[] resource) {
        this.context = context;
        this.resource = resource;
        hashMap = createHashMap(this.resource);
    }

    public HashMap<Milestone, List<Item>> createHashMap(Milestone[] resource){
        HashMap<Milestone, List<Item>> newHashMap = new HashMap<>();
        listDataHeader = new ArrayList<>();
        int i = 0;
        for (Milestone milestone : resource){
            listDataHeader.add(milestone);
            List<Item> temp = new ArrayList<>();
            Collections.addAll(temp, milestone.Items());
            newHashMap.put(listDataHeader.get(i), temp);
            i++;
        }
        return newHashMap;
    }

    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
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
                        new CreateItem(this.getValue(),headerMilestone,groupPosition).execute();
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

        if(headerMilestone.Items().length != 0) {
            //sets the progressbar to green, but if it turns green it never turns back.....
            /*if(headerMilestone.percent() == 100){
                milestoneProgressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            }*/
            milestoneProgressBar.setProgress(headerMilestone.percent());
            milestoneProgressPercent.setText(headerMilestone.percent() + "%");
        }else{
            milestoneProgressBar.setProgress(0);
            milestoneProgressPercent.setText("No items");
        }

        return convertView;
    }

    private class CreateItem extends AsyncTask{

        private String value;
        private Milestone milestone;
        private int groupPosition;
        private ProgressDialog pd;

        public CreateItem(String value, Milestone milestone, int groupPosition){
            this.value = value;
            this.milestone = milestone;
            this.groupPosition = groupPosition;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(context, "", "Adding Item...");
        }

        protected Object doInBackground(Object[] params) {
            Item newItem = Item.create(value, milestone.id(), User.getInstance());
            resource[groupPosition].items().add(newItem);
            hashMap = createHashMap(resource);
            return null;
        }

        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            pd.dismiss();
            notifyDataSetChanged();
        }
    }

    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

            final Item childItem = (Item) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.custom_milestone_item, null);
            }

            CheckBox itemCheckBox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);
            TextView itemDoneBy = (TextView) convertView.findViewById(R.id.doneByTextView);

            itemCheckBox.setText(childItem.name());
            itemCheckBox.setChecked(childItem.done());

            if(itemCheckBox.isChecked()) {
                itemDoneBy.setText(childItem.doneBy());
            }else{
                itemDoneBy.setText("");
            }

            itemCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ToggleItem(childItem, groupPosition, childPosition).execute();
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
                                    resource[groupPosition].items().remove(childPosition);
                                    hashMap = createHashMap(resource);
                                    notifyDataSetChanged();
                                }
                            }, context, childItem.name());
                            return true;
                        }
                    }
            );

            return convertView;
        }

    private class ToggleItem extends AsyncTask{

        private Item childItem;
        private int groupPosition, childPosition;
        private ProgressDialog pd;

        public ToggleItem(Item childItem, int groupPosition, int childPosition){
            this.childItem = childItem;
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(context,"", "Setting item done...");
        }

        protected Object doInBackground(Object[] params) {Item.toggle(childItem.id(), User.getInstance());
            childItem.toggleDone();
            childItem.doneBy(User.getInstance().username());
            resource[groupPosition].items().set(childPosition, childItem);
            hashMap = createHashMap(resource);
            return null;
        }

        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            notifyDataSetChanged();
            pd.dismiss();
        }
    }

    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    public int getChildrenCount(int groupPosition) {
        return this.hashMap.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    public Object getChild(int groupPosition, int childPosition) {
        return this.hashMap.get(this.listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}