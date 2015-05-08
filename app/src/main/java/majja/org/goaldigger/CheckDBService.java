package majja.org.goaldigger;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class CheckDBService extends Service {

    private NotificationCompat.Builder notification;
    private static final int uniqueID = 522462134;

    public CheckDBService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Helper.pelle("CheckDBService onStartCommand called");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                //Check for changes in DB
                // if changes -> sendNotification();
            }
        };
        Thread t = new Thread(r);
        t.start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Helper.pelle("CheckDBService is onDestroy called");
    }

    @Override
    public void onCreate() {
        //Buil the notification
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.drawable.logo2);
        notification.setTicker("This is the ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("GoalDigger");
        notification.setContentText("You have new updated projects!");
        notification.setOnlyAlertOnce(true);
    }

    public void sendNotification(){
        //When notification is clicked sends to Main activity (change later on)
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Builds notification and issues it
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
