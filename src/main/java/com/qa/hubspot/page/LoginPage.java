package com.qa.hubspot.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.Credentials;
import com.qa.hubspot.util.ElementUtil;

public class LoginPage extends BasePage{

	WebDriver driver;
	ElementUtil elementUtil;
	Credentials userCreds;
	
	//1. By locators
	
	By emailId = By.id("username");
	By password = By.id("password");
	By loginButton = By.id("loginBtn");
	By signUpLink = By.linkText("Sign up");
	By loginErrorText = By.xpath("//div[@class='private-alert__inner']");
	
	//constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}	
	
	//2. Page actions / features
	
	public String getPageTitle() {
//		return driver.getTitle();
		return elementUtil.doGetPageTitle();
	}
	
	public boolean checkSignUpLink() {
//		return driver.findElement(signUpLink).isDisplayed();
		return elementUtil.doIsDisplayed(signUpLink);
	}
	
	
	//PAGE CHAINING IN POM: page object returns object of the next landing page 
	//(when click on login button, home page loads so the login method should return a new object of home page)

	public HomePage doLogIn(Credentials userCred) {		
//		elementUtil.waitForElementPresent(emailId);
		elementUtil.doSendKeys(emailId, userCred.getAppUsername());
		elementUtil.doSendKeys(password, userCred.getAppPassword());
		elementUtil.doClick(loginButton);
		
		return new HomePage(driver);
	}

	
	/* 
	 * Use Credentials class instead of passing parameters
	public HomePage doLogIn(String username, String pwd) {
		driver.findElement(emailId).sendKeys(username);
		driver.findElement(password).sendKeys(pwd);
		driver.findElement(loginButton).click();
	
		return new HomePage(driver);
	}
	
	*/
	
	public boolean checkLoginErrorMessage() {
		return elementUtil.doIsDisplayed(loginErrorText);
	}
	
}
