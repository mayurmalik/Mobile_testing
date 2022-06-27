package base;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import utils.ExtentReport;
import utils.ReadXMLData;
import utils.ListenersImplementation;
import utils.xmlUtils;

public class SetupInit extends CommonConstants {
	/**
	 * @param deviceID
	 * @param version
	 * @param app
	 * @param myDeviceContext
	 * @param testContext
	 */
	@Parameters({ "app", "device" })
	@BeforeMethod
	public void initializeSetupInit(String app, @Optional String device, ITestContext testContext) {
		appName = app;
		deviceID = device;
		System.out.println("Execution started");
		System.out.println(
				"####################################################################################################");
		test_data_folder_path = new File(TESTDATA_FOLDER).getAbsolutePath() + File.separator;
		fetchSuiteConfiguration("Master");
		configFileObj = new ReadXMLData(test_data_folder_path + configFileName);
		try {
			appiumURL = new URL("http://" + appiumServer + ":" + appiumPort + "/wd/hub");
			DesiredCapabilities cap;
			switch (appType) {
			case "browser":
				System.setProperty("webdriver.chrome.driver",
						"C:\\Program Files\\Appium Server GUI\\resources\\app\\node_modules\\appium\\node_modules\\appium-chromedriver\\chromedriver\\win\\chromedriver.exe");
				cap = setBrowserCapabilitiesAndroid();
				this.mobileDriver = new AndroidDriver<MobileElement>(appiumURL, cap);
				this.mobileDriver.get("https://www.google.co.in/");
				break;
			case "app":
				cap = setAppCapabilitiesAndroid();
				this.mobileDriver = new AndroidDriver<MobileElement>(appiumURL, cap);
				System.out.println();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DesiredCapabilities setBrowserCapabilitiesAndroid() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "emulator-5554");
		capabilities.setCapability("platformVersion", "11");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		capabilities.setCapability("newCommandTimeout", 1000);
		capabilities.setCapability("autoGrantPermissions", true);
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			capabilities.setCapability("autoAcceptAlerts", true);
		}
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("noReset", false);
		return capabilities;
	}

	public DesiredCapabilities setAppCapabilitiesAndroid() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "emulator-5554");
		File file = new File(currentDir + File.separator + "applications" + File.separator + appName);
		capabilities.setCapability("app", file.getAbsolutePath());
		capabilities.setCapability("platformVersion", "11");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appPackage", appPackage);
