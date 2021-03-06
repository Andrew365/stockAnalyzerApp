package com.awesomesauce.andrew.stockpredictor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
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

public class MainActivity extends Activity {
    final private String TAG = MainActivity.class.getSimpleName();

    private int password;
    private TextView oopsText;
    private Handler mHandler;
    private Button signupButton;
    private EditText editUsername;
    private EditText editPassword;
    private Button loginButton;
    private String jsonData;
    private int statusCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        password = 1903;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.show);
        oopsText = (TextView) findViewById(R.id.usnameholder);
        mHandler = new Handler();
        signupButton = (Button) findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tryToRegister();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tryToLogin();
            }
        });
    }

    private void tryToLogin() {
        editPassword = (EditText) findViewById(R.id.password);
        editUsername = (EditText) findViewById(R.id.username);
        
        //get values from edit texts
        String password = editPassword.getText() + "";
        String username = editUsername.getText() + "";

        HashCode hasher = new HashCode();

        String hashedPass = hasher.computeSHAHash(password);

        //try to login with function
        String apiKey = "k982kdhadnvna02w9fjsj10roajajs92";

        String loginUrl = "http://acw.one/api/AuthForApp.php?key="
                + apiKey + "&username="+ username+ "&password="+ hashedPass;

        getJson(loginUrl);


       // oopsText.setText(hashedPass);
    }



public int getStatusCode(String jsonData) throws JSONException {

    JSONObject statusCode = new JSONObject(jsonData);

    int code = statusCode.getInt("status");

    return code;
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

                                            EditText username = (EditText) findViewById(R.id.username);
                                            String usernameval = username.getText().toString();

                                            Log.d(TAG, usernameval);
                                        }else{
                                         //   Log.v(TAG, )
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




    private void tryToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


    public void AllowAccess(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);


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

