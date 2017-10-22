package com.example.unknwn.caffeineconsumpion;

// import android.app.Application;
import android.app.Application;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;


/**
 * Created by UNKNWN on 10/21/2017.
 */

public class Person extends Application {

    private String apikey;
    private String name;
    private static int consumedCaff;
    private int userWeight;
    private int mgCaffeine = userWeight * 6;
    private double multiplier;
    private volatile int caffeineMeta;
    private volatile int caffeineResistance;
	private static long leftoverTime;

	public Person() {
	}

	/**
	 * Constructor to initialize a person object, set their name and key
	 * @param name the person's name
	 * @param apiKey the person's apikey
	 */
	public Person(String name, String apiKey, int weight) {
		setName(name);
		setKey(apiKey);
		setUserWeight(weight);

		/*
		try {
			// Generating phenotype information based on key
			caffeineMeta = getphenoType("caffeine-metabolite-ratio", "european");
			caffeineResistance = getphenoType("caffeine-consumption", "european");

		}
		catch (IOException e) {
			Log.d("IO", "Error in API Call");
		}
		*/

		//sets user weight
		//setUserWeight(weight);

	}

	/**
	 * Setter for leftover time
	 * @param time sets to whatever time is
	 */

	public void setLeftoverTime(long time) {
		leftoverTime = time;
	}

	/**
	 * Gets leftover time
	 * @return leftoverTime
	 */
	public long getLeftoverTime() {
		return leftoverTime;
	}

	/**
	 * Setter for apikey
	 * @param apikey
	 */
	public void setKey( String apikey ) {
		this.apikey = apikey;
	}
	
	/**
	 * Setter for name
	 * @param name
	 */
	public void setName( String name )
	{
		this.name = name;
	}
	
	/**
	 * Getter for apikey
	 * @return
	 */
	public String getKey() {
		return apikey;
	}

    public int getConsumedCaff()
	{
        return consumedCaff;
    }

    public void setConsumedCaff(int caffeine) {

		consumedCaff = caffeine;
    }

     /**
      * Method to make http request to get phenotype data
	 * for caffeine metabolic rate
      */
    public int getphenoType(String phenotype, String continent) throws IOException {
		String query = continent;
		String url = null;
		String header = "Bearer " + getKey(); 
		try {
			url = "https://genomicexplorer.io/v1/reports/" + phenotype + 
					"/?population=" + URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			new Error("Broken API Call");
			e.printStackTrace();
		}

		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			// Adding the header property
			con.setRequestProperty("Authorization", header);

			int responseCode = con.getResponseCode();

			// Commented out print statements for request code
			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			String responseString = response.toString();

			String responseArray[] = responseString.split(",");
			String scoreString = null;

			for (int i = 0; i < responseArray.length; i++) {

				if (responseArray[i].contains("summary")) {
					scoreString = responseArray[i];
				}
			}

			int score = Character.getNumericValue(scoreString.charAt(scoreString.length() - 1));
			con.disconnect();
		return score;
		
	}


    public void addConsumedCaff(int caffeine) {
        consumedCaff += caffeine;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int weight) {
        userWeight = weight;
    }

    public int getmgCaffeine()
	{
        return mgCaffeine;
    }

    public void setmgCaffeine(int phenotype) {
		mgCaffeine = getUserWeight() * 6;
        switch (phenotype) {
            case 0:
            	mgCaffeine = (int) (mgCaffeine * .75);
                break;
            case 1:
            	mgCaffeine = (int)(mgCaffeine * .875);
                break;
            case 2:
                break;
            case 3:
            	mgCaffeine = (int) (mgCaffeine * 1.25);
                break;
            case 4:
            	mgCaffeine = (int) (mgCaffeine * 1.5);
                break;
            default: mgCaffeine = 400;
        }
    }

    /**
     * Getter for caffeinemeta phenotype
     * @return caffeineMeta
     */
    public int getCaffeineMeta() {
        return caffeineMeta;
    }

	/**
	 * Setter for caffeineMeta
	 */
	public void setCaffeineMeta() {
		try {
			caffeineMeta = getphenoType("caffeine-metabolite-ratio", "european");
			Log.i("Persons", Integer.toString(caffeineMeta));
		}
		catch( IOException e ) {
			Log.d("IO", "Error in API Call");
		}
		// Setting multiplier
		//setMultiplier(caffeineMeta);
	}

	/**
	 * Setter for caffeineResistance
	 */
	public void setCaffeineResistance() {
		try {
			caffeineResistance = getphenoType("caffeine-consumption", "european");
		}

		catch( IOException e) {
			Log.d("IO", "Error in API Call");
		}

		// Setting daily caffeine intake
		//setmgCaffeine(caffeineResistance);
		//caffeineMeta
	}

	/**
     * Getter for caffeineResistance phenotype
     * @param
     */
    public int getCaffeineRes() {
    	return caffeineResistance;
    }

    public double getMultiplier() {
		return multiplier;
	}

    public void setMultiplier(int phenotype) {
        switch (phenotype) {
            case 0: multiplier = .6;
				Log.i("Person", Double.toString(multiplier));
                break;
            case 1: multiplier = .8;
				Log.i("Person", Double.toString(multiplier));
                break;
            case 2: multiplier = 1;
				Log.i("Person", Double.toString(multiplier));
                break;
            case 3: multiplier = 1.25;
				Log.i("Person", Double.toString(multiplier));
                break;
            case 4: multiplier = 1.5;
				Log.i("Person", Double.toString(multiplier));
                break;
            default: multiplier = 1;
				Log.i("Person", Double.toString(multiplier));
        }
    }



    /**
	 * Getter for hasLaunched boolean variable
     */
   // public boolean getHasLaunched() {
	//	return hasLaunched;
	//}

	/**
	 * Setter for hasLaunched boolean variable
	 */
//	public void setLaunched() {
//		hasLaunched = true;
//	}

	/**
	 * Getter for person's name
	 */
	public String getName() {
		return name;
	}

}

