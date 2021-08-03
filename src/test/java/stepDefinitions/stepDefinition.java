package stepDefinitions;

import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.LandingPage;
import pageObjects.LoginPage;
import pageObjects.WSRBodyPage;
import pageObjects.WSRHeaderPage;

import resources.base;

public class stepDefinition extends base {

	WebDriverWait wait;

	// will execute before every scenario setting up the driver and login in
	@Before
	public void setup() throws IOException, InterruptedException {

		driver = initializeDriver("https://login.salesforce.com");
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		wait = new WebDriverWait(driver, 10);

	}

	// will execute after every scenario closing the browser
	@After
	public void close() {
		driver.quit();
	}

	//
	@Given("^Landing page is displayed$")
	public void landing_page_is_displayed() throws Throwable {
		LandingPage landP = new LandingPage(driver);
		Assert.assertTrue(landP.getSquare().isDisplayed());
		System.out.println("Landing Page is Displayed Successfully");

	}

	@And("^User moves to WSR Builder$")
	public void user_moves_to_wsr_builder() throws Throwable {
		LandingPage landP = new LandingPage(driver);
		landP.getToPage("WSR");
	}

	@And("^User click in Headers tab$")
	public void user_click_in_headers_tab() throws Throwable {
		// WSRPage wp = new WSRPage(driver);
		// Search for element containing header and then click it
		// jsClick(wp.dynamicXpathNavBarWSR("Header"));
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getToTab("Header");
	}

	@When("^User click new, fills all the required fields and click save$")
	public void user_click_new_fills_all_the_required_fields_and_click_save() throws Throwable {
		String headerName = "Header Name Cucumber Test";
		String email = "Test@Cucumber.com";
		int userOpt = 1;

		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.employeeHeaderCreation(headerName, email, userOpt);
	}

