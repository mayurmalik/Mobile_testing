package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentTest;
import base.SetupInit;

public class ListenersImplementation extends SetupInit implements ITestListener {

	public static ExtentTest logger;

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult result) {

//		logger.addScreenCaptureFromPath(takeScreenShot(), result.getTestName() +" test is failed" );
//			
//		ExtentReport.extent.flush();
//				

	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestStart(ITestResult arg0) {

		logger = ExtentReport.extentReportGenerator(arg0.getName().toString(), arg0.getName().toString());

	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub

		ExtentReport.extent.flush();
	}

}
