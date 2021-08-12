package pageObjects;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WSRHeaderPage{

	//public WebDriver driver; if i uncomment this Cucumber test wont work this.driver null Error
	JavascriptExecutor js;
	WebDriverWait wait;
	WebDriver driver;
	
	//variables for replace
	public String xpathTextArea = "//div[@class='actionBody']//label[contains(text(), '$TextAreaName')]/following-sibling::div/textarea";
	public String xpathUserName = "(//label[contains(text(), 'User')]//parent::lightning-grouped-combobox/div//input)['$User']";
	public String xpathEmailOrName = "//div[@class='actionBody']//input[@name='$EmailOrName']";
	public String xpathNewOrEdit = "//a[@title='$NewOrEdit']";
	public String xpathErrorEmailOrWrongUser = "//a[contains(text(), '$ErrorEmailOrWrongUser')]";
	public String xpathNameOrEmailFromDetails = "//input[@name='$DetailsInfo']";
	
	//Web Elements
	@FindBy(xpath = "//div[@class='actionBody']//button[@name='SaveEdit']")
	private WebElement saveButtonHeader;

	@FindBy(xpath = "//div[@class='actionBody']//label[contains(text(), 'User')]//parent::lightning-grouped-combobox/div//input")
	private WebElement userForm;

	@FindBy(xpath = "//ul/li/a[contains(text(), 'Header')]")
	private WebElement errorNoNameHeader;
	
	@FindBy(xpath = "//table/tbody/tr/td[3]/span/div/a/span/span[1]")
	private WebElement arrowOptionsHeader;
					
	@FindAll(@FindBy(xpath = "//table/tbody/tr/th/span/a"))    
	List<WebElement> allHeaders;
	
	@FindBy(xpath = "//div[@data-key='success']")
	private WebElement successMsg;

	@FindBy(xpath = "//button[@name='CancelEdit']")
	private WebElement cancelEditHeader;
	
	@FindBy(xpath = "//button[@title='Edit Header Name']")
	private WebElement pencilButtonHeaderName;
	
	@FindBy(xpath = "//button[contains(text(),'Continue Editing')]")
	private WebElement continueEditingButton;
	
	@FindBy(xpath = "//label[contains(text(), 'User')]//parent::lightning-grouped-combobox/div/div/lightning-base-combobox/div/div[2]/ul/li")
	private WebElement userOption;
		
	// Class Constructor
	public WSRHeaderPage(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 20);
	}
	//Methods
	//Validates if header was created
	public boolean valiadateConfirmationMessage() {
		boolean visible = false;
		try {
			wait.until(ExpectedConditions.visibilityOf(successMsg));
			if(successMsg.isDisplayed()) System.out.println("Header was created successfully");
			visible = true;
		}
		catch(Exception e) {
			System.out.println("Header was not created Error");
		}
		return visible;
	}
	
	// Header Creation
	public void employeeHeaderCreation(String headerName, String email, String userOpt) throws IOException {
		getNewOrEdit("New").click();
		getElementEmailOrName("Name").sendKeys(headerName);
		getElementEmailOrName("Salesforce_Manager_Email__c").sendKeys(email);
		// Fill User option sending the option number I want
		getUserOption(userOpt);
		// Fill the text area fields
		String[] fields = {"Vision","Measures","Methods","Obstacles"};
		fillHeaderFormTextAreafields(fields);
		saveButtonHeader.click();
	}
	
	//Method for filling the text areas fields
	public void fillHeaderFormTextAreafields(String[] fields) {
		// Fill the text area fields
		for(String field : fields) {
			getHeaderTextArea(field).sendKeys(field + " text area test - I used the "+field+" label to access the text area using only 1 method");
		}
	}
	//Validates header name empty
	public boolean isHeaderNameErrorDisplayed() throws IOException {
		boolean errorDisplayed = false;
		if(errorNoNameHeader.isDisplayed()) errorDisplayed = true;
		System.out.println("The Header name is empty!");
		return errorDisplayed;
	}
	//Validate if the user already have a header assigned
	public boolean isUserAssigned(String user) throws IOException, InterruptedException {
		List<WebElement> elementsList = allHeaders;
		boolean isAssigned = false;
		for(int i=0; i<elementsList.size(); i++) {
			elementsList.get(i).click();
			pencilButtonHeaderName.click();
			String userName = driver.findElement(By.xpath(xpathUserName.replace("$User", i+1+""))).getAttribute("value");
			if(user.equalsIgnoreCase(userName)) {
				isAssigned = true;
				System.out.println("That user already has a header assigned it should not have more!");
				driver.navigate().back();
				break;
			}else {
				driver.navigate().back();
				wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("div[class= 'slds-spinner_container slds-grid']"))));
			}
		}
		return isAssigned;
	}
	
	//User option method to pick the user based in a parameter name
	public void getUserOption(String opt) {
		userForm.click();
		userForm.sendKeys(opt);
		
		if(opt == "") {
			System.out.println("User empty");
		}else {
			try {
				userOption.click();		
			}
			catch(Exception e) {
				System.out.println("User not found");
			}
		}
	}
	//Validates the user empty
	public boolean isUserEmpty() throws IOException {
		return userForm.getText().equals("")? true:false;		
	}
	//Validates proper use of Email format
	public boolean IsValidEmailFormat() throws IOException {
		return getErrorEmailOrWrongUser("Salesforce Manager Email").isDisplayed()? true:false;
	}
	//Validates if the a wrong user is selected or non existent
	public boolean isWrongUser() throws IOException {
		return getErrorEmailOrWrongUser("User").isDisplayed()? true:false;
	}
	//Edit the header name using the Arrow option
	public void editHeaderNameArrowOption(String name) throws IOException, InterruptedException {
		arrowOptionsHeader.click();
		getNewOrEdit("Edit").click();
		getElementEmailOrName("Name").clear();
		getElementEmailOrName("Name").sendKeys(name);
		saveButtonHeader.click();
		wait.until(ExpectedConditions.urlContains("/Header__c/list?filterName=Recent")); //wait here because i use the validateHeaderName also for Move and Cancel and it dont work with the wait
	}
	//Edit header name clicking on the name, then editing the name clicking on pencil and after try moving to another tab without saving
	public void editHeaderMoveAndCancel(String name) throws IOException, InterruptedException {
		allHeaders.get(0).click();
		pencilButtonHeaderName.click();
		getDetailsEmailOrName("Name").clear();
		getDetailsEmailOrName("Name").sendKeys(name);
		getDetailsEmailOrName("Salesforce_Manager_Email__c").click();
		NavigationPage navP = new NavigationPage(driver);
		navP.getToTab("Holidays");
		wait.until(ExpectedConditions.visibilityOf(continueEditingButton));
		continueEditingButton.click();
		cancelEditHeader.click();
	}
	//return if header name changed or not
	public boolean headerNameChanged(String n) {
		return n.equals(allHeaders.get(0).getText())? true:false;
	}

	//Getters 
	public WebElement getheaderNameFromHeaderTab() { //return first header from the list
		return allHeaders.get(0);
	}
	public WebElement getContinueEditingButton() { 
		return continueEditingButton;
	}
	public WebElement getCancelEditHeader() {
		return cancelEditHeader;
	}
	public WebElement getPencilHeaderName() {
		return pencilButtonHeaderName;
	}
	public WebElement getHeaderTextArea(String s) {
		return driver.findElement(By.xpath(xpathTextArea.replace("$TextAreaName", s)));
	}
	public WebElement getElementEmailOrName(String field) {
		return driver.findElement(By.xpath(xpathEmailOrName.replace("$EmailOrName", field)));
	}
	public WebElement getNewOrEdit(String opt) {
		return driver.findElement(By.xpath(xpathNewOrEdit.replace("$NewOrEdit", opt)));
	}
	public WebElement getErrorEmailOrWrongUser(String opt) {
		return driver.findElement(By.xpath(xpathErrorEmailOrWrongUser.replace("$ErrorEmailOrWrongUser", opt)));
	}
	public WebElement getDetailsEmailOrName(String opt) {
		return driver.findElement(By.xpath(xpathNameOrEmailFromDetails.replace("$DetailsInfo", opt)));
	}
}