package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import java.util.List;

class CustomItemAdapter extends ArrayAdapter<Item> {


    CustomItemAdapter(Context context, Item[] items) {
        super(context, R.layout.custom_milestone_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.custom_milestone_item, parent, false);

        CheckBox checkBox = (CheckBox) customView.findViewById(R.id.itemCheckBox);


        checkBox.setChecked(getItem(position).done());
        checkBox.setText(getItem(position).name());

        return customView;
    }
}