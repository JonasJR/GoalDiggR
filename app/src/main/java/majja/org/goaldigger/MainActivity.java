package majja.org.goaldigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends ActionBarActivity {
    private static Context context;
    private static EditText loginText;
    private static EditText passwordText;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static DB db;
    private static User user;

    String SENDER_ID = "397138210490";

    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;

    String regid;


    TextView forgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SaveSharedPreference.getUserName(MainActivity.this).length() != 0)
        {
            LoginModel loginModel = new LoginModel();

            loginModel.login(SaveSharedPreference.getUserName(MainActivity.this), SaveSharedPreference.getPassword(MainActivity.this));
            Intent intent = new Intent(MainActivity.this, ProjectHandlerActivity.class);
            startActivity(intent);
            finish();
        }

        db = DB.getInstance();

        context = MainActivity.this;

        newUser();
        forgotPass();

        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginText = (EditText) findViewById(R.id.editText);
        passwordText = (EditText) findViewById(R.id.editText2);
        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        new checkLogin(loginText.getText().toString(), passwordText.getText().toString()).execute();
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    private void forgotPass() {

        forgotPasswordButton = (TextView) findViewById(R.id.clickableTextForgot);

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
                                                    public void onClick(View v) {
                                                        Helper.popup(new PromptRunnable() {
                                                            public void run() {
                                                                Helper.toast("Mail sent", context);
                                                                DB.getInstance().resetPassword(this.getValue().trim());
                                                            }
                                                        }, context, "email");

                                                    }
                                                }
        );
    }

    public class checkLogin extends AsyncTask<Void, Void, Boolean> {

        String email, password;
        ProgressDialog pd;
        public checkLogin(String email, String password){
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(MainActivity.this,"", "Loading...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            LoginModel loginModel = new LoginModel();

            if (loginModel.login(email, password)) {
                return true;
            }else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                user = User.getInstance();
                finish();
                if (checkPlayServices()) {
                    gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
                    regid = SaveSharedPreference.getRegistrationId(MainActivity.this);
                    //regid = getRegistrationId(context);
                    if (regid.isEmpty()) {
                        registerInBackground();
                        Helper.pelle("Ny regid skapad");
                    }else {
                        sendRegistrationIdToBackend();
                        Helper.pelle("Regid skickad till backend");
                    }
                } else {
                    Helper.pelle("No valid Google Play Services APK found.");
                }

                SaveSharedPreference.setUserName(MainActivity.this, user);
                Intent intent = new Intent(MainActivity.this, ProjectHandlerActivity.class);
                startActivity(intent);
                finish();
            }else {
                Helper.toast("", MainActivity.this);
            }
            pd.dismiss();
        }
    }


    private void newUser(){
        final TextView newUserTextview = (TextView) findViewById(R.id.clickableTextCreate);
        newUserTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateAUserAcitivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Helper.pelle( "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the registration ID - no need to register again.
                    SaveSharedPreference.storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Helper.pelle(msg);
            }
        }.execute(null, null, null);
    }

    private void sendRegistrationIdToBackend() {
        db.setRegId(regid, user.email(), user.password());
    }
}
