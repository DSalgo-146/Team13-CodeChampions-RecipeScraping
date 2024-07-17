package recipescraping.pages;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.cucumber.java.Before;
import recipescraping.pages.RecipeDetailsPage;
import recipescraping.pojo.RecipeGetterSetter;
import recipescraping.utils.ConfigReader;
import recipescraping.utils.DriverFactory;

public class RecipeDetailsPageTest {
	
	private DriverFactory driverFactory;
	private WebDriver driver;
	private ConfigReader configReader;
	Properties prop;

	@BeforeTest
	public void initProperty() { //getProperty() method called in the below method launchBrowser()
		//System.out.println("initProperty");
		configReader = new ConfigReader();
		prop = configReader.init_prop(); //init_prop() is the function call where method defined in ConfigReader class
		String browserName = prop.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver(browserName);//init_driver() method defined in DriverFactory class which takes the browser name read from the property file and return the corresponding driver of it
	
	}

	
	@Test
	public void testGetRecipeDetails() {
		String url = "https://www.tarladalal.com/achar-dip-achari-dip-22718r";
		String id="800";
		driver. get(url);
		Map<String, String> RecipeIDUrlMap = new HashMap<String, String>();
		RecipeIDUrlMap.put(id, "https://www.tarladalal.com/curried-carrot-soup-800r");
		RecipeDetailsPage page = new RecipeDetailsPage(driver);//page instantiation
		RecipeGetterSetter recipe = page.getRecipeDetails(id,url);
		
		System.out.println(recipe);
		
		
	}

}

