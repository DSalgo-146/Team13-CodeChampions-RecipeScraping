package recipescraping.utils;

import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import recipescraping.utils.ConfigReader;
import recipescraping.utils.DriverFactory;



public class ApplicationHooks {

	private DriverFactory driverFactory;
	private WebDriver driver;
	private ConfigReader configReader;
	Properties prop;

	
	public void initProperty() { //getProperty() method called in the below method launchBrowser()
		//System.out.println("initProperty");
		configReader = new ConfigReader();
		prop = configReader.init_prop(); //init_prop() is the function call where method defined in ConfigReader class
	}

	public void launchBrowser() {
		//System.out.println("launchBrowser");
		String browserName = prop.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver(browserName);//init_driver() method defined in DriverFactory class which takes the browser name read from the property file and return the corresponding driver of it
		
	}

	
	public void quitBrowser() {
		driver.quit();
	}


	}


