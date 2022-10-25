package com.library.management.selenium.angular;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

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

public class RemoveBookPageTest {
	
  public WebDriver driver;
  WebDriverWait wait;
  
  @FindBy(id="isbn")
  private WebElement isbn;
  
  @FindBy(id="submit")
	private WebElement submitButton;
  
  @Test
	public void givenOnRemoveBookPage_whenExistingBookRemoved_thenDisplaySuccess() {
	  driver.navigate().to("http://localhost:4200/product/addBook");
	  String isbnNo ="book " + Math.random();
	  isbn.sendKeys(isbnNo);
	  submitButton.click();
	  
	  driver.navigate().to("http://localhost:4200/product/removeBook");
	  isbn.sendKeys(isbnNo);
	  submitButton.click();
	  
	  String validationMessage = "//*[text()='Successfully removed Book with ISBN: " + isbnNo +"']";
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(validationMessage)));

		WebElement text = driver.findElement(By.xpath(validationMessage));
		assertTrue(text.isDisplayed(), "Success page not displayed");

	}
  
  @Test
	public void givenOnRemoveBookPage_whenNonExistingBookRemoved_thenDisplayErrorMessage() {
	  driver.navigate().to("http://localhost:4200/product/removeBook");
	  String isbnNo ="100";
	  isbn.sendKeys(isbnNo);
	  submitButton.click();
	  
	  String validationMessage = "//*[text()='No book found with ISBN: " + isbnNo +"']";
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(validationMessage)));

		WebElement text = driver.findElement(By.xpath(validationMessage));
		assertTrue(text.isDisplayed(), "Success page not displayed");

	}
  
  @Test
	public void givendOnRemoveBookPage_whenNoISBNEntered_thenDisplayValidationMessage() {
	  driver.navigate().to("http://localhost:4200/product/removeBook");
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
	  wait = new WebDriverWait(driver, Duration.ofNanos(6000));
  }

  @AfterTest
  public void afterTest() {
	  //driver.close();
  }
}
