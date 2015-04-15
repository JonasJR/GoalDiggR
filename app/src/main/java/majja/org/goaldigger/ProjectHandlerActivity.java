package majja.org.goaldigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;


public class ProjectHandlerActivity extends ActionBarActivity {

    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_handler);

        try {
            String email, password;
            Bundle extras = getIntent().getExtras();
            email = extras.getString("email");
            password = extras.getString("password");

            user = new UserModel(email, password);

            Helper.toast("Inloggad", this);
        } catch (Exception e) {
            Helper._("IOException: " + e.getMessage());
        }

        //Göras om till Projekt-objekt?
        String[] projects = {"first,100", "second,35", "third,20", "fourth,99", "fifth,45", "sixth,68"};

        ListAdapter projectAdapter = new CustomProjectAdapter(this, projects);
        ListView projectListView = (ListView) findViewById(R.id.projectListView);
        projectListView.setAdapter(projectAdapter);

        Button addFriendButton = (Button) findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), FriendListActivity.class);
                startActivityForResult(intent, 0);
                Toast.makeText(ProjectHandlerActivity.this, "Sends user tooooo the list of Friends", Toast.LENGTH_SHORT).show();
            }
        });

        projectListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String project = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(ProjectHandlerActivity.this, "Skickar användare till " + project, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(view.getContext(), ProjectActivity.class);
                        startActivityForResult(intent, 0);
                    }
                }
        );


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
}
