package com.socbox.zharif.loginregisterpage;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Reference database : http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 *
 * Created by zharif20 on 5/10/15.
 *
 * Change addRegisteruser, and listUniversity for the numberpicker
 */
public class RegisterPG extends ActionBarActivity {


    public TextView titleEmail,titlePassword,titleUniversity;
    public EditText inputEmail,inputPassword;
    public NumberPicker inputUniversity;

    public Button btnRegister;

    public DatabaseHandler databaseHandler;

    public String whichUniversity;

    private static final String url_create_account = "http://46.101.25.6/database_connection_code/db_login_create_account.php";

    //These are the tags (or names) of the variables to be retrieved from the JSON array the server will return
    private static final String TAG_SUCCEEDED = "succeeded";
    private static final String TAG_USERDATA = "user_data";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_DEBUGMESSAGE = "debugMessage";

    // JSON parser class
    JSONParserForDatabase jsonParser = new JSONParserForDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        openDB();
        setUpLayoutItems();
        listUniversity();
        addRegisterUser();

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    public void openDB(){
//        databaseHandler = new DatabaseHandler(this);
//        databaseHandler = databaseHandler.open();
//    }

    private void setUpLayoutItems() {

        titleEmail      =   (TextView)findViewById(R.id.textEmail);
        titlePassword   =   (TextView)findViewById(R.id.textPassword);
        titleUniversity =   (TextView)findViewById(R.id.textUniversity);
        inputEmail      =   (EditText)findViewById(R.id.editEmail);
        inputPassword   =   (EditText)findViewById(R.id.editPassword);
        inputUniversity =   (NumberPicker)findViewById(R.id.universityPicker);


    }

    private void addRegisterUser() {

        btnRegister = (Button) findViewById(R.id.buttonDone);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
//                int university = inputUniversity.getValue();

                if (email.trim().length() > 0 && password.trim().length() > 0) {

                    if (!email.isEmpty()) {

                        if (!password.isEmpty()) {
                            /**
                             * Reference email validation
                             * http://www.mirc.org/mishbox/reference/re.email.htm
                             * Format email example:
                             * example@example.com - matches
                             * example.example@com - non-matches
                             */
                            if (inputEmail.length() > 0 && inputEmail.getText().toString().matches("^([a-zA-Z0-9_\\-\\.]+)" +
                                    "@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {

                                if (inputPassword.length() > 5) {

                                    //if (registerAccount.inputEmail.getText().toString().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$")) {

    //                                Log.i(TAG, "Guardian Galaxy");
                                    //registerUser(email, password);
                                    addValidAccount();
                                    new Login().execute();
    //                                databaseHandler.addUser(email, password);
    //                                Toast.makeText(getApplicationContext(), "Eureka!", Toast.LENGTH_SHORT).show();

    //                                    } else {
    //                                        registerAccount.inputEmail.setError("Invalid Email");
    //                                    }
                                } else {
                                    inputPassword.setError("Password should be minimum 6 characters");
                                }
                            } else {
                                inputEmail.setError("Invalid Email");
                            }
                        } else {
                            inputPassword.setError("Request Password!");
                        }
                    } else {
                        inputEmail.setError("Request Email!");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Details!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void listUniversity (){

        final String[] arrayString= new String[129];

        ///////////////////////         A           //////////////////////////

        arrayString[0]="Choose University";
        arrayString[1]="Aberystwyth University";
        arrayString[2]="Anglia Ruskin University";
        arrayString[3]="Aston University";

        ///////////////////////         B           //////////////////////////

        arrayString[4]="Bangor University";
        arrayString[5]="Bath Spa University";
        arrayString[6]="Birmingham City University";
        arrayString[7]="Birmingham Conservatoire";
        arrayString[8]="Bishop Grosseteste University";
        arrayString[9]="Bournemouth University";
        arrayString[10]="BPP University";

        arrayString[11]="Brighton and Sussex Medical School";
        arrayString[12]="Brunel University";
        arrayString[13]="Buckinghamshire New University";

        ///////////////////////         C           //////////////////////////

        arrayString[14]="Camberwell College of Arts";
        arrayString[15]="Camborne School of Mines";
        arrayString[16]="Canterbury Christ Church University";
        arrayString[17]="Cardiff University";
        arrayString[18]="Cardiff International Academy of Voice";
        arrayString[19]="Cardiff University School of Medicine";
        arrayString[20]="Cardiff Metropolitan University";

        arrayString[21]="Chelsea College of Art and Design";
        arrayString[22]="City Law School";
        arrayString[23]="City University, London";
        arrayString[24]="Coventry University";
        arrayString[25]="Cranfield University";

        ///////////////////////         D           //////////////////////////

        arrayString[26]="Dartington College of Arts";
        arrayString[27]="De Montfort University";
        arrayString[28]="Durham University";

        ///////////////////////         E           //////////////////////////

        arrayString[29]="Edge Hill University";
        arrayString[30]="Edinburgh Napier University";

        ///////////////////////         F           //////////////////////////

        arrayString[31]="Falmouth University";

        ///////////////////////         G           //////////////////////////

        arrayString[32]="Glasgow Caledonian University";

        ///////////////////////         H           //////////////////////////

        arrayString[33]="Harper Adams University";
        arrayString[34]="Heriot-Watt University";
        arrayString[35]="Hull York Medical School";

        ///////////////////////         I           //////////////////////////

        arrayString[36]="Imperial College London";

        ///////////////////////         J           //////////////////////////

        ///////////////////////         K           //////////////////////////

        arrayString[37]="Keele University";
        arrayString[38]="King's College London";

        ///////////////////////         L           //////////////////////////

        arrayString[39]="Lancaster University";
        arrayString[40]="Leeds Beckett University";
        arrayString[41]="Leeds Trinity University";
        arrayString[42]="London Business School";
        arrayString[43]="London College of Communication";
        arrayString[44]="London College of Fashion";
        arrayString[45]="London School of Economics";
        arrayString[46]="London South Bank University";
        arrayString[47]="Loughborough University";
        arrayString[48]="Liverpool Hope University";
        arrayString[49]="Liverpool John Moores University";

        ///////////////////////         M           //////////////////////////

        arrayString[50]="Manchester Metropolitan University";
        arrayString[51]="Middlesex University]";

        ///////////////////////         N           //////////////////////////

        arrayString[52]="Northumbria University";
        arrayString[53]="Nottingham Trent University";

        ///////////////////////         O           //////////////////////////

        arrayString[54]="Oxford Brookes University";

        ///////////////////////         P           //////////////////////////

        ///////////////////////         Q           //////////////////////////

        arrayString[55]="Queen's University Belfast";
        arrayString[56]="Queen Margaret University";

        ///////////////////////         R           //////////////////////////

        arrayString[57]="Roehampton University";
        arrayString[58]="Royal College of Art";

        ///////////////////////         S           //////////////////////////

        arrayString[59]="Sheffield Hallam University";
        arrayString[60]="Southampton Solent University";
        arrayString[61]="Staffordshire University";
        arrayString[62]="Swansea University";

        ///////////////////////         T           //////////////////////////

        arrayString[63]="Teesside University";
        arrayString[64]="The Open University";
        arrayString[65]="The Robert Gordon University";

        ///////////////////////         U           //////////////////////////

        arrayString[66]="University Centre at Blackburn College";
        arrayString[67]="University College Birmingham";
        arrayString[68]="University for the Creative Arts";
        arrayString[69]="University of Aberdeen";
        arrayString[70]="University of Bath";

        arrayString[71]="University of Bedfordshire";
        arrayString[72]="University of Birmingham";
        arrayString[73]="University of Bolton";
        arrayString[74]="University of Bradford";
        arrayString[75]="University of Brighton";
        arrayString[76]="University of Bristol";
        arrayString[77]="University of Buckingham";
        arrayString[78]="University of Cambridge";
        arrayString[79]="University of Central Lancashire";
        arrayString[80]="University of Chester";

        arrayString[81]="University of Chichester";
        arrayString[82]="University of Cumbria";
        arrayString[83]="University of Derby";
        arrayString[84]="University of Dundee";
        arrayString[85]="University of East Anglia";
        arrayString[86]="University of East London School of Law";
        arrayString[87]="University of Edinburgh";
        arrayString[88]="University of Essex";
        arrayString[89]="University of Exeter";
        arrayString[90]="University of Glasgow";

        arrayString[91]="University of Gloucestershire";
        arrayString[92]="University of Greenwich";
        arrayString[93]="University of Hertfordshire";
        arrayString[94]="University of Huddersfield";
        arrayString[95]="University of Hull";
        arrayString[96]="University of Law";
        arrayString[97]="University of Leeds";
        arrayString[98]="University of Leicester";
        arrayString[99]="University of Lincoln";
        arrayString[100]="University of Liverpool";

        arrayString[101]="University of Manchester";
        arrayString[102]="University of Northampton";
        arrayString[103]="University of Nottingham";
        arrayString[104]="University of Oxford";
        arrayString[105]="University of Plymouth";
        arrayString[106]="University of Portsmouth";
        arrayString[107]="University of Reading";
        arrayString[108]="University of St Andrews";
        arrayString[109]="University of St Mark & St John";
        arrayString[110]="University of Salford";

        arrayString[111]="University of Sheffield";
        arrayString[112]="University of Southampton";
        arrayString[113]="University of South Wales";
        arrayString[114]="University of Stirling";
        arrayString[115]="University of Strathclyde";
        arrayString[116]="University of Surrey";
        arrayString[117]="University of Ulster";
        arrayString[118]="University of Wales";
        arrayString[119]="University of Warwick";
        arrayString[120]="University of West London";

        arrayString[121]="University of Westminster";
        arrayString[122]="University of Wolverhampton";
        arrayString[123]="University of Worcester";
        arrayString[124]="University of York";
        arrayString[125]="University of the Arts London";
        arrayString[126]="University of the Highlands & Islands";


        ///////////////////////         V           //////////////////////////

        ///////////////////////         W           //////////////////////////

        ///////////////////////         X           //////////////////////////

        arrayString[127]="Wimbledon College of Art";

        ///////////////////////         Y           //////////////////////////

        arrayString[128]="York St John University";

        ///////////////////////         Z           //////////////////////////


        inputUniversity.setMinValue(0);
        inputUniversity.setMaxValue((arrayString.length) - 1);
        inputUniversity.setDisplayedValues(arrayString);


        inputUniversity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                whichUniversity = arrayString[inputUniversity.getValue()];
            }
        });
    }

    public void addValidAccount() {
        final Dialog dialog = new Dialog(RegisterPG.this);
        dialog.setContentView(R.layout.activity_valid_account);
        dialog.setTitle("Account Create");

        final TextView accCreate = (TextView)dialog.findViewById(R.id.accCreateText);
        final Button buttonCreate = (Button)dialog.findViewById(R.id.buttonOk);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Link the page to register page
                Intent homeScreenIntent = new Intent(RegisterPG.this, HomeScreen.class);
                startActivity(homeScreenIntent);
                dialog.dismiss();

            }
        });
        dialog.show();
    }


    /**
     * Background Async Task to access the Lgin database. This needs to be done in a background thread
     * or else the APP will crash. One thing to note is you cannot set UI elements (such as seeting the text for a textfield)
     * from a background thread or it will crash. You will need to create a function to run those commands on the main thread.
     * I have created an example function to update a textfield in the UI with the current status of the connection attempt. See
     * the bottom of the file for this. Note also that you can pass variables to an Asynctask as parameters, this example passes
     * a string to tell it whether it is creating an account or logging in
     * */
    class Login extends AsyncTask<String, String, String> {

        /**
         * Before starting the background thread update the status of the connection attempt
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Accessing the login DB in background thread
         */
        protected String doInBackground(String... params) {

            int succeeded;

            try {
                // Building Parameters
                String debugMessage;
                JSONObject json;
                //Get the username and passwords from the textfields (remember, we can get these but cannot set them from this thread)
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                //Create two name value pairs to send to the server, one for the username, one for the password
                List<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
                dataToSend.add(new BasicNameValuePair("username", email));
                dataToSend.add(new BasicNameValuePair("password", password));

                // Send the new username and password to the database by making HTTP POST request
                // A POST request is used as we are writing to the database
                //This is the function you call to access the database, the parameters are:
                //(url of php file to access, "POST" or "GET", the data to send to the server)
                json = jsonParser.makeHttpRequest(url_create_account, "POST", dataToSend);
                //Send the retrieved JSON array to logcat
                Log.d("User login status", json.toString());

                //The returned json object should be an array in the form [Succeeded: (value), debugMessage (message) Login details: [username: (username string), password: (password string)]]

                //Check the variable "succeded" to see if the attempt was successful
                succeeded = json.getInt(TAG_SUCCEEDED);
                //If it was
                if (succeeded == 1) {

                    //Get the message about the login/account creation status
                    //In this case, it will be "Login successful" or "Account succesfully created"
                    debugMessage = json.getString(TAG_DEBUGMESSAGE);
                    //Get the userdata (Username and password) from the response
                    JSONArray userDataArray = json.getJSONArray(TAG_USERDATA);

                    //Get the user login details from JSON Array
                    JSONObject userData = userDataArray.getJSONObject(0);

                    //The best way to store data in the App without having to constantly pass it between activities is
                    //using shared preferences. These allow for variables to be stored until the App is closed.
                    //In this case, we will be using one to staore the username and password
                    SharedPreferences userLoginDetails = getSharedPreferences("userlogindetails", 0);
                    SharedPreferences.Editor editor = userLoginDetails.edit();
                    //Add the username
                    editor.putString("username", userData.getString(TAG_USERNAME));
                    //Add the password
                    editor.putString("password", userData.getString(TAG_PASSWORD));
                    //Commit the changes
                    editor.commit();


                    new Thread() {
                        //Create a new runnable for the UI thread
                        @Override
                        public void run() {
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Update the text field with the status message
                                        addValidAccount();
                                    }
                                });
                            } catch (final Exception ex) {
                                //If an error occured, print this out
                                Log.i("Error:","Exception when updating status message in UI thread");
                            }
                        }
                        //Start the thread
                    }.start();

                    //Create an activity to show the user is logged in (this is just an example I use to
                    //demonstrate that the user has logged in succesfully)
                    //Intent intent = new Intent(RegisterPG.this, HomeScreen.class);
                    //Start the activity
                    //startActivity(intent);

                    //If the login/account creation attempt failed
                } else {
                    //Print this out to inform the user that their attempt failed
                    //debugMessage= json.getString(TAG_DEBUGMESSAGE);
                    //Update the UI
                    //updateStatusMessage(debugMessage);
                }
                //Exception handling
            } catch (JSONException e) {
                //e.printStackTrace();
                //Update the UI with a desription of the error
                //updateStatusMessage("Error: connection problems");
            }

            //Asynctask has to return something, let it return null
            return null;

        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

//        switch (item.getItemId()) {
//            case R.id.action_search:
//                openSearch();
//                return true;
//            case R.id.action_settings:
//                openSettings();
//                return true;
//            default:

            return super.onOptionsItemSelected(item);
        }

}
