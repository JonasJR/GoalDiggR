package majja.org.goaldigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        Button loginButton = (Button)findViewById(R.id.loginButton); //Knappen är knappen
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

    public void checkLogin(View v) {
        String email, password;
        LoginModel loginModel = new LoginModel(this.db);

    public void checkLogin(View v){
        //if(login is correct)
        if(login.getText().toString().equals("Kalle") && password.getText().toString().equals("blomma")){
            Intent intent = new Intent(v.getContext(), ProjectHandlerActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
        }
        else {
            Helper.toast(loginModel.getMessage(), this);
        }
    }

    public void newUser(){
        Button newUser = (Button) findViewById(R.id.newUserButton);
        newUser.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(v.getContext(), createUserAcitivity.class);
                        startActivityForResult(intent, 0);
                    }
                }
        );
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
