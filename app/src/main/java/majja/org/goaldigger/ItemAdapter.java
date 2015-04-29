package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

class ItemAdapter extends ArrayAdapter<Item> {


    ItemAdapter(Context context, Item[] items) {
        super(context, R.layout.custom_milestone_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.custom_milestone_item, parent, false);

        CheckBox checkBox = (CheckBox) customView.findViewById(R.id.itemCheckBox);


        final Item item = getItem(position);
        checkBox.setChecked(item.done());
        checkBox.setText(getItem(position).name());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item.toggle(item.id(), User.getInstance());
                item.toggleDone();
            }
        });
        return customView;
    }
}