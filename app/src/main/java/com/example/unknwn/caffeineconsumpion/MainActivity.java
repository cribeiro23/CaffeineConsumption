package com.example.unknwn.caffeineconsumpion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView countDownText;
    private TextView totalConsumedCaffeine;
    private Handler progressBarHandler = new Handler();
    private ProgressBar progressBarTester;

    private int caffineValueDrank;
    private int consumedCaffeine;
    private int userCaffineValue;
    private double userCaffineMult;
    private int progressBarStatus = 0;
    private String caffeineDrank;
    private int caffeineAmount;
    private int pulledHour;
    private int pulledMinute;
    private int metabolismRate;

    private long fileSize = 0;

    private TextView debugField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlertDialog.Builder caffineAlert = new AlertDialog.Builder(this);
        //Get Globals from phenotype data
        userCaffineMult = ((MyApplication) this.getApplication()).getCaffeineMeta();
        userCaffineValue = ((MyApplication) this.getApplication()).getmgCaffeine();
        consumedCaffeine = ((MyApplication) this.getApplication()).getConsumedCaff();

        debugField = (TextView) findViewById(R.id.debug);
        totalConsumedCaffeine = (TextView) findViewById(R.id.totalConsumedCaffeine);

        //Gets Intent data from whatever prior activity
        Intent readIntent = getIntent();
        //For new Drink
        Button drinkButton = (Button)findViewById(R.id.drinkButton);

        progressBarTester = (ProgressBar) findViewById(R.id.progressBar);
        countDownText = (TextView) findViewById(R.id.countDownText);

        //Pulls prior intent data
        caffeineDrank = readIntent.getStringExtra("CaffeineType");
        pulledHour = Integer.parseInt(readIntent.getStringExtra("Hour"));
        pulledMinute = Integer.parseInt(readIntent.getStringExtra("Minute"));
        int pulledCombine = (pulledHour * 3600) + (pulledMinute * 60);

        //Map to figure out how caffeine was taken in
        Map<String, Integer> caffeineTable = new HashMap<>();
        caffeineTable.put("Red Bull", 80);
        caffeineTable.put("Rockstar", 160);
        caffeineTable.put("5 Hour Energy", 200);
        caffeineTable.put("Monster", 160);
        caffeineTable.put("Coffee", 160);
        caffeineTable.put("Expresso", 80);

        caffeineAmount = caffeineTable.get(caffeineDrank);
        consumedCaffeine = consumedCaffeine + caffeineAmount;
        ((MyApplication) this.getApplication()).setConsumedCaff(consumedCaffeine);

        totalConsumedCaffeine.setText("Daily Caffeine Consumption: " + consumedCaffeine
                + "mg/" + userCaffineValue + "mg");

        metabolismRate = caffeineAmount / 4;
        metabolismRate = (int) (metabolismRate * userCaffineMult);

        //Keep caffeineLevels above 40 mg
        Calendar currDT = Calendar.getInstance();
        int currHour =currDT.get(Calendar.HOUR_OF_DAY);
        int currMinute = currDT.get(Calendar.MINUTE);
        int currCombined = (currHour * 3600) + (currMinute * 60);
        int subtractCombined = currCombined - pulledCombine;

        caffineValueDrank = ((caffeineAmount-40)/metabolismRate) * 3600;
        if (pulledCombine == 0) {
        }
        else {
            caffineValueDrank = caffineValueDrank - subtractCombined;
        }
        debugField.setText(Integer.toString(caffineValueDrank));

        if (consumedCaffeine / userCaffineValue >= .5) {
            AlertDialog.Builder caffAlert  = caffineAlert;
            caffAlert.setMessage("You are approaching your daily caffeine consumption, perhaps you" +
                    " should reconsider this drink?");
            caffAlert.setTitle("Caffeine Consumption");
            caffAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
            caffAlert.setCancelable(true);
            caffAlert.create().show();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < caffineValueDrank) {
                    progressBarStatus++;
                    android.os.SystemClock.sleep(1000);
                    progressBarHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBarTester.setProgress(progressBarStatus);
                        }
                    });
                }
            }
        }).start();

        new CountDownTimer(caffineValueDrank*1000,1000) {
            public void onTick(long millisUntilFinished) {
                long timeLeft = millisUntilFinished / 1000;
                long hours = timeLeft / 3600;
                long minutes = (timeLeft % 3600) /60;
                long seconds = timeLeft % 60;
                String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                //countDownText.setText("" + millisUntilFinished /1000);
                countDownText.setText(time);
            }

            public void onFinish() {
                countDownText.setText("Get more caffeine!");
            }
        }.start();

        drinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddToDrink.class));
            }
        });
    }


}
