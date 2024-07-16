package recipescraping.pojo;

import java.util.List;

import recipescraping.enums.FoodCategory;
import recipescraping.enums.RecipeCategory;

public class RecipeGetterSetter {
	
	private String recipeUrl;
	private String recipeId;
	private String recipeName;
	private List<String> ingredients;
	private List<String> tags;
	private List<String> preparationMethod;
	private String preparationTime;
	private String cookingTime;
	private int noOfServings;
	private String cuisineCategory;
	private  String recipeCategory; 
	//private  RecipeCategory recipeCategory; //RecipeCategory is enum class
	private FoodCategory foodCategory;//FoodCategory is enum class
	private String recipeDescription;
	
	
	public String getRecipeUrl() {
		return recipeUrl;
	}
	public void setRecipeUrl(String recipeUrl) {
		this.recipeUrl = recipeUrl;
	}
	
	public String getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}
	
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getRecipeCategory() {
		return recipeCategory;
	}
	public void setRecipeCategory(String recipeCategory) {
		this.recipeCategory = recipeCategory;
	}

	public FoodCategory getFoodCategory() {
		return foodCategory;
	}
	public void setFoodCategory(FoodCategory foodCategory) {
		this.foodCategory = foodCategory;
	}
	
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	
	public String getPreparationTime() {
		return preparationTime;
	}
	public void setPreparationTime(String preparationTime) {
		this.preparationTime = preparationTime;
	}
	
	public String getCookingTime() {
		return preparationTime;
	}
	public void setCookingTime(String cookTime) {
		this.cookingTime = cookTime;
	}
	
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public List<String> getPreparationMethod() {
		return preparationMethod;
	}
	public void setPreparationMethod(List<String> preparationMethod) {
		this.preparationMethod = preparationMethod;
	}
	
	public int getNoOfServings() {
		return noOfServings;
	}
	public void setNoOfServings(int noOfServings) {
		this.noOfServings = noOfServings;
	}

	public String getCuisineCategory() {
		return cuisineCategory;
	}
	public void setCuisineCategory(String cuisineCategory) {
		this.cuisineCategory = cuisineCategory;
	}

	public String getRecipeDescription() {
		return recipeDescription;
	}
	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}
	
	@Override
	public String toString() {
		return "RecipeGetterSetter [recipeId=" + recipeId + ", recipeName=" + recipeName + ", recipeUrl=" + recipeUrl
				+ ", ingredients=" + ingredients + ", recipeCategory=" + recipeCategory + ", foodCategory=" + foodCategory
				+ ",preparationTime=" +preparationTime +",cookingTime=" +cookingTime +", tags=" + tags +  ",noOfServings=" + noOfServings + ",cuisineCategory=" + cuisineCategory + "]";
	}

	
	
	

}
