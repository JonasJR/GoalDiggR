package majja.org.goaldigger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Xeronic on 15-04-15.
 */
public class Helper {

    private static final String DEBUG_TAG = "Goaldigger";
    private static String str = null, str2 = null, str3 = null;

    /**
     * Method to show a toast
     *
     * @param message
     * @param context
     */
    public static void toast(final String message, final Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void popup(final PromptRunnable postrun, final Context context, String hint) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        final EditText userInput = (EditText) promptView.findViewById(R.id.editTextDialogUserInput);
        userInput.setHint(hint);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        str = userInput.getText().toString();
                        if (str.equals("")) {
                            Helper.toast("You must enter a name", context);
                            return;
                        }
                        postrun.setValue(str);
                        postrun.run();
                        return;
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                str = null;
                dialog.cancel();
                return;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void delete(final PromptRunnable postrun, Context context, String name) {
        str = name;
        LayoutInflater li = LayoutInflater.from(context);
        View promptView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        EditText txtView = (EditText) promptView.findViewById(R.id.editTextDialogUserInput);
        txtView.setText("Delete " + name + "?");
        txtView.setEnabled(false);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        postrun.setValue(str);
                        postrun.run();
                        return;
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                str = null;
                dialog.cancel();
                return;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void passwordPopup (final PromptRunnable postrun,final Context context){

        LayoutInflater li = LayoutInflater.from(context);
        View promptView = li.inflate(R.layout.changepassprompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        final EditText theOld = (EditText) promptView.findViewById(R.id.oldPassword);
        theOld.requestFocus();
        theOld.setHint("Current Password");
        final EditText theNew = (EditText) promptView.findViewById(R.id.newPassword);
        theNew.setHint("Enter new password");
        final EditText theConfirm = (EditText) promptView.findViewById(R.id.passwordConfirmation);
        theConfirm.setHint("Retype new password");


        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        str = theOld.getText().toString() + ":";
                        str2 = theNew.getText().toString() + ":";
                        str3 = theConfirm.getText().toString();

                        if (str.equals("")) {
                            Helper.toast("Can't be blank, \n Enter current password", context);
                            return;
                        }
                        if (str2.equals("")) {
                            Helper.toast("Can't be blank", context);
                        }
                        if (str3.equals("")) {
                            Helper.toast("Can't be blank", context);
                        }
                        if (!str2.equals(str3 + ":")) {
                            Helper.toast("passwords doesn't match", context);
                        }

                        postrun.setPassValue(str, str2, str3);
                        postrun.run();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                str = null;
                str2 = null;
                str3 = null;
                dialog.cancel();
                return;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Method used for debugging messages.
     *
     * @param message
     */
    public static void pelle(String message) {
        Log.d(DEBUG_TAG, message);
    }
}