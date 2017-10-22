package com.example.unknwn.caffeineconsumpion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Credentials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        final EditText apiKey = (EditText) findViewById(R.id.editText2);
        final EditText weight = (EditText) findViewById(R.id.editText);


        final Intent forward = new Intent(Credentials.this, LandingCheck.class);


        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (weight.length() == 0 || weight.equals("")) {
                }
                else {
                    String api = apiKey.getText().toString();
                    String kgWeight = weight.getText().toString();
                    forward.putExtra("API", api);
                    forward.putExtra("kgWeight", kgWeight);
                    startActivity(forward);
                }

            }
        });
    }
}
