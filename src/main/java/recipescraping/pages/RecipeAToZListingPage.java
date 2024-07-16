package recipescraping.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RecipeAToZListingPage {

	private WebDriver driver;

	// constructor
	public RecipeAToZListingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class=\"rcc_recipecard\"]") // this xpath returns more than 1 element(14 elmts)
	private List<WebElement> recipeListInPage; // each recipe block in one page of A-Z

	@FindBy(xpath = "//div/a[@class=\"rescurrpg\"]")
	private WebElement currentPage;

	@FindBy(xpath = "//div/a[@class=\"respglink\"]")
	private List<WebElement> nextPage; // all the other page numbers

	@FindBy(xpath = "//div/a[@class='respglink'or @class='rescurrpg']")
	private List<WebElement> pageLinks; // all the pageindex links with class 'respglink' and 'rescurrpg'

	// @FindBy(xpath = "//div/a[contains(@class, 'respglink') or contains(@class,
	// 'rescurrpg')]")
	// private List<WebElement> pageLinks; //all the pageindex links with contains()

	// ***TO GET THE RECIPE IDs & URLs OF ALL RECIPES IN ONE PAGE OF A-Z******
	public Map<String, String> getRecipeBasicInfoInPage() {
		Map<String, String> recipeUrlMap = new HashMap<String, String>(); // New empty LOCAL Map
		for (WebElement recipe : recipeListInPage) {

			// Locate the "Recipe# id" text using XPath
			String recipeText = "Recipe#";
			String xpathString = String.format(".//div/span[contains(text(), '%s')]", recipeText);

			WebElement recipeNumberElement = recipe.findElement(By.xpath(xpathString));
			String recipeIDText = recipeNumberElement.getText();

			// recipeIDText="Recipe# 22718\n17 Jun 21"
			// Extracting the number only
			int startIndex = "Recipe# ".length();
			int endIndex = recipeIDText.indexOf('\n');

			// Extract the recipe number substring
			String recipeID = recipeIDText.substring(startIndex, endIndex);
			// Print the recipe number
			System.out.println("Recipe ID: " + recipeID);
			// Locate the "Recipe url" text using XPath
			WebElement recipeURLElement = recipe.findElement(By.xpath(".//span[@class='rcc_recipename']/a"));
			String recipeURL = recipeURLElement.getAttribute("href");
			System.out.println("Recipe URL: " + recipeURL);
			recipeUrlMap.put(recipeID, recipeURL); // Add each id, url to Map
		}
		return recipeUrlMap;
	}

	// ****Method to find the last page index number in a page******
	public int getMaxPageCount() {

		// Initialize a variable to keep track of the maximum page number
		int maxPageNumber = 0;

		// Iterate through the list of page links to find the maximum page number
		for (WebElement link : pageLinks) {
			String href = link.getAttribute("href");
			// Extract the pageindex value from the href attribute
			String pageIndexStr = href.split("pageindex=")[1].split("&")[0];
			int pageIndex = Integer.parseInt(pageIndexStr);
			if (pageIndex > maxPageNumber) {
				maxPageNumber = pageIndex;
			}
		}
		
		//System.out.println("Last Page Index: " + maxPageNumber);

		return maxPageNumber;
	}

//****Method to find if the next page is present or not
	public boolean hasNextPage(int maxPageCount) {

		String currentPageNumber = currentPage.getText();
		int nextPageNumber = Integer.parseInt(currentPageNumber) + 1;// converting string to integer and adding 1
		System.out.println("Next Page Index: " + nextPageNumber);
		if (nextPageNumber <= maxPageCount)
			return true;
		else
			return false;

	}

	public RecipeAToZListingPage navigaeToNextPage(boolean nextPageIsPresent) {
		if (nextPageIsPresent) {
			String currentPageNumber = currentPage.getText();
			int nextPageNumberInt = Integer.parseInt(currentPageNumber) + 1;// converting string to integer and adding 1
		
			// Construct the dynamic XPath expression
			String xpathExpression = String.format("//a[@class='respglink' or @class='rescurrpg'][text()='%d']",
					nextPageNumberInt, nextPageNumberInt);

			// Locate the next page link using the dynamic XPath expression
			WebElement nextPageLink = driver.findElement(By.xpath(xpathExpression));
			nextPageLink.click(); // Click on the link to navigate to the next page
			// currentPageNumber=String.valueOf(nextPageNumberInt);
			return new RecipeAToZListingPage(driver);// returns the new page
		} else
			return null; // returns the current page if nextPageIsPresent is false
	}
}
