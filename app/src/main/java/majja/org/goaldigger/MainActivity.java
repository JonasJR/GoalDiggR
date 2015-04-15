package majja.org.goaldigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {
        public static EditText loginText;
        public static EditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        //if(login is correct)
        DB db = new DB();
        String email, password;

        email = loginText.getText().toString();
        password = passwordText.getText().toString();

        db.login(email, password);

        JSONObject obj = null;
        while (db.getReturnData() == null) {

        }
        obj = db.getReturnData();

        /*if(login.getText().toString().equals("Kalle") && password.getText().toString().equals("blomma")){
            Intent intent = new Intent(v.getContext(), ProjectHandlerActivity.class);
            startActivityForResult(intent, 0);
            Toast.makeText(MainActivity.this, "Inlogg: " + login.getText() + "\nLösenord:" + password.getText()
                    , Toast.LENGTH_SHORT).show();
        }
       else{
            Toast.makeText(MainActivity.this, "Fel!" + "\nInlogg:" + login.getText() + "\nLösenord:" + password.getText(), Toast.LENGTH_SHORT).show();
        }
        */
        if (obj != null) {
            try {
                Helper.toast(obj.getString("message").toString(), this);
            } catch (JSONException e) {
            }
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
