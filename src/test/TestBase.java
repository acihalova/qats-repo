package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class TestBase {

	
	  static final String URL_REQRES = "https://reqres.in/api/users?page=2";
	  static final String TOTAL = "total";
	  static final String LAST_NAME = "last_name";
	  static final String DATA = "data";
	  static final String ID = "id";
	 
	  static final String CSV_FILE = "";
	  static final String CSV_SEPARATOR = ",";
	  int last_id = Integer.MIN_VALUE;
	  
	  long expectedTimeout;
	
	  @Parameters( { "expected-timeout" } )
	  @BeforeClass(alwaysRun = true)
	  public void setUp(@Optional("5000") long expectedTimeout) {
		  
		  System.out.println("Hello from setUp!");
		  System.out.println("expectedTimeout = " + expectedTimeout);
		  
		  this.expectedTimeout = expectedTimeout;
	  }
	  
	  int getLastId() {
		  
		  int lastId = -1;
		  
		  try {
			  
			  final URL url = new URL(URL_REQRES);
			  final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			  conn.setRequestMethod("GET");
			  conn.setRequestProperty("Accept", "application/json");
			  
			  
			  if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) { 
				  throw new RuntimeException("Failed : HTTP error code : "
							  + conn.getResponseCode());
			  }
			  
			  final BufferedReader br = new BufferedReader(new InputStreamReader(
			  (conn.getInputStream())));
			  
			  String output;
			  StringBuilder outputBuilder = new StringBuilder();
			  
			  System.out.println("Output from Server .... \n");
			  while ((output = br.readLine()) != null) {
				  System.out.println(output);
				  outputBuilder.append(output);
			  }
			  
			  final JSONObject jsonObject = new JSONObject(outputBuilder.toString());
			  final JSONArray dataArr = jsonObject.getJSONArray(DATA);
			  int length = dataArr.length(); 
			  
			  final JSONObject lastUser = new JSONObject(dataArr.get(length-1).toString());
			  lastId = lastUser.getInt(ID);
			  
			  conn.disconnect();
			  
		   } catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
		   } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
		   }
		   return lastId;
	  }
	  
	  long postData(String user, int id) {
		  
		  final String[] userData = user.split(" ");
		  System.out.println("str[0]: " + userData[0] + ", str[1]: " + userData[1]); 
		  
		  long responseTime = -1;
		  
		  try {
			  
			  final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			  sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			  
			  final String POST_PARAMS = "{\n" + "\"name\": \"" + userData[0] + "\",\r\n" +
					  "    \"job\": \""  + userData[1] + "\",\r\n" +
					  "    \"id\": " + id + ",\r\n" +
					  "    \"createdAt\": " + sdf.format(new Date()) +  "\n}";
			  System.out.println(POST_PARAMS);
			  
			  final URL url = new URL(URL_REQRES);
			  final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			  conn.setRequestMethod("POST");
			  conn.setRequestProperty("Content-Type", "application/json");
			  
			  
			  long startTime = System.currentTimeMillis();
			  int responseCode = conn.getResponseCode();
			  long endTime = System.currentTimeMillis();
			  
			  responseTime = (endTime - startTime) / 1000;
			  
			  System.out.println("POST Response Code :  " + responseCode);
			  System.out.println("POST Response Message : " + conn.getResponseMessage());
			  
			  if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
				  
				  BufferedReader in = new BufferedReader(new InputStreamReader(
						  conn.getInputStream()));
				  String inputLine;
			      StringBuffer response = new StringBuffer();

			      while ((inputLine = in .readLine()) != null) {
			            response.append(inputLine);
			      } in .close();
			      
			      // print result
			      System.out.println(response.toString()); 
			  }
			  
			  conn.disconnect();
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		  	return responseTime;
	  }
	  
}
