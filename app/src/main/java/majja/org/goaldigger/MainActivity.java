package majja.org.goaldigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

public class MainActivity extends ActionBarActivity {
    Context context;
    public static EditText loginText;
    public static EditText passwordText;
    private DB db;
    private PopupWindow popUp;

    Button forgotPasswordButton;
    Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.getBaseContext();

        db = DB.getInstance();

        newUser();
        forgotPass();

        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginText = (EditText) findViewById(R.id.editText);
        passwordText = (EditText) findViewById(R.id.editText2);
        loginButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        checkLogin(v);
                    }
                }
        );
    }

    private void forgotPass() {


        forgotPasswordButton = (Button) findViewById(R.id.forgotPasswordButton);

        EditText email = (EditText)findViewById(R.id.editTextForgotPassEmail);

        forgotPasswordButton.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        getPopUp();
                    }
                }
        );

    }

    private void getPopUp(){
       try {
           LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           View layout = inflater.inflate(R.layout.popup_forgot_pass, (ViewGroup) findViewById(R.id.forgotPassView));
           popUp = new PopupWindow(layout, 500, 500, true);
           popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
           send = (Button)layout.findViewById(R.id.sendRecoveryEmailButton);
           send.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           closePopUp();
                                       }
                                   }
           );
       }catch(Exception e){
           e.printStackTrace();
       }

    }

    private void closePopUp(){

                                        //if(true) {//UserModel.recoverPassword(email.getText().toString());
                                        Helper.toast("Mail sent", context);
                                        popUp.dismiss();
                                        //}else{
                                        // Helper.toast("There is no such email", context);
                                        // }


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
        Button newUserButton = (Button) findViewById(R.id.newUserButton);
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), createUserAcitivity.class);
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
