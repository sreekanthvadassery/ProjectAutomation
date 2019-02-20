package com.client.project.utils;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {
	static HashMap<String, Object> hashMap = new HashMap<String, Object>();

	public static void waitForElement(WebDriver driver,ElementFormatter elementDetails){
		try{
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementDetails.getelementValue())));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementDetails.getelementValue())));
			WebElement element=driver.findElement(By.xpath(elementDetails.getelementValue()));
			wait.until(ExpectedConditions.visibilityOf((element)));
		}
		catch(Exception e){
			throw new RuntimeException(elementDetails.getelementName()+" not found in the page.");
		}
	}

	public static void waitForInvisibleElement(WebDriver driver,ElementFormatter elementDetails){
		try{
			//WebDriverWait wait = new WebDriverWait(driver,61);
			//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementDetails.getelementValue())));
			WebDriverWait wait = new WebDriverWait(driver,91);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementDetails.getelementValue())));
		}
		catch(Exception e){
			throw new RuntimeException(elementDetails.getelementName()+" not found in the page.");
		}
	}

	public static WebElement findElementToClick(WebDriver driver,ElementFormatter elementDetails){
		WebElement element;
		try{
			/* Time out in seconds */
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementDetails.getelementValue())));
			element=driver.findElement(By.xpath(elementDetails.getelementValue()));
			wait.until(ExpectedConditions.visibilityOf((element)));
			element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementDetails.getelementValue())));
		}
		catch(Exception e){
			throw new RuntimeException(elementDetails.getelementName()+" not found in the page.");
		}
		return element;
	}

	public static void waitForElementToBeClickable(WebDriver driver,ElementFormatter elementDetails){
		WebElement element;	
		try{
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementDetails.getelementValue())));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementDetails.getelementValue())));
			element=driver.findElement(By.xpath(elementDetails.getelementValue()));
			wait.until(ExpectedConditions.visibilityOf((element)));
			element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementDetails.getelementValue())));
		}
		catch(Exception e){
			throw new RuntimeException(elementDetails.getelementName()+" not found in the page.");
		}
	}

	public static boolean isVisible(WebDriver driver,ElementFormatter elementDetails){
		/*
		 * This function is to check the visibility of the element.
		 */
		boolean flagValue=false;
		try{
			/* Time out in seconds */
			WebDriverWait wait = new WebDriverWait(driver,1);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementDetails.getelementValue())));
			WebElement element=driver.findElement(By.xpath(elementDetails.getelementValue()));
			wait.until(ExpectedConditions.visibilityOf((element)));
			flagValue=true;
		}
		catch(Exception e){
			flagValue=false;
			//throw new RuntimeException(elementDetails.getelementName()+" not visible in the page.");
		}
		return flagValue;
	}

	public static void mouseHover(WebDriver driver,ElementFormatter elementDetails){
		SeleniumUtils.waitForElement(driver, elementDetails);
		By by_element_1 = By.xpath(elementDetails.getelementValue());
		Actions action = new Actions(driver);
		WebElement element_1 = driver.findElement(by_element_1);
		action.moveToElement(element_1).build().perform();
	}

	public static void mouseHoverWithOffsetValue(WebDriver driver,ElementFormatter elementDetails){
		SeleniumUtils.waitForElement(driver, elementDetails);
		By by_element_1 = By.xpath(elementDetails.getelementValue());
		Actions action = new Actions(driver);
		WebElement element_1 = driver.findElement(by_element_1);
		action.moveToElement(element_1, 10, 10).build().perform();
	}
	
	public static WebElement findElement(WebDriver driver,ElementFormatter elementDetails){
		/*
		 * This function is the add on for selenium findElement method.
		 */
		WebElement element=null;
		try{
			element=driver.findElement(By.xpath(elementDetails.getelementValue()));
		}
		catch(Exception e){
			throw new RuntimeException(elementDetails.getelementName()+" not found in the page.");
		}
		return element;
	}

	public static void storeKeyValue(String key,Object value){
		hashMap.put(key, value);
	}
	
	public static Object retrieveKeyValue(String key){
		Object hashValue=hashMap.get(key);
		return hashValue;
	}

	public static void deleteKeyValue(String key){
		hashMap.remove(key);
	}

}
