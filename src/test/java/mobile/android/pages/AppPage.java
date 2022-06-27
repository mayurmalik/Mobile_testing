package mobile.android.pages;

import org.openqa.selenium.By;

import base.SetupInit;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AppPage extends SetupInit {

	public By btnAccept = By.id("android:id/button1");

	public By btnSideMenu = By.xpath("//android.widget.ImageButton[@content-desc='Open drawer']");

	public By trendingSearches = By.xpath("//*[text()='Trending searches']");

	public By settings = By.xpath("//*[@text='Settings']");

	public By premiumError = By.xpath("//*[@text='Premium error!']");

	public By externalStorage = By.xpath("//*[@text='External storage']");
	
	public By flows = By.xpath("//*[@text='Flows']");
	
	public By community = By.xpath("//*[@text='Community']");
	
	public By helpFeedbackoption = By.xpath("//*[@text='Help & feedback']");
	
	public By helptext = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView");
	
	public By theme = By.xpath("//*[@text='Theme']");
	
	public By darkTheme = By.xpath("//*[@text='Dark']");
	
	public By lightTheme = By.xpath("//*[@text='Light']");
	
	public By aboutVersion = By.xpath("//*[@text='Version 1.32.6']");
	
	
	public AppPage( AndroidDriver<MobileElement> driver) {
		this.mobileDriver = driver;
	}

	public void swipe() {
		try {
			scroll(externalStorage, premiumError);
		} catch (Exception e) {
			throw new RuntimeException("Unbale to scroll");
		}
	}

	public void accept() {
		try {
			clickOnElement(btnAccept);
		} catch (Exception e) {
			throw new RuntimeException("Unbale to click");
		}
	}

	public void clickOnSideMenu() {
		try {
			clickOnElement(btnSideMenu);
		} catch (Exception e) {
			throw new RuntimeException("Unbale to click");
		}
	}

	public void clickOnSettings() {
		try {
			clickOnElement(settings);
		} catch (Exception e) {
			throw new RuntimeException("Unbale to click");
		}
	}
	
	public void clickOnSideMenuoptions(By locator ) {
		try {
			clickOnElement(locator);
		} catch (Exception e) {
			throw new RuntimeException("Unable to click sidemenu option");
		}
	}

}