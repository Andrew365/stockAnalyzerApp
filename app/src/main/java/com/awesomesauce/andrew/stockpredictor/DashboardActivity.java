package com.awesomesauce.andrew.stockpredictor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class DashboardActivity extends Activity {
    private Button AnalyzerButton;
    private Button SimulatorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SimulatorButton = (Button) findViewById(R.id.toSimButton);
        AnalyzerButton = (Button) findViewById(R.id.toAnaButton);

        SimulatorButton.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {

                   goToSimulatorActivity();

               }
           }
        );

        AnalyzerButton.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {

                   goToInfoActivity();

               }
           }
        );
    }

    private void goToSimulatorActivity() {
        Intent intent = new Intent(this, SimulatorActivity.class);
        startActivity(intent);
    }

    private void goToInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
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
