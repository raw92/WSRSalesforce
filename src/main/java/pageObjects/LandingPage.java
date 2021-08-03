package pageObjects;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {

	public WebDriver driver;
	JavascriptExecutor js;

	@FindBy(css = ".slds-icon-waffle")
	private WebElement square;

	@FindBy(xpath = "//input[@class = 'slds-input']")
	private WebElement inputSearch;

	@FindBy(xpath = "//p[@class='slds-truncate']")
	private WebElement appSearcherElement;

	
	
	// Class Constructor
	public LandingPage(WebDriver driver) throws IOException {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		//ASsert validar titulo	
	}

	
//After getting to Home/Landing page i click at the Square at the up/left corner then type "WSR" and click on it to access the WSR page
//	public void getWSR() {
//		js.executeScript("arguments[0].click();", square);
//		inputSearch.sendKeys("WSR");
//		js.executeScript("arguments[0].click();", WSR);
//	}
	
	public void getToPage(String arg) {
		js.executeScript("arguments[0].click();", square);
		inputSearch.sendKeys(arg);
		js.executeScript("arguments[0].click();", appSearcherElement);
	}
	
	//return the square options element from the page
	public WebElement getSquare() {
		return square;
		
	}
	
	
	
	
}
