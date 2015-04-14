package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

class CustomItemAdapter extends ArrayAdapter<String> {


    CustomItemAdapter(Context context, String[] theTitle) {
        super(context, R.layout.milestone_item, theTitle);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.milestone_item, parent, false);


        String theTitle = getItem(position);

        CheckBox checkBox = (CheckBox) customView.findViewById(R.id.itemCheckBox);

        checkBox.setText(theTitle);

        return customView;
    }
}