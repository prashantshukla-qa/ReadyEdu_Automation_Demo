package com.qait.automation.keywords;

import com.qait.automation.getpageobjects.GetPage;
import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;

public class LandingPage extends GetPage {

	public LandingPage(WebDriver driver) {
		super(driver, "LandingPage");
	}

	public void navigateToLoginPage(String campusName) {
		element("button_covid19_banner_close").click();
		if (getProperty("./Config.properties", "browser").equalsIgnoreCase("mobile")
				|| getProperty("./Config.properties", "browser").equalsIgnoreCase("android")) {
			this.driver.get("https://campuscloud.readyeducation.com/#/login?goTo=%252Fdashboard");
		} else {
			element("link_signin").click();
			String campus = "link_us_login".replace("_us", "_" + campusName.toLowerCase().replace(" ", "_"));
			System.out.println(campus);
			element(campus).click();
		}
		logMessage("info", "Opened " + campusName + " Cloud Login Page");
	}

	public void requestForADemo() {
		element("button_covid19_banner_close").click();
		element("button_req_demo").click();
	}
}
