package majja.org.goaldigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private static Context context;
    private static EditText loginText;
    private static EditText passwordText;

    TextView forgotPasswordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        newUser();
        forgotPass();

        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginText = (EditText) findViewById(R.id.editText);
        passwordText = (EditText) findViewById(R.id.editText2);
        loginButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        new checkLogin(loginText.getText().toString(),passwordText.getText().toString()).execute();
                    }
                }
        );
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
        public checkLogin(String email, String password){
            this.email = email;
            this.password = password;
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
                finish();
                Intent intent = new Intent(MainActivity.this, ProjectHandlerActivity.class);
                startActivity(intent);
            }else {
                Helper.toast("Invalid email or password", MainActivity.this);
            }
            Helper.hideProgress();
        }
    }


    private void newUser(){
        final TextView newUserTextview = (TextView) findViewById(R.id.clickableTextCreate);
        newUserTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateAUserAcitivity.class);
                startActivity(intent);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
