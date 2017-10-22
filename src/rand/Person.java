package rand;

// import android.app.Application;
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
    private int consumedCaff = 0;
    private int userWeight = 50;
    private int mgCaffeine = userWeight * 6;
    private double multiplier;
    private int caffeineMeta;
    private int caffeineResistance;
    
	/**
	 * Constructor to initialize a person object, set their name and key
	 * @param name the person's name
	 * @param apiKey the person's apikey
	 */
	public Person(String name, String apiKey, int weight) {
		setName(name);
		setKey(apiKey);
		setUserWeight(weight);
		
		// Generating phenotype information based on key
		caffeineMeta = getphenoType( "caffeine-metabolite-ratio","european");
		caffeineResistance = getphenoType( "caffeine-consumption","european");
		
		// Setting multiplier
		setMultiplier(caffeineMeta);
		
		// Setting daily caffeine intake
		setmgCaffeine(caffeineResistance);
		
		
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

    public int getConsumedCaff() {
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
		return score;
		
	}


    public void addConsumedCaff(int caffeine) {
        consumedCaff = consumedCaff + caffeine;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int weight) {
        userWeight = weight;
    }

    public int getmgCaffeine() {
        return mgCaffeine;
    }

    public void setmgCaffeine(int phenotype) {
        switch (phenotype) {
            case 0: mgCaffeine = (int) (mgCaffeine * .75);
                break;
            case 1: mgCaffeine = (int)(mgCaffeine * .875);
                break;
            case 2: mgCaffeine = (int) (mgCaffeine);
                break;
            case 3: mgCaffeine = (int) (mgCaffeine * 1.25);
                break;
            case 4: mgCaffeine = (int) (mgCaffeine * 1.5);
                break;
            default: mgCaffeine = 400;
        }
    }

    /**
     * Getter for caffeinemeta phenotype
     * @return
     */
    public int getCaffeineMeta() {
        return caffeineMeta;
    }
    
    /**
     * Getter for caffeineResistance phenotype
     * @param phenotype
     */
    public int getCaffeineRes() {
    	return caffeineResistance;
    }

    public void setMultiplier(int phenotype) {
        switch (phenotype) {
            case 0: multiplier = .6;
                break;
            case 1: multiplier = .8;
                break;
            case 2: multiplier = 1;
                break;
            case 3: multiplier = 1.25;
                break;
            case 4: multiplier = 1.5;
                break;
            default: multiplier = 1;
        }
    }
}

