package com.example.unknwn.caffeineconsumpion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddToMainDrinks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_main_drinks);


        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button backButton = (Button) findViewById(R.id.backButton);
        final Spinner drinksSpinner = (Spinner) findViewById(R.id.drinkList);
        ArrayAdapter<String> drinkHolder;
        List<String> drinkList;

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
        drinksSpinner.setAdapter(drinkHolder);

        Intent readInent = getIntent();
        final String api = readInent.getStringExtra("API");
        final int kgWeight = Integer.parseInt(readInent.getStringExtra("kgWeight"));

        final Intent backToMain = new Intent(this, MainActivity.class);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(backToMain);
                //finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain.putExtra("CaffeineType", drinksSpinner.getSelectedItem().toString());
                backToMain.putExtra("Hour", "0");
                backToMain.putExtra("Minute", "0");
                backToMain.putExtra("API", api);
                backToMain.putExtra("kgWeight", Integer.toString(kgWeight));
                startActivity(backToMain);
            }
        });
    }
}
