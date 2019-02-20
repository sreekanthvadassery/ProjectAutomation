package com.client.project.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;

import com.client.project.pages.GitHubLoginPage;
import com.client.project.utils.TestInitializeUtils;

public class GitHubLoginTestCases {
	
	@Test (groups={"GitHub_Login_Test_1","GitHub_Login_Testcases","GitHub_Smoke_Testcases","GitHub_Regression_Testcases"},
			description = "To validate the GitHub login functionality")
	@Issue("")
	public void GitHub_Login_Test_1() throws Exception{
	/*
	Test Case		:	GitHub_Login_Test_1
	Test Module		:	GitHub_Login_Testcases
	Description    	:	Base story --> 
						To validate the GitHub login functionality
	Author 			:   Sreekanth Vadassery
	Date			:   17-Feb-2019
	*/
		String testCaseName = new Exception().getStackTrace()[0].getMethodName();
		TestInitializeUtils testStart= new TestInitializeUtils();
		WebDriver driver=testStart.initializeDriver();
		try{
			GitHubLoginPage gitHubLoginPage= new GitHubLoginPage(driver);
			gitHubLoginPage
				.validateGithubLoginPage()
				.clickSignInButton();
			
			testStart.closeDriver(driver);
			TestInitializeUtils.onTestPass(testCaseName);
		}
		catch(Exception e){
			TestInitializeUtils.onTestFailureScreenshot(driver, testCaseName,e);
			testStart.closeDriver(driver);
			org.testng.Assert.fail(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
		
}
