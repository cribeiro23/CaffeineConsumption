package com.example.unknwn.caffeineconsumpion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


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
	private static final String USER_TABLE = "UserInformation";
	
	// Contacts Table Columns names
	private static final String KEY_API = "Apikey";
	private static final String KEY_WEIGHT = "Weight";
	private static final String KEY_HAS_LAUNCHED = "Has_Launched";
	private static final String KEY_NAME = "Name";
	
	public DatabaseHandler(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Log.i("DatabaseHandler","Hello, this happened");

		String createStatement =
				String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY,"
						+ " %s TEXT, %s TEXT, %s TEXT);",
                   		USER_TABLE,
						KEY_WEIGHT,
						KEY_API,
						KEY_HAS_LAUNCHED,
						KEY_NAME);

	    db.execSQL(createStatement);
	}


	/**
	 * Adding new Person to the database
 	 */
	public void addPerson(Person person) {
	    SQLiteDatabase db = this.getWritableDatabase();

		// Checking if value is already in database to avoid duplicates
		Cursor cursor = db.query(USER_TABLE, new String[] { KEY_WEIGHT,
						KEY_API, KEY_HAS_LAUNCHED }, KEY_NAME + "=?",
				new String[] { String.valueOf(person.getName()) }, null, null, null, null);

		// Exiting insert operation if row already existed
		if (cursor.moveToFirst() ) {
			// Log.i("DatabaseHandler", "Repeated entry! Do nothing");
			return;
		}

	    ContentValues values = new ContentValues();
		values.put(KEY_NAME, person.getName() );
	    values.put(KEY_WEIGHT, person.getUserWeight()); // Adding weight
	    values.put(KEY_API, person.getKey() ); // Adding caffeine metabolite
		values.put(KEY_HAS_LAUNCHED, true);
	 
	    // Inserting Row
	    db.insert(USER_TABLE, null, values);
	    db.close(); // Closing database connection
	}
	
	// Pulling data from contact

	/**
	 * Method to query the database, using person's name as
	 * key, and return their weight
	 * @param name person's name
	 * @return weight person's weight
	 */
    int getWeight(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(USER_TABLE, new String[] { KEY_WEIGHT,
                KEY_API, KEY_HAS_LAUNCHED }, KEY_NAME + "=?",
                new String[] { String.valueOf(name) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return Integer.parseInt(cursor.getString(0));
    }

	/**
	 * Method to query the database, using person's name as
	 * key, and return whether the app has been launched before
	 * @param name person's name
	 * @return whether app has been launched before
	 */
	 boolean getLaunched(String name) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(USER_TABLE, new String[] { KEY_WEIGHT,
						KEY_API, KEY_HAS_LAUNCHED }, KEY_NAME + "=?",
				new String[] { String.valueOf(name) }, null, null, null, null);

		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		else {
			return false;
		}

		return Boolean.valueOf(cursor.getString(2));
	}

	/**
	 * Method to query the database, using person's name as
	 * key, and return their apikey
	 * @param name person's name
	 * @return person's apikey
	 */
	String getApiKey(String name ) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(USER_TABLE, new String[] { KEY_WEIGHT,
						KEY_API, KEY_HAS_LAUNCHED }, KEY_NAME + "=?",
				new String[] { String.valueOf(name) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		return cursor.getString(1);
	}

	/**
	 * Method to delete person from database
	 * @param person the person to be deleted
	 */
	public void deletePerson(Person person) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(USER_TABLE, KEY_NAME + " = ?",
				new String[] { person.getName() });
		db.close();
	}



}