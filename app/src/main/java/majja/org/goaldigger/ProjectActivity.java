package majja.org.goaldigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;


public class ProjectActivity extends ActionBarActivity {

    private Project project;
    private ExpandableListView projectListView;
    private Button addMilestone;
    private Context context;
    private User user;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        context = ProjectActivity.this;
        user = User.getInstance();
        project = (Project)getIntent().getExtras().getSerializable("project");

        updateList();
        shareButton = (Button) findViewById(R.id.shareWithFriendsButton);
        shareButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(context, AddedFriendList.class);
                intent.putExtra("project", project);
                startActivity(intent);
            }
        });
        addMilestone = (Button) findViewById(R.id.addMileStoneButton);
        addMilestone.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Helper.popup(new PromptRunnable(){
                    @Override
                public void run(){
                        new AddMile(this.getValue()).execute();
                    }
                }, context, "name of milestone");
            }
        });

    }
    private class AddMile extends AsyncTask{

        private String value;
        private ProgressDialog pd;

        public AddMile(String value){
            this.value = value;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(context,"", "Creating Milestone...");
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Milestone newMilestone = Milestone.create(value,project.id(), user);
            project.milestones().add(newMilestone);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            updateList();
            pd.dismiss();
        }
    }

    private void updateList() {
        projectListView = (ExpandableListView) findViewById(R.id.projectListView);

        final ExpandableListAdapter milestoneAdapter = new MilestoneAdapter(this, project.getMilestones());
        projectListView.setAdapter(milestoneAdapter);
        projectListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);

                if(itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    final Milestone headerMilestone = (Milestone) milestoneAdapter.getGroup(groupPosition);
                    Helper.delete(new PromptRunnable(){
                        @Override
                        public void run() {
                            new RemoveMile(headerMilestone).execute();
                        }
                    }, context, headerMilestone.name());
                    return true;
                }

                return false;
            }
        });
    }

    public class RemoveMile extends AsyncTask{

        private Milestone headerMilestone;
        private ProgressDialog pd;

        public RemoveMile(Milestone milestone){
            this.headerMilestone = milestone;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(context, "", "Deleting Milestone...");
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Milestone.delete(headerMilestone.id(), project.id(), user);
            project.milestones().remove(headerMilestone);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Helper.toast(headerMilestone.name() + " removed from milestones", context);
            updateList();
            pd.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
