package com.example.springroll.database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import library.DatabaseHandler;
import library.UserFunctions;

/**
 * Created by SpringRoll on 1/28/2016.
 */
public class RegisterActivity extends Activity {
    private final static String LOG_TAG = RegisterActivity.class.getSimpleName();
    //private final CountDownLatch timeoutLatch = new CountDownLatch(2);
    /**
     * JSON Response node names.
     **/
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";

    /**
     * Defining layout items.
     **/
    private AutoCompleteTextView mUsername;
    private EditText mPassword, mRePassword;
    private TextView mError;
    private Button mSignUpButton;
    private NetCheck mAuthTask = null;
    private UserFunctions functionsManager;
    private DatabaseHandler db;
    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mError = (TextView) findViewById(R.id.register_error_message);
        mUsername = (AutoCompleteTextView) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mRePassword = (EditText) findViewById(R.id.re_password);
        mSignUpButton = (Button) findViewById(R.id.user_sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptRegister(v);
            }
        });
        //timeoutLatch.countDown();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Touch event bypasses waiting for the splash timeout to expire.
        //timeoutLatch.countDown();
        return true;
    }

    /**
     * Attempts to register the account specified by the register form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual register attempt is made.
     */
    private void AttemptRegister(View view){
        Log.d(LOG_TAG,"Launching Attempt Register...");
        if(mAuthTask != null){
            return;
        }

        //Reset errors.
        mUsername.setError(null);
        mPassword.setError(null);
        mRePassword.setError(null);

        //Store values at the time of the login attempt.
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String rePassword = mRePassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //Check for a valid password, if the user entered one.
        if(TextUtils.isEmpty(password)){
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        }
        else if(!password.equals(rePassword)){
            mRePassword.setError(getString(R.string.error_invalid_rePassword));
            focusView = mRePassword;
            cancel = true;
        }
        else if(!isPasswordValid(password)){
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        //Check for a valid username input
        if(TextUtils.isEmpty(username)){
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel =true;
        }else if(!isUserValid(username)){
            mUsername.setError(getString(R.string.error_invalid_username));
            focusView = mUsername;
            cancel =true;
        }

        if(cancel){
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new NetCheck();
            mAuthTask.execute();
        }
    }

    /**
     * Validates username format
     * @param username username
     * @return returns true if username format is valid, false otherwise.
     */
    private boolean isUserValid(String username) {
        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(username);
        boolean regexFound = m.find();
        if(username.length() > 4 && username.length() < 12){
            for(int i = 0; i < username.length();i++){
                if(!Character.isWhitespace(username.charAt(i)) && regexFound)
                    return false;
            }
            return true;
        }else
            return false;
    }

    /**
     * Validate password format
     * @param password password
     * @return returns true if password format is valid, false otherwise.
     */
    private boolean isPasswordValid(String password) {
        if (password.length() >= 5 && password.length() < 17) {
            for (int i = 0; i < password.length(); i++) {
                if (Character.isWhitespace(password.charAt(i))) {
                    return false;
                }
            }
            return true;
        }else
            return false;
    }

    /**
     * Async Task to check whether internet connection is working
     **/

    private class NetCheck extends AsyncTask<String, String, Boolean> {
        private final String LOG_TAG = NetCheck.class.getSimpleName();
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute() {
            Log.d(LOG_TAG,"onPreExecute...");
            super.onPreExecute();
            nDialog = new ProgressDialog(RegisterActivity.this);
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Checking Network");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {

        /**
         * Gets current device state and checks for working internet connection by trying Google.
         **/
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Log.d(LOG_TAG,"onPostExecute...");
            mAuthTask = null;
            mError.setText("");

            if (success) {
                nDialog.dismiss();
                new ProcessRegister().execute();
            } else {
                nDialog.dismiss();
                mError.setText("Error in Network Connection");
            }
        }

        @Override
        protected void onCancelled() {
            //super.onCancelled();
            mError.setText("");
            mAuthTask = null;
        }
    }

    private class ProcessRegister extends AsyncTask<String, String, JSONObject> {
        private final String LOG_TAG = ProcessRegister.class.getSimpleName();
        /**
         * Defining Process dialog
         **/
        private ProgressDialog pDialog;

        String password, username;
        @Override
        protected void onPreExecute() {
            Log.d(LOG_TAG,"onPreExecute...");
            super.onPreExecute();
            mUsername = (AutoCompleteTextView) findViewById(R.id.username);
            mPassword = (EditText) findViewById(R.id.password);
            username= mUsername.getText().toString();
            password = mPassword.getText().toString();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Registering ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            functionsManager = new UserFunctions(getApplicationContext());
            JSONObject json = functionsManager.registerUser(username, password);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            Log.d(LOG_TAG,"OnPostExecute the JSON...");
            /**
             * Checks for success message.
             **/
            try {
                if (json.getString(KEY_SUCCESS) != null) {
                    mError.setText("");
                    String res = json.getString(KEY_SUCCESS);

                    String red = json.getString(KEY_ERROR);

                    if(Integer.parseInt(res) == 1){
                        pDialog.setTitle("Getting Data");
                        pDialog.setMessage("Loading Info");
                        mError.setText("Successfully Registered");

                        /**
                         * Removes all the previous data in the SQlite database
                         **/
                        functionsManager.logoutUser();

                        /**
                         * Launch Registered screen
                         **/
                        Intent signIn = new Intent(getApplicationContext(), SignInActivity.class);

                        /**
                         * Close all views before launching Registered screen
                         **/
                        signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pDialog.dismiss();



                        startActivity(signIn);
                        /**
                         * Close Register Screen
                         **/
                        finish();
                    }
                    else if (Integer.parseInt(red) ==2){
                        pDialog.dismiss();
                        mError.setText("User already exists");
                    }
                    else if (Integer.parseInt(red) ==3){
                        pDialog.dismiss();
                        mError.setText("Invalid username id");
                    }
                }
                else{
                    pDialog.dismiss();
                    mError.setText("Error occured in registration");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
