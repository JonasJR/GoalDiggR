package majja.org.goaldigger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Goaldigger on 2015-04-16.
 */
public class FriendListActivity extends ActionBarActivity {

    private Project project;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_friend_list);

        Bundle extras = getIntent().getExtras();
        project = (Project) extras.get("project");

        Friend[] friends = User.getInstance().getFriends();

        final ListAdapter friendAdapter = new FriendAdapter(this, friends, project);
        final ListView friendListView = (ListView)findViewById(R.id.addedFriendsListView);
        friendListView.setAdapter(friendAdapter);

        Button shareWithFriendsButton = (Button) findViewById(R.id.shareWithFriendsButton);
        shareWithFriendsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CheckBox cb;
                String shareFriends = null;
                for (int x = 0; x <friendListView.getChildCount();x++) {
                    Friend friend = (Friend) friendListView.getItemAtPosition(x);
                    cb = (CheckBox)friendListView.getChildAt(x).findViewById(R.id.itemCheckBox);
                    if(cb.isChecked())
                    {
                        if (shareFriends == null) {
                            shareFriends = "" + friend.getId();
                        } else {
                            shareFriends += ":" + friend.getId();
                        }
                    }
                }
                if (shareFriends == null) shareFriends = ""; {
                    new ShareWithFriends(shareFriends).execute();
                }
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(FriendListActivity.this, ProjectActivity.class);
        intent.putExtra("project", project);
        startActivity(intent);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_added_friend_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Helper.toast("You logged out", FriendListActivity.this);
            SaveSharedPreference.logout(FriendListActivity.this);
            Intent intent = new Intent(FriendListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ShareWithFriends extends AsyncTask {

        private ProgressDialog pd;
        private String shareFriends;
        public ShareWithFriends(String shareFriends){
            this.shareFriends = shareFriends;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(FriendListActivity.this,"", "Sharing with friends...");
        }

        protected Object doInBackground(Object[] params) {
            if (shareFriends != null) {
                Helper.log(shareFriends);
                User.getInstance().share(shareFriends, project.id());
            }
            return null;
        }

        protected void onPostExecute(Object o) {
            Intent intent = new Intent(FriendListActivity.this, ProjectActivity.class);
            intent.putExtra("project", project);
            startActivity(intent);
            pd.dismiss();
            finish();
        }
    }
}