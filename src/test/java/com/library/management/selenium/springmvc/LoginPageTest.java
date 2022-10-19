package com.library.management.selenium.springmvc;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginPageTest {
	
  public WebDriver driver;
  @FindBy(id="username")
  private WebElement username;
	
  @FindBy(id="password")
  private WebElement password;
  
  @FindBy(id="submit")
	private WebElement loginButton;
  
  @FindBy(id="welcome")
	private WebElement successPage;
  
  @Test
	public void givenOnLoginPage_whenValidUsernameAndPassword_thenNavigateToSuccessPage() {
	  driver.navigate().to("http://localhost:8080/library-management-system/library/login");

	  submitCredentials("inder", "inder123");

		assertTrue(successPage.isDisplayed(), "Success page not displayed");
	}
  
  @Test
	public void givenOnLoginPage_whenInValidUsernameAndPassword_thenShowError() {
	  driver.navigate().to("http://localhost:8080/library-management-system/library/login");
	  submitCredentials("inder", "inder1243");
	
		WebElement text = driver.findElement(By.xpath("//*[text()='Invalid Details']"));
		assertTrue(text.isDisplayed(), "Success page not displayed");
	}
  
  @BeforeTest
  public void beforeTest() {
	  
      System.setProperty("webdriver.chrome.driver","C:\\Users\\inderpreet.sidhu\\Desktop\\Testing\\chromedriver.exe");
      WebDriverManager.chromedriver().setup();
	  driver = new ChromeDriver();
	  PageFactory.initElements(driver, this);
  }

  @AfterTest
  public void afterTest() {
	  driver.close();
  }
 
  private void submitCredentials(String testUsername, String testPassword) {
		if (testUsername != null) {
			username.sendKeys(testUsername);
		}
		if (testPassword != null) {
			password.sendKeys(testPassword);
		}
		loginButton.click();
	}
}
