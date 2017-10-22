package com.example.unknwn.caffeineconsumpion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DrankPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drank_page);


        Button submitButton = (Button) findViewById(R.id.drankMainbutton);
        final Spinner drinkSpinners = (Spinner) findViewById(R.id.drankSpinner);
        //Spinner timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker2);
        ArrayAdapter<String> drinkHolder;
        List<String> drinkList;
        ArrayAdapter<String> timeHolder;
        List<String> timeList;

        timePicker.setIs24HourView(true);
        //Fills drinkList
        drinkList = new ArrayList<String>();
        drinkList.add("Red Bull");
        drinkList.add("Rockstar");
        drinkList.add("5 Hour Energy");
        drinkList.add("Monster");
        drinkList.add("Coffee");
        drinkList.add("Expresso");
        drinkHolder = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, drinkList);
        drinkHolder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drinkSpinners.setAdapter(drinkHolder);

        Map<String, Integer> caffeineTable = new HashMap<>();
        caffeineTable.put("Red Bull", 80);
        caffeineTable.put("Rockstar", 160);
        caffeineTable.put("5 Hour Energy", 200);
        caffeineTable.put("Monster", 160);
        caffeineTable.put("Coffee", 160);
        caffeineTable.put("Expresso", 80);

        final Intent toMain = new Intent(DrankPage.this, MainActivity.class);
        final Bundle toMainBundle = new Bundle();
        /*Fills TimeList
        timeList = new ArrayList<String>();
        timeList.add("12 AM");
        timeList.add("12:30 AM");
        timeList.add("1 AM");
*/

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String hour = Integer.toString(timePicker.getHour());
                final String minute = Integer.toString(timePicker.getMinute());
                toMain.putExtra("CaffeineType", drinkSpinners.getSelectedItem().toString());
                toMain.putExtra("Hour", hour);
                toMain.putExtra("Minute", minute);
                startActivity(toMain);
            }
        });
    }

}
