package majja.org.goaldigger;

import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        context = ProjectActivity.this;
        user = User.getInstance();
        project = (Project)getIntent().getExtras().getSerializable("project");

        updateList();

        addMilestone = (Button) findViewById(R.id.addMileStoneButton);
        addMilestone.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Helper.popup(new PromptRunnable(){
                    @Override
                public void run(){
                        Milestone newMilestone = Milestone.create(this.getValue(),project.id(), user);
                        project.milestones().add(newMilestone);
                        updateList();
                    }
                }, context, "name of milestone");
            }
        });

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
                            Milestone.delete(headerMilestone.id(), user);
                            project.milestones().remove(headerMilestone);
                            Helper.toast(headerMilestone.name() + " removed from milestones", context);
                            updateList();
                        }
                    }, context, headerMilestone.name());
                    //do your per-group callback here
                    return true;
                }

                return false;
            }
        });
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
