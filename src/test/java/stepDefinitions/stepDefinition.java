package stepDefinitions;


import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.LoginPage;
import pageObjects.NavigationPage;
import pageObjects.WSRBodyPage;
import pageObjects.WSRHeaderPage;

import resources.base;

public class stepDefinition extends base {

	WebDriverWait wait;

	// will execute before every scenario setting up the getDriver() and login in
	@Before
	public void cucumberSetup() throws IOException {
		setup();
		LoginPage lp = new LoginPage(getDriver());
		lp.loginSalesforce();
		wait = new WebDriverWait(getDriver(), 10);
	}

	// will execute after every scenario closing the browser
	@After
	public void close() {
		getDriver().quit();
	}
	//<<<<<<< Commons methods >>>>>>>
	@Given("^Landing page is displayed$")
	public void landing_page_is_displayed() throws Throwable {
		NavigationPage navP = new NavigationPage(getDriver());
		Assert.assertTrue(navP.getSquare().isDisplayed());
		System.out.println("Landing Page is Displayed Successfully");
	}

	@And("^User moves to WSR Builder$")
	public void user_moves_to_wsr_builder() throws Throwable {
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToPage("WSR");
	}

	//<<<<<<< Header methods Steps >>>>>>>>
	@And("^User click in Headers tab$")
	public void user_click_in_headers_tab() throws Throwable {
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToTab("Header");
	}

	@When("^User click new, fills all the required fields and click save$")
	public void user_click_new_fills_all_the_required_fields_and_click_save() throws Throwable {
		String headerName = "Header Name Cucumber Test";
		String email = "Test@Cucumber.com";
		String userOpt = "Rodrigo Miguez";

		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.employeeHeaderCreation(headerName, email, userOpt);
	}

