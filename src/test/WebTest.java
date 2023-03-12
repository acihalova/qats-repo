package test;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class WebTest {
	 
	 
	 WebDriver driver;
	 
	 static int WAIT_TIME = 10;
	 
	 String newArticleTitle;
	 
	 
	 void waitForNewPage() {
		 
		  String currentUrl = driver.getCurrentUrl();
		  String newUrl;
		  
		  System.out.println("Old URL: " + currentUrl);
		  
		  do {
		       newUrl = driver.getCurrentUrl();
		       try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			       
		  } while(newUrl.contentEquals(currentUrl));
		  
		  System.out.println("New URL: " + driver.getCurrentUrl());
	 }
	 
	 
	 void logout() {
		 
		 
		  waitForNewPage();
		 
		  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/nav/div/ul/li[3]/a"))).click();
	      new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div/div/div/button"))).click();
	      //new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@text, 'logout']"))).click();
	      
	      System.out.println("Logout");
	 }
	 
	 
	 @BeforeTest
	 public void BeforeTest()
	 {
		 driver = new ChromeDriver();
		 
		 //System.setProperty("webdriver.chrome.driver", "C:\\Users\\cihalali\\chromedriver_linux64\\chromedriver.exe");
		 System.setProperty("webdriver.chrome.driver", "..\\WebDriver\\chromedriver.exe");
		 driver.manage().window().maximize();
	 }
	 
	 
	 @BeforeMethod
	 public void beforeMethod()
	 {
		  driver.get("https://react-redux.realworld.io/#/login?_k=3wzrhk");

		  //new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Username']"))).sendKeys("tech_task@qats.sk");
		  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Email']"))).sendKeys("acihalova@email.cz");
		  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Password']"))).sendKeys("alice2610"); 
		  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//button"))).click();
	 }
	 
	 @AfterTest
	 public void AfterTest()
	 {
		 
		 System.out.println("After test");
		 driver.close();
		 driver = null;
	 }
	
	 @Test
	 public void test1() {
		 
		  System.out.println("1");
		  waitForNewPage();
		

		  new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/nav/div/ul/li[2]/a"))).click();
		  //waitForNewPage.run();
		  
		  // Create new article
		  final Random rnd = new Random(System.currentTimeMillis());
		  final int articleNum = rnd.nextInt(1,100);
		  
		  final String articleTitle = "Article_" + articleNum;
		  newArticleTitle = articleTitle;
		  final String articleIsAbout = "The article is about new technlogies.";
		  final String articleText = "This article covrs the topic ...";
		  final String articleTag = "quia";
		  
		  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div/div/div/form/fieldset/fieldset[1]/input"))).sendKeys(articleTitle);
		  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div/div/div/form/fieldset/fieldset[2]/input"))).sendKeys(articleIsAbout);
		  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div/div/div/form/fieldset/fieldset[3]/textarea"))).sendKeys(articleText);
		  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div/div/div/form/fieldset/fieldset[4]/input"))).sendKeys(articleTag); 
		  
		  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div/div/div/form/fieldset/button"))).click();
		  
		  waitForNewPage();
		  
		  System.out.println("2");
	
		  new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(
				  By.xpath("//*[@id=\"main\"]/div/div/div[1]/div/div/span/a"))).click();
		  
		  System.out.println("3");
		  
		  System.out.println("New URL: " + driver.getCurrentUrl());
		  
		  driver.get(driver.getCurrentUrl());
		 
		
		  try {
			  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions
					  .attributeToBe(By.xpath("//*[@id=\"main\"]/div/div/div/div/div/form/fieldset/fieldset[1]/input"),
							  "value", articleTitle));
			  
		  } catch(TimeoutException ex) {
			  
			  fail("'Article Title' is not corrrect");
		  }
		  
		  try {
			  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions
				  .attributeToBe(By.xpath("//*[@id=\"main\"]/div/div/div/div/div/form/fieldset/fieldset[2]/input"),
						  "value", articleIsAbout));
		  
		 } catch(TimeoutException ex) {
			  
			  fail("'Article is about' is not correct");
		 }
	
		 try {
			new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions
					  .attributeToBe(By.xpath("//*[@id=\"main\"]/div/div/div/div/div/form/fieldset/fieldset[3]/textarea"),
							  "value", articleText));
		 } catch(TimeoutException ex) {
			  
			  fail("'Article text' is not correct");
		 }
		  
		 try {
			  new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions
					  .attributeToBe(By.xpath("//*[@id=\"main\"]/div/div/div/div/div/form/fieldset/fieldset[4]/input"),
							  "value", articleTitle));
			  
		 } catch(TimeoutException ex) {
			  
			  fail("'Article tag' is not correct");
		 }
		  
		 logout(); 
	 }
	  

	 @Test
	 public void test2() {
		 
		 System.out.println("=> Home");
		 
		 new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions.
				 elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/nav/div/ul/li[1]/a"))).click();
		 
		 System.out.println("=> Global feed");
		 waitForNewPage();
		 
		 new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions
				 .elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div/div/div[1]/div[1]/ul/li[2]/a"))).click();
		
	
		 new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME * 5)).until(ExpectedConditions.numberOfElementsToBe(By.className("article-preview"), 10));
		 
		 List<WebElement> items = driver.findElements(By.className("article-preview"));
		 
		 new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME * 5)).until(ExpectedConditions.visibilityOfAllElements(items));
		 
		 System.out.println("=> Items count " + items.size());
	
		 
		 for(int i = 0; i <items.size(); i++) {
			 
			 try {
			 
				 new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME * 5)).until(ExpectedConditions
						 .attributeToBe( items.get(i).findElement(By.className("preview-link")).findElement(By.tagName("h1")), "outerText", newArticleTitle));
						 
				 String h1 = items.get(i).findElement(By.className("preview-link")).findElement(By.tagName("h1")).getAttribute("outerText");
			
				 System.out.println("h1 = " + h1);
				 
				 if(h1.equals(newArticleTitle)) {
				 
						 new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions
								 .visibilityOf(items.get(i).findElement(By.className("preview-link"))));
						 
						 System.out.println("1");
						 
						
						 new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions
								 .elementToBeClickable(items.get(i).findElement(By.className("preview-link"))
										 .findElement(By.tagName("h1"))));
						 
						 System.out.println("2");
						
						 items.get(i).findElement(By.className("preview-link")).findElement(By.tagName("h1")).click();
				
						 new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME)).until(ExpectedConditions
								 .elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div[1]/div/div/span/button"))).click();
						 
						 //waitForNewPage();
						 break;
				 }
				  
			} catch(TimeoutException ex) {
				
				continue;
			}
		 }
		 logout();
	 }
}
