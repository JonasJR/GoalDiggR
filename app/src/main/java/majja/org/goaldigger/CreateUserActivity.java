package majja.org.goaldigger;

import android.app.ProgressDialog;
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


/**
 * Created by Goaldigger on 2015-04-16.
 */
public class CreateUserActivity extends ActionBarActivity {

    Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        context = this.getBaseContext();
        final EditText userName = (EditText)findViewById(R.id.editTextAddUsername);
        final EditText email = (EditText)findViewById(R.id.editTextAddEmail);
        final EditText pass = (EditText)findViewById(R.id.editTextAddPassword);
        final EditText passConfirm = (EditText)findViewById(R.id.editTextAddPassword2);

        Button createUserButton = (Button)findViewById(R.id.acceptNewUserButton);
        createUserButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        new CheckCreateUser(userName.getText().toString(), email.getText().toString(),
                                pass.getText().toString(), passConfirm.getText().toString()).execute();
                    }
                }
        );
    }

    private class CheckCreateUser extends AsyncTask<Void, Void, Boolean>{

        String userName, email, pass, passConfirm;
        ProgressDialog pd;
        public CheckCreateUser(String username, String email, String pass, String passConfirm){
            this.userName = username;
            this.email = email;
            this.pass = pass;
            this.passConfirm = passConfirm;
        }

        protected void onPreExecute() {
            pd = ProgressDialog.show(CreateUserActivity.this, "", "Loading...");
        }

        protected Boolean doInBackground(Void... params) {
            if(User.createUser(userName, email, pass, passConfirm)) {
                return true;
            }else{
                return false;
            }
        }

        protected void onPostExecute(Boolean created) {
            if(created){
                Helper.toast("Det gick!", context);
                Intent intent = new Intent(CreateUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Helper.toast(User.errorMessage(), context);
            }
            pd.dismiss();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user, menu);
        return true;
    }

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

    public void onBackPressed() {
        Intent intent = new Intent(CreateUserActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}