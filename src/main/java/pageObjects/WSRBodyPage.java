package pageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import resources.base;
import resources.dataDriven;

public class WSRBodyPage extends base {

	//public WebDriver driver; if i uncomment this Cucumber test wont work this.driver null Error
	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;
	ArrayList<String> data;
	
	//getting the days textboxes - < Xpaths for apply replace >
	String xpathTextArea = "//span[contains(text(), '$TargetBoxName')]/parent::span/following-sibling::div/div/div[2]/div/p";
	//selecting dates from calendar
	String dayOptionCalendar = "//td[@data-value='$DateToChoose']";
	//Select day text box div
	String dayTextBoxDiv = "//span[contains(text(), '$Day')]/parent::span/following-sibling::div/div";
	//Select option from header and user picker
	String pickerOption = "//span[contains(@title,'$Option')]";
	//Select dropdown
	String dropdownPicker = "//label[contains(text(), '$Dropdown')]/following-sibling::div//input";
	//Xpath search boxes for user and header
	String searchBoxXpath = "//label[contains(text(), '$searchBoxName')]//parent::lightning-grouped-combobox/div//input";
	//Xpath for multiple differents inputs from the body form
	String commonTextInputsXpath = "//input[@name = '$InputName']";
	//Xpath for selecting corresponding body to send
	String bodySelectedToSendToManager = "//a[@title='$BodyName']";
	
	//<Data loaded from Excel file>
	String dataUserName;
	String dataHeaderName;
	String dataBodyName;
	String dataSprintStartDate = "2021-08-02";
	String dataSprintEndDate = "2021-08-30";
	String dataStoriesAssigned;
	String dataStoriesCompleted;
	String dataStoriesInProgress;
	String dataStoriesNotStarted;
	String dataExtraHours;
	String dataNormalHours;
	String dataDayTextBox;

	// Web Elements 
	@FindBy(xpath = "//div[@data-key = 'success']")
	private WebElement successMsg;
	
	@FindBy(xpath = "//h2[contains(text(), 'New Body')]")
	private WebElement newBodyFormTitle;
	
	@FindBy(xpath = "//a[@title='New']")
	private WebElement newBody;

	@FindBy(xpath = "//table[@class='slds-datepicker__month']")
	private WebElement tableDatePicker;

	@FindBy(xpath = "//button[@name = 'SaveEdit']")
	private WebElement saveButton;

	@FindBy(xpath = "//span[contains(text(), 'Header Name')]")
	private WebElement headerFromBodyDetails;
	
	@FindBy(xpath = "//div[contains(text(), 'No time Travel')]")
	private WebElement errorSprintDates;

	@FindBy(xpath = "//a[contains(text(), 'test@test.com')]")
	private WebElement SMEFromHeaderDetails; // SME - Salesforce Manager Email

	@FindBy(xpath = "//button[@name='Body_WSR__c.Submit_to_Manager']")
	private WebElement submitManagerButton;

	@FindBy(xpath = "//footer//span[contains(text(),'Save')]")
	private WebElement saveButtonManager;
	
