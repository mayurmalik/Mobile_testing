package mobile.android.testCases;

import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import base.SetupInit;
import createObject.CreateObject;
import utils.ListenersImplementation;

public class AppTestcase extends SetupInit {

	Map<Object, Object> data;
	protected CreateObject co;
	public static ExtentTest logger;

	@SuppressWarnings("unchecked")
	@BeforeMethod
	public void beforeMethod(ITestContext context) {
		co = new CreateObject(getMobileDriver());
		context.setAttribute("MobileDriver", this.mobileDriver);

	}

	@Test(enabled = true, priority = 0)
	public void test() throws Exception {

		logger = ListenersImplementation.logger;
		co.aPage.accept();
		co.aPage.clickOnSideMenu();
		co.aPage.clickOnSettings();
		logger.info("setting icon is clicked");

	}

	// day-3 assignment locators are created in respective page object class and
	// test case is also created below.
	@Test(enabled = true, priority = 1)
	public void sideMenuOption() throws Exception {

		logger = ListenersImplementation.logger;
		co.aPage.accept();
		co.aPage.clickOnSideMenu();
		co.aPage.clickOnSideMenuoptions(co.aPage.community);
		logger.info("community icon is clicked");
		logger.pass("Sidemenu option test case is passed");
	}

	// day-4 assignment scenarios

	@Test(enabled = true, priority = 2)
	public void contextValidation() throws Exception {

		logger = ListenersImplementation.logger;
		co.aPage.accept();
		co.aPage.clickOnSideMenu();
		co.aPage.clickOnSideMenuoptions(co.aPage.helpFeedbackoption);
		String context = co.aPage.getElementAttribute(co.aPage.helptext, "class");
		assert (context).contains("WebView");

		logger.pass("Context validation test case is passed");

	}

	@Test(enabled = true, priority = 3)
	public void themeValidation() throws Exception {
		
		logger = ListenersImplementation.logger;
		co.aPage.accept();
		co.aPage.clickOnSideMenu();
		co.aPage.clickOnSideMenuoptions(co.aPage.settings);
		co.aPage.clickOnElement(co.aPage.theme);
		co.aPage.clickOnElement(co.aPage.darkTheme);

		assert (co.aPage.getElementText(co.aPage.darkTheme).equals("Dark"));

		logger.pass("Dark theme validation is successfull");
		
		// again changing it to light theme

		co.aPage.clickOnElement(co.aPage.theme);
		co.aPage.clickOnElement(co.aPage.lightTheme);

		assert (co.aPage.getElementText(co.aPage.lightTheme).equals("Light"));

		logger.pass("Light theme validation is successfull");

	}

	@Test(enabled = true, priority = 4)
	public void versionValidation() throws Exception {
		
		logger = ListenersImplementation.logger;
		co.aPage.accept();
		co.aPage.clickOnSideMenu();
		co.aPage.clickOnSideMenuoptions(co.aPage.settings);
		co.aPage.scrollToParticularText("About Automate");

		assert (co.aPage.getElementText(co.aPage.aboutVersion).equals("Version 1.32.6"));

		logger.pass("version is validated successfully");

	}

	// Explicitly failing the test case to test the screenshot functionality

	@Test(enabled = true, priority = 5)
	public void ScreenhotValidation() throws Exception {
		
		logger = ListenersImplementation.logger;
		co.aPage.accept();
		co.aPage.clickOnSideMenu();
		co.aPage.clickOnSideMenuoptions(co.aPage.settings);
		co.aPage.scrollToParticularText("About Automate");

		assert (co.aPage.getElementText(co.aPage.aboutVersion).equals("Version 1.34.6"));

	}

}
