package com.client.project.utils;

import java.io.File;
import java.net.InetAddress;
import java.util.Scanner;

public class JenkinsAndAllureUtils {
	
	public static String getFullComputerName(){
		//Author		: Sreekanth
		//Date			: 11-06-2018
		//Description	: To get the full computer name of the machine in which the test is running 
		String fullComputerName = "fullComputerName";
		try{
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			fullComputerName = addr.getCanonicalHostName().toLowerCase();
		}
		catch (Exception ex)	{
			System.out.println("Full Computer Name can not be resolved");
		}
		return fullComputerName;
	}

	public static String getProjectName(){
		//Author		: Sreekanth
		//Date			: 11-06-2018
		//Description	: To get the Project Name 
		String projectName = "projectName";
		try{
			File workingFile=new File("");
			String workingDir = workingFile.getAbsolutePath();
			String [] workingDirSplit=workingDir.split("\\\\");
			projectName=workingDirSplit[workingDirSplit.length-1];
		}
		catch (Exception e){
			System.out.println("Error in finding the Project Name");
		}
		return projectName;
	}

	public static String getJenkinsJobName(){
		//Author		: Sreekanth
		//Date			: 11-06-2018
		//Description	: To get the Jenkins Job Name 
		String jenkinsJobName = "jenkinsJobName";
		try{
			File workingFile=new File("");
			String workingDir = workingFile.getAbsolutePath();
			String [] workingDirSplit=workingDir.split("\\\\");
			jenkinsJobName=workingDirSplit[workingDirSplit.length-2];
		}
		catch (Exception e){
			System.out.println("Error in finding the Jenkins Job Name");
		}
		return jenkinsJobName;
	}

	@SuppressWarnings({"finally"})
	public static String getJenkinsBuildNumber() {
		String result="";
		try{
			File workingFile=new File("");
			String workingDir = workingFile.getAbsolutePath();
			workingDir=workingDir.replaceAll("workspace", "jobs");
			workingDir=workingDir.replaceAll(getProjectName(), "");
			@SuppressWarnings("resource")
			int build=Integer.parseInt(new Scanner(new File(workingDir+"\\nextBuildNumber")).useDelimiter("\\Z").next());
			build--;
			result=Integer.toString(build);
		}
		catch(Exception e){
			result="BuildNumberERROR";
		}
		finally{return result;}
	}
	
	public static String getDynamicAllureReportURL(){
		//Author		: Sreekanth
		//Date			: 12-06-2018
		//Description	: To create the Dynamic Allure report URL 
		String allureReportUrl="http://fullComputerName:1337/jenkinsJobName/builds/buildNumber/archive/projectName/Project/build/allure-report/index.html";
		String buildNumber=getJenkinsBuildNumber();
		String fullComputerName=getFullComputerName();
		String projectName=getProjectName();
		String jenkinsJobName=getJenkinsJobName();
		allureReportUrl=allureReportUrl.replaceAll("fullComputerName", fullComputerName);
		allureReportUrl=allureReportUrl.replaceAll("jenkinsJobName", jenkinsJobName);
		allureReportUrl=allureReportUrl.replaceAll("buildNumber", buildNumber.toString());
		allureReportUrl=allureReportUrl.replaceAll("projectName", projectName);
		return allureReportUrl;
	}
	
	public static String getDynamicAllureReportFailureURL(){
		//Author		: Sreekanth
		//Date			: 12-09-2018
		//Description	: To create the Dynamic Allure report URL which is pointing to failure list 
		String allureReportFailureUrl=getDynamicAllureReportURL();
		allureReportFailureUrl=allureReportFailureUrl+"#categories";
		return allureReportFailureUrl;
	}

	public static String getJenkinsURL(){
		//Author		: Sreekanth
		//Date			: 21-06-2018
		//Description	: To create the Jenkins URL
		String jenkinsUrl="http://fullComputerName:8080/job/jenkinsJobName/buildNumber/console";
		String fullComputerName=getFullComputerName();
		String jenkinsJobName=getJenkinsJobName();
		String buildNumber=getJenkinsBuildNumber();
		jenkinsUrl=jenkinsUrl.replaceAll("fullComputerName", fullComputerName);
		jenkinsUrl=jenkinsUrl.replaceAll("jenkinsJobName", jenkinsJobName);
		jenkinsUrl=jenkinsUrl.replaceAll("buildNumber", buildNumber);
		return jenkinsUrl;
	}

}
