package mobile.android.testCases;

import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.SetupInit;
import createObject.CreateObject;

public class BrowserTestcase extends SetupInit {

	Map<Object, Object> data;
	protected CreateObject co;

	@SuppressWarnings("unchecked")
	@BeforeMethod
	public void beforeMethod() {
		co = new CreateObject(getMobileDriver());
	}

	@Test(enabled = true, priority = 1)
	public void test() throws Exception {
		try {
			co.bPage.scroll();
		} catch (Exception e) {
			throw e;
		}
	}

}
