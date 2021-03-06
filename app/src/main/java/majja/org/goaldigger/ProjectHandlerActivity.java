package majja.org.goaldigger;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.OnClickListener;

/**
 * Created by Goaldigger on 2015-04-16.
 */
public class ProjectHandlerActivity extends ActionBarActivity {

    private User user;
    private Context context;
    private Project[] projects;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public ProjectHandlerActivity() {
        this.user = User.getInstance();
    }

    protected void onResume() {
        super.onResume();
        new Fetch().execute();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_handler);

        if(SaveSharedPreference.getUserName(ProjectHandlerActivity.this).length() == 0) {
            Intent intent = new Intent(ProjectHandlerActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            LoginModel loginModel = new LoginModel();
            loginModel.login(SaveSharedPreference.getUserName(ProjectHandlerActivity.this), SaveSharedPreference.getPassword(ProjectHandlerActivity.this));
        }

            this.context = ProjectHandlerActivity.this;

        new Fetch().execute();

        addProjectButton();

        Button addFriendButton = (Button) findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_project_handler_swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            public void onRefresh() {
                new Fetch().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void addProjectButton() {
        Button addProjectButton = (Button) findViewById(R.id.addProjectButton);
        addProjectButton.setOnClickListener(new OnClickListener() {
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_handler, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void updateList() {
        TextView noProjects = (TextView)findViewById(R.id.noProjectsTextView);
        /*if (projects == null || projects.length == 0 ) {
            projects = new Project[1];
            projects[0] = new Project(0, "No created projects...");
        }*/
        if(projects == null || projects.length == 0 ){
            noProjects.setText("No projects created");
        }else {
            noProjects.setVisibility(View.INVISIBLE);
            ListAdapter projectAdapter = new ProjectAdapter(this, projects);
            ListView projectListView = (ListView) findViewById(R.id.projectsListView);
            projectListView.setAdapter(projectAdapter);

            projectListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {

                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(view.getContext(), ProjectActivity.class);
                            intent.putExtra("project", projects[position]);
                            startActivity(intent);
                            finish();
                        }
                    }
            );

            projectListView.setOnItemLongClickListener(
                    new AdapterView.OnItemLongClickListener() {

                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                            Helper.delete(new PromptRunnable() {

                                public void run() {
                                    if (projects[position].owner().equals(user.email())) {
                                        Project.delete(projects[position].id(), user);
                                        Helper.toast(this.getValue() + " removed from projects", context);
                                        if(position == 0){
                                            Intent intent = new Intent(context, ProjectHandlerActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        DB.getInstance().leaveProject(projects[position].id(), user.email(), user.password());
                                        Helper.toast(this.getValue() + " You left the project", context);
                                    }
                                    new Fetch().execute();
                                }
                            }, context, projects[position].name());
                            return true;
                        }
                    }
            );
        }
    }

    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Do you want to exit?");
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private class Fetch extends AsyncTask{
        ProgressDialog pd;

        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(context,"", "Retrieving Projects...");
        }

        protected Object doInBackground(Object[] params) {
            projects = Project.all(User.getInstance());
            return null;
        }

        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            updateList();
            pd.dismiss();
        }
    }
}