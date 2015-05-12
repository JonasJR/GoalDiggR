package majja.org.goaldigger;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

class FriendAdapter extends ArrayAdapter<Friend> {

    private Activity activity;
    private String newFriend;
    private Friend[] friends;
    public static EditText userTextField;
    public static ListView listView;


    FriendAdapter(Activity context, Friend[] friends) {
        super(context, R.layout.custom_friend, friends);
        this.friends = friends;
        this.activity = context;
    }

    FriendAdapter(Context context, Friend[] friends, String newFriend) {
        super(context, R.layout.custom_friend, friends);
        this.newFriend = newFriend;
        this.friends = friends;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.custom_friend, parent, false);

        final Friend friend = getItem(position);

        TextView friendName = (TextView) customView.findViewById(R.id.friendName);
        TextView friendEmail = (TextView) customView.findViewById(R.id.friendEmail);

        final ImageButton addFriend = (ImageButton) customView.findViewById(R.id.friendAddButton);
        if(friend.getEmail() == null){
            addFriend.setVisibility(View.INVISIBLE);
        }else {
            addFriend.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Friend.add(User.getInstance(), friend.getEmail());
                            ((FriendListActivity) activity).fetch();
                        }
                    }
            );
        }

        friendName.setText(friend.getName());
        friendEmail.setText(friend.getEmail());


        return customView;
    }
}