package mobile.android.pages;

import org.openqa.selenium.By;

import base.SetupInit;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class BrowserPage extends SetupInit {


	By logo = By.xpath("//img[@id='hplogo']");

	By trendingSearches = By.xpath("//*[text()='Trending searches']");

	public BrowserPage(AndroidDriver<MobileElement> driver) {
		this.mobileDriver = driver;
	}

	public void scroll() {
		try {
			scroll(trendingSearches, logo);
			scroll(trendingSearches, logo);
			scroll(trendingSearches, logo);
		} catch (Exception e) {
			throw new RuntimeException("Unbale to scroll");
		}
	}

}