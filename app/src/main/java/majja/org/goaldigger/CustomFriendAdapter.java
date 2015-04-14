package majja.org.goaldigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

class CustomFriendAdapter extends ArrayAdapter<String> {

    private String newFriend;
    private String[] friends;
    public static EditText userTextField;
    public static ListView listView;


    CustomFriendAdapter(Context context, String[] friends) {
        super(context, R.layout.custom_friend, friends);
        this.friends = friends;
    }

    CustomFriendAdapter(Context context, String[] friends, String newFriend) {
        super(context, R.layout.custom_friend, friends);
        this.newFriend = newFriend;
        this.friends = friends;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        userTextField = (EditText) convertView.findViewById(R.id.addUserTextField);
        LayoutInflater projectInflater = LayoutInflater.from(getContext());
        View customView = projectInflater.inflate(R.layout.custom_friend, parent, false);
        String temp = userTextField.getText().toString();

        String friend = getItem(position);

        TextView friendUnit = (TextView) customView.findViewById(R.id.friendUnit);

        friendUnit.setText(friend);

        return customView;
    }
}