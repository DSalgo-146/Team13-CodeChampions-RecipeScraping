package Recipes_LFV_Diet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import Base.baseclass;

public class LFV_ToAdd extends baseclass {
    public int finalno;

	@Test
	public void extractRecipe() throws InterruptedException, IOException {
		List<String> eliminators = Arrays.asList(new String[] { "Pork", "Meat", "Poultry", "Fish", "Sausage","ham","salami","bacon","milk","cheese","yogurt","butter","ice cream","egg","prawn","oil","olive oil","coconut oil","soybean oil","corn oil","safflower oil","sunflower oil","rapeseed oil","peanut oil","cottenseed oil","canola oil","mustard oil" });
		
	//	try {
		driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
		Thread.sleep(2000);
	//	} catch(Exception e) {
	//	}
	
		// run in a loop for all recipe in a page
		List<String> pageBeginsWithList = Arrays.asList(new String[] { "0-9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" });
		
	
		//for (int i = 3; i <= 5; i=i+2) // Recipe Pagination A(3) to Z(53)
		for (int i = 1; i <= 2 /*pageBeginsWithList.size()*/; i++) // Recipe Pagination A(3) to Z(53)
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
			//for (int j = 2; j <= finalno; j++) // Recipe Pagination 1 to 100000000
			Map<String, String> recipeIdUrls = new HashMap<>();
			for (int j = 1; j <= 2/*finalno*/; j++) // Recipe Pagination 1 to 100000000
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
					String recipeId = recipeIdUrlEntry.getKey(); //System.out.println("id "+recipeId);
					
					driver.navigate().to(recipeUrl);
					driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

					if (isEliminated(eliminators)) {

				
					} else {


						try {
							WebElement recipeTitle = driver
									.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
							System.out.println("Title "+recipeTitle.getText());
						} catch(Exception e) {
						}
                        try {
							WebElement recipeCategory = driver.findElement(By.xpath(
									"//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner')]"));
							System.out.println("Recipe Category "+recipeCategory.getText());
						} catch(Exception e) {
						}
                         try {
							WebElement foodCategory = driver
									.findElement(By.xpath("//a/span[text()= 'No Cooking Veg Indian']"));
							System.out.println("Food Category "+foodCategory.getText());
 						} catch(Exception e) {
 						}
                        try {
							WebElement nameOfIngredients = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
							System.out.println("Ingredients "+nameOfIngredients.getText());
						} catch(Exception e) {
						}
                        try {
							WebElement preparationTime = driver.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
							System.out.println("Preparation Time "+preparationTime.getText());
						} catch(Exception e) {
						}
						try {	
							WebElement cookTime = driver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
							System.out.println("Cook Time "+cookTime.getText());
						} catch(Exception e) {
						}
                        try {
							WebElement prepMethod = driver
									.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
							System.out.println("Prep Method"+prepMethod.getText());
						} catch(Exception e) {
						}
					    try {
							WebElement nutrients = driver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
							System.out.println("Nutrients "+nutrients.getText());
						} catch(Exception e) {
						}
							
							System.out.println("Url "+recipeUrl);
						
						
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

}
