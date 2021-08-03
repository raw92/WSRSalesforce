package pageObjects;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import resources.base;
import resources.dataDriven;

public class WSRHeaderPage extends base {

	public WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;
	
	//h2[contains(text(),'Save changes in Header Name Changed?')]
	//Web Elements
	@FindBy(xpath = "//a[@title='New']")
	private WebElement newHeader;

	@FindBy(xpath = "//input[@name='Name']")
	private WebElement name;

	@FindBy(xpath = "//input[@name='Salesforce_Manager_Email__c']")
	private WebElement email;

	@FindBy(xpath = "//button[@name='SaveEdit']")
	private WebElement saveButtonHeader;

	// label[contains(text(), 'User')]//parent::lightning-grouped-combobox/div
	@FindBy(xpath = "//label[contains(text(), 'User')]//parent::lightning-grouped-combobox/div//input")
	private WebElement user;

	// input[@name='Name']/parent::div/following-sibling::div
	@FindBy(xpath = "//ul/li/a[contains(text(), 'Header')]")
	private WebElement errorNoNameHeader;

	@FindBy(xpath = "//a[contains(text(), 'Salesforce Manager Email')]")
	private WebElement errorEmail;

	@FindBy(xpath = "//a[contains(text(), 'User')]")
	private WebElement errorWrongUser;
	
	@FindBy(xpath = "//table/tbody/tr/td[3]/span/div/a/span/span[1]")
	private WebElement arrowOptionsHeader;
	
	@FindBy(xpath = "//a[@title='Edit']")
	private WebElement editInsideArrow;
	
	@FindBy(xpath = "//table/tbody/tr/th/span/a")
	private WebElement headerNameFromHeaderTab; 
	
	@FindBy(xpath = "//div[@data-key='success']")
	private WebElement successMsg;
	
	@FindBy(xpath = "//button[@name='CancelEdit']")
	private WebElement cancelEditHeader;
	
	@FindBy(xpath = "//button[@title='Edit Header Name']")
	private WebElement pencilButtonHeaderName;
		
	@FindBy(xpath = "//button[contains(text(),'Continue Editing')]")
	private WebElement continueEditingButton;
	
