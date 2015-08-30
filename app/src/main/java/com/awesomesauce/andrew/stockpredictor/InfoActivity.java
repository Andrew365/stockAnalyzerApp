package com.awesomesauce.andrew.stockpredictor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

import android.os.Handler;

import java.io.IOException;



public class InfoActivity extends Activity {
    private EditText tickerView;

    final private String TAG = InfoActivity.class.getSimpleName();
    public static final String PREFS = "myPrefs";

    private boolean continueLoadingAnimation;
    private TextView ticker;
   private TextView CurrentPriceView;
    private TextView LastYearsPriceView;
   private  TextView LastSixMonthsPriceView;
  private  TextView LastMonthsPriceView;
    private TextView avgPriceView;
  private  TextView BuyView;
   private TextView sellValueView;
  private TextView predPriceView;
    private TextView loading;
    private CurrentData mCurrentData;
    private EditText sinceView;
    private boolean mstatus;
    private TextView since1;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Button button = (Button) findViewById(R.id.button);
        getPrediction("GOOG", 2000);
        tickerView = (EditText) findViewById(R.id.ticker);
        sinceView = (EditText) findViewById(R.id.since);


        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                String tick = tickerView.getText().toString();
                //convert the editText number from string to since again
                try {
                    int since = Integer.parseInt(sinceView.getText().toString());
                    getPrediction(tick, since);
                } catch (NumberFormatException nfe) {

                }

