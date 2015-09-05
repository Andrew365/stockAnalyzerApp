package com.awesomesauce.andrew.stockpredictor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegisterActivity extends Activity {

    private Button regButton;
    private final String TAG = RegisterActivity.class.getSimpleName();
    private String jsonData;
    private int statusCode;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editConfrim;
    private TextView errorView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        errorView = (TextView) findViewById(R.id.errorView);
        errorView.setVisibility(View.INVISIBLE);


        regButton = (Button) findViewById(R.id.registerButton);
        editPassword = (EditText) findViewById(R.id.pass1);
        editConfrim = (EditText) findViewById(R.id.pass2);
        editUsername = (EditText) findViewById(R.id.usernamePost);


        regButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                String Password = editPassword.getText().toString();
                String Username = editUsername.getText().toString();
                String Confirm = editConfrim.getText().toString();

                if(Password.matches("")){
                    errorView.setVisibility(View.VISIBLE);
                    errorView.setText("Cannot have an empty password");
                }else if(Username.matches("")){
                    errorView.setVisibility(View.VISIBLE);
                    errorView.setText("Cannot have an empty username");

                }else if(Confirm.matches("")) {
                    errorView.setVisibility(View.VISIBLE);
                    errorView.setText("Cannot have an empty username");
                }else if(!Password.equals(Confirm)) {
                    errorView.setVisibility(View.VISIBLE);
                    errorView.setText("Passwords do not match");
                }else{
                    sendRegisterRequest();
                }
            }
        });
    }
    public int getStatusCode(String jsonData) throws JSONException {

        JSONObject statusCode = new JSONObject(jsonData);

        int code = statusCode.getInt("status");

        return code;
    }

    private int sendRegisterRequest() {

        HashCode hasher = new HashCode();

        editPassword = (EditText) findViewById(R.id.pass1);
        editUsername = (EditText) findViewById(R.id.usernamePost);
        editConfrim = (EditText) findViewById(R.id.pass2);

        //get values from edit texts
        String password = editPassword.getText() + "";
        String username = editUsername.getText() + "";
        String confirmPass = editConfrim.getText() + "";

        String hashedPass = hasher.computeSHAHash(password);
        String hashedConfirmPass = hasher.computeSHAHash(confirmPass);


        //try to login with function
        String apiKey = "k982kdhadnvna02w9fjsj10roajajs92";

        String registerKey = "o19p4o2infkjsdnaasdf3oirlkamfaiasfjp9y984hfkjfsdbfy8ey";

        String registerUrl = "http://acw.one/api/AuthForApp.php?" +
                "key="+apiKey+"&username="+username+
                "&password="+hashedPass+
                "&register=true&registerKey="
                +registerKey+"&confirmPassword="+hashedConfirmPass;


            statusCode = getJson(registerUrl);

        return statusCode;
    }

    public void AllowAccess(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);


    }
    public int getJson(String url){
        if(isNetworkAvailable())
        {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            toggleRefresh();
                        }
                    });
                    Log.e(TAG, "hit failure thing");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    try {
                        jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            Log.v(TAG, jsonData);


                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        statusCode = getStatusCode(jsonData);
                                        if ((statusCode == 0) || (statusCode == 1) ){
                                            AllowAccess();
                                        }else{
                                            analyzeErrorCode(statusCode);
                                        }
                                    } catch (JSONException e) {
                                        Log.e(TAG, "JSON Exception found " + e);
                                    }
                                    // updateDisplay();
                                }
                            });

                        } else {
                            Log.e(TAG, "we have a problem");
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "IO exception caught", e);
                    }
//                    catch (JSONException e) {
//                        Log.e(TAG, "Json exception caught", e);
//                    }
                }
            });
        }

        else {
            Toast.makeText(this, "Network Not available",
                    Toast.LENGTH_LONG).show();
        }
        return statusCode;
    }
/*

2 == username found but password doesnt match
3 == tried to register with taken username
4 == tried to login but username not found
5 == tried to register with bad registerKey
6 == passwords dont match
7 == confirm password not set
 */

    private void analyzeErrorCode(int statusCode) {
        errorView = (TextView) findViewById(R.id.errorView);
        errorView.setVisibility(View.VISIBLE);
        switch (statusCode){
            case 3:
                errorView.setText("Username is taken");
                break;
            case 6:
                errorView.setText("Passwords dont match");
                break;

        }
        editPassword = (EditText) findViewById(R.id.pass1);
        editConfrim = (EditText) findViewById(R.id.pass2);

        String Password = editPassword.getText().toString();

        if(Password.matches("")){
            errorView.setText("Cannot have an empty password");
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;

        }

        return isAvailable;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
