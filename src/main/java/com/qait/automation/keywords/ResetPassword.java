package com.qait.automation.keywords;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.ReportMsg;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ResetPassword extends GetPage {

	public ResetPassword(WebDriver driver) {
		super(driver, "reset_password");
	}

	public void verifyUserIsOnResetPasswordPage() {
		verifyPageTitleContains();
		verifyLostPasswordHeaderisVisible();
	}

	public void verifyLostPasswordHeaderisVisible() {
		Assert.assertTrue(element("text_title").isDisplayed(), "'Lost Password' header is not displayed");
		ReportMsg.pass("Lost Password header is visible");
	}

	public void recoverPasswordUsingEmailAddress(String email) {
		element("input_email").sendKeys(email);
		element("button_reset_password").click();
		ReportMsg.info("Entered '" + email + "' for password recovery");
	}

	public void verifyPasswordRecoveryErrorMessage(String errorMessage) {
		Assert.assertTrue(element("text_login_error").isDisplayed());
		Assert.assertEquals(element("text_login_error").getText(), errorMessage);
		ReportMsg.pass("Error message is visible and text is :- '" + errorMessage + "'");
	}
}
