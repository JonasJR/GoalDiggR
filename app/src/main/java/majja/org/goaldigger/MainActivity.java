package majja.org.goaldigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

public class MainActivity extends ActionBarActivity {
    public static EditText loginText;
    public static EditText passwordText;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        final PopupWindow popUp = new PopupWindow(this);
        Button forgotPasswordButton = (Button) findViewById(R.id.forgotPasswordButton);

        forgotPasswordButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        popUp.showAtLocation(v, Gravity.CENTER, 10, 10);
                    }
                }
                
        );
    }

    public void checkLogin(View v) {
        String email, password;
        LoginModel loginModel = new LoginModel(this.db);

        email = loginText.getText().toString();
        password = passwordText.getText().toString();
        UserModel user = new UserModel("test", email, password);

        if (loginModel.login(email, password)) {
            Intent intent = new Intent(v.getContext(), ProjectHandlerActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
        }
        else {
            Helper.toast(loginModel.getMessage(), this);
        }
    }

    private void newUser(){
        Button newUser = (Button) findViewById(R.id.newUserButton);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), createUserAcitivity.class);
                startActivity(intent);
            }
        });
        /*
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(v.getContext(), createUserAcitivity.class);
                        startActivity(intent);
                    }
                }
        );
        */
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
