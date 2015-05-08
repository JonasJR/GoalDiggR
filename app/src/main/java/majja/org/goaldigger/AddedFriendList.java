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
    private int projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_friend_list);

        Bundle extras = getIntent().getExtras();
        projectId = extras.getInt("projectId");

        final ListAdapter friendAdapter = new AddedFriendAdapter(this, User.getInstance().getFriends());
        final ListView friendListView = (ListView)findViewById(R.id.addedFriendsListView);
        friendListView.setAdapter(friendAdapter);

        shareWithFriendsButton = (Button) findViewById(R.id.shareWithFriendsButton);
        shareWithFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friend = null;
                int friends = friendListView.getCount();
                String shareFriends = null;
                for (int i = 0; i < friends; i++) {
                    if (friendListView.isItemChecked(i)) { // Denna koden funkar inte

                        // Koden här under bör vara rätt, problemet är att
                        // jag inte vet hur man kontrollerar vilka som är ikryssade.
                        friend = (Friend)friendAdapter.getItem(i);
                        friend.getId();
                        if (shareFriends == null) {
                            shareFriends = "" + friend.getId();
                        } else {
                            shareFriends += ":" + friend.getId();
                        }
                    }
                }

                User.getInstance().share(shareFriends, projectId);

                if (shareFriends != null) {
                    User.getInstance().share(shareFriends, projectId);
                }

                Intent intent = new Intent(AddedFriendList.this, ProjectHandlerActivity.class);
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
