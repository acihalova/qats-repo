package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestRestApi extends TestBase {

  
  @Test
  public void test1() {
	  
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
			final StringBuilder outputBuilder = new StringBuilder();
			
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				outputBuilder.append(output);
			}
			
			JSONObject jsonObject = new JSONObject(outputBuilder.toString());
			System.out.println(TOTAL + " = " + jsonObject.get(TOTAL));
			
			JSONArray users = jsonObject.getJSONArray(DATA);
			int length = users.length(); 
			
			System.out.println("data length = " + length);
			
			final BiConsumer<String, Integer> cm = (s, i) -> {
				
				JSONObject user = new JSONObject(users.get(i).toString());
				assertNotNull(user.get(LAST_NAME), s + " user's last_name doesn't exist");
				System.out.println(s + " user:  " + user.get(LAST_NAME));
			};
			
			assertTrue(length > 1, "No user found.");
			cm.accept("first", 0);
			
			assertTrue(length > 2, "Just one user found.");
			cm.accept("second", 1);
			
			assertEquals(jsonObject.get(TOTAL), length, "total is not equal to data count");
			
			conn.disconnect();
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
  }
  
  
  @DataProvider(name = "users-provider")
  public Object[][] provider() {
	  
	  return new Object[][]{{"Josef teacher"}, {"Pavel student"}, {"Jan plumber"}};
  } 
  
  
  @BeforeGroups("post-user")  
  public void before_it()  
  {  
	  last_id  = getLastId();
	  System.out.println("lastId: " + last_id);
  }  
  

  @Test(dataProvider = "users-provider", groups = {"post-user"})  
  public void test2(String data) {
	  
	  System.out.println("Data is: " + data);
	  // System.out.println("expectedTimeout = " + expectedTimeout);
		  
	  String[] str = data.split(" ");
	  System.out.println("str[0]: " + str[0] + ", str[1]: " + str[1]);
	  
	  long postTime = postData(data, ++last_id);
	  
	  assertTrue(postTime <= expectedTimeout, "POST time is to long.");
  }
 
  
  
  
  
}