//		capabilities.setCapability("appActivity", appActivity);
		capabilities.setCapability("newCommandTimeout", 1000);
		capabilities.setCapability("autoGrantPermissions", true);
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			capabilities.setCapability("autoAcceptAlerts", true);
		}
		capabilities.setCapability("automationName", "UiAutomator2");
		return capabilities;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {

		this.mobileDriver.quit();
	}

	public void captureVideo(AndroidDriver<MobileElement> driver) {
		((CanRecordScreen) driver)
				.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofSeconds(1800)));
	}

	public void fetchSuiteConfiguration(String configuration) {
		// configFileObj = new ReadXMLData(test_data_folder_path + configFileName);
		String xmlFilePath = test_data_folder_path + configFileName;
		appType = xmlUtils.getChildNodeValue(xmlFilePath, "MasterType");
		if (appType.equals("android")) {
			hubUrl = xmlUtils.getChildNodeValue(xmlFilePath, configuration, "Hub");
			hubPort = xmlUtils.getChildNodeValue(xmlFilePath, configuration, "Port");
		}
		appPackage = xmlUtils.getChildNodeValue(xmlFilePath, configuration, "packageName");
		appActivity = xmlUtils.getChildNodeValue(xmlFilePath, configuration, "appWaitActivity");
	}

	// *************************************************

	public void clickOnElementWithOffset(int x, int y, By locator) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed);
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				new Actions(mobileDriver).moveToElement(element, x, y).click().build().perform();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void clickOnElement(By locator) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed);
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				element.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MobileElement getElement(By locator) {
		return getElement(Condition.isDisplayed, locator);
	}

	public String getElementText(By locator) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isPresent);
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element.getText();
	}

	public String getElementAttribute(By locator, String attribute) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed);
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element.getAttribute(attribute.trim()).trim();
	}

	public void clearAndsendKeys(By locator, String text) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed);
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				element.clear();
				element.sendKeys(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendKeys(By locator, Keys keys) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed);
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				element.sendKeys(keys);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<MobileElement, String> waitForElementState(By locator, Condition condition) {
		MobileElement element;
		Map<MobileElement, String> map = new HashMap<>();
		element = getElement(condition, locator);
		String message = "";
		if (element == null) {
			try {
				throw new Exception();
			} catch (Exception e) {
				message = "State = " + condition.toString() + " failed: ";
			}
		} else {
			message = "State = " + condition.toString() + " Passed: ";
		}
		map.put(element, message);
		return map;
	}

	public Instant getCurrentTime() {
		return Instant.now();
	}

	public MobileElement getElement(Condition condition, By by) {
		MobileElement element = null;
		WebDriverWait wait = new WebDriverWait(mobileDriver, 30);
		try {
			switch (condition) {
			case isClickable:
				element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				if (element == null) {
					return element;
				} else if (element.getAttribute("clickable") == null) {
					return element;
				} else if (element.getAttribute("clickable") != null) {
					element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(by));
					return element;
				}
				if (!isVisibleInViewport(element) && element != null) {
					scrollToElement(element);
				}
				break;
			case isDisplayed:
				element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				if (!isVisibleInViewport(element) && element != null) {
					scrollToElement(element);
				}
				break;
			case isPresent:
				element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(by));
				break;
			default:
				break;
			}
		} catch (Exception e) {
		}
		return element;
	}

	protected boolean isVisibleInViewport(MobileElement element) {
		return ((Boolean) ((JavascriptExecutor) ((MobileElement) element).getWrappedDriver()).executeScript(
				"var elem = arguments[0],                   box = elem.getBoundingClientRect(),      cx = box.left + box.width / 2,           cy = box.top + box.height / 2,           e = document.elementFromPoint(cx, cy); for (; e; e = e.parentElement) {           if (e === elem)                            return true;                         }                                        return false;                            ",
				new Object[] { element })).booleanValue();
	}

	private void scrollToElement(MobileElement element) {
		((JavascriptExecutor) mobileDriver).executeScript(
				"window.scrollTo(" + element.getLocation().x + "," + (element.getLocation().y - 80) + ");");
	}

	public boolean isDisplayed(By by) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(by, Condition.isDisplayed);
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isPresent(By by) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(by, Condition.isPresent);
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<MobileElement> getElementList(By locator) {
		waitForElementState(locator, Condition.isDisplayed);
		ArrayList<MobileElement> elementLst = new ArrayList<>();
		try {
			elementLst = (ArrayList<MobileElement>) mobileDriver.findElements(locator);
		} catch (Exception e) {
		}
		return elementLst;
	}

	@SuppressWarnings("rawtypes")
	public void swipe(MobileElement androidElement) {
		int startX = androidElement.getLocation().getX() + (androidElement.getSize().getWidth() / 2);
		int startY = androidElement.getLocation().getY() + (androidElement.getSize().getHeight() / 2);
		int endX = androidElement.getLocation().getX();
		int endY = androidElement.getLocation().getY();
		new TouchAction(mobileDriver).press(point(startX, startY)).waitAction(waitOptions(ofMillis(1000)))
				.moveTo(point(endX, endY)).release().perform();
	}

	@SuppressWarnings("rawtypes")
	public void swipe(By locator) {
		MobileElement androidElement = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed);
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			androidElement = entry.getKey();
		}
		try {
			if (androidElement == null)
				throw new Exception();
			else {
				int startX = androidElement.getLocation().getX() + (androidElement.getSize().getWidth() / 2);
				int startY = androidElement.getLocation().getY() + (androidElement.getSize().getHeight() / 2);
				int endX = androidElement.getLocation().getX();
				int endY = androidElement.getLocation().getY();
				new TouchAction(mobileDriver).press(point(startX, startY)).waitAction(waitOptions(ofMillis(1000)))
						.moveTo(point(endX, endY)).release().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void scroll(By fromLocator, By toLocator) {
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(fromLocator, Condition.isDisplayed);
		MobileElement fromElement = null;
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			fromElement = entry.getKey();
		}
		elementState = waitForElementState(toLocator, Condition.isDisplayed);
		MobileElement toElement = null;
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			toElement = entry.getKey();
		}
		int startX = fromElement.getLocation().getX() + (fromElement.getSize().getWidth() / 2);
		int startY = fromElement.getLocation().getY() + (fromElement.getSize().getHeight() / 2);

		int endX = toElement.getLocation().getX() + (toElement.getSize().getWidth() / 2);
		int endY = toElement.getLocation().getY() + (toElement.getSize().getHeight() / 2);

		new TouchAction(mobileDriver).press(PointOption.point(startX, startY))
				.waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
				.moveTo(PointOption.point(endX, endY)).release().perform();
	}

	@SuppressWarnings("rawtypes")
	public void scrollUpAndroidElement(By mobileElement) {
		try {
			Dimension dimension = mobileDriver.manage().window().getSize();
			int scrollHeight = dimension.getHeight();
			int scrollWidth = dimension.getWidth();
			new TouchAction(mobileDriver).press(PointOption.point(scrollWidth / 2, scrollHeight / 2))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
					.moveTo(PointOption.point(scrollWidth / 2, 10)).release().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void scrollDownAndroidElement(By mobileElement) {
		try {
			Dimension dimension = mobileDriver.manage().window().getSize();
			int scrollHeight = dimension.getHeight();
			int scrollWidth = dimension.getWidth();
			new TouchAction(mobileDriver).press(PointOption.point(scrollWidth / 2, scrollHeight / 2))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
					.moveTo(PointOption.point(scrollWidth / 2, scrollHeight - 10)).release().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// added new method for scroll upto particular text until visibility is located
	// on mobile screen
	@SuppressWarnings("rawtypes")
	public void scrollToParticularText(String scrollText) {
		mobileDriver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ scrollText + "\").instance(0))"));
	}

	// added new method for taking screenshot
	public String takeScreenShot(MobileDriver<MobileElement> driver) throws IOException {

		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		String destination = "./test-results/screenshots/" + System.currentTimeMillis() + ".png";
		File finalDestination = new File(destination);

		FileUtils.copyFile(source, finalDestination);

		return destination;

	}
}
