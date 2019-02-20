package com.client.project.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.SkipException;

import ru.yandex.qatools.allure.annotations.Attachment;

public class TestInitializeUtils {

	public WebDriver initializeDriver() 
	{
		//LOG Entry : "org.openqa.selenium.remote.ProtocolHandshake createSession" --> OFF
		Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
		
		WebDriver driver=null;
		for(int x=0;x<3;x++){
			try{
				String browser=ConfigFileUtils.readConfigFile("Selenium_Test_Browser", "./test.properties").toLowerCase();
				String baseSite=ConfigFileUtils.readConfigFile("Selenium_Test_Url", "./test.properties");
				String testExecutionOn=ConfigFileUtils.readConfigFile("Test_Execution_On", "./test.properties");
				if(testExecutionOn.equalsIgnoreCase("")){
					testExecutionOn="localhost";
				}
				//Remote web driver code
				if(browser.contains("ff")||browser.contains("firefox")||browser.contains("fire")){
					System.setProperty("webdriver.gecko.driver","./lib/geckodriver.exe");
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.setCapability("marionette", true);
					firefoxOptions.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"); //Location where Firefox is installed
					driver=new FirefoxDriver(firefoxOptions);
					DesiredCapabilities firefoxCapability=DesiredCapabilities.firefox();
					firefoxCapability.setCapability("moz:firefoxOptions", firefoxOptions);
					//testExecutionOn --> localhost or ip address of machine
					driver=new RemoteWebDriver(new URL("http://"+testExecutionOn+":5566/wd/hub"),firefoxCapability);
				}
				else if(browser.contains("google")||browser.contains("chrome")||browser.contains("googlechrome")){
					System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
					ChromeOptions chromeOptions=new ChromeOptions();
					chromeOptions.addArguments("disable-infobars");
					//testExecutionOn --> localhost or ip address of machine
					driver=new RemoteWebDriver(new URL("http://"+testExecutionOn+":5566/wd/hub"),chromeOptions);
				}
				//End of remote web driver code
				
				driver.manage().window().maximize();
				driver.manage().timeouts().pageLoadTimeout(70*20*1000,TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(70*20*1000,TimeUnit.SECONDS);
				driver.get(baseSite);
				break;
			}
			catch(Exception e){
				System.out.println(e);
				try{driver.close();}
				catch(Exception e1){}
				try{driver.quit();}
				catch(Exception e1){}
				if(x==3){
					throw new RuntimeException(e.toString());
				}
			}
		}
		return driver;
	}

	public  void closeDriver(WebDriver driver) 
	{
		try{
			try{
				driver.close();
			}
			catch(Exception e1){}
			driver.quit();
		}
		catch(Exception e){}
	}

	public static void onTestFailureScreenshot(WebDriver driver,String testCaseName,Exception exception) {
		// taking screenshot
		String path;
		try {
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File source = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
			path = "./Project/build/test-output/screenshots/" + testCaseName+".png";
			FileUtils.copyFile(source, new File(path));
			Reporter.log(path);
			saveScreenShotAsPNG(driver);
		}
		catch(IOException e) {
			try{
				driver.close();
			}catch(Exception e1){}
			driver.quit();
			path = "Failed to capture screenshot: " + e.getMessage();
			Reporter.log(path);
		}
		if(exception.getClass().getName().toLowerCase().contains("skipexception")){
			System.out.println(testCaseName+" : Skipped");
			driver.close();
			throw new SkipException(exception.getMessage());
		}
		else{
			System.out.println(testCaseName+" : Failed");
		}
	}
	
	@Attachment(value="Failure Screenshot", type="image/png")
	public static byte[] saveScreenShotAsPNG (WebDriver driver){
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	}

	public static void onTestPass(String testCaseName){
		System.out.println(testCaseName+" : Passed");
	}
	
	public static void storeEnvDetailsForAllure(){
		//Author		: Sreekanth Vadassery
		//Description	: Finding the test execution details and storing it into 
		// 				  environment.properties file inside allure-results folder
		OutputStream outputStream = null;
        Properties prop = new Properties();
        //Getting the browser details
        prop.setProperty("Browser", ConfigFileUtils.readConfigFile("Selenium_Test_Browser", "./test.properties"));
        prop.setProperty("Test URL", ConfigFileUtils.readConfigFile("Selenium_Test_Browser", "./test.properties"));
        prop.setProperty("Test Groups", ConfigFileUtils.readConfigFile("TestNG_Test_Groups", "./test.properties"));
        try {
        	outputStream = new FileOutputStream("./target/allure-results/environment.properties");
            prop.store(outputStream, "Envoronment Property File");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
	}

}
