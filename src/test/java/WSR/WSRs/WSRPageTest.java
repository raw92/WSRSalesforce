package WSR.WSRs;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.NavigationPage;
import pageObjects.LoginPage;
import pageObjects.WSRBodyPage;
import pageObjects.WSRHeaderPage;
import resources.base;

public class WSRPageTest extends base {
	
	
	@Test
	public void createNewHeader() throws InterruptedException, IOException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Header");
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		// I send the header name, the email and the User field option in this case the
		// first opt.
		hp.employeeHeaderCreation("Header Name", "Test@test.com", "Rodrigo Miguez");

	}

	// This should give an error when trying to create the account it will pass if
	// the test catch the error
	@Test
	public void createNewHeaderWithoutName() throws InterruptedException, IOException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Header");
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		// I send the name empty in this case
		hp.employeeHeaderCreation("", "noName@test.com", "Rodrigo Miguez");
		Assert.assertTrue(hp.isHeaderNameErrorDisplayed());

	}

	// This should give and error when trying to create the account it will pass if
	// the test catch the error and will create the header without the user
	// the user will be automatically added after the header was created with your user
	@Test
	public void createNewHeaderWithoutUser() throws InterruptedException, IOException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
				
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Header");
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		// If i select 0 it will not select any choice so it will be empty
		hp.employeeHeaderCreation("Header with no user", "test@test.com", "");
		Assert.assertTrue(hp.isUserEmpty());
	}

	// This should give and error when trying to create the account it will pass if
	// the test catch the error
	@Test
	public void createNewHeaderWithoutProperEmail() throws InterruptedException, IOException {

		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Header");	
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		// If i remove the @ / text before the @ / text after the @ or . something after
		// it should give an error
		hp.employeeHeaderCreation("Header invalid Email", "test.com", "Rodrigo Miguez");
		Assert.assertTrue(hp.IsValidEmailFormat());
	}

	@Test
	public void createNewHeaderWithWrongInput() throws InterruptedException, IOException {

		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Header");
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		// I'll send text to the field not matching any user at all, in the end it
		// should throw
		// an error
		hp.employeeHeaderCreation("Header invalid user", "invalidUser@test.com", "Invalid User");
		Assert.assertTrue(hp.isWrongUser());
	}

	// This should give and error when trying to create the account it will pass if
	// the test catch the error
	@Test
	public void errorCreatingHeaderOneAllowedPerUser() throws InterruptedException, IOException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Header");
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		Assert.assertTrue(hp.isUserAssigned("Rodrigo Miguez"));
		hp.employeeHeaderCreation("Header already created", "test@test.com", "Rodrigo Miguez");
	}
	
	@Test
	public void employeeEditHeader() throws IOException, InterruptedException {
		String n = "Header Name Changed";
		
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Header");
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.editHeaderNameArrowOption(n);
		Assert.assertTrue(hp.headerNameChanged(n));
	}
	
	@Test
	public void employeeEditHeaderCancelAndValidate() throws IOException, InterruptedException {
		String n = "Header Name Cancelled";
		
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Header");		
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.editHeaderMoveAndCancel(n);
		Assert.assertFalse(hp.headerNameChanged(n));
	}
	
	@Test
	public void wsrBodyValidationFieldsPresent() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Body WSRs");
		
		WSRBodyPage bp = new WSRBodyPage(getDriver());
		Assert.assertTrue(bp.isFormDisplayed());
		Assert.assertTrue(bp.areFieldsBodyFormDisplayed());
	}
	
	@Test
	public void wsrBodyCreation() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Body WSRs");
		
		WSRBodyPage bp = new WSRBodyPage(getDriver());
		Assert.assertTrue(bp.isFormDisplayed());
		bp.bodyCreation();
	}
	
	@Test
	public void wsrBodyValidationDaysFieldsEmpty() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Body WSRs");
		
		WSRBodyPage bp = new WSRBodyPage(getDriver());
		Assert.assertTrue(bp.isFormDisplayed());
		bp.bodyValidationDaysFields();
	}
	
	@Test
	public void wsrBodyValidationExtraHours() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Body WSRs");
		
		WSRBodyPage bp = new WSRBodyPage(getDriver());
		Assert.assertTrue(bp.isFormDisplayed());
		bp.bodyValidationExtraHours();
	}
	
	@Test
	public void wsrBodyValidationStoriesAmount() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Body WSRs");
		
		WSRBodyPage bp = new WSRBodyPage(getDriver());
		Assert.assertTrue(bp.isFormDisplayed());
		bp.bodyValidationStoriesAmount();
	}
	
	@Test
	public void wsrBodyValidateSprintDatesEndBeforeStart() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Body WSRs");
		
		WSRBodyPage bp = new WSRBodyPage(getDriver());
		Assert.assertTrue(bp.isFormDisplayed());
		bp.bodyValidationSprintDates("2021-08-30", "2021-08-25");
		Assert.assertTrue(bp.sprintDatesEndBeforeStartErrorDisplayed());
	}
	
	@Test
	public void wsrBodyValidateSprintDatesSameDate() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Body WSRs");
		
		WSRBodyPage bp = new WSRBodyPage(getDriver());
		Assert.assertTrue(bp.isFormDisplayed());
		bp.bodyValidationSprintDates("2021-08-30", "2021-08-30");
		Assert.assertFalse(bp.isSprintDatesSameDayErrorDisplayed()); // expected false because there is no actual error displaying
	}
	
	@Test
	public void wsrSendRecordToManager() throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
		navP.getToTab("Body WSRs");
		
		WSRBodyPage bp = new WSRBodyPage(getDriver());
		bp.sendRecordManager("Rodrigo Miguez", "test@test.com");
		Assert.assertTrue(bp.successMessageDisplayed());
	}
	
	
	
}
