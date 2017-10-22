package com.example.unknwn.caffeineconsumpion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Button enterButton = (Button) findViewById(R.id.enterButton);
        String deviceName = android.os.Build.MODEL;
        boolean beenLaunched = true;

        DatabaseHandler db = null;

        try {
            db= new DatabaseHandler(this);
            Log.i("SplashScreen",Boolean.toString(db.getLaunched(deviceName)));

            beenLaunched = db.getLaunched( deviceName );
        }

        catch( Exception e ) {
            Log.e("MainActivity", "Error message: " + e);
            e.printStackTrace();
        }

        final boolean bLaunch = beenLaunched;

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !bLaunch ) {
                    startActivity(new Intent(SplashScreen.this, Credentials.class));
                }
                else {
                    startActivity(new Intent(SplashScreen.this, LandingCheck.class));
                }
            }
        });
    }
}
