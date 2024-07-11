package Base;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class baseclass {

	public WebDriver driver;

	@Test
	public void setUpDriver() {

		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("--remote-allow-origins=*");
		opt.addArguments("--headless");
		driver = new ChromeDriver(opt);
		driver.get("http://www.tarladalal.com/");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();

	}

	@AfterClass
	public void teardown() throws InterruptedException {
		Thread.sleep(3000);
		driver.close();

	}
}
