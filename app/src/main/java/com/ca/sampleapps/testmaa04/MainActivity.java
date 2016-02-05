package com.ca.sampleapps.testmaa04;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.ca.integration.CaMDOIntegration;

public class MainActivity extends Activity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;

    TextView tv1;
    TextView tv2;
    TextView tv3;

    private Handler myHandler;
    private Timer myTimer;

    private SimpleDateFormat myDF = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
    Boolean isTimerStarting = false;

    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);

        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);

        myHandler = new Handler(getMainLooper());
        myTimer = new Timer();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MAA
                CaMDOIntegration.startApplicationTransaction("Timer", "MyTest");

                if(isTimerStarting){
                    myTimer.cancel();
                    tv1.setText("Elapse Time : " + String.valueOf(System.currentTimeMillis() - startTime) + "  (ms)");
                    isTimerStarting=false;
                    btn1.setText("Timer");

                }
                else {
                    startTime = System.currentTimeMillis();

                    myTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            myHandler.post(new Runnable() {
                                public void run() {
                                    Calendar myCal = Calendar.getInstance();
                                    String nowDate = myDF.format(myCal.getTime());
                                    tv1.setText(nowDate);
                                    btn1.setText("Stop");

                                    //MAA
                                    CaMDOIntegration.stopApplicationTransaction("Timer", "MyTest");
                                }
                            });
                        }
                    }, 0, 1000);
                    isTimerStarting=true;
                }
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crashMe();
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RESTCall rc = new RESTCall(tv2);
                rc.execute("https://httpbin.org/ip", "origin");
            }
        });

        btn4 = (Button)findViewById(R.id.button4);

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn5 = (Button)findViewById(R.id.button5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RESTCall rc = new RESTCall(tv3);
                rc.execute("https://httpbin.org/user-agent", "user-agent");
            }
        });

    }

    private class RESTCall extends AsyncTask<String, Void, String> {

        private TextView targetTextView;

        private JSONObject resultJSON;
        private String resultString;

        RESTCall(TextView tv){
            targetTextView = tv;
        }

        protected String doInBackground(String... args){


            resultJSON = null;
            resultString = null;

            HttpURLConnection httpConnection = null;

            try {
                // Get the url into a URL object
                URL url = new URL(args[0]);

                // Open the URL connection
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.connect();

                // Test the HTTP response code
                int rc = httpConnection.getResponseCode();
                if (rc == HttpURLConnection.HTTP_OK) {
                    // If we actually got something...
                    InputStream responseData = new BufferedInputStream(httpConnection.getInputStream());
                    resultJSON = new JSONObject(getResponseText(responseData));
                    resultString = resultJSON.getString(args[1]);

                } else {
                    // Return the HTTP error details
                    try {
                        resultJSON = new JSONObject("{\"id\":0,\"content\":\"" + "HTTP Error: " + String.format("%d", rc) + "\"}");
                        resultString = resultJSON.getString("content");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (MalformedURLException ex) {
                // Malformed URL
                try {
                    resultJSON = new JSONObject("{\"id\":0,\"content\":\"" + ex.getLocalizedMessage() + "\"}");
                    resultString = resultJSON.getString("content");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException ex) {
                // IO Exception on the HTTP request
                try {
                    resultJSON = new JSONObject("{\"id\":0,\"content\":\"" + ex.getLocalizedMessage() + "\"}");
                    resultString = resultJSON.getString("content");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException ex) {
                // JSON parsing error
                try {
                    resultJSON = new JSONObject("{\"id\":0,\"content\":\"" + ex.getLocalizedMessage() + "\"}");
                    resultString = resultJSON.getString("content");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } finally {
                httpConnection.disconnect();
            }

            // resultJSON should hold our resulting return JSONObject
            return resultString;
        }

        protected void onPostExecute(String result) {
            // This is invoked when the background thread has completed
            // Get a reference to the content TextView
            // so we can update the text and its colour
                targetTextView.setText(result);
        }

        // Read the InputStream and get the JSON out as a string
        private String getResponseText(InputStream inStream) {
            // very nice trick from
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }

    private void crashMe() {
        //zero divide
        Integer a = (2 * 2) / (7 - 7);
    }
}