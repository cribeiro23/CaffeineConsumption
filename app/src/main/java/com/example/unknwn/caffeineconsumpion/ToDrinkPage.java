package com.example.unknwn.caffeineconsumpion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ToDrinkPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_drink_page);

        Button mainButton = (Button)findViewById(R.id.mainButton);
        final Spinner drinkSpinners = (Spinner) findViewById(R.id.drinksSpinner);
        ArrayAdapter<String> drinksHolder;
        List<String> drinksList;

        drinksList = new ArrayList<String>();
        drinksList.add("Red Bull");
        drinksList.add("Rockstar");
        drinksList.add("5 Hour Energy");
        drinksList.add("Monster");
        drinksList.add("Coffee");
        drinksList.add("Expresso");
        drinksHolder = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, drinksList);
        drinksHolder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drinkSpinners.setAdapter(drinksHolder);

        Intent readIntent = getIntent();
        final String api = readIntent.getStringExtra("API");
        final String kgWeight = readIntent.getStringExtra("kgWeight");
        final Intent toMain = new Intent(ToDrinkPage.this, MainActivity.class);
        final Bundle toMainBundle = new Bundle();
        Log.i("ToDrinkPage", api + " " + kgWeight);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMain.putExtra("CaffeineType", drinkSpinners.getSelectedItem().toString());
                toMain.putExtra("Hour", "0");
                toMain.putExtra("Minute", "0");
                toMain.putExtra("API", api);
                toMain.putExtra("kgWeight", kgWeight);
                startActivity(toMain);
            }
        });
    }
}
