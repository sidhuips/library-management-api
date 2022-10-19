package com.library.management.selenium.springmvc;

import static org.testng.Assert.assertEquals;
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

public class AddBookPageTest {
	
  public WebDriver driver;
  
  @FindBy(id="isbn")
  private WebElement isbn;
  
  @FindBy(id="submit")
	private WebElement submitButton;
  
  @Test
	public void givenOnAddBookPage_whenNonExistingISBNAdded_thenDisplaySuccess() {
	  driver.navigate().to("http://localhost:8080/library-management-system/library/addBook");
	  String isbnNo ="book " + Math.random();
	  isbn.sendKeys(isbnNo);
	  submitButton.click();
	  
	  String validationMessage = "//*[text()='Successfully added Book with ISBN: " + isbnNo +"']";
		WebElement text = driver.findElement(By.xpath(validationMessage));
		assertTrue(text.isDisplayed(), "Success page not displayed");

	}
  
  @Test
	public void givenOnAddBookPage_whenExistingISBNAdded_thenDisplayErrorMEssage() {
	  driver.navigate().to("http://localhost:8080/library-management-system/library/addBook");
	  String isbnNo ="1";
	  isbn.sendKeys(isbnNo);
	  submitButton.click();
	  
	  String validationMessage = "//*[text()='A book already exists with ISBN: " + isbnNo +"']";
		WebElement text = driver.findElement(By.xpath(validationMessage));
		assertTrue(text.isDisplayed(), "Success page not displayed");

	}
  
  @Test
	public void givendOnAddBookPage_whenExistingISBNAdded_thenDisplayErrorMEssage() {
	  driver.navigate().to("http://localhost:8080/library-management-system/library/addBook");
	  submitButton.click();	  
	  String validationMessage = "Please fill out this field.";
	  String msg = isbn.getAttribute("validationMessage");
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
	  //driver.close();
  }
}
