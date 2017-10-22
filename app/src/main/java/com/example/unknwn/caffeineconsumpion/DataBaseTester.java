package com.example.unknwn.caffeineconsumpion;

import android.app.Application;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by UNKNWN on 10/22/2017.
 */

public class DataBaseTester extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("DataBaseTester","Hello, this happened");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drank_page);

        DatabaseHandler db = new DatabaseHandler(this);

        // Insert sample person
        Person person1 = new Person("John", "GENOMELINKTEST", 70);
        db.addPerson(person1);

        int weight = db.getWeight(person1.getName());
        Log.i("DataBaseTester",Integer.toString(weight));
    }

}
