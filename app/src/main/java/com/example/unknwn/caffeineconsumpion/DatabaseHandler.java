package com.example.unknwn.caffeineconsumpion;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHandler extends SQLiteOpenHelper {


	/*public void onUpgrade(SQLiteDatabase db ,int old, int new) {
		db.execSQL("Stuff" + USER_TABLE);

		onCreate(db);
	}*/

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
		onCreate(db);
	}

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	
	// Database Name
	private static final String DATABASE_NAME = "userManager";
	
	// Contacts table name
	private static final String USER_TABLE = "User Information";
	
	// Contacts Table Columns names
	private static final String KEY_CAFFEINEMETABOLITE = "Coffee metabolite score";
	private static final String KEY_CAFFEINETOLERANCE = "Coffee tolerance score";
	private static final String KEY_WEIGHT = "Weight";
	
	public DatabaseHandler(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
	    String CREATE_CONTACTS_TABLE = "CREATE TABLE " + USER_TABLE + "("
	            + KEY_WEIGHT + " INTEGER PRIMARY KEY," + KEY_CAFFEINEMETABOLITE + " TEXT,"
	            + KEY_CAFFEINETOLERANCE + " TEXT" + ")";
	    db.execSQL(CREATE_CONTACTS_TABLE);
	}
	
	//addContact
    // Adding new contact
	public void addContact(Person person) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_WEIGHT, person.getUserWeight()); // Adding weight
	    values.put(KEY_CAFFEINEMETABOLITE, person.getCaffeineMeta() ); // Adding caffeine metabolite
	    values.put(KEY_CAFFEINETOLERANCE, person.getCaffeineRes() );
	 
	    // Inserting Row
	    db.insert(USER_TABLE, null, values);
	    db.close(); // Closing database connection
	}
	
	
}