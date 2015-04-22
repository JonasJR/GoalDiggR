package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

class CustomProjectAdapter extends ArrayAdapter<Project> {


    CustomProjectAdapter(Context context, Project[] projects) {
        super(context, R.layout.custom_project_row, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.custom_project_row, parent, false);

        TextView projectName = (TextView) customView.findViewById(R.id.projectName);
        TextView projectPercentage = (TextView) customView.findViewById(R.id.projectPercentage);
        ProgressBar progress = (ProgressBar) customView.findViewById(R.id.progressBar);

        projectName.setText(getItem(position).name());
        projectPercentage.setText(getItem(position).percent() + "%");
        progress.setProgress(getItem(position).percent());

        return customView;
    }
}