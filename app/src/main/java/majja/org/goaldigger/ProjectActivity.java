package majja.org.goaldigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Goaldigger on 2015-04-16.
 */
public class ProjectActivity extends ActionBarActivity {

    private Project project;
    private Context context;
    private User user;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ProjectActivity.this;
        user = User.getInstance();
        new Fetch().execute();
        project = (Project)getIntent().getExtras().getSerializable("project");// Ändra till det hämtade projectet
        setTitle(project.name());
        setContentView(R.layout.activity_project);

        Spinner participants = (Spinner)findViewById(R.id.participantsSpinner);

        List<String> list = new ArrayList<>();
        String[] temp = project.participants();
        if(temp != null) {
            Collections.addAll(Arrays.asList("Participants:", temp));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        participants.setAdapter(dataAdapter);


        if(!project.owner().equals(user.email())){
            Button leaveButton = (Button) findViewById(R.id.shareWithFriendsButton);
            leaveButton.setText("Leave Project");
            leaveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DB.getInstance().leaveProject(project.id(), user.email(), user.password());
                    Intent intent = new Intent(context, ProjectHandlerActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            Button addMilestone = (Button) findViewById(R.id.addMileStoneButton);
            addMilestone.setVisibility(View.INVISIBLE);
        }else {
            Button shareButton = (Button) findViewById(R.id.shareWithFriendsButton);
            shareButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context, FriendListActivity.class);
                    intent.putExtra("project", project);
                    startActivity(intent);
                    finish();
                }
            });
            Button addMilestone = (Button) findViewById(R.id.addMileStoneButton);
            addMilestone.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Helper.popup(new PromptRunnable() {

                        public void run() {
                            new AddMilestone(this.getValue()).execute();
                        }
                    }, context, "name of milestone");
                }
            });
        }

        // /You will setup the action bar with pull to refresh layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_project_swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            public void onRefresh() {
                new Fetch().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateList() {
        ExpandableListView projectListView = (ExpandableListView) findViewById(R.id.projectListView);

        final ExpandableListAdapter milestoneAdapter = new MilestoneAdapter(this, project.milestonesArray(), project);
        projectListView.setAdapter(milestoneAdapter);
        projectListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);

                if(itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    final Milestone headerMilestone = (Milestone) milestoneAdapter.getGroup(groupPosition);
                    Helper.delete(new PromptRunnable(){

                        public void run() {
                            new RemoveMilestone(headerMilestone).execute();
                        }
                    }, context, headerMilestone.name());
                    return true;
                }
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }


    public void onBackPressed() {
        Intent intent = new Intent(ProjectActivity.this, ProjectHandlerActivity.class);
        startActivity(intent);
        finish();
    }

    private class AddMilestone extends AsyncTask{

        private String value;
        private ProgressDialog pd;

        public AddMilestone(String value){
            this.value = value;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(context,"", "Creating Milestone...");
        }

        protected Object doInBackground(Object[] params) {
            Milestone newMilestone = Milestone.create(value, project.id(), user);
            project.milestones().add(newMilestone);
            return null;
        }

        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            new Fetch().execute();
            pd.dismiss();
        }
    }

    public class RemoveMilestone extends AsyncTask{

        private Milestone headerMilestone;
        private ProgressDialog pd;

        public RemoveMilestone(Milestone milestone){
            this.headerMilestone = milestone;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(context, "", "Deleting Milestone...");
        }

        protected Object doInBackground(Object[] params) {
            Milestone.delete(headerMilestone.id(), project.id(), user);
            project.milestones().remove(headerMilestone);
            return null;
        }

        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Helper.toast(headerMilestone.name() + " removed from milestones", context);
            new Fetch().execute();
            pd.dismiss();
        }
    }

    private class Fetch extends AsyncTask{

        private ProgressDialog pd;

        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(context,"", "Fetching projects...");
        }

        protected Object doInBackground(Object[] params) {
            Project[] projects = Project.all(User.getInstance());
            if(projects != null) {
                for (Project temp : projects) {
                    if (temp.id() == project.id()) {
                        project = temp;
                    }
                }
            }
            return null;
        }

        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            updateList();
            pd.dismiss();
        }
    }
}