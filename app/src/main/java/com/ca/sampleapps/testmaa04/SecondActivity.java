package com.ca.sampleapps.testmaa04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static com.ca.integration.CaMDOIntegration.CAMAA_STRING;
import static com.ca.integration.CaMDOIntegration.addSessionEvent;
import com.ca.integration.CaMDOIntegration;

public class SecondActivity extends AppCompatActivity {

    private String selection=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnBack = (Button)findViewById(R.id.button1);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selection.equals(null)) {
                    addSessionEvent(CAMAA_STRING, "What is your favorite band?", selection);
                } else {
                    addSessionEvent(CAMAA_STRING, "What is your favorite band?", "no selection");
                }
                Intent i = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGrp1);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selection = ((RadioButton) findViewById(checkedId)).getText().toString();
            }
        });


    }
}
