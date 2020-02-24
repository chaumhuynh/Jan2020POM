package com.qa.hubspot.config;

import org.testng.annotations.Test;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class Notes {

	/* Single control for changing browser: change browser in .properties file
	 * Maintain different users in .properties file
	 * OR keep separate user roles like MPM.properties, CRG.properties, PAR.properties
	 */
	
	/*
	 * headless control is also enabled from .properties file
	 * Chrome has headless option recently
	 * HTML Driver is also deprecated - won't work for complex DOM, pop up, alert...  
	 */
	
	/* depends on the application, we can either put explicit wait in ElemntUtil > getElement
	 * but this creates a dependency -> not guaranteed to work
	 * It is not recommended to put wait in doSendKeys or doClick b/c wait is applied for every single element >> slow
	 *  
	 * we can also create independent methods for waitForElementPresent, waitForTitlePresent, waitForElementVisible etc
	 * and then call those methods when needed
	 */
	
	/* AJAX = Asynchronous JavaScript and XML
	 * when we navigate to or refresh a page, page and DOM is loaded
	 * for app like ESPN live sport scores, DOM has some Ajax components are changing
	 * Ajax allows web pages to be updated without refreshing the page
	 */
	
	/* JavascriptExecutor
	 * An Interface that helps to execute JavaScript through Selenium Webdriver
	 */
	
	/* MAVEN - from COMMAND LINE
	 C:\Users\kazie>cd C:\Users\kazie\eclipse-workspace\Jan2020POMSeries
	 C:\Users\kazie\eclipse-workspace\Jan2020POMSeries>mvn clean install -Denv=qa
	 -D is used to set env (can change to -Denv=qa or -Denv=stg
	 when no env is specified, catch block is used (in BasePage) and default config.properties is used

	 We can pass package.testclass in cmd to specify which test to execute 
	 C:\Users\kazie\eclipse-workspace\Jan2020POMSeries>mvn clean install -Dtest=com.qa.hubspot.tests.LoginPageTest -Denv=stg
	 
	 Use * to execute all test classes
	 C:\Users\kazie\eclipse-workspace\Jan2020POMSeries>mvn clean install -Dtest=com.qa.hubspot.tests.* -Denv=qa
	 
	 Specify browser and url -Dbrowser=chrome -Durl= ... (then, we don't maintain url and browser in .properties file, but this is not preferred)
	 
	 In Eclipse, we can also right click on project > run as > Maven build
	 We can define env variables in the "Edit configuration and launch" but generally this is not preferred
	 
	 */
	
	/* MAVEN LIFE CYCLE 
	 - mvn clean install: first clean project, generate resources, compile code, go through testResources, then execute test
	 - mvn test: check src/main/resources, if there is non existing resource then skip; slip compiling if all classes are up to date, then execute test >> quicker 
	 */
	
	/* REPORTING
	 - default TestNG reports are index.html and emailable-report.html - not as detailed and graphically pleasing
	 - Extent Report: third party report; add xml library and listener
	 - Allure Report: more complicated to configure (more docs at docs.qmeta.io/allure)
	 	- need to add dependency io.qameta.allure and plugin config (surefire, aspectjweaver,...)
	 	- json files are generated in the allure-results folder
	 	- html reports are not generated - have to copy project path and go to cmd >> cd [path]
	 	  then use command: allure serve allure-results and then html report is generated
	 	- highly recommended b/c it has rich details and graphic elements like chart (overview, suites, graphs, time of execution, different languages...) 
	 	- might be good for qa personnel and during demo but can't be downloaded and emailed (can save as pdf or web page manually?)
	 	- ALLURE ANNOTATIONS: at class level we have @Epic and @Feature; for each tc we can have @Description, @Severity, @Step

	@Epic("HARP-1523: Create Login Page Feature")
	@Feature("HARP-1525: Create test for login page on HubSpot")
	public class LoginPageTest { .... }
	
	@Test (priority = 1)
	@Description("This test verifies login page title")
	@Severity(SeverityLevel.NORMAL)
	public void verifyLoginPageTitleTest()  { ... }
	 
	@Step ("Create new contact with {0}, {1}, {2}, {3}")
	public void createNewContact(String mail, String FN, String LN, String jobtitle) { ... }
	 
	 	
	 - PDF Report: can be shown to manager / client; pdf file is generated and file name matches test tag name in xml file
	 - Web report --> Tesults report (also very good, detailed report) - have to sign up and login
	 	- go to tesults.com/config > create new project > choose the free plan > store target token (used in script to generate report)
	 	- go to tesults.com/docs to check out documentations > choose TestNG > copy the dependencies and paste into pom.xml
	 	- create listener class (copy/paste from tesults website) and in the method onFinish, paste correct target token in 
	 	- add listener tag in test runner xml file
	 	- can be integrated with JIRA or other bug tracking software (associate bugs, assign tc to a team member, can maintain history and comment like JIRA)
		- can also maintain execution result history 
		- for a lot more users and features, team should consider paid version 
		- don't need to maintain any infrastructure; URL for report can be shared with anyone; good customer support
	 
	 LISTENERS listen to activities to events happening with tc
	 - once execution is done, listeners collect information and generate reports
	 - listener is a feature provided by TestNG; already available so we only need to customize and use it
	 - add dependencies in pom.xml and add <listeners> tag in test runner (xml) file - after suite level
	 - good practice is team has to decide on which report to use, not using multiple listeners at the same time (slow scripts down)
	
	 */
	
	/* SHORTCUTS -- More at: https://dzone.com/articles/effective-eclipse-shortcut-key
	 - comment out: Ctrl+Shift+/ or Ctrl+Shift+c
	 - format code: Ctrl+a then Ctrl+Shift+f
	 - correct indentation: Ctrl+i
	 - automatic suggestion / filling: Ctrl+Space
	 - organize imports / add imports: Ctrl+Shift+o
	 */
	
	/* We can use groups to specify smoke test or specific tagging
	   Must use (alwaysRun=true) at @BeforeMethod otherwise driver won't be initialized --> NullPointerException
	   Grouping is generally hard to maintain (have to fix every single tc; better practice is define tc in test runner xml file)
	 */
	
	
	/* PARALLEL EXECUTION
	   - we can have multiple test block in the test runner xml file and specify browser in <parameter> tag 
	   - we also specify at the suite level thread count (for example if w have 2 browsers, we put thread-count = 2) 
	     and parallel = tests (meaning at test level / inside the test block)
	   - then, in test class, at setup method, we use @Parameters annotation	
		
	   <suite name="Hub Spot Parallel Execution" thread-count = "2" parallel = "tests">
		...
	   
	   <test name="Hub Spot Functional Test - Chrome">
			<parameter name="browser" value="chrome" />
				<classes>
					<class name="com.qa.hubspot.tests.HomePageTest" />
				</classes>
		</test>
	
	 */
	
}
