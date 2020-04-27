package com.qait.functionaltests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import com.qait.automation.TestSessionInitiator;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ReadyEducationLoginTest {

    TestSessionInitiator test;
    String app_url;

    @BeforeClass
    public void setupTest() {
        test = new TestSessionInitiator("Login Test");
        app_url = getYamlValue("base_url");
    }

    @Test(description = "Test to verify campus login button is disabled without username and password")
    public void test_campus_login_page() {
        test.launchApplication(app_url);
        test.landingpage.navigateToLoginPage("US Campuses");
        test.campusLoginPage.verifyuserisoncampusloginpage();
        test.campusLoginPage.verifyLoginButtonIsDisabled();
    }

    @Test(dependsOnMethods = "test_campus_login_page", description = "Test error message is displayed on entring wrong username and password")
    public void test_error_message_on_entering_wrong_username_password() {
        test.campusLoginPage.enterUserNameAndPassword(getYamlValue("credentials.username"),
                getYamlValue("credentials.password"));
        test.campusLoginPage.verifyLoginErrorMessage(getYamlValue("credentials.error_message.text"));
    }


    @Test(dependsOnMethods = "test_error_message_on_entering_wrong_username_password")
    public void test_password_recovery_page(){
        test.campusLoginPage.chooseToResetPassword();
        test.resetPasswordPage.verifyUserIsOnResetPasswordPage();
        test.resetPasswordPage.recoverPasswordUsingEmailAddress(getYamlValue("recovery.email"));
        test.resetPasswordPage.verifyPasswordRecoveryErrorMessage(getYamlValue("recovery.error_message.text"));
    }


    @AfterMethod
    public void takeScreennshot(ITestResult result){
        test.takescreenshot.takeScreenShotOnException(result);
    }

    @AfterClass
    public void testEnd() {
        test.closeTestSession();
    }
}