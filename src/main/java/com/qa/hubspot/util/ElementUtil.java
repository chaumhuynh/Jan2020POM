package com.qa.hubspot.util;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.hubspot.base.BasePage;

public class ElementUtil extends BasePage{

	WebDriver driver;
	WebDriverWait wait;
	JavaScriptUtil jsUtil;
	Properties prop;
	
	// constructor
	public ElementUtil(WebDriver driver) {
		prop = super.prop;
		this.driver = driver;
		wait = new WebDriverWait (driver, AppConstants.DEFAULT_TIME_OUT);
		jsUtil = new JavaScriptUtil(driver);
	}

	
	public boolean waitForTitlePresent(String title) {
		wait.until(ExpectedConditions.titleIs(title));
		return true;
	}
	
	
	public boolean waitForElementPresent(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return true;
	}
	
	//Visibility means that the element is not only displayed but also has a height and width that is greater than 0
	public boolean waitForElementVisible(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return true;
	}
	
	public String doGetPageTitle() {
		try {
		return driver.getTitle();
		} catch (Exception e) {
			System.out.println("Some exception has occured while getting the page's title.");
		} return null;
	}
	
	
	// good practice in framework: should handle exception (with try/catch)
	/**
	 * This method is used to create the web element on the basis of By locator
	 * @param locator
	 * @return element
	 */
	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
//			if(waitForElementPresent(locator));   >>create dependency (wait is applied within getElement method)
			element = driver.findElement(locator);
			if (highlightElement) {
				jsUtil.flash(element);
			}
		} catch (Exception e) {
			System.out.println("Some exception has occured while creating the web element.");
		}
		return element;
	}

	public void doClick(By locator) {
		try {
		getElement(locator).click();
		} catch (Exception e) {
			System.out.println("Some exception has occured while clicking on the web element.");
		}
	}
	
	public void doSendKeys(By locator, String value) {
		try {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
		} catch (Exception e){
			System.out.println("Some exception has occured while entering value in the web element.");
		}
	}
	
	public boolean doIsDisplayed(By locator) {
		try {
		return getElement(locator).isDisplayed();
		} catch (Exception e) {
			System.out.println("Some exception has occured while checking if the web element is displayed.");
		}
		return false;
	}
	
	public String doGetText(By locator) {
		try {
		return getElement(locator).getText();
		} catch (Exception e) {
			System.out.println("Some exception has occured while getting the web element's text.");
		}
		return null;  //if exception occurred, return null; if not return element's text
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
