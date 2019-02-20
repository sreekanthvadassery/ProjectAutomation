package com.client.project.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.client.project.utils.ElementFormatter;
import com.client.project.utils.SeleniumUtils;
import com.client.project.utils.TestNGUtils;

public class GitHubLoginPage {
	
	public static  ElementFormatter GITHUB_LOGIN_PAGE_SIGNIN_BUTTON = new ElementFormatter("github-login-page-signin-button","//a[@class='HeaderMenu-link no-underline mr-3']");
	
	//Common for all page class
	private  WebDriver driver ;

	public GitHubLoginPage(WebDriver driver) {
		this.driver=driver;
	}
	//Common for all page class
	
	public GitHubLoginPage validateGithubLoginPage(){
		//Author 		: Sreekanth Vadassery
		//Date			: 17-Feb-2019
		//Description 	: Validate the GitHub Login page
		TestNGUtils.reportLog("Validate the GitHub Login Page");
		SeleniumUtils.waitForElement(driver, GITHUB_LOGIN_PAGE_SIGNIN_BUTTON);
		return this;
	}
	
	public GitHubLoginPage clickSignInButton(){
		//Author 		: Sreekanth Vadassery
		//Date			: 17-Feb-2019
		//Description 	: Click on the Signin button
		TestNGUtils.reportLog("Click on the SignIn button");
		SeleniumUtils.waitForElement(driver, GITHUB_LOGIN_PAGE_SIGNIN_BUTTON);
		WebElement element=SeleniumUtils.findElement(driver, GITHUB_LOGIN_PAGE_SIGNIN_BUTTON);
		element.click();
		return this;
	}
	
}
