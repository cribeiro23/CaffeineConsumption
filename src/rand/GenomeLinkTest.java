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

public class GenomeLinkTest {
	
	private static final String HEADER = "Bearer GENOMELINKTEST"; 
			
	public static void main(String args[] ) throws IOException {
		String query = "european";
		String url = null;
		try {
			url = "https://genomicexplorer.io/v1/reports/caffeine-metabolite-ratio/?population=" + URLEncoder.encode(query, "UTF-8");
			System.out.println(url);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		
		// Adding the header property
		con.setRequestProperty("Authorization", HEADER);
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		String responseString = response.toString();

		//print result
		// System.out.println(responseString);
		
		 String responseArray[] = responseString.split(",");
		 String scoreString = null;
		 
		for (int i = 0; i < responseArray.length; i++ ) {
			
		//	System.out.println(responseArray[i]);
			if ( responseArray[i].contains("summary") ) {
				scoreString = responseArray[i];
			}
		}
				
		int score = Character.getNumericValue(scoreString.charAt(scoreString.length() - 1));
		System.out.println(score);		
		
	}
	
}