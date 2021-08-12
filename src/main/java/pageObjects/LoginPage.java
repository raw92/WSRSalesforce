package pageObjects;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import resources.dataDriven;

public class LoginPage {

	public WebDriver driver;
	ArrayList<String> data;

	@FindBy(id = "username")
	WebElement username;

	@FindBy(id = "password")
	WebElement Password;

	@FindBy(id = "Login")
	WebElement Login;

	
	// Class Constructor
	public LoginPage(WebDriver driver) throws IOException {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		
		//Create an object from dataDriven and use a method to access Data from a Excel file in which i have the credentials to log in
		dataDriven d= new dataDriven();
		data = d.getData("Login");
	}

	//Pull the info inside the Array at the pos(1) getting the Username
	public void getUsername() {
		username.sendKeys(data.get(1));;
	}
	//Pull the info inside the Array at the pos(2) getting the Password
	public void getPassword() {
		Password.sendKeys(data.get(2));;
	}

	public void getLogin() {
		Login.click();
	}
	public void loginSalesforce() {
		getUsername();
		getPassword();
		getLogin();
	}

}
