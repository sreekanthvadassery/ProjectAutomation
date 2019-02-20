package com.client.project.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigFileUtils {
	private final static Logger LOGGER = Logger.getLogger(ConfigFileUtils.class.getName());
	
	public static Properties initialize(String filePath)  {
		/*
		 * Input Type  : Nil
		 * Return Type : Nil
		 * Description : This method is used to initialize the config property file.
		 */
		SortedProperties testdataConfig = new SortedProperties();
		try {
			testdataConfig.load(new FileInputStream(filePath));
		} 
		catch (Exception e) {LOGGER.log(Level.SEVERE,"script exception"+e);}
		return testdataConfig;
	}
	
	public static String readConfigFile(String key,String filePath) {
		/*
		 * Input Type  : String
		 * Return Type : String
		 * Description : This method will fetch the property value for given key
		 */
		String propertyValue=null;
		try{
			Properties testdataConfig=initialize(filePath);
			propertyValue = testdataConfig.getProperty(key);
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE,"script exception"+e);
		}
		return propertyValue; 
	}
	
	public static void writeConfigFile(String key, String value,String filePath) {
		//Author 		: Sreekanth Vadassery
		//Date			: 17-Feb-2019
		//Description 	: Write Key-Value pair to the config file at 'filePath'
        FileOutputStream fileOut = null;
        FileInputStream fileIn = null;
        try {
            Properties configProperty = new Properties();
            File file = new File(filePath);
            fileIn = new FileInputStream(file);
            configProperty.load(fileIn);
            configProperty.setProperty(key, value);
            fileOut = new FileOutputStream(file);
            configProperty.store(fileOut, "sample properties");
        } 
        catch (Exception ex) {
            Logger.getLogger(ConfigFileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                fileOut.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfigFileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
	
}

/*
 * Sorting the property keys
 */
@SuppressWarnings("serial")
class SortedProperties extends Properties {
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Enumeration keys() {
		Enumeration keysEnum = super.keys();
		Vector<String> keyList = new Vector<String>();
		while(keysEnum.hasMoreElements()){
			keyList.add((String)keysEnum.nextElement());
		}
		Collections.sort(keyList);
		return keyList.elements();
	}
}