package majja.org.goaldigger;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Xeronic on 15-04-15.
 */
public class Helper {

    private static final String DEBUG_TAG = "Goaldigger";
    private static String str = null;
    private static ProgressDialog progress;

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

    public static void delete(final PromptRunnable postrun, Context context, String name){
        str = name;
        LayoutInflater li = LayoutInflater.from(context);
        View promptView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        EditText txtView = (EditText) promptView.findViewById(R.id.editTextDialogUserInput);
        txtView.setText("Delete " + name + "?");
        txtView.setEnabled(false);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
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

    public static void showProgress(Context context){
        progress = new ProgressDialog(context);

        progress.setMessage("Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        final int totalProgressTime = 100;

        final Thread t = new Thread(){

            @Override
            public void run(){

                int jumpTime = 0;
                while(jumpTime < totalProgressTime){
                    try {
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                        sleep(200);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();

    }



    public static void hideProgress(){
        progress.hide();
    }
}