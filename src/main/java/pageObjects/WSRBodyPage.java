package pageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class WSRBodyPage extends base {

	public WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;

	ArrayList<String> data;

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

	// Web Elements - Information part
	@FindBy(xpath = "//div[@data-key = 'success']")
	private WebElement bodySuccessMsg;
	
	@FindBy(xpath = "//h2[contains(text(), 'New Body')]")
	private WebElement newBodyFormTitle;
	
	@FindBy(xpath = "//a[@title='New']")
	private WebElement newBody;

	@FindBy(xpath = "//label[contains(text(), 'Header')]//parent::lightning-grouped-combobox/div//input")
	private WebElement header;

	@FindBy(xpath = "//label[contains(text(), 'User')]//parent::lightning-grouped-combobox/div//input")
	private WebElement user;

	@FindBy(xpath = "//input[@name = 'Name']")
	private WebElement bodyName;

	@FindBy(xpath = "//input[@name = 'Upcoming_Leaves__c']")
	private WebElement upcomingLeaves;

	@FindBy(xpath = "//input[@name = 'Highlights__c']")
	private WebElement highlights;

	@FindBy(xpath = "//input[@name = 'Lowlights__c']")
	private WebElement lowlights;

	@FindBy(xpath = "//label[contains(text(), 'Status')]/following-sibling::div//input")
	private WebElement statusDropdown;

	@FindBy(xpath = "//label[contains(text(), 'Status')]/following-sibling::div//div/div[2]/lightning-base-combobox-item[2]")
	private WebElement statusOptionNew;

	@FindBy(xpath = "//label[contains(text(), 'Sprint Deliverables')]/following-sibling::div//input")
	private WebElement sprintDeliverablesDropdown;

	@FindBy(xpath = "//label[contains(text(), 'Sprint Deliverables')]/following-sibling::div//div/div[2]/lightning-base-combobox-item[2]")
	private WebElement sprintDeliverablesOptionOnTrack;

	// Sprint Range part
	@FindBy(xpath = "//input[@name = 'Sprint_Start_Date__c']")
	private WebElement sprintStartDate;

	@FindBy(xpath = "//input[@name = 'Sprint_End_Date__c']")
	private WebElement sprintEndDate;

	@FindBy(xpath = "//table[@class='slds-datepicker__month']")
	private WebElement tableDatePicker;

	// Stories Information part
	@FindBy(xpath = "//input[@name='Stories_Assigned__c']")
	private WebElement storiesAssigned;

	@FindBy(xpath = "//input[@name='Stories_Completed__c']")
	private WebElement storiesCompleted;

	@FindBy(xpath = "//input[@name = 'Stories_In_Progress__c']")
	private WebElement storiesInProgress;

	@FindBy(xpath = "//input[@name = 'Stories_Not_Started__c']")
	private WebElement storiesNotStarted;

	// Deliverables
	@FindBy(xpath = "//input[@name = 'Monday_Hours__c']")
	private WebElement mondayHours;

	@FindBy(xpath = "//input[@name = 'Tuesday_Hours__c']")
	private WebElement tuesdayHours;

	@FindBy(xpath = "//input[@name = 'Wednesday_Hours__c']")
	private WebElement wednesdayHours;

	@FindBy(xpath = "//input[@name = 'Thursday_Hours__c']")
	private WebElement thursdayHours;

	@FindBy(xpath = "//input[@name = 'Friday_Hours__c']")
	private WebElement fridayHours;

	// System Information
	@FindBy(xpath = "//span[contains(text(),'Owner')]/parent::div/following-sibling::div//span[@class='displayLabel']")
	private WebElement ownerName;

	// Form Buttons
	@FindBy(xpath = "//button[@name = 'SaveEdit']")
	private WebElement saveButton;

	@FindBy(xpath = "//button[@name = 'CancelEdit']")
	private WebElement cancelButton;

	@FindBy(xpath = "//a[@title='Rodrigo Miguez']")
	private WebElement bodySelected;

	@FindBy(xpath = "//span[contains(text(), 'Header Name')]")
	private WebElement headerFromBodyDetails;

	// Errors
	@FindBy(xpath = "//div[contains(text(), 'No time Travel')]")
	private WebElement errorSprintDates;

	@FindBy(xpath = "//a[contains(text(), 'test@test.com')]")
	private WebElement SMEFromHeaderDetails; // SME - Salesforce Manager Email

	@FindBy(xpath = "//button[@name='Body_WSR__c.Submit_to_Manager']")
	private WebElement submitManagerButton;

	@FindBy(xpath = "//footer//span[contains(text(),'Save')]")
	private WebElement saveButtonManager;

	// Variable for getting the days textboxes
	public static final String xpathTextArea = "//span[contains(text(), '$TargetBoxName')]/parent::span/following-sibling::div/div/div[2]/div/p";
	// Variable for selecting dates from calendar
	public static final String dayOptionCalendar = "//td[@data-value='$DateToChoose']";
	// Var to navigate tabs
	public static final String xpathNavBarBody = "//a[contains(@title,'$BarName')]";

	// Class Constructor
	public WSRBodyPage(WebDriver driver) throws IOException {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 20);
		// Create an object from dataDriven and use a method to access Data from a Excel
		// file in which i have the credentials to log in

		////// BORRAR DESPUES ES UNA PRUEBA PARA EL EXCEL

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
//		    System.out.println(data.toString());

		///// BORRAR DESPUES ES UNA PRUEBA PARA EL EXCEL

	}

	// Will click on div containing the text box then will clear the field and
	// proceed to return the element
	public WebElement getDayTextBoxDiv(String arg) {
		return driver.findElement(
				By.xpath("//span[contains(text(), '" + arg + "')]/parent::span/following-sibling::div/div"));
	}

	public WebElement getDayTextBoxClickAndClear(String arg) {
		driver.findElement(By.xpath("//span[contains(text(), '" + arg + "')]/parent::span/following-sibling::div/div"))
				.click();
		WebElement e = driver.findElement(By.xpath(xpathTextArea.replace("$TargetBoxName", arg)));
		e.clear();
		return e;
	}

	// Option picker - User - Header
	// with the element received, i click on it then pick the first opt available
	public void pickOption(WebElement e, String arg) throws InterruptedException {
		e.click();
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//span[contains(@title,'" + arg + "')]"))));
		e.sendKeys(Keys.ENTER);
	}

	// Format of date - yyyy-mm-dd -
	public void selectDateCalendar(String date) {
		driver.findElement(By.xpath(dayOptionCalendar.replace("$DateToChoose", date))).click();
	}

	// Used to navigate through tabs
	public void getToTab(String arg) throws IOException {
		// looks for element containing the argument from the parameter and then click
		// it
		jsClick(driver.findElement(By.xpath(xpathNavBarBody.replace("$BarName", arg))));
	}

	// Validates if the asked fields are present inside the form
	public void validateFieldsBodyFormPresent() throws InterruptedException {
		//newBody.click();

		WebElement[] fields = { user, header, bodyName, upcomingLeaves, highlights, lowlights, statusDropdown,
				sprintDeliverablesDropdown, sprintStartDate, sprintEndDate, storiesAssigned, storiesCompleted,
				storiesInProgress, storiesNotStarted, getDayTextBoxDiv("Monday"), mondayHours,
				getDayTextBoxDiv("Tuesday"), tuesdayHours, getDayTextBoxDiv("Wednesday"), wednesdayHours,
				getDayTextBoxDiv("Thursday"), thursdayHours, getDayTextBoxDiv("Friday"), fridayHours };

		for (WebElement field : fields) {
			try {
				Assert.assertTrue(field.isDisplayed());
			} catch (Exception e) {
				System.out.println(field + "is not Displayed");
			}
		}

	}
	
	public void newBodyValidateForm() {
		newBody.click();
				
		Assert.assertTrue(newBodyFormTitle.isDisplayed(), "New Body Form its not present or taking to long for displaying");
		
	}

	// Creates a body record with all parameters correct ("Happy path") example
	public void bodyCreation() throws InterruptedException {
		newBody.click();

		pickOption(user, dataUserName);

		pickOption(header, dataHeaderName);

		bodyName.sendKeys(dataBodyName);

		statusDropdown.click();
		statusOptionNew.click();

		sprintDeliverablesDropdown.click();
		sprintDeliverablesOptionOnTrack.click();

		sprintStartDate.click();

		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));

		selectDateCalendar(dataSprintStartDate); // Mejorar funcion calendario
		sprintEndDate.click();

		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));
		selectDateCalendar(dataSprintEndDate);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesAssigned);
		storiesAssigned.sendKeys(dataStoriesAssigned);
		storiesCompleted.sendKeys(dataStoriesCompleted);
		storiesInProgress.sendKeys(dataStoriesInProgress);
		storiesNotStarted.sendKeys(dataStoriesNotStarted);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesNotStarted);
		getDayTextBoxClickAndClear("Monday").sendKeys(getDayFieldData("Monday"));
		mondayHours.clear();
		mondayHours.sendKeys(dataNormalHours);
		getDayTextBoxClickAndClear("Tuesday").sendKeys(getDayFieldData("Tuesday"));
		tuesdayHours.clear();
		tuesdayHours.sendKeys(dataNormalHours);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", tuesdayHours);
		getDayTextBoxClickAndClear("Wednesday").sendKeys(getDayFieldData("Wednesday"));
		wednesdayHours.clear();
		wednesdayHours.sendKeys(dataNormalHours);
		getDayTextBoxClickAndClear("Thursday").sendKeys(getDayFieldData("Thursday"));
		thursdayHours.clear();
		thursdayHours.sendKeys(dataNormalHours);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", thursdayHours);
		getDayTextBoxClickAndClear("Friday").sendKeys(getDayFieldData("Friday"));
		fridayHours.clear();
		fridayHours.sendKeys(dataNormalHours);

		saveButton.click();

	}

	// Try to create a body record without the "Days fields filled"
	public void bodyValidationDaysFields() throws InterruptedException {
		//newBody.click();

		pickOption(user, dataUserName);

		pickOption(header, dataHeaderName);

		bodyName.sendKeys(dataBodyName);

		statusDropdown.click();
		statusOptionNew.click();

		sprintDeliverablesDropdown.click();
		sprintDeliverablesOptionOnTrack.click();

		sprintStartDate.click();

		// wait for table to open and pick date after that
		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));

		selectDateCalendar(dataSprintStartDate); // Mejorar funcion calendario
		sprintEndDate.click();

		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));
		selectDateCalendar(dataSprintEndDate);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesAssigned);
		storiesAssigned.sendKeys(dataStoriesAssigned);
		storiesCompleted.sendKeys(dataStoriesCompleted);
		storiesInProgress.sendKeys(dataStoriesInProgress);
		storiesNotStarted.sendKeys(dataStoriesNotStarted);

		WebElement[] daysHours = { mondayHours, tuesdayHours, wednesdayHours, thursdayHours, fridayHours };
		fillDaysHours(daysHours);
		
		
		// Fill all days
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
		fillDaysTextBoxes(days);

		// Try to create a Body WSR record with the given field empty - Error should
		// show
		for (String day : days) {
			fillDaysFieldsWithoutDay(day);
			saveButton.click();
			
			if(day != "Friday") {
			getDayTextBoxClickAndClear(day).sendKeys(getDayFieldData(day));
			
			}
		}

	}
	
	public void fillDaysTextBoxes(String[] days) throws InterruptedException {
		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesNotStarted);
		int cont = 0;
		for(String day:days) {
			getDayTextBoxClickAndClear(day).sendKeys(getDayFieldData(day));
			
			cont++;
			
			if (cont == 2)
				js.executeScript("arguments[0].scrollIntoView();", tuesdayHours);
			else if (cont == 4)
				js.executeScript("arguments[0].scrollIntoView();", thursdayHours);
			
		}
	}

	public void fillDaysHours(WebElement[] daysHours) throws InterruptedException {
		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesNotStarted);
		int cont = 0;
		for (WebElement dayHours : daysHours) {
			dayHours.clear();
			dayHours.sendKeys(dataNormalHours);

			cont++;

			if (cont == 2)
				js.executeScript("arguments[0].scrollIntoView();", tuesdayHours);
			else if (cont == 4)
				js.executeScript("arguments[0].scrollIntoView();", thursdayHours);
			}
	}
	
	// Receive by parameter the day you want to exclude, then proceed to fill the
	// Days fields without the excluded day leaving it empty
	public void fillDaysFieldsWithoutDay(String day) throws InterruptedException {

		switch (day) {
		case "Monday":
			// scroll
			js.executeScript("arguments[0].scrollIntoView();", storiesNotStarted);
			getDayTextBoxClickAndClear("Monday").clear();
			break;

		case "Tuesday":
			// scroll
			js.executeScript("arguments[0].scrollIntoView();", storiesNotStarted);
			getDayTextBoxClickAndClear("Tuesday").clear();
			break;

		case "Wednesday":
			// scroll
			js.executeScript("arguments[0].scrollIntoView();", tuesdayHours);
			getDayTextBoxClickAndClear("Wednesday").clear();
			break;

		case "Thursday":
			// scroll
			js.executeScript("arguments[0].scrollIntoView();", tuesdayHours);
			getDayTextBoxClickAndClear("Thursday").clear();
			break;

		case "Friday":
			// scroll
			js.executeScript("arguments[0].scrollIntoView();", thursdayHours);
			getDayTextBoxClickAndClear("Friday").clear();
			break;

		default:

		}
	}

	// Return data to fill the field from the selected Day it takes from the
	// parameter.
	public String getDayFieldData(String day) {

		return day + dataDayTextBox;
	}

	// Try to create a body record with extra hours inside Days hours fields
	public void bodyValidationExtraHours() throws InterruptedException {
		//newBody.click();
		
		pickOption(user, dataUserName);

		pickOption(header, dataHeaderName);

		bodyName.sendKeys(dataBodyName);

		statusDropdown.click();
		statusOptionNew.click();

		sprintDeliverablesDropdown.click();
		sprintDeliverablesOptionOnTrack.click();

		sprintStartDate.click();

		// Wait for the table to be visible then pick the date
		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));

		selectDateCalendar(dataSprintStartDate); // Mejorar funcion calendario
		sprintEndDate.click();

		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));
		selectDateCalendar(dataSprintEndDate);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesAssigned);
		storiesAssigned.sendKeys(dataStoriesAssigned);
		storiesCompleted.sendKeys(dataStoriesCompleted);
		storiesInProgress.sendKeys(dataStoriesInProgress);
		storiesNotStarted.sendKeys(dataStoriesNotStarted);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesNotStarted);

		// Select the textbox from the given day and send data to the specific input day
		getDayTextBoxClickAndClear("Monday").sendKeys(getDayFieldData("Monday"));

		getDayTextBoxClickAndClear("Tuesday").sendKeys(getDayFieldData("Tuesday"));

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", tuesdayHours);
		getDayTextBoxClickAndClear("Wednesday").sendKeys(getDayFieldData("Wednesday"));

		getDayTextBoxClickAndClear("Thursday").sendKeys(getDayFieldData("Thursday"));

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", thursdayHours);
		getDayTextBoxClickAndClear("Friday").sendKeys(getDayFieldData("Friday"));

		// Test each field if any is able to pass with Extra hours on it
		// fills all day hours fields with different data and with extra hours in the
		// given day name
		WebElement[] daysHours = { mondayHours, tuesdayHours, wednesdayHours, thursdayHours, fridayHours };

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
		//newBody.click();

		pickOption(user, dataUserName);

		pickOption(header, dataHeaderName);

		bodyName.sendKeys(dataBodyName);

		statusDropdown.click();
		statusOptionNew.click();

		sprintDeliverablesDropdown.click();
		sprintDeliverablesOptionOnTrack.click();

		sprintStartDate.click();

		// Wait for the Table to open then pick the date
		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));

		selectDateCalendar(dataSprintStartDate); // Mejorar funcion calendario
		sprintEndDate.click();

		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));
		selectDateCalendar(dataSprintEndDate);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesAssigned);
		storiesAssigned.sendKeys("6");
		storiesCompleted.sendKeys("2");
		storiesInProgress.sendKeys("2");
		storiesNotStarted.sendKeys("2");

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesNotStarted);

		// Get the textbox from the given day and then send data to the selected day
		getDayTextBoxClickAndClear("Monday").sendKeys(getDayFieldData("Monday"));
		mondayHours.clear();
		mondayHours.sendKeys(dataNormalHours);
		getDayTextBoxClickAndClear("Tuesday").sendKeys(getDayFieldData("Tuesday"));
		tuesdayHours.clear();
		tuesdayHours.sendKeys(dataNormalHours);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", tuesdayHours);
		getDayTextBoxClickAndClear("Wednesday").sendKeys(getDayFieldData("Wednesday"));
		wednesdayHours.clear();
		wednesdayHours.sendKeys(dataNormalHours);
		getDayTextBoxClickAndClear("Thursday").sendKeys(getDayFieldData("Thursday"));
		thursdayHours.clear();
		thursdayHours.sendKeys(dataNormalHours);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", thursdayHours);
		getDayTextBoxClickAndClear("Friday").sendKeys(getDayFieldData("Friday"));
		fridayHours.clear();
		fridayHours.sendKeys(dataNormalHours);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesAssigned);

		WebElement[] stories = { storiesCompleted, storiesInProgress, storiesNotStarted };

		for (WebElement story : stories) {
			story.clear();
			story.sendKeys("20");
			saveButton.click();
			story.clear();
			story.sendKeys("2");
		}
	}

	// Try to create a body record with invalid sprint dates
	public void bodyValidationSprintDates(String dateOne, String dateTwo) throws InterruptedException {
		newBody.click();

		pickOption(user, dataUserName);

		pickOption(header, dataHeaderName);

		bodyName.sendKeys(dataBodyName);

		statusDropdown.click();
		statusOptionNew.click();

		sprintDeliverablesDropdown.click();
		sprintDeliverablesOptionOnTrack.click();

		sprintStartDate.click();

		// Wait for table to be displayed then pick the date
		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));

		selectDateCalendar(dateOne); // Mejorar funcion calendario
		sprintEndDate.click();

		// Here i selected a sprint end date before the sprint start date this should
		// give an error
		wait.until(ExpectedConditions.visibilityOf(tableDatePicker));
		selectDateCalendar(dateTwo);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesAssigned);
		storiesAssigned.sendKeys(dataStoriesAssigned);
		storiesCompleted.sendKeys(dataStoriesCompleted);
		storiesInProgress.sendKeys(dataStoriesInProgress);
		storiesNotStarted.sendKeys(dataStoriesNotStarted);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", storiesNotStarted);
		getDayTextBoxClickAndClear("Monday").sendKeys(getDayFieldData("Monday"));
		mondayHours.clear();
		mondayHours.sendKeys(dataNormalHours);
		getDayTextBoxClickAndClear("Tuesday").sendKeys(getDayFieldData("Tuesday"));
		tuesdayHours.clear();
		tuesdayHours.sendKeys(dataNormalHours);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", tuesdayHours);
		getDayTextBoxClickAndClear("Wednesday").sendKeys(getDayFieldData("Wednesday"));
		wednesdayHours.clear();
		wednesdayHours.sendKeys(dataNormalHours);
		getDayTextBoxClickAndClear("Thursday").sendKeys(getDayFieldData("Thursday"));
		thursdayHours.clear();
		thursdayHours.sendKeys(dataNormalHours);

		// scroll
		js.executeScript("arguments[0].scrollIntoView();", thursdayHours);
		getDayTextBoxClickAndClear("Friday").sendKeys(getDayFieldData("Friday"));
		fridayHours.clear();
		fridayHours.sendKeys(dataNormalHours);

		saveButton.click();

	}

	public void validateSprintDatesEndBeforeStart() {
		Assert.assertTrue(errorSprintDates.isDisplayed());
		System.out.println("The error 'No time travel allowed is displaying correctly'");
	}

	public void validateSprintDatesSameDay() {

		try {
			Assert.assertTrue(errorSprintDates.isDisplayed());
		} catch (Exception e) {
			System.out.println("The error should be displayed, but it doesn't");
		}

	}
	
	public WebElement getBodySelected() {
		return bodySelected;
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

	public void sendRecordManager(String email) throws InterruptedException {
		bodySelected.click();
		headerFromBodyDetails.click();

		
		try {
			String emailFromDetails = SMEFromHeaderDetails.getText();
			Assert.assertTrue(emailFromDetails.equals(email));
			System.out.println("The email is correct");
			// Navigate to the previous page
			driver.navigate().back();
			submitManagerButton.click();
			saveButtonManager.click();
		} catch (Exception e) {
			System.out.println("The email is not the same!");
			Assert.assertTrue(false);
		}

	}
	
	public void validateBodySuccessCreation() {
		try {
		wait.until(ExpectedConditions.visibilityOf(bodySuccessMsg));
		Assert.assertTrue(bodySuccessMsg.isDisplayed());
		}
		catch(Exception e) {
			System.out.println("Body was not created");
		}
	}
}
