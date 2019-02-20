package com.client.project.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtils {
	
	public static boolean isStringcontainsSpecialChars(String string){
		//Author		: Sreekanth
		Pattern p = Pattern.compile("\\W");
		Matcher m = p.matcher(string);
		return m.find();
	}

	public static String getCurrentDateTime(String format){
		//Author		: Sreekanth
		//Example formats : dd/MM/yyyy hh mm ss, hh mm ss
		SimpleDateFormat sdfDate = new SimpleDateFormat(format);
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static int getRandomNumberBetween(int min, int max){
		//Author		: Sreekanth
		//excludes min and max
		Random foo = new Random();
		int randomNumber = foo.nextInt(max - min) + min;
		if(randomNumber == min){
			// Since the random number is between the min and max values, simply add 1
			return min + 1;
		}
		else{
			return randomNumber;
		}
	}

	public static int getRandomNumberFrom(int min, int max){
		//Author		: Sreekanth
		//including min and max
		Random foo = new Random();
		int randomNumber = foo.nextInt((max + 1) - min) + min;
		return randomNumber;
	}

	public static String convertDateFormatIntoddmmyyyy(String dateFromDB){
		//Author		: Sreekanth
		//Converting a String like "2016-09-23 06:30:18.290" to "09/23/2016"
		String yyyy_mm_dd=dateFromDB.split(" ")[0];
		String[] stringSplitted=yyyy_mm_dd.split("-");
		String mm,dd,yyyy;
		mm=stringSplitted[1];
		dd=stringSplitted[2];
		yyyy=stringSplitted[0];
		if(mm.length()==1){
			mm="0"+stringSplitted[0];
		}
		if(dd.length()==1){
			dd="0"+dd;
		}
		String mmddyyyy=mm+"/"+dd+"/"+yyyy;
		return mmddyyyy;
	}

	public static String convertDateFormatIntoyyyymmdd(String dateFromDB){
		//Author		: Sreekanth
		//Converting a String like "2016-09-23 06:30:18.290" to "2016/09/23"
		String yyyy_mm_dd=dateFromDB.split(" ")[0];
		String[] stringSplitted=yyyy_mm_dd.split("-");
		String mm,dd,yyyy;
		mm=stringSplitted[1];
		dd=stringSplitted[2];
		yyyy=stringSplitted[0];
		if(mm.length()==1){
			mm="0"+stringSplitted[0];
		}
		if(dd.length()==1){
			dd="0"+dd;
		}
		String yyyymmdd=yyyy+"/"+mm+"/"+dd;
		return yyyymmdd;
	}

	public static String getTimeDiff(Date dateOne, Date dateTwo){
		//Author		: Sreekanth
		//Date			: 14-06-2018
		//Description	: To get the time difference in 'hour(s) min(s) sec(s)' format
		String diff = "";
		long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
		diff = String.format("%d hour %d min %d sec", 
				TimeUnit.MILLISECONDS.toHours(timeDiff),
				TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)),
				TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)))); 
		return diff;
	}

	public static String getCurrentProjectFolderPath(){
		String currentProjectFolderPath="";
		try{
			currentProjectFolderPath=new File(GeneralUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
			//currentProjectPath=currentProjectPath.replace("bin", "");
			currentProjectFolderPath=currentProjectFolderPath.split("Project")[0];
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return currentProjectFolderPath;
	}

	public static void executeBatFile(String filePath,String fileName){
		try{
			//String[] cmd = {"cmd", "/c", "start", path to bat file};
			String[] cmd = {"cmd", "/c", "start", filePath+"\\"+fileName};
			Runtime.getRuntime().exec(cmd);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String percentageDifference(Double number1,Double number2){
		//Author		: Sreekanth Vadassery
		//Description	: Calculate the percentage change between number1 and number2
		//Date			: 24-Jan-2019
		final DecimalFormat df2 = new DecimalFormat("#.##");
		if(number2-number1==0){
			return "0";
		}
		if(number1==0){
			return "NA";
		}
		Double percentageChange=((number2-number1)/number1)*100;
		String change=df2.format(percentageChange);
		return change;
	}
	
	public static String includePercentageSymbol(String percentage){
		//Author		: Sreekanth Vadassery
		//Description	: Convert the Normal Double variable to % style
		//Date			: 24-Jan-2019
		String percentageValue="";
		if(percentage.equalsIgnoreCase("NA")){
			percentageValue="NA";
		}
		else if(Double.parseDouble(percentage)>0){
			percentageValue= "\u2191"+percentage+"%";
		}
		else if(Double.parseDouble(percentage)<0){
			percentageValue= "\u2193"+Math.abs(Double.parseDouble(percentage))+"%";
		}
		else if(Double.parseDouble(percentage)==0){
			percentageValue= percentage+"%";
		}
		return percentageValue;
	}

}
