package WSR.WSRs;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import resources.ExtentReporterNG;
import resources.base;


public class Listeners extends base implements ITestListener{
		
	//ExtentTest test;
	public static ExtentReports extent = ExtentReporterNG.getReportObject();
	static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test Started");
		//test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(extent.createTest(result.getMethod().getMethodName()));
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		//ITestListener.super.onTestFailure(result);
		//ScreenShot
		System.out.println("Test Failed");
		extentTest.get().fail(result.getThrowable());
	
		try {
			TakesScreenshot ts = (TakesScreenshot) getDriver();
			String source = ts.getScreenshotAs(OutputType.BASE64);
			extentTest.get().addScreenCaptureFromBase64String(source);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		//ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		
		extent.flush();
		
	}

	
	
	
	
	
	
}
