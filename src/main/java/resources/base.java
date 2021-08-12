package resources;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class base {
	public static ThreadLocal <WebDriver> WebDriver = new ThreadLocal <WebDriver>(); 
	
	WebDriverWait wait;
	JavascriptExecutor js;
			
	// Method to initialize Driver / js Exe / Implicit and Explicit Wait / windows
	// management etc
	@BeforeMethod
	public void setup() {
		// Generic dir to be able to open it from another machine
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\main\\java\\resources\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://login.salesforce.com");
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 20);

		WebDriver.set(driver);
		
	}
		
	@AfterMethod
	public void close() {
		WebDriver.get().quit();
	}

	public WebDriver getDriver() {
		return WebDriver.get();
	}
// JS Generic Click


//	public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
//		TakesScreenshot ts = (TakesScreenshot) driver;
//		String source = ts.getScreenshotAs(OutputType.BASE64);
//		String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
//		//FileUtils.copyFile(source, new File(destinationFile));
//		return destinationFile;
//	}
	

	

}
