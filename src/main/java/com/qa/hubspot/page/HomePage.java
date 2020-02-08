package com.qa.hubspot.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.ElementUtil;

public class HomePage extends BasePage{

	WebDriver driver;
	ElementUtil elementUtil;
	
	By header = By.cssSelector("h1.private-page__title");
	By accountName = By.cssSelector("span.account-name");
	
	//create constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	public String getHomePageTitle() {
		return elementUtil.doGetPageTitle();
	}
	
	public String getHomePageHeader() {
		return elementUtil.doGetText(header);
	}
	
	public String getLoggedInUserAccountName() {
		return elementUtil.doGetText(accountName);
	}
}
