package com.qa.hubspot.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.ElementUtil;
import com.qa.hubspot.util.JavaScriptUtil;

import io.qameta.allure.Step;

public class ContactsPage extends BasePage {

	WebDriver driver;
	ElementUtil elementUtil;
	JavaScriptUtil jsUtil;
	
//	By mainContactsLink = By.xpath("(//a[contains(text(),'Contacts')])[1]");
//	By childContactsLink = By.xpath("(//a[@id = 'nav-secondary-contacts'])[position() = 2]//div[@style='color:inherit']");
	
	By createContactButton = By.xpath("//button[@type='button']//span[text()='Create contact']");
	By createContactFormButton = By.xpath("(//span[@class='private-loading-button__content private-button--internal-spacing'])[position()=1]");
	
	By email = By.xpath("//input[@data-field='email']");
	By firstName = By.xpath("//input[@data-field='firstname']");
	By lastName = By.xpath("//input[@data-field='lastname']");
	By jobTitle = By.xpath("//input[@data-field='jobtitle']");

	public ContactsPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
	}
	
	public String getContactsPageTitle() {
		//jsUtil.checkPageIsReady();
		elementUtil.waitForTitlePresent(AppConstants.CONTACTS_PAGE_TITLE);
		return elementUtil.doGetPageTitle();
	}

//	Allure Report annotation (will show parameters - useful with data driven tc)
	@Step ("Create new contact with {0}, {1}, {2}, {3}")
	public void createNewContact(String mail, String FN, String LN, String jobtitle) {
		jsUtil.checkPageIsReady();
		elementUtil.waitForElementPresent(createContactButton);
		elementUtil.doClick(createContactButton);
		
		elementUtil.waitForElementPresent(email);
		elementUtil.doSendKeys(email, mail);
		
		elementUtil.doSendKeys(firstName, FN);
		elementUtil.doSendKeys(lastName, LN);
		elementUtil.doSendKeys(jobTitle, jobtitle);
		
		jsUtil.clickElementByJS(elementUtil.getElement(createContactFormButton));
	}





}
