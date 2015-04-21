package majja.org.goaldigger;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Xeronic on 15-04-15.
 */
public class Helper {

    private static final String DEBUG_TAG = "Goaldigger";

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

    /**
     * Method used for debugging messages.
     * @param message
     */
    public static void _(String message) {
        Log.d(DEBUG_TAG, message);
    }
}