package majja.org.goaldigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    Context context;
    public static EditText loginText;
    public static EditText passwordText;
    private PopupWindow popUp;

    TextView forgotPasswordButton;
    Button send,cancel;


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
                        checkLogin(v);
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
                                                                DB.getInstance().resetPassword(this.getValue());
                                                            }
                                                        }, context, "email");

                                                    }
                                                }
        );

    }

    public void checkLogin(View v) {
        String email, password;
        LoginModel loginModel = new LoginModel();

        email = loginText.getText().toString();
        password = passwordText.getText().toString();

        if (loginModel.login(email, password)) {
            Intent intent = new Intent(v.getContext(), ProjectHandlerActivity.class);
            startActivity(intent);
        }
        else {
            Helper.toast("Invalid email or password", this);
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
