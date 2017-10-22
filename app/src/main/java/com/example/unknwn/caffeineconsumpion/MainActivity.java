package com.example.unknwn.caffeineconsumpion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    private TextView countDownText;
    private TextView totalConsumedCaffeine;
    private Handler progressBarHandler = new Handler();
    private ProgressBar progressBarTester;

    private int caffineValueDrank;
    //private int consumedCaffeine;
    private int userCaffineValue;
    private double userMult;
    private int progressBarStatus = 0;
    private String caffeineDrank;
    private int caffeineAmount;
    private int pulledHour;
    private int pulledMinute;
    private double metabolismRate;
    //private volatile int phenotype;

    private long fileSize = 0;

    private TextView debugField;

    Future<Integer> futurePhenoMeta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Person person1 = new Person("Test", "GENOMELINKTEST", 60);
        final AlertDialog.Builder caffineAlert = new AlertDialog.Builder(this);
        final Person person1 = new Person("Test","GENOMELINKTEST", 50);
        //Get Globals from phenotype data

        //Gets Intent data from whatever prior activity
        Intent readIntent = getIntent();
        //For new Drink
        Button drinkButton = (Button)findViewById(R.id.drinkButton);

        progressBarTester = (ProgressBar) findViewById(R.id.progressBar);
        countDownText = (TextView) findViewById(R.id.countDownText);
        debugField = (TextView) findViewById(R.id.debug);
        totalConsumedCaffeine = (TextView) findViewById(R.id.totalConsumedCaffeine);

        //Pulls prior intent data
        caffeineDrank = readIntent.getStringExtra("CaffeineType");
        pulledHour = Integer.parseInt(readIntent.getStringExtra("Hour"));
        pulledMinute = Integer.parseInt(readIntent.getStringExtra("Minute"));
        int pulledCombine = (pulledHour * 3600) + (pulledMinute * 60);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               // try {
                person1.setCaffeineMeta();
                //Log.i("MainActivity",Integer.toString(phenotype));
                person1.setCaffeineResistance();
                   // Log.wtf("MainActivity", "Phenotype value is" + pheno);
              //  }
              //  catch (IOException e) {
              //      new Error("IO Exception GETPHENO IS BROKE");
              //  }

            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //person1.setCaffeineMeta();
        //person1.setCaffeineResistance();
        //.i("MainActivity",Integer.toString(phenotype));
        person1.setMultiplier(person1.getCaffeineMeta());
        userMult = person1.getMultiplier();
        person1.setmgCaffeine(person1.getCaffeineRes());
        userCaffineValue = person1.getmgCaffeine();

        //consumedCaffeine = person1.getConsumedCaff();

        debugField.setText(Double.toString(userMult));

        //Map to figure out how caffeine was taken in
        Map<String, Integer> caffeineTable = new HashMap<>();
        caffeineTable.put("Red Bull", 80);
        caffeineTable.put("Rockstar", 160);
        caffeineTable.put("5 Hour Energy", 200);
        caffeineTable.put("Monster", 160);
        caffeineTable.put("Coffee", 160);
        caffeineTable.put("Expresso", 80);

        int consumedCaffeine = person1.getConsumedCaff();
        caffeineAmount = caffeineTable.get(caffeineDrank);
        debugField.setText(Integer.toString(caffeineAmount));
        consumedCaffeine = consumedCaffeine + caffeineAmount;
        debugField.setText(Integer.toString(consumedCaffeine));
        // debugField.setText(Integer.toString(person1.getConsumedCaff()));
        person1.setConsumedCaff(consumedCaffeine);

        // person1.addConsumedCaff(caffeineAmount);

        totalConsumedCaffeine.setText("Daily Caffeine Consumption: " + consumedCaffeine
                + "mg/" + userCaffineValue + "mg");

        metabolismRate = caffeineAmount / 4;
        metabolismRate = (int) (metabolismRate * userMult);

        //Keep caffeineLevels above 40 mg
        Calendar currDT = Calendar.getInstance();
        int currHour =currDT.get(Calendar.HOUR_OF_DAY);
        int currMinute = currDT.get(Calendar.MINUTE);
        int currCombined = (currHour * 3600) + (currMinute * 60);
        int subtractCombined = currCombined - pulledCombine;

        caffineValueDrank = (int) (((caffeineAmount-40)/metabolismRate) * 3600);
        if (pulledCombine == 0) {
        }
        else {
            caffineValueDrank = caffineValueDrank - subtractCombined;
        }


        double tempConsum = (double) consumedCaffeine;
        double tempCaffVal = (double) userCaffineValue;
        double percentDaily = tempConsum / tempCaffVal;
        //debugField.setText(Double.toString(percentDaily));
        if (percentDaily > 0.75) {
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
                /*if (percentDaily >= .5) {
                    AlertDialog.Builder caffAlert = caffineAlert;
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
                }*/
                startActivity(new Intent(MainActivity.this, AddToMainDrinks.class));
            }
        });
    }


}
