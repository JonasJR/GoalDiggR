package majja.org.goaldigger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
        public static EditText login;
        public static EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button)findViewById(R.id.loginButton); //Knappen är knappen
        login = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        loginButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        checkLogin(v);
                        Toast.makeText(MainActivity.this, "Inlogg: " + login.getText() + "\nLösenord:" + password.getText()
                                , Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void checkLogin(View v){
        //if(login is correct)
        Intent intent = new Intent(v.getContext(), ProjectHandlerActivity.class);
        startActivityForResult(intent, 0);
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
