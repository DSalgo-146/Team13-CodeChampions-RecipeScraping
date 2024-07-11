package Utilities;

import org.openqa.selenium.By;



import org.openqa.selenium.WebElement;

import Base.baseclass;

public class recipeInformation extends baseclass {

	public void collectRecipeInfo() {
		WebElement recipeTitle = driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
		WebElement recipeCategory = driver.findElement(By.xpath("//*[text()= 'breakfast lunch dinner']"));
		WebElement foodCategory = driver.findElement(By.xpath("//a/span[text()= 'No Cooking Veg Indian']"));
		WebElement ingredientList = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));

	}

	public void getIngredients() {

	}

}
