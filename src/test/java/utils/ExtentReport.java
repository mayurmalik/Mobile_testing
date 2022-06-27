package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {

	public static ExtentReports extent;
	public static ExtentTest logger;
	
	public static ExtentTest extentReportGenerator(String reportName, String testCaseName) {

		ExtentSparkReporter reporter = new ExtentSparkReporter("./test-results/reports/" + reportName + ".html");

		extent = new ExtentReports();

		extent.attachReporter(reporter);

		// setting the theme dark here
		reporter.config().setTheme(Theme.DARK);

		logger = extent.createTest(testCaseName);

		return logger;
	}

}
