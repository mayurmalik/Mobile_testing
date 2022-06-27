package createObject;

import base.SetupInit;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import mobile.android.pages.AppPage;
import mobile.android.pages.BrowserPage;

public class CreateObject extends SetupInit {
	public BrowserPage bPage;
	public AppPage aPage;

	public CreateObject(AndroidDriver<MobileElement> mobileDriver) {
		if (mobileDriver != null) {
			bPage = new BrowserPage(mobileDriver);
			aPage = new AppPage(mobileDriver);
		}
	}

}
