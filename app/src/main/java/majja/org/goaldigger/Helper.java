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
    private static String str = null;
    /**
     * Method to show a toast
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

    public static void popup(final PromptRunnable postrun, Context context, String hint){
        LayoutInflater li = LayoutInflater.from(context);
        View promptView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        final EditText userInput = (EditText) promptView.findViewById(R.id.editTextDialogUserInput);
        userInput.setHint(hint);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        str = userInput.getText().toString();
                        postrun.setValue(str);
                        postrun.run();
                        return;
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                str = null;
                dialog.cancel();
                return;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    /**
     * Method used for debugging messages.
     * @param message
     */
    public static void pelle(String message) {
        Log.d(DEBUG_TAG, message);
    }
}