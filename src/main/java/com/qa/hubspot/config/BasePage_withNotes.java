package com.qa.hubspot.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.hubspot.util.OptionsManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage_withNotes {

//	public WebDriver driver;
	public Properties prop;
	public static boolean highlightElement;
	public OptionsManager optionsManager;
	
	/* THREAD / THREAD SAFETY:
	 - Multi-threading refers to two or more tasks executing concurrently within a single program. 
     - A thread is an independent path of execution within a program. 
     - Many threads can run concurrently within a program.
	 
     - Thread-safe code is code which can safely be used or shared in concurrent or multi-threading environment and they will behave as expected. 
     - Any code, class or object which can behave differently from its contract on concurrent environment is not thread-safe.
	 
	 
	 * THREADLOCAL:  stores data inside of a map – with the thread as the key.
	 - ThreadLocal class enables you to create variables that can only be read and written by the same thread
	 - Thus, even if two threads are executing the same code, and the code has a reference to the same ThreadLocal variable, 
	   the two threads cannot see each other's ThreadLocal variables. 
	 - Thus, the Java ThreadLocal class provides a simple way to make code thread safe that would not otherwise be so.
	   
     - useful in parallel execution (ex: 5 browsers at the same time)
     - have to use a thread-local copy and initialize driver to create multiple local copies 
     - return ThreadLocal driver with the help of getDriver() method
	 
	 * SYNCHRONIZED keyword -- https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
	 - only allow one thread at a time into a particular section of code thus allowing us to protect, for example, variables or data from being corrupted 
	   by simultaneous modifications from different threads
	 - a constructor can not be synchronized 
	 
	 */
	
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
	
	//get method - get driver
	public static synchronized WebDriver getDriver() {
		return tldriver.get(); //Returns the value in the current thread's copy of this thread-local variable
	}
	
	public WebDriver init_driver(String browserName) {
		highlightElement = prop.getProperty("highlight").equals("yes") ? true : false;  
		// if condition is satisfied, returns true, otherwise returns false
		
		System.out.println("Browser name is " + browserName);

		optionsManager = new OptionsManager(prop);
		
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
		//	driver = new ChromeDriver(optionsManager.getChromeOptions());
			tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));

			//*** Use OptionsManager util class instead of putting every option in Base Page
//			if (prop.getProperty("headless").equals("yes")) {
//				ChromeOptions co = new ChromeOptions();
//				co.addArguments("--headless");
//				driver = new ChromeDriver(co);
//			}
//			else {
//				driver = new ChromeDriver();
//			}
		
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
		//	driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

//			if (prop.getProperty("headless").equals("yes")) {
//				FirefoxOptions fo = new FirefoxOptions();
//				fo.addArguments("--headless");
//				driver = new FirefoxDriver(fo);
//			}
//			else {
//				driver = new FirefoxDriver();
//			}
			
		} else if (browserName.equalsIgnoreCase("safari")) {
			WebDriverManager.getInstance(SafariDriver.class).setup();
		//	driver = new SafariDriver();
			tldriver.set(new SafariDriver());

		} else {
			System.out.println("Browser name " + browserName + " is not found. Please pass the correct browser.");
		}

		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();

//		driver.manage().deleteAllCookies();
//		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//		driver.manage().window().maximize();

//		return driver;
		return getDriver();

	}

	public Properties init_properties() {
		prop = new Properties();
		String path = null;
		String env = null;

		try {
			env = System.getProperty("env");
			if (env.equals("qa")) {
				path = ".\\src\\main\\java\\com\\qa\\hubspot\\config\\qa.config.properties";				
			} else if (env.equals("stg")) {
				path = ".\\src\\main\\java\\com\\qa\\hubspot\\config\\stg.config.properties";
			}
		} catch (Exception e) {
			path = ".\\src\\main\\java\\com\\qa\\hubspot\\config\\config.properties";
		}
		
		try {
			FileInputStream ip = new FileInputStream(path);
			prop.load(ip);
		} catch (FileNotFoundException e) {
			System.out.println("Please correct your config.properties file");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/** 
	 *  Take screenshot
	 */
	public String getScreenshot() {
		//driver typecasted into TakeScreenshot
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		
		//hardcoded path is C:\Users\kazie\eclipse-workspace\Jan2020POMSeries\screenshots
		//however, we should use "user.dir" of current project and concatenated with /screenshots folder - plus the latest timestamp and file type
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		
		File destination = new File(path);

		try {
			//can not write path inside FileUtils.copyFile b/c path is a String, so we must transfer to File object
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.out.println("Screenshot capturing has failed");
		}

		return path;

	}
}
	

