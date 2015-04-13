package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<String> {


    CustomAdapter(Context context, String[] projects) {
        super(context, R.layout.custom_row, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.custom_row, parent, false);

        String singleProjectItem = getItem(position);
        String[] separated = singleProjectItem.split(",");

        TextView projectName = (TextView) customView.findViewById(R.id.projectName);
        TextView projectPercentage = (TextView) customView.findViewById(R.id.projectPercentage);
        ProgressBar progress = (ProgressBar) customView.findViewById(R.id.progressBar);

        projectName.setText(separated[0]);
        projectPercentage.setText(separated[1] + "%");
        progress.setProgress(Integer.parseInt(separated[1]));

        return customView;
    }
}