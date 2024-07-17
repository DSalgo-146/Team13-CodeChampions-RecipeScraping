package recipescraping.utils;

import java.util.ArrayList;
import java.util.List;

import recipescraping.enums.FoodCategory;
import recipescraping.pojo.RecipeGetterSetter;

public class IngredientEliminateAndToAddUtil {

	// eliminateIngredients() method common for all LVF Elimination, LCHF
	// Elimination/Allergy
	public List<RecipeGetterSetter> eliminateIngredients(List<RecipeGetterSetter> recipeDetailsList, List<String> eliminateList) {

		List<RecipeGetterSetter> ingredientEliminatedList = new ArrayList<RecipeGetterSetter>();
		for (RecipeGetterSetter recipe : recipeDetailsList) {
		
			boolean ingredientCheckResult = !recipeContainsIngredientsList(recipe, eliminateList);// calling the
																									// recipeContainsIngredientsList()
																									// to check if
																									// ingredients
																									// present or not
			if (ingredientCheckResult) {
				ingredientEliminatedList.add(recipe);
			}
			else {
				System.out.println("Recipe eliminated " +recipe);
			}

		}
		return ingredientEliminatedList;

	}

	// addIngredients() method common for all LVF Elimination, LCHF
	// Elimination/Allergy
	public List<RecipeGetterSetter> addIngredients(List<RecipeGetterSetter> recipeDetailsList, List<String> addList) {
		List<RecipeGetterSetter> ingredientIncludedList = new ArrayList<RecipeGetterSetter>();
		for (RecipeGetterSetter recipe : recipeDetailsList) {
			
			boolean ingredientCheckResult = recipeContainsIngredientsList(recipe, addList);// calling the
																									// recipeContainsIngredientsList()
																									// to check if
																									// ingredients
																									// present or not
			if (ingredientCheckResult) {
				ingredientIncludedList.add(recipe);
			}

		}
		return ingredientIncludedList;

	}

	public boolean recipeContainsIngredientsList(RecipeGetterSetter recipe, List<String> inputIngredientsList) {

		// recipeIngredientsList is the ingredients list
		List<String> pageIngredientsList = recipe.getIngredients();
		for (String pageIngredient : pageIngredientsList) {
			for (String inputIngredient : inputIngredientsList) {
				if (pageIngredient.toLowerCase().contains(inputIngredient.toLowerCase())) {
					System.out.println("Recipe ingredient "+pageIngredient);
					System.out.println("Contains  ingredient "+inputIngredient);
					return true;
				}
			}
		}
		return false;
	}

}
