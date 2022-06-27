package base;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Wait;

import io.appium.java_client.android.AndroidDriver;
import utils.ReadXMLData;

public class CommonConstants {
	static String currentDir = System.getProperty("user.dir");
	public static String currentOS = System.getProperty("os.name");
	protected static final String TESTDATA_FOLDER = currentDir + File.separator	+"TestData";
	static final String APPLICATIONS_FOLDER = currentDir + File.separator	+ "applications";
	public String appiumServer = "127.0.0.1";
	public int appiumPort = 4723;
	public static String userName;
	public static String password;
	String configFilePath;
	@SuppressWarnings("rawtypes")
	public AndroidDriver mobileDriver;

	protected enum Condition {
		isDisplayed, isClickable, isPresent, isNotVisible
	}

	Wait<WebDriver> wait;
	public static String configFileName = "Configuration.xml";
	public static ReadXMLData configFileObj;
	protected ReadXMLData fwTestData = null;

	protected static String appType; // AUT Type
	protected String hubUrl; // Selenium hub IP
	protected String hubPort; // Selenium hub port
	protected String appActivity;
	protected static String appPackage;
	public static String appName;
	public static String deviceID;
	protected static String version;
	protected static String test_data_folder_path = null;
	static ReadXMLData readFilePath = null;
	URL appiumURL = null;

	@SuppressWarnings("rawtypes")
	public AndroidDriver getMobileDriver() {
		return this.mobileDriver;
	}

	DesiredCapabilities capabilities;
}
