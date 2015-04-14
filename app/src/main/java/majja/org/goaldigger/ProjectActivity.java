package majja.org.goaldigger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ProjectActivity extends ActionBarActivity {

    String title = "Title";
    List<String> items = new ArrayList<String>();
    String[] theTitle={"Title", "Pierre", "Axel Preben", "Dorius Agosta", "Lille Pia Dreng", "Stygge Sesser", "Sven Dennis"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        //prepare the explistview content
        prepareContent();

        addItem();

    }

    private void addItem() {
        ListAdapter projectAdapter = new CustomItemAdapter(ProjectActivity.this, theTitle);
        ListView projectListView = (ListView) findViewById(R.id.mileStoneListView);
        projectListView.setAdapter(projectAdapter);
    }

    private void prepareContent() {
        // hämtar info från databas, om milestones/delmål

        title = "Title";
        items.add("delmål1");
        items.add("mellanmål");
        items.add("skrovmål");
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
