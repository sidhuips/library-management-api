package com.library.management.selenium.angular;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HomePageTest {
	
  public WebDriver driver;
  WebDriverWait wait;

  @FindBy(id="bookName")
  private WebElement bookName;
  
  @FindBy(id="searchString")
  private WebElement searchString;
  
  @FindBy(id="submit")
	private WebElement submitButton;
  
  @Test
	public void givenOnSearchBookPage_whenBooksFound_thenDisplayResult() {
	  driver.navigate().to("http://localhost:4200/product/home");

	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bookList")));

			
		WebElement simpleTable = driver.findElement(By.id("bookList"));
	    List<WebElement> rows = simpleTable.findElements(By.tagName("tr"));
	    assertTrue(rows.size() >= 1);
	}  
  
  @BeforeTest
  public void beforeTest() {
	  
      System.setProperty("webdriver.chrome.driver","C:\\Users\\inderpreet.sidhu\\Desktop\\Testing\\chromedriver.exe");
      WebDriverManager.chromedriver().setup();
	  driver = new ChromeDriver();
	  PageFactory.initElements(driver, this);
	  wait = new WebDriverWait(driver, Duration.ofNanos(60));

  }

  @AfterTest
  public void afterTest() {
	  //driver.close();
  }
}