	// Class Constructor
	public WSRBodyPage(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 20);
		// Create an object from dataDriven and use a method to access Data from a Excel file in which i have the credentials to log in
		dataDriven d = new dataDriven();
		data = d.getData("WSRBody");
		dataUserName = data.get(1);
		dataHeaderName = data.get(2);
		dataBodyName = data.get(3);
		dataStoriesAssigned = data.get(4);
		dataStoriesCompleted = data.get(5);
		dataStoriesInProgress = data.get(6);
		dataStoriesNotStarted = data.get(7);
		dataExtraHours = data.get(8);
		dataNormalHours = data.get(9);
		dataDayTextBox = data.get(10);
	}
		
	//Validates if the asked fields are present inside the form / with a list of all web elements that needs to be there 
	public boolean areFieldsBodyFormDisplayed() throws InterruptedException {
		WebElement[] fields = { getSearchBox("User"), getSearchBox("Header"), getInputWebElement("Name"), getInputWebElement("Upcoming_Leaves__c"), getInputWebElement("Highlights__c"), getInputWebElement("Lowlights__c"), getDropdown("Status"),
				getDropdown("Sprint Deliverables"), getInputWebElement("Sprint_Start_Date__c"), getInputWebElement("Sprint_End_Date__c"), getInputWebElement("Stories_Assigned__c"), getInputWebElement("Stories_Completed__c"),
				getInputWebElement("Stories_In_Progress__c"), getInputWebElement("Stories_Not_Started__c"), getDayTextBoxDiv("Monday"), getInputWebElement("Monday_Hours__c"),
				getDayTextBoxDiv("Tuesday"), getInputWebElement("Tuesday_Hours__c"), getDayTextBoxDiv("Wednesday"), getInputWebElement("Wednesday_Hours__c"),
				getDayTextBoxDiv("Thursday"), getInputWebElement("Thursday_Hours__c"), getDayTextBoxDiv("Friday"), getInputWebElement("Friday_Hours__c") };
		boolean flag = true;
		for (WebElement field : fields) {
				if(!field.isDisplayed()) {
				flag = false;
				System.out.println(field + "is not Displayed");
				break;
			}
		}
		return flag;
	}
	//checks if body form is displaying 
	public boolean isFormDisplayed() {
		newBody.click();
		return newBodyFormTitle.isDisplayed();
	}

	//Fill all body fields with already loaded information
	public void fillBodyfields() throws InterruptedException {
		//Pick option for User
		UserAndHeaderOptionPicker(getSearchBox("User"), dataUserName);
		//Pick option for Header
		UserAndHeaderOptionPicker(getSearchBox("Header"), dataHeaderName);
		//Send data to Body Name field
		getInputWebElement("Name").sendKeys(dataBodyName);
				
		//Select the dropdown i want to click and select an option
		selectOptionDropdown("Status", 2);
		selectOptionDropdown("Sprint Deliverables", 2);
		
		//fill calendar dates
		setDatesCalendar(dataSprintStartDate, dataSprintEndDate);

		//Fill the stories with preloaded data
		String[] storiesValues = { dataStoriesAssigned, dataStoriesCompleted, dataStoriesInProgress, dataStoriesNotStarted};
		fillStories(storiesValues);

		// Fill all days using method to give the data in form of "Day name " + "the data from excel file"
		String[] daysValues = {getDayFieldData("Monday "),getDayFieldData("Tuesday "),getDayFieldData("Wednesday "),getDayFieldData("Thursday "),getDayFieldData("Friday ")};
		fillDaysTextBoxes(daysValues);
			
		String[] daysHoursValues = { dataNormalHours, dataNormalHours ,dataNormalHours, dataNormalHours, dataNormalHours };
		fillDaysHours(daysHoursValues);
	}
	// Creates a body record with all parameters correct ("Happy path") example
	public void bodyCreation() throws InterruptedException {
		fillBodyfields();
		saveButton.click();
	}

	// Try to create a body record without the "Days fields filled"
	public void bodyValidationDaysFields() throws InterruptedException {
		fillBodyfields();
		js.executeScript("arguments[0].scrollIntoView();", getInputWebElement("Stories_Not_Started__c"));
	
		String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		// Try to create a Body WSR record with the given field empty - Error should show for each day empty
		for (String day : days) {
			WebElement labDay = getDayTextBoxDiv(day);
			wait.until(ExpectedConditions.elementToBeClickable(labDay));
			js.executeScript("arguments[0].scrollIntoView();", labDay);
						
			WebElement ele = getDayTextBoxClickAndClear(day);
			saveButton.click();
			ele.sendKeys(getDayFieldData(day));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
		}
	}
	
	//get the web element based in the name provided
	public WebElement getInputWebElement(String inputName) {
		return driver.findElement(By.xpath(commonTextInputsXpath.replace("$InputName", inputName)));
	}
	
	// Try to create a body record with extra hours inside Days hours fields
	public void bodyValidationExtraHours() throws InterruptedException {
		fillBodyfields();
		//Test each field if any is able to pass with Extra hours on it fills all day hours fields with different data and with extra hours in the given day name
		WebElement[] daysHours = { getInputWebElement("Monday_Hours__c"), getInputWebElement("Tuesday_Hours__c"), 
				getInputWebElement("Wednesday_Hours__c"), getInputWebElement("Thursday_Hours__c"), getInputWebElement("Friday_Hours__c") };

		for (WebElement day : daysHours) {
			day.clear();
			day.sendKeys(dataExtraHours);
			saveButton.click();// error should show
			day.clear();
			day.sendKeys(dataNormalHours);
		}
	}

	// Try to create a body record with different amount of stories being those not
	// equal to the total of the stories assigned
	public void bodyValidationStoriesAmount() throws InterruptedException {
		fillBodyfields();
		//List of web elements for stories fields
		WebElement[] stories = { getInputWebElement("Stories_Completed__c"), getInputWebElement("Stories_In_Progress__c"), getInputWebElement("Stories_Not_Started__c") };
		String [] storieValues = {dataStoriesCompleted, dataStoriesInProgress, dataStoriesNotStarted};
		int cont=0;
		for (WebElement story : stories) {
			story.clear();
			story.sendKeys("20");
			saveButton.click();
			story.clear();
			story.sendKeys(storieValues[cont]);
			cont++;
		}
	}

	// Try to create a body record with invalid sprint dates
	public void bodyValidationSprintDates(String startDate, String endDate) throws InterruptedException {
		fillBodyfields();
		js.executeScript("arguments[0].scrollIntoView();", getInputWebElement("Sprint_Start_Date__c"));
		setDatesCalendar(startDate, endDate);
		saveButton.click();
	}

	public boolean sprintDatesEndBeforeStartErrorDisplayed() {
		return errorSprintDates.isDisplayed();
	}
	
	//This will return false because there is not error message actually displaying for this particular case
	public boolean isSprintDatesSameDayErrorDisplayed() {
		boolean valid = true;
		try {
			errorSprintDates.isDisplayed();
		}
		catch(Exception e) {
			System.out.println("An error should be displayed explaning that Start and End dates can't be the same day but no error was displayed");
			valid = false;
		}
		return valid;
	}
	//Take the email from the header assigned to the body record selected, then compares if the email is the same than the given one and if so, proceed to send to manager
	public void sendRecordManager(String bodyName, String email) throws InterruptedException {
		getBodyToSend(bodyName).click();
		headerFromBodyDetails.click();
		//Takes email from the details page from the body to compare after	
		String emailFromDetails = SMEFromHeaderDetails.getText();
		if(emailFromDetails.equals(email)) {
			System.out.println("The email is correct");
			driver.navigate().back(); // Navigate to the previous page
			submitManagerButton.click();
			saveButtonManager.click();
		}else {
			System.out.println("The email is not the same!");
		}
	}
	//checks if success message is displayed
	public boolean successMessageDisplayed() {
		boolean isDisplayed = false;
		try {
		wait.until(ExpectedConditions.visibilityOf(successMsg));
			if(successMsg.isDisplayed()) {
				isDisplayed = true;
				System.out.println("Success msg displayed");
			}
		}
		catch(Exception e) {
			System.out.println("Success msg was not displayed it was not sended nor created");
		}
		return isDisplayed;
	}
	
	//>>>>>Getters Setters - options - pickers<<<<<
	
	//Clicks on first date input field then wait to be displayed and select the date received, then do the same for the next date
	public void setDatesCalendar(String startDate, String endDate) {
		getInputWebElement("Sprint_Start_Date__c").click();
		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));
		selectDateCalendar(startDate);
		getInputWebElement("Sprint_End_Date__c").click();
		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));
		selectDateCalendar(endDate);
	}
	//fills days text boxes with given data
	public void fillDaysTextBoxes(String[] daysValues) throws InterruptedException {
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
		int cont = 0;
		for(String day:days) {
			js.executeScript("arguments[0].scrollIntoView();", getDayTextBoxDiv(day));
			getDayTextBoxClickAndClear(day).sendKeys(daysValues[cont]);
			cont++;
		}
	}
	//Fills stories fields with given values	
	public void fillStories(String[] values) throws InterruptedException {
		ArrayList<WebElement> storiesElements = new ArrayList<WebElement>(Arrays.asList(getInputWebElement("Stories_Assigned__c"), getInputWebElement("Stories_Completed__c"), 
				getInputWebElement("Stories_In_Progress__c"), getInputWebElement("Stories_Not_Started__c")));

		fillElementsInputsGiven(storiesElements, values);
	}
	
	//Fills DaysHours fields with given values	
	public void fillDaysHours(String[] daysHoursValues) throws InterruptedException {
		ArrayList<WebElement> daysHoursElements = new ArrayList<WebElement>(Arrays.asList(getInputWebElement("Monday_Hours__c"), getInputWebElement("Tuesday_Hours__c"), 
				getInputWebElement("Wednesday_Hours__c"), getInputWebElement("Thursday_Hours__c"), getInputWebElement("Friday_Hours__c")));

		fillElementsInputsGiven(daysHoursElements, daysHoursValues);
	}
	//Fills Stories and Day hours inputs grab an Array List using parameters with all the elements and a list of strings with the values to use inside of them
		public void fillElementsInputsGiven(ArrayList<WebElement> elements, String[] values) {
			int cont = 0;
			for (WebElement element : elements) {
				js.executeScript("arguments[0].scrollIntoView();", element);
				element.click();
				element.clear();
				element.sendKeys(values[cont]);
				cont++;
			}
		}
	// Return data to fill the field from the selected Day it takes from the parameter.
	public String getDayFieldData(String day) {
		return day + dataDayTextBox;
	}
	//Option picker for User and Header with the element received, I click on it then pick the first opt available base on opt sended.
	public void UserAndHeaderOptionPicker(WebElement e, String opt) throws InterruptedException {
		e.click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(pickerOption.replace("$Option", opt)))));
		e.sendKeys(Keys.ENTER);
	}
	// Format of date - yyyy-mm-dd -
	public void selectDateCalendar(String date) {
		driver.findElement(By.xpath(dayOptionCalendar.replace("$DateToChoose", date))).click();
	}
	//get webelement of body selected to send it to corresponding manager
	public WebElement getBodyToSend(String bodyName) {
		return driver.findElement(By.xpath(bodySelectedToSendToManager.replace("$BodyName", bodyName)));
	} 
	//get webelement for user or header search fields inside form	
	public WebElement getSearchBox(String fieldName) {
		return driver.findElement(By.xpath(searchBoxXpath.replace("$searchBoxName", fieldName)));
	}
	//get webElement for dropdowns like status and deliverables
	public WebElement getDropdown(String dropdown) {
		return driver.findElement(By.xpath(dropdownPicker.replace("$Dropdown", dropdown)));
	}
	//Select the option from the dropdown provided
	public void selectOptionDropdown(String dropdown, int opt) {
		getDropdown(dropdown).click();
		driver.findElement(By.xpath("//label[contains(text(), '"+dropdown+"')]/following-sibling::div//div/div[2]/lightning-base-combobox-item["+opt+"]")).click();
	}
	//Get webElement day text box div
	public WebElement getDayTextBoxDiv(String day) {
		return driver.findElement(By.xpath(dayTextBoxDiv.replace("$Day", day)));
	}
	//Click on Div containing the text box then will clear the field and return the element
	public WebElement getDayTextBoxClickAndClear(String day) {
		getDayTextBoxDiv(day).click();
		WebElement e = driver.findElement(By.xpath(xpathTextArea.replace("$TargetBoxName", day)));
		e.clear();
		return e;
	}
	
	public WebElement getHeaderFromBodyDetails() {
		return headerFromBodyDetails;
	}
	
	public WebElement getSubmitManagerButton() {
		return submitManagerButton;
	}
	
	public WebElement getSaveButtonManager() {
		return saveButtonManager;
	}
	
	public WebElement getSMEFromHeaderDetails() {
		return SMEFromHeaderDetails;
	}
}
