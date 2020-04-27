package com.qait.automation.keywords;

import com.qait.automation.getpageobjects.GetPage;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class CampusLoginPage extends GetPage {

	public CampusLoginPage(WebDriver driver) {
		super(driver, "campuscloud_login");
	}

	public void verifyuserisoncampusloginpage() {
		Assert.assertEquals(driver.getTitle(), getPageTitle());
		logMessage("pass", "User is on Campus Login Page");
	}

	public void verifyLoginButtonIsDisabled() {
		Assert.assertTrue(!element("button_login").isEnabled(), "The login button is not disabled");
		logMessage("pass", "The login button is not disabled");
	}

	public void verifyLoginButtonIsEnabled() {
		Assert.assertTrue(element("button_login").isEnabled(), "The login button is not enabled");
		logMessage("pass", "The login button is enabled");
	}

	public void enterUserNameAndPassword(String username, String password) {
		element("input_username").sendKeys(username);
		element("input_password").sendKeys(password);
		verifyLoginButtonIsEnabled();
		element("button_login").click();

	}

	public void verifyLoginErrorMessage(String expectedErrormessage) {
		Assert.assertEquals(element("text_login_error").getText(), expectedErrormessage,
				"The login error message is not correct");
		logMessage("pass", "The login error message is as expected :- '" + expectedErrormessage + "'");
	}

	public void verifyLoginErrorMessageIsVisible() {
		Assert.assertTrue(element("text_login_error").isDisplayed(),
				"The login error message is not visible to the user");
		logMessage("pass", "The login error message is visible to the user.");
	}

	public void chooseToResetPassword() {
		element("link_reset_password").click();
	}

}