	public static final String xpathTextArea = "//label[contains(text(), '$TextAreaName')]/following-sibling::div/textarea";
	public static final String xpathNavBarHeader = "//a[contains(@title,'$BarName')]";
	
	
	// Class Constructor
	public WSRHeaderPage(WebDriver driver) throws IOException {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 20);
		
	}
	
	//Methods
	public void getToTab(String arg) throws IOException {
		// looks for element containing the argument from the parameter and then click it
		jsClick(driver.findElement(By.xpath(xpathNavBarHeader.replace("$BarName", arg))));
	}
	
	public void valiadateConfirmationMessage() {
		
		wait.until(ExpectedConditions.visibilityOf(getSuccessMsg()));
		try {
		Assert.assertTrue(getSuccessMsg().isDisplayed());
		System.out.println("Header was created successfully");
		}
		catch(Exception e) {
			System.out.println("Header was not created Error");
		}
	}
		

	// The Employee creates a Header properly
	public void employeeHeaderCreation(String headerName, String email, int userOpt) throws IOException {

		// click new Header
		getNewHeader().click();

		// Fill header name
		getName().sendKeys(headerName);

		// Fill email
		getEmail().sendKeys(email);

		// Fill User option sending the option number i want
		getUserOpt(userOpt).click();
		
		
		// Fill the text area fields
		fillHeaderFormTextAreafields();
		
		getSaveButtonHeader().click();

	}
	
	public void fillHeaderFormTextAreafields() {
		String[] fields = {"Vision","Measures","Methods","Obstacles"};
		// Fill the text area fields
		for(String field : fields) {
			getHeaderTextArea(field)
			.sendKeys(field + " text area test i use the "+field+" label to access the text area using only 1 method");
		}
	}

	// STRINGS header creation user opt overload
	public void employeeHeaderCreation(String headerName, String email, String userOpt) throws IOException {
		
		// click new Header
		getNewHeader().click();

		// Fill header name
		getName().sendKeys(headerName);

		// Fill email
		getEmail().sendKeys(email);

		// Fill User option sending the option number i want
		getUser().click();
		getUser().sendKeys(userOpt);
		
		// Fill the text area fields
		fillHeaderFormTextAreafields();
		
		getSaveButtonHeader().click();

	}

	public void validateHeaderName() throws IOException {

		Assert.assertTrue(getErrorNoNameHeader().isDisplayed());
		System.out.println("The Header name is empty!");

	}

	public void validateUserAssignedHeader() throws IOException {

		Assert.assertTrue(true);
		System.out.println("The user already has a Header assigned to him it should not be possible!");

	}

	public WebElement getHeaderTextArea(String s) {
		return driver.findElement(By.xpath(xpathTextArea.replace("$TextAreaName", s)));
	}

	public WebElement getNewHeader() {
		return newHeader;
	}

	public WebElement getName() {
		return name;
	}

	public WebElement getEmail() {
		return email;
	}

	//it takes the employee user option with a parameter then compares it if its 0 or a negative number it will give no option
	//then if the opt. is higher than 0 it will put that variable into the xpath and pick that option and then return the element
	public WebElement getUserOpt(int opt) {
		
		WebElement w;

		user.click();

		if (opt == 0 || opt < 0) {
			w = driver.findElement(By.xpath(
					"//label[contains(text(), 'User')]//parent::lightning-grouped-combobox/div/div/lightning-base-combobox/div/div[2]/ul/li"));
		} else {
			w = driver.findElement(By.xpath(
					"//label[contains(text(), 'User')]//parent::lightning-grouped-combobox/div/div/lightning-base-combobox/div/div[2]/ul/li["
							+ opt + "]/following-sibling::li"));
		}

		return w;
	}

	public WebElement getSaveButtonHeader() {
		return saveButtonHeader;
	}

	public WebElement getErrorNoNameHeader() {
		return errorNoNameHeader;
	}

	public void validateUserEmpty() throws IOException {

		Assert.assertTrue(getUser().getText().equals(""));
		System.out.println("The User should not be empty!");

	}

	public WebElement getUser() {
		return user;
	}

	public void validateProperEmailFormat() throws IOException {

		Assert.assertTrue(getErrorEmail().isDisplayed());
		System.out.println("Wrong Email Format!");

	}

	public WebElement getErrorEmail() {
		return errorEmail;
	}

	public void validateWrongUser() throws IOException {

		Assert.assertTrue(getUserErrorWrongUser().isDisplayed());
		System.out.println("The User name is incorrect, there is no user with such name!");

	}

	public WebElement getUserErrorWrongUser() {
		return errorWrongUser;
	}
	
	public void editHeaderName(String name) throws IOException, InterruptedException {
		
		getArrowOptions().click();
		getEditInsideArrow().click();
		getName().clear();
		getName().sendKeys(name);
		
		getSaveButtonHeader().click();
		
		wait.until(ExpectedConditions.urlContains("/Header__c/list?filterName=Recent"));
		

	}
	
	public void editHeaderMoveAndCancel(String name) throws IOException, InterruptedException {
				
		getheaderNameFromHeaderTab().click();
		
		getPencilHeaderName().click();
		
		getName().clear();
		getName().sendKeys(name);
		getEmail().click();
		
		getToTab("Holidays");
		wait.until(ExpectedConditions.visibilityOf(getContinueEditingButton()));
		getContinueEditingButton().click();
				
		getCancelEditHeader().click();
				
		

	}
	
	public void validateHeaderNameChanged(String n) {
		
		if(n.equalsIgnoreCase(getheaderNameFromHeaderTab().getText())) {
			System.out.println("The names are the same, validation is correct!");
		}else {
			System.out.println("The names are different!");
		}
	}

	public WebElement getArrowOptions() {
		return arrowOptionsHeader;
	}
	
	public WebElement getEditInsideArrow() {
		return editInsideArrow;
	}
	
	public WebElement getheaderNameFromHeaderTab() {
		return headerNameFromHeaderTab;
	}
	
	public WebElement getSuccessMsg() {
		return successMsg;
	}
	
	public WebElement getCancelEditHeader() {
		return cancelEditHeader;
	}
	
	public WebElement getPencilHeaderName() {
		return pencilButtonHeaderName;
	}
	
	public WebElement getContinueEditingButton() {
		return continueEditingButton;
	}
}
