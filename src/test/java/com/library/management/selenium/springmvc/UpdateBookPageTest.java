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

public class UpdateBookPageTest {
	
  public WebDriver driver;
  
  @FindBy(id="isbn")
  private WebElement isbn;
  
  @FindBy(id="submit")
	private WebElement submitButton;
  
  @Test
	public void givenOnUpdateBookPage_whenExistingBookUpdated_thenDisplaySuccess() {
	  driver.navigate().to("http://localhost:8080/library-management-system/library/addBook");
	  String isbnNo ="book " + Math.random();
	  isbn.sendKeys(isbnNo);
	  submitButton.click();
	  
	  driver.navigate().to("http://localhost:8080/library-management-system/library/updateBook");
	  isbn.sendKeys(isbnNo);
	  submitButton.click();
	  
	  String validationMessage = "//*[text()='Successfully updated Book with ISBN: " + isbnNo +"']";
		WebElement text = driver.findElement(By.xpath(validationMessage));
		assertTrue(text.isDisplayed(), "Success page not displayed");

	}
  
  @Test
	public void givenOnUpdateBookPage_whenNonExistingBookUpdated_thenDisplayErrorMessage() {
	  driver.navigate().to("http://localhost:8080/library-management-system/library/updateBook");
	  String isbnNo ="100";
	  isbn.sendKeys(isbnNo);
	  submitButton.click();
	  
	  String validationMessage = "//*[text()='No book found with ISBN: " + isbnNo +"']";
		WebElement text = driver.findElement(By.xpath(validationMessage));
		assertTrue(text.isDisplayed(), "Success page not displayed");

	}
  
  @Test
	public void givendOnUpdateBookPage_whenNoISBNEntered_thenDisplayValidationMessage() {
	  driver.navigate().to("http://localhost:8080/library-management-system/library/updateBook");
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
