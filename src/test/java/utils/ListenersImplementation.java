package utils;

import java.io.IOException;

import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Media;

import base.CommonConstants;
import base.SetupInit;
import io.appium.java_client.MobileDriver;


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
		try {
            Media mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir")+"/"+takeScreenShot((MobileDriver)result.getTestContext().getAttribute("MobileDriver"))).build();
			logger.log(Status.FAIL, result.getName()+ "testcase is failed", mediaModel);
        } catch (IOException e) {
           
            e.printStackTrace();
        }
       
        ExtentReport.extent.flush();
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
