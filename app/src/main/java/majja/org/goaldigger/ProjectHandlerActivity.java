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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;


public class ProjectHandlerActivity extends ActionBarActivity {

    private User user;
    private PopupWindow popUp;
    private Context context;
    Button add;
    Button cancel;
    EditText addProject;
    DB db = DB.getInstance();
    Project[] projects;

    public ProjectHandlerActivity() {
        this.user = User.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Fetch().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_handler);

        this.context = ProjectHandlerActivity.this;

        new Fetch().execute();

        addProjectButton();

        Button addFriendButton = (Button) findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), FriendListActivity.class);
                startActivityForResult(intent, 0);
                Toast.makeText(ProjectHandlerActivity.this, "Friendlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void addProjectButton() {
        Button addProjectButton = (Button) findViewById(R.id.addProjectButton);
        addProjectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Helper.popup(new PromptRunnable() {
                    public void run() {
                        Project.create(user, this.getValue());
                        Helper.toast(this.getValue() + " added to projects", context);
                        new Fetch().execute();
                    }
                }, context, "project name");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_handler, menu);
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

    private class Fetch extends AsyncTask{
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(context,"", "Retrieving Projects...");
        }

        @Override
        protected Object doInBackground(Object[] params) {
            projects = Project.all(User.getInstance());
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            fetchAndUpdateList();
            pd.dismiss();
        }
    }

    private void fetchAndUpdateList() {

        if (projects == null ){
            projects = new Project[1];
            projects[0] = new Project(0, "No created projects...");
        }

        ListAdapter projectAdapter = new ProjectAdapter(this, projects);
        ListView projectListView = (ListView)findViewById(R.id.projectsListView);
        projectListView.setAdapter(projectAdapter);

        projectListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(view.getContext(), ProjectActivity.class);
                        intent.putExtra("project", projects[position]);

                        startActivity(intent);
                    }
                }
        );

        projectListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        Helper.delete(new PromptRunnable(){
                            @Override
                            public void run() {
                                Project.delete(projects[position].id(), user);
                                Helper.toast(this.getValue() + " removed from projects", context);
                                new Fetch().execute();
                            }
                        }, context, projects[position].name());
                        return true;
                    }
                }
        );
    }

}
