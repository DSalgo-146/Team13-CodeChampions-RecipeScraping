package recipescraping.utils;

import java.util.ArrayList;
import java.util.List;

import recipescraping.pojo.RecipeGetterSetter;

public class IngredientEliminateAndToAddUtil {
	
	 //eliminateIngredients() method common for all LVF Elimination, LCHF Elimination/Allergy
       public List<RecipeGetterSetter> eliminateIngredients(List<RecipeGetterSetter> recipeDetailsList, List<String> eliminateList){
    	   
    	   List<RecipeGetterSetter> ingredientEliminatedList=new ArrayList<RecipeGetterSetter>();
    	   for(RecipeGetterSetter recipe : recipeDetailsList) {
    		  boolean result= recipeContainsIngredientsList(recipe,eliminateList);//calling the recipeContainsIngredientsList() to check if ingredients present or not
    		  if(result==false) {
    			  ingredientEliminatedList.add(recipe);   			  
    		  }
    			   		   
    	   }
    	   return ingredientEliminatedList;
    	   
       }
       public List<RecipeGetterSetter> addIngredients(List<RecipeGetterSetter> LFVEliminationList, List<String> addList){
    	   List<RecipeGetterSetter> ingredientIncludedList=new ArrayList<RecipeGetterSetter>();
    	   for(RecipeGetterSetter recipe : LFVEliminationList) {
    		  boolean result= recipeContainsIngredientsList(recipe,addList);//calling the recipeContainsIngredientsList() to check if ingredients present or not
    		  if(result==true) {
    			  ingredientIncludedList.add(recipe);   			  
    		  }
    			   		   
    	   }
    	   return ingredientIncludedList;
    	   

       }
       
      public boolean recipeContainsIngredientsList(RecipeGetterSetter recipe, List<String> inputIngredientsList) {
    	  
    	  //recipeIngredientsList is the ingredients list
    	  List<String>pageIngredientsList = recipe.getIngredients();
    	  for(String pageIngredient:pageIngredientsList) {
    		  for(String inputIngredient:inputIngredientsList) {
    			  if (pageIngredient.toLowerCase().contains(inputIngredient.toLowerCase())) {
    			  return true;
    		    }
    	    }
    	  }
    	  return false;
      }
      
}

