package com.awesomesauce.andrew.stockpredictor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    final private String TAG = MainActivity.class.getSimpleName();

    private CurrentData mCurrentData;
    private EditText pinView;
    private int count;
    private int pin;
    private int password;
    private TextView oopsText;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        password = 1903;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showButton = (Button) findViewById(R.id.show);
        pinView = (EditText) findViewById(R.id.pin);
        oopsText = (TextView) findViewById(R.id.Oops);
        mHandler = new Handler();
        showButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //convert the editText number from string to since again
                try {
                     pin = Integer.parseInt(pinView.getText().toString());
                } catch(NumberFormatException nfe) {

                }

                if(pin == password){
                    AllowAccess();
                }else{
                    oopsText.setText("");
                    mHandler.postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            wrongPin();
                        }
                    }, 50);

                }

            }
        });
    }

    private void wrongPin() {

        oopsText.setText("Wrong Pin");

    }

    public void AllowAccess(){
        Intent intent = new Intent(this, InfoActivity.class);
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

