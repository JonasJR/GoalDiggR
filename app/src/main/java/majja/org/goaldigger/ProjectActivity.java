package majja.org.goaldigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;


public class ProjectActivity extends ActionBarActivity {

    private Project project;
    private ExpandableListView projectListView;
    private Button addMilestone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        projectListView = (ExpandableListView) findViewById(R.id.projectListView);
        project = (Project)getIntent().getExtras().getSerializable("project");

        ExpandableListAdapter milestoneAdapter = new MilestoneAdapter(this, project.getMilestones());
        projectListView.setAdapter(milestoneAdapter);

        addMilestone = (Button) findViewById(R.id.addMileStoneButton);
        addMilestone.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

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
