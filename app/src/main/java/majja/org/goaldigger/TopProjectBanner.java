package majja.org.goaldigger;

/**
 * Created by Goaldigger on 2015-04-12.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TopProjectBanner extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_projekt_fragment, container, false);
        Button bigLabel = (Button)view.findViewById(R.id.mainFragmentButton);
        bigLabel.setText(getActivity().getTitle());

        Button logoutButton= (Button)view.findViewById(R.id.menuButton);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setTitle("Do you want to log-out?");
                alertDialogBuilder.setCancelable(false).setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Helper.toast("You logged out", getActivity());
                                SaveSharedPreference.logout(getActivity());
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        return view;
    }
}