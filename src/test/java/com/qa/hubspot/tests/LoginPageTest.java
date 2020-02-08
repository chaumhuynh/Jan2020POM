package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.page.HomePage;
import com.qa.hubspot.page.LoginPage;
import com.qa.hubspot.util.Credentials;

public class LoginPageTest {

	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage ;
	Credentials userCred;
	
	@BeforeTest
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.init_properties();
		String browserName = prop.getProperty("browser");
		driver = basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		
		loginPage = new LoginPage(driver);  //call constructor of LoginPage
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test (priority = 1)
	public void verifyLoginPageTitleTest() throws InterruptedException {
		Thread.sleep(5000);
		String title = loginPage.getPageTitle();
		System.out.println("Login page title is: " + title);
		
		//Asserts that two Strings are equal. If they are not,an AssertionError is thrown.
		Assert.assertEquals(title, "HubSpot Login");
	}
	
	@Test (priority = 2)
	public void verifySignUpLinkTest() {
		
		//Asserts that a condition is true. If it isn't, an AssertionError is thrown.
		Assert.assertTrue(loginPage.checkSignUpLink());
	}
	
	@Test (priority = 3)
	public void loginTest() {
		HomePage homePage = loginPage.doLogIn(userCred);
		String accountName = homePage.getLoggedInUserAccountName();
		Assert.assertEquals(accountName, "crmpro");
	}
	
	/* Example of negative test case
	 * However, better practice is using Data Provider to write data driven negative scenarios
	
	@Test (priority = 4)
	public void negativeLoginTest() {
		HomePage homePage = loginPage.doLogIn(userCred);
		String accountName = homePage.getLoggedInUserAccountName();
		Assert.assertEquals(accountName, "crmpro");
	}
	
	*/
	
	
	//DATA PROVIDER - execute the same tc with multiple data sets
	@DataProvider
	public Object[][] getInvalidLoginData() {
		Object data[][] = {
							{"test111@gmail.com", "test123"},
							{"test2@gmail.com", " "},
							{" ", "test12345"},
							{"test", "test"},
							{" ", " "}
						  };
		return data;
	}
	
	@Test (priority = 4, dataProvider = "getInvalidLoginData", enabled=false)
	public void invalidLoginTest(String username, String pwd) {
		userCred.setAppUsername(username);
		userCred.setAppPassword(pwd);
		loginPage.doLogIn(userCred);
		
		Assert.assertTrue(loginPage.checkLoginErrorMessage());
	}
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
