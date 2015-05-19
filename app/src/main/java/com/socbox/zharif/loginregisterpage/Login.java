package com.socbox.zharif.loginregisterpage;

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


public class Login extends ActionBarActivity {

    public TextView titleEmail,titlePassword;
    public EditText inputEmail,inputPassword;

    public Button loginButton,linkRegister;

    public DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        openDB();
        setLayoutItem();
        addLoginButton();
        addLinkRegisterButton();

    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void openDB (){
        databaseHandler = new DatabaseHandler(this);
        databaseHandler = databaseHandler.open();
    }

    public void setLayoutItem() {

        titleEmail      = (TextView)findViewById(R.id.textEmail);
        titlePassword   = (TextView)findViewById(R.id.textPassword);
        inputEmail      = (EditText)findViewById(R.id.editEmail);
        inputPassword   = (EditText)findViewById(R.id.editPassword);
    }

    public void addLoginButton(){
        loginButton = (Button)findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                String storedPassword=databaseHandler.getUserLogin(email);

                if (email.trim().length() > 0 && password.trim().length() > 0) {

                    /**
                     * Reference email validation
                     * http://www.mirc.org/mishbox/reference/re.email.htm
                     * Format email example:
                     * example@example.com - matches
                     * example.example@com - non-matches
                     */
                    if (inputEmail.getText().toString().matches("^([a-zA-Z0-9_\\-\\.]+)" +
                            "@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")){

                        if (password.equals(storedPassword)) {
                            Intent homeScreenIntent = new Intent(Login.this, HomeScreen.class);
                            startActivity(homeScreenIntent);
                            Toast.makeText(getApplicationContext(), "Access Granted!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Sign Up!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        inputEmail.setError("Invalid Email!");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Details!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addLinkRegisterButton(){
        linkRegister = (Button)findViewById(R.id.buttonToRegisterPage);
        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Link the page to register page
                Intent registerPageIntent = new Intent(Login.this, RegisterPG.class);
                startActivity(registerPageIntent);
            }
        });
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
