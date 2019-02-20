package com.client.project.utils;

import java.util.logging.Logger;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{
	/*
	 * (non-Javadoc)
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 * 
	 * This method decides how many times a test needs to be rerun.
	 * TestNg will call this method every time a test fails. So we 
	 * can put some code in here to decide when to rerun the test.
	 * 
	 * Note: This method will return true if a tests needs to be retried
	 * and false it not.
	 */
	
	protected Logger log;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	private int count = 0;
	// set your count to re-run test
	private int maxCount = Integer.parseInt(ConfigFileUtils.readConfigFile("TestCaseRetryCount", "./test.properties").trim()); 

	public boolean retry(ITestResult result) {
		String failCount = null;
		if (count < maxCount) {
			count++;
			failCount = ""+count;
			TestNGUtils.reportLog("TestCase ID:"+result.getMethod().getMethodName()+", Retry:"+failCount);
			System.out.println(result.getMethod().getMethodName()+", Retry:"+failCount);
			return true;
		}
		TestNGUtils.reportLog("TestCase ID:"+result.getMethod().getMethodName()+", Count:"+failCount);
		return false;
	}
}
