package com.client.project.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import ru.yandex.qatools.allure.annotations.Step;

public class TestNGUtils 
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws IOException
	{
		try{
			System.out.println("Test URL: "+ConfigFileUtils.readConfigFile("Selenium_Test_Url", "./test.properties"));
			
			//Setting the environment details 
			TestInitializeUtils.storeEnvDetailsForAllure();
			
			//Test suite creation
			List testGroupList = new ArrayList();
			List testPackageList = new ArrayList();
			XmlSuite suite = new XmlSuite();
			suite.setName("Project_Automation");
			suite.setParallel(ParallelMode.METHODS);
			
			//Adding the listener for test retry
			suite.addListener("com.client.project.utils.AnnotationTransformer");
			suite.addListener("com.client.project.utils.TestListener");
			
			String threadCount=ConfigFileUtils.readConfigFile("TestNG_Thread_Count", "./test.properties");
			suite.setThreadCount(Integer.parseInt(threadCount));
			XmlTest test = new XmlTest(suite);
			test.setJunit(false);
			test.setName("Project_Automation");
			String groups=ConfigFileUtils.readConfigFile("TestNG_Test_Groups", "./test.properties").trim();
			String[] groupArray=groups.split(",");
			testGroupList.addAll(Arrays.asList(groupArray));
			test.setIncludedGroups(testGroupList);
			String packages=ConfigFileUtils.readConfigFile("TestNG_Package_Name", "./test.properties");
			testPackageList.add(new XmlPackage(packages));
			test.setPackages(testPackageList);
			String buildName="Project";

			List suites = new ArrayList();
			suites.add(suite);
			TestNG tng = new TestNG();
			
			//Old code start - Before Allure functionality
			/*
			tng.setOutputDirectory((new StringBuilder()).append(buildName).append("/build/test-output").toString());
			tng.setXmlSuites(suites);
			tng.run();
			FileWriter fstream = new FileWriter((new StringBuilder()).append(buildName).append("/build/test-output").append("/run.xml").toString());
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(suite.toXml());
			out.close();
			*/
			//Old code ends - Before Allure functionality
			
			//New code start - With allure reporting
			tng.setOutputDirectory((new StringBuilder()).append(buildName).append("/build/test-output").toString());
			tng.setXmlSuites(suites);
			//Commenting tng.run() as per new change for Allure reporting. 
			//We are just creating the run.xml file with all the test suite execution details here.
			//From the build.xml, we will simply invoke the run.xml using testng task.
			//For running the TestNGUtils.java as simple java application un-comment tng.run() method
			//tng.run();
			FileWriter fstream = new FileWriter((new StringBuilder()).append(buildName).append("/build/test-output").append("/run.xml").toString());
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(suite.toXml());
			out.close();
			//New code ends - With Allure reporting
			
			//Get the Dynamic URL for the Allure Report
			System.out.println("Allure report will be hopsted to the below URL after the build is finished");
			System.out.println(JenkinsAndAllureUtils.getDynamicAllureReportURL());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void reportLog(String message){
		//DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM hh:mm:ss a");
		Calendar cal = Calendar.getInstance();
		String Timestamp = dateFormat.format(cal.getTime());
		message =Timestamp+": "+message;
		//Reporter.log(message);
		reportLogWithTimeStamp(message);
	}
	
	@Step("{0}")
	public static void reportLogWithTimeStamp(String message){
		Reporter.log(message);
	}

}

