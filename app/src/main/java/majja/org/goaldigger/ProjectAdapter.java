package majja.org.goaldigger;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Goaldigger on 2015-04-16.
 */
class ProjectAdapter extends ArrayAdapter<Project> {

    private User user;

    ProjectAdapter(Context context, Project[] projects) {
        super(context, R.layout.custom_project_row, projects);
        user = User.getInstance();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.custom_project_row, parent, false);

        TextView projectName = (TextView) customView.findViewById(R.id.projectName);
        TextView projectPercentage = (TextView) customView.findViewById(R.id.projectPercentage);
        ProgressBar progress = (ProgressBar) customView.findViewById(R.id.progressBar);
        TextView projectOwner = (TextView) customView.findViewById(R.id.projectOwner);

        projectName.setText(getItem(position).name());
        projectPercentage.setText(getItem(position).percent() + "%");

        if(!getItem(position).owner().equals(user.email())){
            projectOwner.setText(( getItem(position).owner()));
        }else{
            projectOwner.setVisibility(View.INVISIBLE);
        }
        progress.setProgress(getItem(position).percent());
        if(getItem(position).percent() == 100){
            progress.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

        }
        return customView;
    }
}