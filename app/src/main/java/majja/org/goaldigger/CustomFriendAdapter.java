package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

class CustomFriendAdapter extends ArrayAdapter<Friend> {

    private String newFriend;
    private Friend[] friends;
    public static EditText userTextField;
    public static ListView listView;


    CustomFriendAdapter(Context context, Friend[] friends) {
        super(context, R.layout.custom_friend, friends);
        this.friends = friends;
    }

    CustomFriendAdapter(Context context, Friend[] friends, String newFriend) {
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

        if(friend.getEmail() == null){
            Button addFriend = (Button) customView.findViewById(R.id.friendAddButton);
            addFriend.setVisibility(View.INVISIBLE);
        }else {
            Button addFriend = (Button) customView.findViewById(R.id.friendAddButton);
            addFriend.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Friend.add(User.getInstance(), friend.getEmail());
                        }
                    }
            );
        }

        friendName.setText(friend.getName());
        friendEmail.setText(friend.getEmail());


        return customView;
    }
}