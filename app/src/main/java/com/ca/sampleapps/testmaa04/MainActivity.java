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
import java.util.Scanner;
import java.util.TimerTask;

import com.ca.integration.CaMDOIntegration;

public class MainActivity extends Activity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    TextView tv1;
    TextView tv2;

    Handler timeHandler;
    Boolean IsTimerStarting = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);

        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);

        timeHandler = new Handler();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*********************************
                CaMDOIntegration.startApplicationTransaction("Timer", "MyTest");

                for(int i=1; i<=5; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tv1.setText(String.valueOf(i) + ".0 seconds passed");

                }

                CaMDOIntegration.stopApplicationTransaction("Timer", "MyTest");
***********************************/

                new Thread(new Runnable() {
                    public void run() {
                        timeHandler.post(new Runnable() {
                            public void run() {
                                for(int i=1; i<=5; i++){
                                    try {
                                        Thread.sleep(1000);
                                    }catch(InterruptedException e){
                                        e.printStackTrace();
                                    }
                                    tv1.setText(String.valueOf(i) + ".0 seconds passed");
                                }
                            }
                        });
                    }
                }).start();

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
                RESTCall rc = new RESTCall();
                rc.execute("https://httpbin.org/ip");
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

    }

    private class RESTCall extends AsyncTask<String, Void, JSONObject> {

        protected JSONObject doInBackground(String... urlREST) {

            JSONObject resultJSON = null;
            HttpURLConnection httpConnection = null;

            try {
                // Get the url into a URL object
                URL url = new URL(urlREST[0]);

                // Open the URL connection
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.connect();

                // Test the HTTP response code
                int rc = httpConnection.getResponseCode();
                if (rc == HttpURLConnection.HTTP_OK) {
                    // If we actually got something...
                    InputStream responseData = new BufferedInputStream(httpConnection.getInputStream());
                    resultJSON = new JSONObject(getResponseText(responseData));
                } else {
                    // Return the HTTP error details
                    try {
                        resultJSON = new JSONObject("{\"id\":0,\"content\":\"" + "HTTP Error: " + String.format("%d", rc) + "\"}");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (MalformedURLException ex) {
                // Malformed URL
                try {
                    resultJSON = new JSONObject("{\"id\":0,\"content\":\"" + ex.getLocalizedMessage() + "\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException ex) {
                // IO Exception on the HTTP request
                try {
                    resultJSON = new JSONObject("{\"id\":0,\"content\":\"" + ex.getLocalizedMessage() + "\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException ex) {
                // JSON parsing error
                try {
                    resultJSON = new JSONObject("{\"id\":0,\"content\":\"" + ex.getLocalizedMessage() + "\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } finally {
                httpConnection.disconnect();
            }

            // resultJSON should hold our resulting return JSONObject
            return resultJSON;
        }

        protected void onPostExecute(JSONObject jsonResult) {
            // This is invoked when the background thread has completed
            // Get a reference to the content TextView
            // so we can update the text and its colour
            try {
                tv2.setText(jsonResult.getString("origin"));

            } catch (JSONException ex) {
                tv2.setText("ERROR : " + ex.getLocalizedMessage());
            }
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