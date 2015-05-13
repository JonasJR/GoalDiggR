package majja.org.goaldigger;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;


public class FriendListActivity extends ActionBarActivity {
    public static EditText userField;
    private static Friend[] friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        userField = (EditText) findViewById(R.id.addUserTextField);
        Button searchFriend = (Button) findViewById(R.id.searchFriendButton);
        searchFriend.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                fetch();
            }
        });

        fetch();
    }
    public void fetch(){
        new Fetch().execute();
    }

    private class Fetch extends AsyncTask{
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(FriendListActivity.this, "", "Loading...");
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String temp = userField.getText().toString();
            friends = Friend.search(User.getInstance(), temp);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            updateFriendsList();
            pd.dismiss();
        }
    }

    public void updateFriendsList() {

        if(friends == null){
            friends = new Friend[1];
            friends[0] = new Friend(0, "No matching friends...", null);
        }

        ListAdapter friendsAdapter = new FriendAdapter(this, friends);
        ListView friendsListView = (ListView)findViewById(R.id.friendListView);
        friendsListView.setAdapter(friendsAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
