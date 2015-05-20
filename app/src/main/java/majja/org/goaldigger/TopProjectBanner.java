package majja.org.goaldigger;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Goaldigger on 2015-04-12.
 */
public class TopProjectBanner extends Fragment{
    private User user = User.getInstance();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.top_projekt_fragment, container, false);
            Button bigLabel = (Button)view.findViewById(R.id.mainFragmentButton);
            bigLabel.setText(getActivity().getTitle());

            Spinner spinner = (Spinner)view.findViewById(R.id.fragmentSpinner);
            List<String> list = new ArrayList<>();
            list.add("Menu");
            list.add("Edit PW");
            list.add("Logout");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position == 0){
                        }
                        else if (position == 1) {
                            Helper.toast("Lets change Password", getActivity());
                            Helper.passwordPopup(new PromptRunnable() {
                                public void run() {
                                    String[] passwords = this.getChangePasswordValue();
                                    String old, newP;
                                    old = passwords[0].replace(":", "");
                                    newP = passwords[1].replace(":", "");
                                    DB.getInstance().changePassword(user.email(), user.password(), old, newP);
                                    SaveSharedPreference.logout(getActivity());
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }, getActivity());

                        } else if (position == 2) {
                            Helper.toast("You logged out", getActivity());
                            SaveSharedPreference.logout(getActivity());
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });

            return view;
    }
}
