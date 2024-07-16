package recipescraping.utils;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {

	private Properties prop;
	
	private static Logger logger = LogManager.getLogger();

	/**
	 * This method is used to load the properties from config.properties file
	 * 
	 * @return it returns Properties prop object
	 */
	public Properties init_prop() { // fn call in appln hooks

		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream("./src/main/resources/config/config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			logger.error("Error while reading file ", e);
		} catch (IOException e) {
			logger.error("IOException while reading file ", e);
		}

		return prop; // entire ket, values pairs(as object) is read from property file and returned
						// to hooks

	}

	public  String getExcelFilePath() {
		String excelfilelpath = prop.getProperty("excelfilepath");
		if (excelfilelpath != null)
			return excelfilelpath;
		else
			throw new RuntimeException("Please specify Excel file path in the config.properties file.");
	}

}