                mstatus = true;

            }


        });





    }
    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_showWithYahoo) {
            tickerView = (EditText) findViewById(R.id.ticker);

            String tick = tickerView.getText().toString();

            String url = "http://finance.yahoo.com/q?s=" + tick;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }

        if(id == R.id.action_showWithGoogle){

            tickerView = (EditText) findViewById(R.id.ticker);

            String tick = tickerView.getText().toString();
            String url = "https://www.google.com/finance?q=" + tick;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public void getPrediction (String ticker, int since){
        String apiKey = "4bce061aa735532411ec418104f9e24f";


        String stockUrl = "http://acw.one/api/?check="+ apiKey+"&since="+ since + "&ticker=" +  ticker;

        if(isNetworkAvailable())
        {
            //make all views blank
            clearViews();

            //set text for loading view
            makeLoadingAnimation();
            continueLoadingAnimation = true;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(stockUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    Log.e(TAG, "hit failure thing");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            clearLoading();continueLoadingAnimation = false;
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mCurrentData = getCurrentDetails(jsonData);


                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    clearLoading();continueLoadingAnimation = false;

                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "IO exception caught", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Json exception caught", e);
                    }
                }
            });
        }

        else {
            Toast.makeText(this, "Network Not available",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void makeLoadingAnimation() {
        loading = (TextView) findViewById(R.id.loading);
        //loading.setText("Loading...");
        mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading = (TextView) findViewById(R.id.loading);
                if(continueLoadingAnimation) {
                    loading.setText("Loading");
                    nextStep();
                }
            }
        }, 500);

    }

    private void nextStep() {
        loading = (TextView) findViewById(R.id.loading);
        //loading.setText("Loading...");
        mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading = (TextView) findViewById(R.id.loading);
                if(continueLoadingAnimation) {
                    loading.setText("Loading.");
                    nextStep1();
                }
            }
        }, 500);

    }

    private void nextStep1() {

        loading = (TextView) findViewById(R.id.loading);
        //loading.setText("Loading...");
        mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading = (TextView) findViewById(R.id.loading);
                if(continueLoadingAnimation) {
                    loading.setText("Loading..");
                    nextStep2();
                }
            }
        }, 500);
    }

    private void nextStep2() {
        loading = (TextView) findViewById(R.id.loading);
        //loading.setText("Loading...");
        mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading = (TextView) findViewById(R.id.loading);

                if(continueLoadingAnimation) {
                    loading.setText("Loading...");
                    makeLoadingAnimation();
                }else{
                    clearLoading();
                }
            }
        }, 500);
    }

    public void clearLoading(){
        loading = (TextView) findViewById(R.id.loading);
        loading.setText("");
    }





    private void alertUserAboutError() {
        ticker = (TextView) findViewById(R.id.ticker);
        ticker.setText("Not Found");
    }

    private void clearViews() {


        ticker = (TextView) findViewById(R.id.ticker1);
        CurrentPriceView = (TextView) findViewById(R.id.currPrice);
        LastYearsPriceView = (TextView) findViewById(R.id.lastYearsPrice);
        LastSixMonthsPriceView = (TextView) findViewById(R.id.lastSixMonthsPrice);
        LastMonthsPriceView = (TextView) findViewById(R.id.lastMonthsPrice);
        avgPriceView = (TextView) findViewById(R.id.avgPrice);
        BuyView = (TextView) findViewById(R.id.buy);
        sellValueView = (TextView) findViewById(R.id.sellValue);
        predPriceView = (TextView) findViewById(R.id.predPrice);
        loading = (TextView) findViewById(R.id.loading);
        since1 = (TextView) findViewById(R.id.since1);

        ticker.setText("");
        CurrentPriceView.setText("");
        LastYearsPriceView.setText("");
        LastSixMonthsPriceView.setText("");
        LastMonthsPriceView.setText("");
        avgPriceView.setText("");
        BuyView.setText("");
        sellValueView.setText("");
        predPriceView.setText("");
        since1.setText("");

    }

    public void clearViewsWithSpaces(){

        ticker = (TextView) findViewById(R.id.ticker1);
        CurrentPriceView = (TextView) findViewById(R.id.currPrice);
        LastYearsPriceView = (TextView) findViewById(R.id.lastYearsPrice);
        LastSixMonthsPriceView = (TextView) findViewById(R.id.lastSixMonthsPrice);
        LastMonthsPriceView = (TextView) findViewById(R.id.lastMonthsPrice);
        avgPriceView = (TextView) findViewById(R.id.avgPrice);
        BuyView = (TextView) findViewById(R.id.buy);
        sellValueView = (TextView) findViewById(R.id.sellValue);
        predPriceView = (TextView) findViewById(R.id.predPrice);
        loading = (TextView) findViewById(R.id.loading);
        since1 = (TextView) findViewById(R.id.since1);

        ticker.setText(" ");
        CurrentPriceView.setText(" ");
        LastYearsPriceView.setText(" ");
        LastSixMonthsPriceView.setText(" ");
        LastMonthsPriceView.setText(" ");
        avgPriceView.setText(" ");
        BuyView.setText(" ");
        sellValueView.setText(" ");
        predPriceView.setText(" ");
        since1.setText("");

    }

    private void toggleRefresh() {
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



    private void updateDisplay() {



        ticker = (TextView) findViewById(R.id.ticker1);
        CurrentPriceView = (TextView) findViewById(R.id.currPrice);
        LastYearsPriceView = (TextView) findViewById(R.id.lastYearsPrice);
        LastSixMonthsPriceView = (TextView) findViewById(R.id.lastSixMonthsPrice);
        LastMonthsPriceView = (TextView) findViewById(R.id.lastMonthsPrice);
        avgPriceView = (TextView) findViewById(R.id.avgPrice);
        BuyView = (TextView) findViewById(R.id.buy);
        sellValueView = (TextView) findViewById(R.id.sellValue);
        predPriceView = (TextView) findViewById(R.id.predPrice);
        loading = (TextView) findViewById(R.id.loading);
        since1 = (TextView) findViewById(R.id.since1);
        if(mstatus) {

        ticker.setText(mCurrentData.getMticker() + "");
        CurrentPriceView.setText("Price: " + mCurrentData.getMcurrPrice() + "");
        LastYearsPriceView.setText("Last Year's Avg Price: " + mCurrentData.getMlavgPrice() + "");
        LastSixMonthsPriceView.setText("Last Six Month's Avg Price: " + mCurrentData.getMl6avgPrice() + "");
        LastMonthsPriceView.setText("Last Month's Avg Price: " + mCurrentData.getMl1avgPrice() + "");
        avgPriceView.setText("Avg Price: " + mCurrentData.getMavgPrice() + "");
        BuyView.setText("Buy: " + mCurrentData.getMbuy() + "");
        sellValueView.setText("Sell Value: " + mCurrentData.getMsellValue() + "");
        predPriceView.setText("Predicted Price: " + mCurrentData.getMpredprice() + "");
        since1.setText(mCurrentData.getMsince() + "");

        loading.setText("");
    }else{
        clearViews();
            loading.setText("Oops, That ticker is not found");
    }

    }



    private CurrentData getCurrentDetails(String jsonData) throws JSONException {
        JSONObject currentStockData = new JSONObject(jsonData);


        CurrentData currentData = new CurrentData();
        currentData.setMticker(currentStockData.getString("ticker"));
        String ticker = currentStockData.getString("ticker");
        if(!currentStockData.isNull("avgPrice")){
                currentData.setMavgPrice(currentStockData.getDouble("avgPrice"));
                currentData.setMbuy(currentStockData.getString("buy"));
                currentData.setMcurrPrice(currentStockData.getDouble("currentPrice"));
                currentData.setMlavgPrice(currentStockData.getDouble("lastYearsAvgPrice"));
                currentData.setMl6avgPrice(currentStockData.getDouble("lastSixMonthsAvgPrice"));
                currentData.setMl1avgPrice(currentStockData.getDouble("lastMonthsAvgPrice"));
                currentData.setMpredprice(currentStockData.getDouble("predPrice"));
                currentData.setMsellValue(currentStockData.getDouble("sellValue"));
                currentData.setMsince(currentStockData.getInt("since"));
            mstatus = true;
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clearViews();
                    }
                });
            mstatus = false;
            }
        return currentData;
    }



}
