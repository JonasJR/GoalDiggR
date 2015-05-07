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


public class CreateAUserAcitivity extends ActionBarActivity {

    Context context;

    @Override
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
                        boolean created = User.createUser(userName.getText().toString(), email.getText().toString(), pass.getText().toString(), passConfirm.getText().toString());
                        if(created){
                            Helper.toast("Det gick!", context);
                            Intent intent = new Intent(v.getContext(), ProjectHandlerActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Helper.toast(User.errorMessage(), context);
                        }
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user, menu);
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
