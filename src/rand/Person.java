package rand;

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
import java.util.ArrayList; 


public class Person {
	private int dailyAmount;
	private int phenotypeScore;
	private String apiKey;
	private String name;
	
	// Defining constats
	private static final int SCORE_0_AMOUNT = 300;
	private static final int SCORE_1_AMOUNT = 350;
	private static final int SCORE_2_AMOUNT = 400;
	private static final int SCORE_3_AMOUNT = 450;
	private static final int SCORE_4_AMOUNT = 500;
	private static final int WARNING_AMOUNT = 100;
	
	
	/**
	 * Constructor to initialize a person object, set their name and key
	 * @param name the person's name
	 * @param apiKey the person's apikey
	 */
	public Person(String name, String apiKey ) {
		setName(name);
		setKey(apiKey);
	}
	
	/**
	 * This method uses the genomelink API to get the phenotype score
	 * from the user
	 */
	public void setScore(String phenotype, String continent) throws IOException {
		String query = continent;
		String url = null;
		String header = "Bearer " + getKey(); 
		try {
			url = "https://genomicexplorer.io/v1/reports/" + phenotype + 
					"/?population=" + URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
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
		 
		for (int i = 0; i < responseArray.length; i++ ) {
			
			if ( responseArray[i].contains("summary") ) {
				scoreString = responseArray[i];
			}
		}
				
		int score = Character.getNumericValue(scoreString.charAt(scoreString.length() - 1));
		phenotypeScore = score;
		
	}
		
		
	/**
	 * Method to set the person's API key
	 */
	public void setKey(String key) {
		this.apiKey = key;
	}
	
	/**
	 * Method to set person's name
	 */
	public void setName( String name ) {
		this.name = name;
	}
	
	/**
	 * Method to access the person's API key
	 * @return apiKey, the person's API key
	 */
	public String getKey() {
		return apiKey;
	}
	
	/**
	 * Method to return person's phenotype score
	 */
	public int getScore() {
		return phenotypeScore;
	}
	
	/**
	 * This method establishes the person's daily amount based on their 
	 * phenotype score
	 */
	public void setDailyAmount() {
		switch( phenotypeScore ) {
			case 0:
				dailyAmount = SCORE_0_AMOUNT;
				break;
			case 1:
				dailyAmount = SCORE_1_AMOUNT;
				break;
			case 2:
				dailyAmount = SCORE_2_AMOUNT;
				break;
			case 3:
				dailyAmount = SCORE_3_AMOUNT;
				break;
			case 4:
				dailyAmount = SCORE_4_AMOUNT;
				break;
			default:
				System.out.println("Invalid phenotype score!");
			
		}
	}
	
	/**
	 * This method gets the person recommended caffeine amount
	 * @return the person's amount
	 */
	public int getDailyAmount() {
		return dailyAmount;		
	}
	
	/**
	 * This method adjusts the remaining dailyAmount after a certain
	 * amount of caffeine is consumed
	 * @param caffeineconsumed the amount consumed
	 */
	public void deductCaffeine(int caffeineconsumed ) {
		dailyAmount -= caffeineconsumed;
		
		if ( dailyAmount <= 0 ) {
			System.out.println("You have exceeded recommended daily"
					            + " ammount of caffeine. It is highly recommended"
					            + " that you stop your consumption for today");
		}
		else if ( dailyAmount <= WARNING_AMOUNT ) {
			System.out.println("You have almost consumed all of your" +
								" recommended daily amount! Your next caffeine"
								+ " drink should be your last");
		}
	}
	

		
		
	}


