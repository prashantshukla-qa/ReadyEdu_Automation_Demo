package com.qait.cucumber;

import java.io.File;

import com.qait.automation.TestSessionInitiator;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepdefinitions {

    private TestSessionInitiator test;

    @Before()
    public void startTest() {
        test = new TestSessionInitiator("CukeTest");
        test.launchApplication();
    }

    @Given("I am on {string} login page")
    public void i_am_on_login_page(String campusName) {
        test.landingpage.navigateToLoginPage(campusName);
    }

    @When("I try to login using {string} and {string}")
    public void i_try_to_login_using_and(String username, String password) {
        test.campusLoginPage.verifyuserisoncampusloginpage();
        test.campusLoginPage.verifyLoginButtonIsDisabled();
        test.campusLoginPage.enterUserNameAndPassword(username, password);
    }

    @Then("Application throws login error message")
    public void application_throws_login_error_message() {
        test.campusLoginPage.verifyLoginErrorMessageIsVisible();
    }

    @Then("The error message states {string}")
    public void the_error_message_states(String expectedErrormessage) {
        test.campusLoginPage.verifyLoginErrorMessage(expectedErrormessage);
    }

    @Given("I opt to reset my password")
    public void i_opt_to_reset_my_password() {
        test.campusLoginPage.chooseToResetPassword();
    }

    @When("I provide {string} as recovery mail")
    public void i_provide_as_recovery_mail(String email) {
        test.resetPasswordPage.recoverPasswordUsingEmailAddress(email);
    }

    @After()
    public void closeBrowser(Scenario s) {
        if (s.isFailed())
            try {
                byte[] screenshot = ((TakesScreenshot) test.getDriver()).getScreenshotAs(OutputType.BYTES);
                s.embed(screenshot, "image/png", s.getName());
            } catch (ClassCastException cce) {
                cce.printStackTrace();
            }
        test.closeTestSession();
    }

}