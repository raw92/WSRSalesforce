package WSR.WSRs;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.LandingPage;
import pageObjects.LoginPage;
import pageObjects.WSRBodyPage;
import pageObjects.WSRHeaderPage;

import resources.base;
import resources.dataDriven;

public class WSRPageTest extends base {
	
	// This will trigger everytime before a testcase will start initializing the
	// driver and opening the browser
	@BeforeMethod
	public void setup() {
		driver = initializeDriver("https://login.salesforce.com");
		
	}

	// This will close my browser after test finish
	@AfterMethod
	public void close() {
		driver.quit();
	}

	////////////////////// <<< TESTS >>> ///////////////////////////

	// Test - Logs in and move to WSRs then go to header and create header
	@Test
	public void createNewHeader() throws InterruptedException, IOException {
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getToTab("Header");
		// I send the header name, the email and the User field option in this case the
		// first opt.
		hp.employeeHeaderCreation("Header Name", "Test@test.com", 1);

	}

	// This should give an error when trying to create the account it will pass if
	// the test catch the error
	@Test
	public void createNewHeaderWithoutName() throws InterruptedException, IOException {

		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getToTab("Header");
		// I send the name empty in this case
		hp.employeeHeaderCreation("", "noName@test.com", 1);
		hp.validateHeaderName();

	}

	// This should give and error when trying to create the account it will pass if
	// the test catch the error and will create the header without the user
	// the user will be automatically added after the header was created with your
	// user
	@Test
	public void createNewHeaderWithoutUser() throws InterruptedException, IOException {
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
				
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
				
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getToTab("Header");
		// If i select 0 it will not select any choice so it will be empty
		hp.employeeHeaderCreation("Header with no user", "test@test.com", 0);
		hp.validateUserEmpty();
	}

	// This should give and error when trying to create the account it will pass if
	// the test catch the error
	@Test
	public void createNewHeaderWithoutProperEmail() throws InterruptedException, IOException {

		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
				
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getToTab("Header");
		// If i remove the @ / text before the @ / text after the @ or . something after
		// it should give an error
		hp.employeeHeaderCreation("Header invalid Email", "test.com", 1);
		hp.validateProperEmailFormat();
	}

	@Test
	public void createNewHeaderWithWrongInput() throws InterruptedException, IOException {

		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getToTab("Header");
		// I'll send text to the field not matching any user at all, in the end it
		// should throw
		// an error
		hp.employeeHeaderCreation("Header invalid user", "invalidUser@test.com", "usuario invalido");
		hp.validateWrongUser();
	}

	// This should give and error when trying to create the account it will pass if
	// the test catch the error
	@Test
	public void errorCreatingHeaderOneAllowedPerUser() throws InterruptedException, IOException {

		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getToTab("Header");
		// If i remove the @ / text before the @ / text after the @ or . something after
		// it should give an error
		hp.employeeHeaderCreation("Header already created", "test@test.com", 1);
		hp.validateUserAssignedHeader();
		
	}
	
	@Test
	public void employeeEditHeader() throws IOException, InterruptedException {
		String n = "Header Name Changed";
		
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getToTab("Header");
		hp.editHeaderName(n);
		hp.validateHeaderNameChanged(n);
	}
	
	@Test
	public void employeeEditHeaderCancelAndValidate() throws IOException, InterruptedException {
		String n = "Header Name Cancelled";
		
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
				
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getToTab("Header");
		hp.editHeaderMoveAndCancel(n);
		hp.validateHeaderNameChanged(n);
	}
	
	@Test
	public void wsrBodyValidationFieldsPresent() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRBodyPage bp = new WSRBodyPage(driver);
		bp.getToTab("Body WSRs");
		bp.newBodyValidateForm();
		bp.validateFieldsBodyFormPresent();
	}
	
	@Test
	public void wsrBodyCreation() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRBodyPage bp = new WSRBodyPage(driver);
		bp.getToTab("Body WSRs");
		bp.bodyCreation();
	}
	
	@Test
	public void wsrBodyValidationDaysFieldsEmpty() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRBodyPage bp = new WSRBodyPage(driver);
		bp.getToTab("Body WSRs");
		bp.newBodyValidateForm();
		bp.bodyValidationDaysFields();
	}
	
	@Test
	public void wsrBodyValidationExtraHours() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRBodyPage bp = new WSRBodyPage(driver);
		bp.getToTab("Body WSRs");
		bp.newBodyValidateForm();
		bp.bodyValidationExtraHours();
	}
	
	@Test
	public void wsrBodyValidationStoriesAmount() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRBodyPage bp = new WSRBodyPage(driver);
		bp.getToTab("Body WSRs");
		bp.newBodyValidateForm();
		bp.bodyValidationStoriesAmount();
	}
	
	@Test
	public void wsrBodyValidateSprintDatesEndBeforeStart() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRBodyPage bp = new WSRBodyPage(driver);
		bp.getToTab("Body WSRs");
		bp.bodyValidationSprintDates("2021-08-30", "2021-08-25");
		bp.validateSprintDatesEndBeforeStart();
	}
	
	@Test
	public void wsrBodyValidateSprintDatesSameDate() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRBodyPage bp = new WSRBodyPage(driver);
		bp.getToTab("Body WSRs");
		bp.bodyValidationSprintDates("2021-08-30", "2021-08-30");
		bp.validateSprintDatesSameDay();
	}
	
	@Test
	public void wsrSendRecordToManager() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		LandingPage lanP = new LandingPage(driver);
		lanP.getToPage("WSR");
		
		WSRBodyPage bp = new WSRBodyPage(driver);
		bp.getToTab("Body WSRs");
		bp.sendRecordManager("test@test.com");
	}
	
	
	
}
