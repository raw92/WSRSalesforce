package WSR.WSRs;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import resources.base;
import resources.dataDriven;

public class TestDataTest extends base{
	public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		driver = initializeDriver("https://login.salesforce.com");
		
	}

	// This will close my browser after test finish
	@AfterMethod
	public void close() {
		driver.quit();
	}
	
	
	
	@Test
	public void data() throws IOException {
		
		LoginPage lp = new LoginPage(driver);
		lp.loginSalesforce();
		
		Assert.assertEquals(driver.getTitle(), "titulo equivocado");
		
		
//		dataDriven d= new dataDriven();
//		ArrayList<String>data = d.getData("WSRBody");
//		System.out.println(data.toString());
//		System.out.println(data.get(3));
		//Assert.assertTrue(false);
	}
}
