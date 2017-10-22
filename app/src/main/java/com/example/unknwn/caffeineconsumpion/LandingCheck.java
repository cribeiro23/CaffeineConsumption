package com.example.unknwn.caffeineconsumpion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.RadioButton;

public class LandingCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_check);
        RadioButton yesButton = (RadioButton)findViewById(R.id.radioButtonYes);
        RadioButton noButton = (RadioButton)findViewById(R.id.radioButtonNo);

        Intent readIntent = getIntent();
        final String api = readIntent.getStringExtra("API");
        final String kgWeight = readIntent.getStringExtra("kgWeight");

        final Intent noForward = new Intent(LandingCheck.this, DrankPage.class);
        final Intent yesForward = new Intent(LandingCheck.this, ToDrinkPage.class);
        //Listener for Yes to send to drank page
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noForward.putExtra("API", api);
                noForward.putExtra("kgWeight", kgWeight);
                startActivity(noForward);
            }
        });
        //Listener for No to send to ToDrink Page
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesForward.putExtra("API", api);
                yesForward.putExtra("kgWeight", kgWeight);
                startActivity(yesForward);
            }
        });
    }


}