	@Then("^Validate confirmation message$")
	public void validate_confirmation_message() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		Assert.assertTrue(hp.valiadateConfirmationMessage());
	}

	@When("^User click new, set all the required fields but leaves Header name empty and click save$")
	public void user_click_new_set_all_the_required_fields_but_leaves_header_name_empty_and_click_save()
			throws Throwable {
		String headerName = "";
		String email = "Test@Cucumber.com";
		String userOpt = "Rodrigo Miguez";

		WSRHeaderPage hp = new WSRHeaderPage(getDriver());

		hp.employeeHeaderCreation(headerName, email, userOpt);
	}

	@Then("^Validate error message for name field$")
	public void validate_error_message_for_name_field() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		Assert.assertTrue(hp.isHeaderNameErrorDisplayed());
	}

	@When("^User click new, set all the required fields but leaves user name empty and click save$")
	public void user_click_new_set_all_the_required_fields_but_leaves_user_name_empty_and_click_save()
			throws Throwable {
		String headerName = "Header user empty cucumber";
		String email = "Test@Cucumber.com";
		String userOpt = "";
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.employeeHeaderCreation(headerName, email, userOpt);
	}

	@Then("^Validate error message for user field$")
	public void validate_error_message_for_user_field() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		Assert.assertTrue(hp.isUserEmpty());
	}

	@When("^User click new, set all the required fields but send email field with \"([^\"]*)\" and click save$")
	public void user_click_new_set_all_the_required_fields_but_send_email_field_with_something_and_click_save(
			String email) throws Throwable {
		String headerName = "Header wrong email cucumber";
		String userOpt = "Rodrigo Miguez";
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.employeeHeaderCreation(headerName, email, userOpt);
	}

	@Then("^Validate error message for email field$")
	public void validate_error_message_for_email_field() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		Assert.assertTrue(hp.IsValidEmailFormat());
	}

	@When("^User click new, set all the required fields but send user field with \"([^\"]*)\" and click save$")
	public void user_click_new_set_all_the_required_fields_but_send_user_field_with_something_and_click_save(String arg)
			throws Throwable {
		String headerName = "Header wrong email cucumber";
		String email = "Test@cucumber.com";
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.employeeHeaderCreation(headerName, email, arg);
	}

	@Then("^Validate error message for wrong user field$")
	public void validate_error_message_for_wrong_user_field() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		Assert.assertTrue(hp.isWrongUser());
	}

	@When("^User start going through all headers check if \"([^\"]*)\" already have one assigned$")
    public void user_start_going_through_all_headers_check_if_something_already_have_one_assigned(String user) throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		Assert.assertTrue(hp.isUserAssigned(user));
    }

    @Then("^proceed to create the header$")
    public void proceed_to_create_the_header() throws Throwable {
    	String headerName = "Header Name Cucumber Test";
		String email = "Test@Cucumber.com";
		String userOpt = "Rodrigo Miguez";

		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.employeeHeaderCreation(headerName, email, userOpt);
    }

	@When("^User click arrow, then in name field, then send new name \"([^\"]*)\" and save$")
	public void user_click_arrow_then_in_name_field_then_send_new_name_something_and_save(String arg) throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.editHeaderNameArrowOption(arg);
	}

	@Then("^Validate if header name changed for \"([^\"]*)\"$")
	public void validate_if_header_name_changed_for_something(String arg) throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		Assert.assertTrue(hp.headerNameChanged(arg));
	}
	
	@Then("^Validate header name didnt changed for \"([^\"]*)\"$")
    public void validate_header_name_didnt_changed_for_something(String hName) throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		Assert.assertFalse(hp.headerNameChanged(hName));
    }

	@When("^User click on header name from headers tab$")
	public void user_click_on_header_name_from_headers_tab() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.getheaderNameFromHeaderTab().click();
	}

	@And("^Click in name field it send new name \"([^\"]*)\"$")
	public void click_in_name_field_it_send_new_name_something(String arg) throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.getheaderNameFromHeaderTab().click();
		hp.getPencilHeaderName().click();
		
		hp.getDetailsEmailOrName("Name").clear();
		hp.getDetailsEmailOrName("Name").sendKeys(arg);
		hp.getDetailsEmailOrName("Salesforce_Manager_Email__c").click();
	}

	@And("^Move to tab \"([^\"]*)\" a window shows and click on continue editing button$")
	public void move_to_tab_something_a_window_shows_and_click_on_continue_editing_button(String arg) throws Throwable {
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToTab(arg);
		
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		wait.until(ExpectedConditions.visibilityOf(hp.getContinueEditingButton()));
		hp.getContinueEditingButton().click();
	}

	@And("^Click on cancel button from Header details$")
	public void click_on_cancel_button_from_header_details() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(getDriver());
		hp.getCancelEditHeader().click();
	}
		  		       
	// <<<<<<< Body WSR Steps >>>>>>>>
	@When("^User click new, Body form display$")
	public void user_click_new_body_form_display() throws Throwable {
		WSRBodyPage bp = new WSRBodyPage(getDriver());
		Assert.assertTrue(bp.isFormDisplayed());
	}

	@Then("^Validate all the fields from the form are displayed$")
	public void validate_all_the_fields_from_the_form_are_displayed() throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	Assert.assertTrue(bp.areFieldsBodyFormDisplayed());
	}

	@And("^User click in Body WSRs tab$")
	public void user_click_in_body_wsrs_tab() throws Throwable {
		NavigationPage navP = new NavigationPage(getDriver());
		navP.getToTab("Body WSRs");
	}
	  
	@When("^User click new, fills all the fields from the form and click save$")
	public void user_click_new_fills_all_the_fields_from_the_form_and_click_save() throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	Assert.assertTrue(bp.isFormDisplayed());
	   	bp.bodyCreation();
	}

	@Then("^Validate if the body was created$")
	public void validate_if_the_body_was_created() throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	Assert.assertTrue(bp.successMessageDisplayed());
	}
	
	@Then("^Fill in all fields, leave one day empty then save, it repeats for everyday error is displayed$")
	public void fill_in_all_fields_leave_one_day_empty_then_save_it_repeats_for_everyday_error_is_displayed() throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	bp.bodyValidationDaysFields();
	}
	    
	@Then("^Fill in all fields, putting extra hours then save, it repeats for every day hours fields an error is displayed$")
	public void fill_in_all_fields_putting_extra_hours_then_save_it_repeats_for_every_day_hours_fields_an_error_is_displayed() throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	bp.bodyValidationExtraHours();
	}
	    
	@Then("^Fill in all fields, validates the total stories amounts$")
	public void fill_in_all_fields_validates_the_total_stories_amounts() throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	bp.bodyValidationStoriesAmount();
	}
	    
	@When("^User click new, then fill in all fields sending end date \"([^\"]*)\" and start date \"([^\"]*)\"$")
	public void user_click_new_then_fill_in_all_fields_sending_end_date_something_and_start_date_something(String startDate, String endDate) throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	Assert.assertTrue(bp.isFormDisplayed());
	   	bp.bodyValidationSprintDates(startDate, endDate);
	}

	@Then("^validates Sprint end date could not be before start date$")
	public void validates_sprint_end_date_could_not_be_before_start_date() throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	Assert.assertTrue(bp.sprintDatesEndBeforeStartErrorDisplayed());
	}
	    
	@When("^User click new, then fill in all fields and sending \"([^\"]*)\" for both dates$")
	public void user_click_new_then_fill_in_all_fields_and_sending_something_for_both_dates(String date) throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	Assert.assertTrue(bp.isFormDisplayed());
	   	bp.bodyValidationSprintDates(date, date);
	}

	@Then("^validates Sprint end date and start date should not be able to be the same day$")
	public void validates_sprint_end_date_and_start_date_should_not_be_able_to_be_the_same_day() throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	Assert.assertFalse(bp.isSprintDatesSameDayErrorDisplayed());
	}
	    
	@Then("^click on submit manager button and proceed to click save to send the report$")
	public void click_on_submit_manager_button_and_proceed_to_click_save_to_send_the_report() throws Throwable {
	  	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	bp.getSubmitManagerButton().click();
	   	bp.getSaveButtonManager().click();
	}

	@And("^User click on Body Name \"([^\"]*)\"$")
	public void user_click_on_body_name_something(String bodyName) throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	bp.getBodyToSend(bodyName).click();
	}
	    
	@And("^User click on Header Name to validate if the email there is the same than \"([^\"]*)\" then go back to details$")
	public void user_click_on_header_name_to_validate_if_the_email_there_is_the_same_than_something_then_go_back_to_details(String email) throws Throwable {
	   	WSRBodyPage bp = new WSRBodyPage(getDriver());
	   	bp.getHeaderFromBodyDetails().click();
	    	
	   	String emailFromDetails = bp.getSMEFromHeaderDetails().getText();
		if(emailFromDetails.equals(email)) {
			System.out.println("The email is correct");
			// Navigate to the previous page
			getDriver().navigate().back();
	   	}
		else {
			System.out.println("The email is not the same!");
		}
	}
}