	@Then("^Validate confirmation message$")
	public void validate_confirmation_message() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);

		hp.valiadateConfirmationMessage();
	}

	@When("^User click new, set all the required fields but leaves Header name empty and click save$")
	public void user_click_new_set_all_the_required_fields_but_leaves_header_name_empty_and_click_save()
			throws Throwable {
		String headerName = "";
		String email = "Test@Cucumber.com";
		int userOpt = 1;

		WSRHeaderPage hp = new WSRHeaderPage(driver);

		hp.employeeHeaderCreation(headerName, email, userOpt);
	}

	@Then("^Validate error message for name field$")
	public void validate_error_message_for_name_field() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);

		hp.validateHeaderName();
	}

	@When("^User click new, set all the required fields but leaves user name empty and click save$")
	public void user_click_new_set_all_the_required_fields_but_leaves_user_name_empty_and_click_save()
			throws Throwable {
		String headerName = "Header user empty cucumber";
		String email = "Test@Cucumber.com";
		int userOpt = 0;
		
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.employeeHeaderCreation(headerName, email, userOpt);
	}

	@Then("^Validate error message for user field$")
	public void validate_error_message_for_user_field() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.validateUserEmpty();
	}

	@When("^User click new, set all the required fields but send email field with \"([^\"]*)\" and click save$")
	public void user_click_new_set_all_the_required_fields_but_send_email_field_with_something_and_click_save(
			String arg) throws Throwable {
		String headerName = "Header wrong email cucumber";
		int userOpt = 1;
		
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.employeeHeaderCreation(headerName, arg, userOpt);
	}

	@Then("^Validate error message for email field$")
	public void validate_error_message_for_email_field() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.validateProperEmailFormat();
	}

	@When("^User click new, set all the required fields but send user field with \"([^\"]*)\" and click save$")
	public void user_click_new_set_all_the_required_fields_but_send_user_field_with_something_and_click_save(String arg)
			throws Throwable {
		String headerName = "Header wrong email cucumber";
		String email = "Test@cucumber.com";
		
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.employeeHeaderCreation(headerName, email, arg);
	}

	@Then("^Validate error message for wrong user field$")
	public void validate_error_message_for_wrong_user_field() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.validateWrongUser();
	}

	@Then("^Validate error message for user already assigned another header$")
	public void validate_error_message_for_user_already_assigned_another_header() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.validateUserAssignedHeader();
	}

	@When("^User click arrow, then in name field, then send new name \"([^\"]*)\" and save$")
	public void user_click_arrow_then_in_name_field_then_send_new_name_something_and_save(String arg) throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.editHeaderName(arg);

	}

	@Then("^Validate if header name changed for \"([^\"]*)\"$")
	public void validate_if_header_name_changed_for_something(String arg) throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.validateHeaderNameChanged(arg);
	}

	/////
	@When("^User click on header name from headers tab$")
	public void user_click_on_header_name_from_headers_tab() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getheaderNameFromHeaderTab().click();

	}

	@And("^Click in name field it send new name \"([^\"]*)\"$")
	public void click_in_name_field_it_send_new_name_something(String arg) throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getheaderNameFromHeaderTab().click();
		hp.getPencilHeaderName().click();

		hp.getName().clear();
		hp.getName().sendKeys(arg);
		hp.getEmail().click();
	}

	@And("^Move to tab \"([^\"]*)\" a window shows and click on continue editing button$")
	public void move_to_tab_something_a_window_shows_and_click_on_continue_editing_button(String arg) throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);

		hp.getToTab(arg);

		wait.until(ExpectedConditions.visibilityOf(hp.getContinueEditingButton()));
		hp.getContinueEditingButton().click();
	}

	@And("^Click on cancel button from Header details$")
	public void click_on_cancel_button_from_header_details() throws Throwable {
		WSRHeaderPage hp = new WSRHeaderPage(driver);
		hp.getCancelEditHeader().click();
	}
	
		
	    @When("^User click new, Body form display$")
	    public void user_click_new_body_form_display() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.newBodyValidateForm();
	    }

	    @Then("^Validate all the fields from the form are displayed$")
	    public void validate_all_the_fields_from_the_form_are_displayed() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.validateFieldsBodyFormPresent();
	    }

	    @And("^User click in Body WSRs tab$")
	    public void user_click_in_body_wsrs_tab() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
			bp.getToTab("Body WSRs");
	    }
	    
	    /////
	    
	    @When("^User click new, fills all the fields from the form and click save$")
	    public void user_click_new_fills_all_the_fields_from_the_form_and_click_save() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
			bp.bodyCreation();
	    }

	    @Then("^Validate if the body was created$")
	    public void validate_if_the_body_was_created() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.validateBodySuccessCreation();
	    }
	   	    
	    @Then("^Fill in all fields, leave one day empty then save, it reapeats for everyday error is displayed$")
	    public void fill_in_all_fields_leave_one_day_empty_then_save_it_reapeats_for_everyday_error_is_displayed() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.bodyValidationDaysFields();
	    }
	    
	    @Then("^Fill in all fields, putting extra hours then save, it reapeats for every day hours fields an error is displayed$")
	    public void fill_in_all_fields_putting_extra_hours_then_save_it_reapeats_for_every_day_hours_fields_an_error_is_displayed() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.bodyValidationExtraHours();
	    }
	    
	    @Then("^Fill in all fields, validates the total stories amounts$")
	    public void fill_in_all_fields_validates_the_total_stories_amounts() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.bodyValidationStoriesAmount();
	    }
	    
	    @When("^User click new, then fill in all fields sending end date \"([^\"]*)\" and start date \"([^\"]*)\"$")
	    public void user_click_new_then_fill_in_all_fields_sending_end_date_something_and_start_date_something(String endDate, String startDate) throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.bodyValidationSprintDates(endDate, startDate);
	    }

	    @Then("^validates Sprint end date could not be before start date$")
	    public void validates_sprint_end_date_could_not_be_before_start_date() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.validateSprintDatesEndBeforeStart();
	    }
	    ////
	    @When("^User click new, then fill in all fields and sending \"([^\"]*)\" for both dates$")
	    public void user_click_new_then_fill_in_all_fields_and_sending_something_for_both_dates(String date) throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.bodyValidationSprintDates(date, date);
			
	    }

	    @Then("^validates Sprint end date and start date should not be able to be the same day$")
	    public void validates_sprint_end_date_and_start_date_should_not_be_able_to_be_the_same_day() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.validateSprintDatesSameDay();
	    }
	    ///////////
	    @Then("^click on submit manager button and proceed to click save to send the report$")
	    public void click_on_submit_manager_button_and_proceed_to_click_save_to_send_the_report() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.getSubmitManagerButton().click();
	    	bp.getSaveButtonManager().click();
	    }

	    @And("^User click on Body Name for the Body which wants to send$")
	    public void user_click_on_body_name_for_the_body_which_wants_to_send() throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.getBodySelected().click();
	    }

	    @And("^User click on Header Name to validate if the email there is the same than \"([^\"]*)\" then go back to details$")
	    public void user_click_on_header_name_to_validate_if_the_email_there_is_the_same_than_something_then_go_back_to_details(String email) throws Throwable {
	    	WSRBodyPage bp = new WSRBodyPage(driver);
	    	bp.getHeaderFromBodyDetails().click();
	    	
	    	try {
				String emailFromDetails = bp.getSMEFromHeaderDetails().getText();
				Assert.assertTrue(emailFromDetails.equals(email));
				System.out.println("The email is correct");
				// Navigate to the previous page
				driver.navigate().back();
	    	}
	    	catch(Exception e) {
	    		System.out.println("The email is not the same!");
				Assert.assertTrue(false);
	    	}
	    }
	    

}