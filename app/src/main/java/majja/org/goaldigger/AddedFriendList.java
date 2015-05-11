package majja.org.goaldigger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


public class AddedFriendList extends ActionBarActivity {

    private Button shareWithFriendsButton;
    private Project project;
    private Friend[] friends = User.getInstance().getFriends();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_friend_list);

        Bundle extras = getIntent().getExtras();
        project = (Project) extras.get("project");

        final ListAdapter friendAdapter = new AddedFriendAdapter(this, friends, project);
        final ListView friendListView = (ListView)findViewById(R.id.addedFriendsListView);
        friendListView.setAdapter(friendAdapter);

        shareWithFriendsButton = (Button) findViewById(R.id.shareWithFriendsButton);
        shareWithFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = friends.length;
                String shareFriends = null;
                for(int i = 0; i < count; i ++){
                    Friend friend = (Friend) friendListView.getItemAtPosition(i);
                    if (shareFriends == null) {
                        shareFriends = "" + friend.getId();
                    } else {
                        shareFriends += ":" + friend.getId();
                    }
                }

                if (shareFriends != null) {
                    User.getInstance().share(shareFriends, project.id());
                    Helper.pelle(project.id() + "Kolla id");
                }

                Intent intent = new Intent(AddedFriendList.this, ProjectActivity.class);
                intent.putExtra("project", project);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_added_friend_list, menu);
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
