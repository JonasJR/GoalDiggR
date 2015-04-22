package majja.org.goaldigger;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ProjectActivity extends ActionBarActivity {

    Context context;
    Project project;

    public ProjectActivity(Project project){
        this.project = project;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        this.context = this.getBaseContext();


        addMilestones();
        addItem();

    }

    private void addMilestones() {
        ListAdapter milestoneAdapter = new CustomMilestoneAdapter(ProjectActivity.this, project.milestones.toArray(new Milestone[project.milestones.size()]));
        ListView projectListView = (ListView) findViewById(R.id.milestoneListView);
        projectListView.setAdapter(milestoneAdapter);
    }

    private void addItem() {
        ListAdapter itemAdapter = new CustomItemAdapter(ProjectActivity.this, project.items.toArray(new Item[project.items.size()]));
        ListView projectListView = (ListView) findViewById(R.id.projectListView);
        projectListView.setAdapter(itemAdapter);
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
