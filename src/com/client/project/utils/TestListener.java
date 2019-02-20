package com.client.project.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

	int passCount;
	int failCount;
	int skipCount;
	String executionTime;

	@Override
	public void onFinish(ITestContext context) { 
		//Author	: Sreekanth
		// TODO Auto-generated method stub
		Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
		for (ITestResult temp : failedTests) {
			ITestNGMethod method = temp.getMethod();
			if (context.getFailedTests().getResults(method).size() > 1) {
				failedTests.remove(temp);
			} else {
				if (context.getPassedTests().getResults(method).size() > 0) {
					failedTests.remove(temp);
				}
			}
		}
		//Finding the test counts and time
		passCount=context.getPassedTests().getAllResults().size();
		failCount=context.getFailedTests().getAllResults().size();
		skipCount=context.getSkippedTests().getAllResults().size();
		executionTime=GeneralUtils.getTimeDiff(context.getStartDate(),context.getEndDate());
		
		//Calling the method to store the test count and execution time details to testresult.properties file (inside test-output folder)
		storeTestCountDetailsToPropertiesFile(passCount,failCount,skipCount,executionTime);
	}
	
	public void storeTestCountDetailsToPropertiesFile(int passCount,int failCount,int skipCount,String executionTime){
		//Author		: Sreekanth
		//Date			: 14-06-2018
		//Description	: Storing the test counts to testresult.properties
		OutputStream outputStream = null;
		Properties prop = new Properties();
		prop.setProperty("passCount", Integer.toString(passCount));
		prop.setProperty("failCount", Integer.toString(failCount));
		prop.setProperty("skipCount", Integer.toString(skipCount));
		prop.setProperty("executionTime", executionTime);
		try {
			outputStream = new FileOutputStream("./Project/build/test-output/testresult.properties");
			prop.store(outputStream, "Test Result Property File");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Following are all the method stubs that you do not have to implement
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onTestFailure(ITestResult result) {
		// TODO  Auto-generated method stub
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

}
