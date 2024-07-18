package Recipes_LFV_Diet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.sql.DriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import Base.baseclass;
import Utilities.RecipeDetailsDBUtil;

public class LFV_ToAdd extends baseclass {
    public int finalno;

	public String recipetitle = null,recipecategory = null,foodcategory = null,nameofingredients = null,preptime = null,cooktime = null,prepmethod = null,nutrient = null;
	public String tag = null, noofserve = null, cuisinecategory = null, recipedesc = null;
	public Connection connection;
	
    private static final String URL = "jdbc:postgresql://localhost:5432/recipescraping_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "sdet146";
    
   
	@Test
	public void extractRecipe() throws InterruptedException, IOException, SQLException, ClassNotFoundException {
		List<String> eliminators = Arrays.asList(new String[] { "pork", "Meat", "Poultry", "Fish", "Sausage", "ham", "salami",
				"bacon", "milk", "cheese", "yogurt", "butter", "Ice cream", "egg", "prawn", "Oil", "olive oil",
				"coconut oil", "soybean oil", "corn oil", "safflower oil", "sunflower oil", "rapeseed oil",
				"peanut oil", "cottonseed oil", "canola oil", "mustard oil", "cereals", "tinned vegetable", "bread",
				"maida", "atta", "sooji", "poha", "cornflake", "cornflour", "pasta", "White rice", "pastry", "cakes",
				"biscuit", "soy", "soy milk", "white miso paste", "soy sauce", "soy curls", "edamame", "soy yogurt",
				"soy nut", "tofu", "pies", "Chip", "cracker", "potatoe", "sugar", "jaggery", "glucose", "fructose",
				"corn syrup", "cane sugar", "aspartame", "cane solid", "maltose", "dextrose", "sorbitol", "mannitol",
				"xylitol", "maltodextrin", "molasses", "brown rice syrup", "splenda", "nutra sweet", "stevia",
				"barley malt" });
		List<String> toadd = Arrays.asList(new String[] { "Butter","ghee","salmon","mackeral","sardines" });	
		
	
	//	try {
		driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
		Thread.sleep(2000);
	//	} catch(Exception e) {
	//	}
	
		// run in a loop for all recipe in a page
		List<String> pageBeginsWithList = Arrays.asList(new String[] { "0-9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" });
		
	
		//for (int i = 3; i <= 5; i=i+2) // Recipe Pagination A(3) to Z(53)
		for (int i = 1; i <= 27 /*pageBeginsWithList.size()*/; i++) // Recipe Pagination A(3) to Z(53)
		{
			
				
			    try {
			    	String xpathstralpha = "//div/div[1]/div[1]/table[1]/tbody/tr/td[" + i + "]";
			    	//driver.findElement(By.xpath(xpathstralpha)).click();
			    	//System.out.println("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="+pageBeginsWithList.get(i)+"&pageindex=1");
		    		driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="+pageBeginsWithList.get(i)+"&pageindex=1");
			    } catch(Exception e) {
			    	
			    }

				try {
				List<WebElement> pages = driver.findElements(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
				int pagecnt = pages.size();
						
				WebElement finalnoxpath = driver.findElement(By.xpath("//div[1]/div[2]/a["+pagecnt+"]"));
				String finalnostr = finalnoxpath.getText();
				finalno = Integer.parseInt(finalnostr);
				
					
				WebElement recipeTitle = driver.findElement(By.xpath("//span[@class='rcc_recipename']/a"));
				//System.out.println("recname "+recipeTitle.getText());
				} catch (Exception e) {
					
				}
			//for (int j = 2; j <= finalno; j++) 
			Map<String, String> recipeIdUrls = new HashMap<>();
			for (int j = 1; j <= finalno; j++) 
			{

				
				try {
				WebElement xpathstr1 = driver.findElement(By.xpath("//div[1]/div[2]/a[@class='rescurrpg']"));
				String xpathstr1text = xpathstr1.getText();
				int finalno1 = Integer.parseInt(xpathstr1text);
				finalno1++;
				String checkstr = String.valueOf(finalno1);
				driver.findElement(By.linkText(checkstr)).click();
				} catch (Exception e) {
					
				}
				/*try {
				WebElement recipeTitle = driver.findElement(By.xpath("//span[@class='rcc_recipename']/a"));
				//System.out.println("recname "+recipeTitle.getText());
				} catch(Exception e) {
					
				}*/
				
				List<WebElement> recipeCardElements = driver.findElements(By.xpath("//div[@class='rcc_recipecard']"));
				int recipe_numbers = recipeCardElements.size();
				List<String> recipeUrls = new ArrayList<>();
				
			    //Map<String, String> recipeIdUrls = new HashMap<>();
				
				for(int k=1;k<=recipe_numbers;k++) {

					try {
					WebElement recipeCardElements1 = driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+k+"]"));
					//System.out.println("recid "+recipeCardElements1.getAttribute("id"));
					WebElement recipeCardElement = driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+k+"]//span[@class='rcc_recipename']/a"));					
					//System.out.println("recurl "+recipeCardElement.getAttribute("href"));
					//System.out.println("recname "+recipeCardElement.getText());
					String recipeUrl = recipeCardElement.getAttribute("href");
					
					recipeIdUrls.put(recipeCardElements1.getAttribute("id").replace("rcp", ""),
							recipeCardElement.getAttribute("href"));
					}
					catch (Exception e) {
					}
				}
				//System.out.println("******recipearray "+recipeIdUrls);
			}

				
				for (Map.Entry<String, String> recipeIdUrlEntry : recipeIdUrls.entrySet()) {
					String recipeUrl = recipeIdUrlEntry.getValue(); //System.out.println("url "+recipeUrl);
					String recipeId = recipeIdUrlEntry.getKey(); System.out.println("id "+recipeId);
					
					driver.navigate().to(recipeUrl);
					driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                   

					if (check_toadd(toadd)) {

						
						
					try {
						WebElement recipeTitle = driver
								.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
						System.out.println("Title "+recipeTitle.getText());
						recipetitle = recipeTitle.getText();
					} catch(Exception e) {
					}
                    try {
						WebElement recipeCategory = driver.findElement(By.xpath(
								"//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner')]"));
						System.out.println("Recipe Category "+recipeCategory.getText());
						recipecategory = recipeCategory.getText();
					} catch(Exception e) {
					}
                     try {
						WebElement foodCategory = driver
								.findElement(By.xpath("//a/span[text()= 'No Cooking Veg Indian']"));
						System.out.println("Food Category "+foodCategory.getText());
						foodcategory = foodCategory.getText();
						} catch(Exception e) {
						}
                    try {
						WebElement nameOfIngredients = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
						System.out.println("Ingredients "+nameOfIngredients.getText());
						nameofingredients = nameOfIngredients.getText();
					} catch(Exception e) {
					}
                    try {
						WebElement preparationTime = driver.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
						System.out.println("Preparation Time "+preparationTime.getText());
						preptime = preparationTime.getText();
					} catch(Exception e) {
					}
					try {	
						WebElement cookTime = driver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
						System.out.println("Cook Time "+cookTime.getText());
						cooktime = cookTime.getText();
					} catch(Exception e) {
					}
                    try {
						WebElement prepMethod = driver
								.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
						System.out.println("Prep Method"+prepMethod.getText());
						prepmethod = prepMethod.getText();
					} catch(Exception e) {
					}
				    try {
						WebElement nutrients = driver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
						System.out.println("Nutrients "+nutrients.getText());
						nutrient = nutrients.getText();
					} catch(Exception e) {
					}
				    
				    //Tags
					try {
						WebElement tags = driver.findElement(By.xpath("//div/a[@itemprop= 'recipeCategory']"));
						System.out.println("\n"+"Tags:"+tags.getText()+"\n");
						tag = tags.getText();
					} catch (Exception e) {
					}
					//	No. of servings			
					try {
						WebElement noOfServings = driver.findElement(By.id("ctl00_cntrightpanel_lblServes"));
						System.out.print("\n"+"No.of servings:"+noOfServings.getText()+"\n");
						noofserve = noOfServings.getText();
					} catch (Exception e) {
					}
					//	Cuisine
					try {
						WebElement cuisine = driver.findElement(By.xpath("//div/a[@itemprop= 'recipeCuisine']"));
						if(cuisine!=null) {
							System.out.print("\n"+"Cuisine :"+cuisine.getText()+"\n");
						    cuisinecategory = cuisine.getText();
						}
						else {
							System.out.print("\n"+"No Cuisine information available"+"\n");
						}
					} catch (Exception e) {
					}
					//description
					try {
						WebElement description = driver.findElement(By.id("recipe_description"));
						System.out.print("\n"+"Description :"+description.getText()+"\n");
						recipedesc = description.getText();
					} catch (Exception e) {
					}
						
						System.out.println("Url "+recipeUrl);
						
						Class.forName("org.postgresql.Driver");	

						connection = DriverManager.getConnection(URL, USER, PASSWORD);	

						
						String sql = "INSERT INTO lfv_toadd_ttoz (recipeid, recipename, recipecategory, foodcategory, ingredients, preparationtime, cookingtime, tags, noOfServings, cuisineCategory, recipeDescription, preparationMethod, nutrientvalues, recipeUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement preparedstatement = connection.prepareStatement(sql);
						preparedstatement.setString(1, recipeId);
						preparedstatement.setString(2, recipetitle);
						preparedstatement.setString(3, recipecategory);
						preparedstatement.setString(4, foodcategory);
						preparedstatement.setString(5, nameofingredients);
						preparedstatement.setString(6, preptime);
						preparedstatement.setString(7, cooktime);
						preparedstatement.setString(8, tag);
						preparedstatement.setString(9, noofserve);
						preparedstatement.setString(10, cuisinecategory);
						preparedstatement.setString(11, recipedesc);
						preparedstatement.setString(12, prepmethod);
						preparedstatement.setString(13, nutrient);
						preparedstatement.setString(14, recipeUrl);
						preparedstatement.executeUpdate();
					
				
					} else {



						
						
					}

				}
				recipeIdUrls.clear();
		}

		//System.out.println("recipearray "+recipeIdUrls);
	}

	private boolean isEliminated(List<String> eliminators) {
		AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

		eliminators.parallelStream().forEach(eliminator -> {
			try {
				WebElement ingredientWebElement = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
				String ingredients = ingredientWebElement.getText();
				if (null != ingredients && null != eliminator
						&& ingredients.toLowerCase().contains(eliminator.toLowerCase())) {
					isEliminatorPresent.set(true);
				}
			} catch (Exception e) {
				//System.out.print("No Such Element " + e.getLocalizedMessage());
			}
			try {

				WebElement methodWebElement = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
				String method = methodWebElement.getText();
				if (null != method && null != eliminator && method.toLowerCase().contains(eliminator.toLowerCase())) {
					isEliminatorPresent.set(true);
				}
			} catch (Exception e) {
				//System.out.print("No Such Element " + e.getLocalizedMessage());
			}
		});
		return isEliminatorPresent.get();
	} 

	private boolean check_toadd(List<String> toadd) {
		AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

		toadd.parallelStream().forEach(eliminator -> {
			try {
				WebElement ingredientWebElement = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
				String ingredients = ingredientWebElement.getText();
				if (null != ingredients && null != eliminator
						&& ingredients.toLowerCase().contains(eliminator.toLowerCase())) {
					isEliminatorPresent.set(true);
				}
			} catch (Exception e) {
				//System.out.print("No Such Element " + e.getLocalizedMessage());
			}
			try {

				WebElement methodWebElement = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
				String method = methodWebElement.getText();
				if (null != method && null != eliminator && method.toLowerCase().contains(eliminator.toLowerCase())) {
					isEliminatorPresent.set(true);
				}
			} catch (Exception e) {
				//System.out.print("No Such Element " + e.getLocalizedMessage());
			}
		});
		return isEliminatorPresent.get();
	} 
	

}
