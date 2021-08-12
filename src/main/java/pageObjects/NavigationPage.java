package pageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavigationPage {

	public WebDriver driver;
	JavascriptExecutor js;
	
	public String xpathNavBarHeader = "//a[contains(@title,'$BarName')]";

	@FindBy(css = ".slds-icon-waffle")
	private WebElement square;

	@FindBy(xpath = "//input[@class = 'slds-input']")
	private WebElement inputSearch;

	@FindBy(xpath = "//p[@class='slds-truncate']")
	private WebElement appSearcherElement;
	
	// Class Constructor
	public NavigationPage(WebDriver driver) throws IOException {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
	}
	
	public void getToPage(String arg) {
		js.executeScript("arguments[0].click();", square);
		inputSearch.sendKeys(arg);
		js.executeScript("arguments[0].click();", appSearcherElement);
	}
	
	//return the square options element from the page
	public WebElement getSquare() {
		return square;
	}
	
	
	public void getToTab(String arg) throws IOException {
		// looks for element containing the argument from the parameter and then click it
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath(xpathNavBarHeader.replace("$BarName", arg))));
	}
	
	
	
	
}
