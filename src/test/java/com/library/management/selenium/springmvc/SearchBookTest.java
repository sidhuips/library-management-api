package com.library.management.selenium.springmvc;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

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

public class SearchBookTest {
	
  public WebDriver driver;
    
  @FindBy(id="bookName")
  private WebElement bookName;
  
  @FindBy(id="searchString")
  private WebElement searchString;
  
  @FindBy(id="submit")
	private WebElement submitButton;
  
  @Test
	public void givenOnSearchBookPage_whenBooksFound_thenDisplayResult() {
	  String bookToSearch = "The";
	  driver.navigate().to("http://localhost:8080/library-management-system/library/search");
	  searchString.sendKeys(bookToSearch);
	  submitButton.click();
	  
			
		WebElement simpleTable = driver.findElement(By.id("searchResult"));
	    List<WebElement> rows = simpleTable.findElements(By.tagName("tr"));
        List<WebElement> cols = rows.get(1).findElements(By.tagName("td"));
        String bookFound = cols.get(1).getText();
        assertTrue(bookFound.contains(bookToSearch));
	}
  
  @Test
	public void givenOnSearchBookPage_whenBooksNotFound_thenDisplayEmptyTable() {
	  String bookToSearch = "xyzzz";
	  driver.navigate().to("http://localhost:8080/library-management-system/library/search");
	  searchString.sendKeys(bookToSearch);
	  submitButton.click();
	  
			
		WebElement simpleTable = driver.findElement(By.id("searchResult"));
	    List<WebElement> rows = simpleTable.findElements(By.tagName("tr"));
        assertTrue(rows.size() == 1);
	}
   
  @Test
	public void givendOnSearchBookPage_whenNoNameEntered_thenDisplayValidationMessage() {
	  driver.navigate().to("http://localhost:8080/library-management-system/library/search");
	  submitButton.click();	  
	  String validationMessage = "Please fill out this field.";
	  String msg = searchString.getAttribute("validationMessage");
	  assertEquals(validationMessage,msg);

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
}
