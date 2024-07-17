package recipescraping.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import recipescraping.enums.FoodCategory;
import recipescraping.enums.RecipeCategory;
import recipescraping.pojo.RecipeGetterSetter;

public class RecipeDetailsPage {

	private WebDriver driver;

	@FindBy(id = "ctl00_cntrightpanel_lblRecipeName")
	private WebElement recipeName; // Recipe name

	@FindBy(xpath = "//span[@itemprop='recipeIngredient']/a/span")
	private List<WebElement> recipeIngredients;

	@FindBy(xpath = "//time[@itemprop='prepTime']")
	private WebElement preparationTime;

	@FindBy(xpath = "//time[@itemprop='cookTime']")
	private WebElement cookingTime;

	@FindBy(xpath = "//div[@class='tags']/a/span")
	private List<WebElement> recipeTags;

	@FindBy(xpath = "//span[@id='ctl00_cntrightpanel_lblServes']")
	private WebElement noOfServings;

	@FindBy(xpath = "//div[@id='recipe_small_steps']/span/ol/li/span")
	private List<WebElement> preparationMethod;

	@FindBy(xpath = "//span[@id='ctl00_cntrightpanel_lblDesc']")
	private WebElement recipeDescription;

	// 2. Constructor of the page class:
	public RecipeDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public RecipeGetterSetter getRecipeDetails(String id, String url) {// returns an obj of RecipeGetterSetter class

		RecipeGetterSetter recipe = new RecipeGetterSetter();

		// Getting each value and setting the value to recipe object using the
		// setRecipeId() in RecipeGetterSetter class
		recipe.setRecipeId(getRecipeId(url));
		recipe.setRecipeName(getRecipeName(url));
		recipe.setRecipeCategory(getRecipeCategory(url));
		recipe.setFoodCategory(getFoodCategory(url));
		recipe.setIngredients(getIngredients(url));
		recipe.setPreparationTime(getPreparationTime(url));
		recipe.setCookingTime(getCookingTime(url));
		recipe.setTags(getTags(url));
		recipe.setNoOfServings(getNoOfServings(url));
		recipe.setCuisineCategory(getCuisineCategory(url));
		recipe.setPreparationMethod(getPreparationMethod(url));
		recipe.setRecipeDescription(getRecipeDescription(url));
		recipe.setNutrientValues(getNutrientValues(url));
		recipe.setRecipeUrl(url);

		return recipe; // returning a recipe object
	}

	// ***Recipe ID
	public String getRecipeId(String url) {
		String recipeID = url.replaceAll("[^0-9]", "");
		return recipeID;
	}

	// ***Recipe Name
	public String getRecipeName(String url) {
		String recipeNameText = recipeName.getText();
		// System.out.println("recipeNameText::" +recipeNameText);
		return recipeNameText;
	}

	// ***Ingredients
	public List<String> getIngredients(String url) {
		List<String> ingredients = new ArrayList<>();
		for (WebElement ingredient : recipeIngredients) {
			String ingredientText = ingredient.getText();
			ingredients.add(ingredientText);
		}
		return ingredients;// returns List
	}

	// ***Preparation Time
	public String getPreparationTime(String url) {
		// Get the text content of the WebElement
		String prepTime = preparationTime.getText();
		//System.out.println("Preparation Time: " + prepTime);
		return prepTime;
	}

	// ***Cooking Time
	public String getCookingTime(String url) {
		// Get the text content of the WebElement
		String cookTime = cookingTime.getText();
		//System.out.println("Cook Time: " + cookTime);
		return cookTime;
	}

	// ***Tag
	public List<String> getTags(String url) {
		List<String> tags = new ArrayList<>();// tags--> List name
		for (WebElement tag : recipeTags) {
			String tagText = tag.getText();
			tags.add(tagText);
		}
		return tags;
	}

	// ***Number of servings
	public int getNoOfServings(String url) {
		String noOfServingsText = noOfServings.getText();
		// Iterate through each character in the text
		for (char ch : noOfServingsText.toCharArray()) {
			if (Character.isDigit(ch)) { // Check if the character is a digit
				return ch; // Return the first digit found
			}
		}
		return 0;
	}

	// ***Cuisine Category
	public String getCuisineCategory(String url) {
		List<String> tags = getTags(url);// calling the getTags() method

		List<String> cuisineCategories = Arrays.asList("Italian", "Chinese", "Mexican", "Indian", "Japanese",
				"American", "Mediterranean");

		for (String tag : tags) {
			for (String category : cuisineCategories) {
				if (tag.contains(category)) {
					return category;
				}
			}
		}

		return null; // Return null if no cuisine category is found
	}

	// ***Recipe Category
	public RecipeCategory getRecipeCategory(String url) {
	    List<String> tags = getTags(url); // calling the getTags() method

	    for (String tag : tags) {
	        for (RecipeCategory category : RecipeCategory.values()) {
	            if (tag.toLowerCase().contains(category.name().toLowerCase())) {
	                return category;
	            }
	        }
	    }
	    return RecipeCategory.UNKNOWN; // Return UNKNOWN if no recipe category is found
	}
	
	
	// ***Food Category
		public FoodCategory getFoodCategory(String url) {
		    List<String> tags = getTags(url); // calling the getTags() method

		    for (String tag : tags) {
		        for (FoodCategory category : FoodCategory.values()) {
		            if (tag.toLowerCase().contains(category.name().toLowerCase())) {
		                return category;
		            }
		        }
		    }
		    return FoodCategory.UNKNOWN; // Return UNKNOWN if no recipe category is found
		}


	// ***Preparation Method
	public String getPreparationMethod(String url) {
		String preparationMethodStr =" ";
		// Extract the text of each step, add numbering, and add to the list
		int methodNumber = 1;
		for (WebElement method : preparationMethod) {
			preparationMethodStr =preparationMethodStr + methodNumber + ". " + method.getText();
			methodNumber++;
		}
		return preparationMethodStr;
	}

	// ***Recipe Description
	public String getRecipeDescription(String url) {
		// Get the text content of the WebElement
		String recipeDescriptionText = recipeDescription.getText();
		// System.out.println("Preparation Time: " + prepTime);
		return recipeDescriptionText;
	}

	// Nutrients value of recipe
	public String getNutrientValues(String url) {
	    // Find the rows of the table
	    List<WebElement> rows = driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr"));
	    int colWidth = 20; // Adjust column width to a more appropriate value
       // Map<String,String> newNutrientMap=new HashMap<>();
        
	    // Initialize a StringBuilder to accumulate the nutrient values
	    StringBuilder nutrientValues = new StringBuilder();
	   // nutrientValues.append("Nutrient Values (Abbrv) per piece\n");
	    // Loop through each row
	    for (WebElement row : rows) {
	        List<WebElement> cells = row.findElements(By.tagName("td"));
	        for (WebElement cell : cells) {
	            String cellText = cell.getText().trim();
	            nutrientValues.append(String.format("%-" + colWidth + "s", cellText));
	        }
	        nutrientValues.append("\n");
	    }
	   // System.out.println("Nutrient Values"+nutrientValues);
	    // Return the accumulated nutrient values
	    return nutrientValues.toString();
	}

}

