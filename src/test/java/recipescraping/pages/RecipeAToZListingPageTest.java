package recipescraping.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import recipescraping.dbutils.RecipeDetailsDBUtil;
import recipescraping.pages.RecipeAToZListingPage;
import recipescraping.pages.RecipeDetailsPage;
import recipescraping.pojo.RecipeGetterSetter;
import recipescraping.utils.ConfigReader;
import recipescraping.utils.DriverFactory;
import recipescraping.utils.IngredientEliminateAndToAddUtil;

public class RecipeAToZListingPageTest {

	private DriverFactory driverFactory;
	private WebDriver driver;
	private ConfigReader configReader;
	Properties prop;

	@BeforeTest
	public void initProperty() { // getProperty() method called in the below method launchBrowser()
		// System.out.println("initProperty");
		configReader = new ConfigReader();
		prop = configReader.init_prop(); // init_prop() is the function call where method defined in ConfigReader class
		String browserName = prop.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver(browserName);// init_driver() method defined in DriverFactory class which
														// takes the browser name read from the property file and return
														// the corresponding driver of it

	}

	// ****Get the recipe id and recipe name and url for all Recipes A-Z *****
	@Test
	public void testGetReceipeListDetailsAll() {

		Map<String, String> globalRecipeUrlMap = new HashMap<String, String>(); // New empty GLOBAL Map
		for (char index = 'A'; index < 'B'; index++) {
			// Replacing %s with index
			String url = String.format("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=%s&pageindex=1",
					String.valueOf(index));

			driver.get(url);
			System.out.println("Opened new alphabet url " + url);

			RecipeAToZListingPage page = new RecipeAToZListingPage(driver);// page instantiation
			int maxPageCount = page.getMaxPageCount();
			maxPageCount = 1;

			Map<String, String> pageIdUrlMap = page.getRecipeBasicInfoInPage();
			globalRecipeUrlMap.putAll(pageIdUrlMap);

			while (page.hasNextPage(maxPageCount)) {
				pageIdUrlMap = page.getRecipeBasicInfoInPage(); // method defined in the corresponding page
																// "RecipeAToZListingPage"
				globalRecipeUrlMap.putAll(pageIdUrlMap);
				page = page.navigaeToNextPage(true);
			}
		}
		System.out.println("Entire Recipe Ids & URLs from A-Z: " + globalRecipeUrlMap + '\n');

		// Sample output
		/*
		 * Entire Recipe URLs from A-Z:
		 * {36291=https://www.tarladalal.com/aam-ke-pakode-mango-bhajiya-36291r,
		 * 37261=https://www.tarladalal.com/a-truly-delectable-bunch--flower-
		 * arrangements-37261r
		 */

		/*
		 * globalRecipeUrlMap contains all the URLs from A-Z. Iterating thru that map &
		 * getting the url value and opening each page with that url
		 */
		List<RecipeGetterSetter> recipeDetailsList = new ArrayList<RecipeGetterSetter>();
		for (Map.Entry<String, String> entry : globalRecipeUrlMap.entrySet()) {
			String recipeId = entry.getKey();
			String recipeUrl = entry.getValue();
			driver.get(recipeUrl); // opens each recipe page with the url
			RecipeDetailsPage detailsPage = new RecipeDetailsPage(driver);
			RecipeGetterSetter recipe = detailsPage.getRecipeDetails(recipeId, recipeUrl);
			System.out.println("Recipe details of a single recipe :" + recipe + '\n');
			recipeDetailsList.add(recipe);
		}
		// recipeDetailsList contains entire recipe details scrapped from A-Z
		System.out.println("FINAL RECIPE SCRAPE LIST: " + recipeDetailsList);
		
		//saveRecipesToDB
		for(RecipeGetterSetter recipe:recipeDetailsList){
			
			RecipeDetailsDBUtil.createRecipe(recipe,"all_recipes");//"all_recipes" is the table name . actual value given in double quotes
		}
		
		
		IngredientEliminateAndToAddUtil filterUtil = new IngredientEliminateAndToAddUtil();
		List<String> lvfEliminateList = Arrays.asList("pork", "Meat", "Poultry", "Fish", "Sausage", "ham", "salami",
				"bacon", "milk", "cheese", "yogurt", "butter", "Ice cream", "egg", "prawn", "Oil", "olive oil",
				"coconut oil", "soybean oil", "corn oil", "safflower oil", "sunflower oil", "rapeseed oil",
				"peanut oil", "cottonseed oil", "canola oil", "mustard oil", "cereals", "tinned vegetable", "bread",
				"maida", "atta", "sooji", "poha", "cornflake", "cornflour", "pasta", "White rice", "pastry", "cakes",
				"biscuit", "soy", "soy milk", "white miso paste", "soy sauce", "soy curls", "edamame", "soy yogurt",
				"soy nut", "tofu", "pies", "Chip", "cracker", "potatoe", "sugar", "jaggery", "glucose", "fructose",
				"corn syrup", "cane sugar", "aspartame", "cane solid", "maltose", "dextrose", "sorbitol", "mannitol",
				"xylitol", "maltodextrin", "molasses", "brown rice syrup", "splenda", "nutra sweet", "stevia",
				"barley malt");
		//calling the method eliminateIngredients by passing the A-Z recipeDetailsList and the eliminate ingredients list given
		List<RecipeGetterSetter> LFVEliminationList = filterUtil.eliminateIngredients(recipeDetailsList, lvfEliminateList);
		System.out.println("FINAL LIST FOR LVF ELIMINATE: " + LFVEliminationList);
		
		
		List<String> lvfAddList = Arrays.asList(
	            "Lettuce", "kale", "chard", "arugula", "spinach", "cabbage", "pumpkin", 
	            "sweet potatoes", "purple potatoes", "yams", "turnip", "parsnip", "karela", 
	            "bittergourd", "beet", "carrot", "cucumber", "red onion", "white onion", 
	            "broccoli", "cauliflower", "carrot", "celery", "artichoke", "bell pepper", 
	            "mushroom", "tomato", "sweet and hot pepper", "banana", "mango", "papaya", 
	            "plantain", "apple", "orange", "pineapple", "pear", "tangerine", 
	            "all berry varieties", "all melon varieties", "peach", "plum", "nectarine", 
	            "Avocado", "Amaranth", "Rajgira", "Ramdana Barnyard", "Sanwa", "Samvat ke chawal", 
	            "buckwheat", "kuttu", "finger millet", "Ragi", "Nachni", "foxtail millet", "kangni", 
	            "kakum", "kodu", "kodon", "little millet", "moraiyo", "kutki", "shavan", "sama", 
	            "pearl millet", "bajra", "broom corn millet", "chena", "sorghum", "jowar", "Lentil", 
	            "Pulse", "Moong dhal", "masoor dhal", "toor dhal", "urd dhal", "lobia", "rajma", 
	            "matar", "all forms of chana", "almond", "cashew", "pistachio", "brazil nut", 
	            "walnut", "pine nut", "hazelnut", "macadamia nut", "pecan", "peanut", "hemp seed", 
	            "sun flower seed", "sesame seed", "chia seed", "flax seed"
	        );
		
		//calling the method addIngredients by passing the Eliminated list(output list from eliminateIngredients()) and the add ingredients list given
		List<RecipeGetterSetter> LFVAddList = filterUtil.addIngredients(LFVEliminationList, lvfAddList);
		System.out.println("FINAL LIST FOR LVF ADD : " + LFVAddList);
		
		
	}

}
