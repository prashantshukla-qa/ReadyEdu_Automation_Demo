package com.qait.functionaltests;

import static com.qait.automation.utils.YamlReader.getYamlValue;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import com.qait.automation.TestSessionInitiator;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ReadyEducationRequestForDemoTest {


    TestSessionInitiator test;
    String app_url;

    @BeforeClass
    public void setupTest() {
        test = new TestSessionInitiator("Login Test");
        app_url = getYamlValue("base_url");
    }

    @Test
    public void launch_application_and_request_a_demo(){
        test.launchApplication();
        test.landingpage.requestForADemo();
        test.requestDemoPage.verifyUserIsOnRequestADemoPage();
    }

    @Test(dependsOnMethods = "launch_application_and_request_a_demo")
    public void test_request_demo_form_heading_verbatim(){
        test.requestDemoPage.verifyFormHeadingTextis(getYamlValue("request_demo.form_heading"));
    }

    @Test(dependsOnMethods="test_request_demo_form_heading_verbatim")
    public void test_form_fields_and_error_messages(){
        test.requestDemoPage.submitform();
        test.requestDemoPage.verifyErrorMessages(getYamlValues("request_demo.form_entries"), getYamlValue("request_demo.form_field_error_msg"));
        test.requestDemoPage.fillInFormDetails(getYamlValues("request_demo.form_entries"));
    }

    @AfterMethod
    public void takeScreenshot(ITestResult result){
        test.takescreenshot.takeScreenShotOnException(result);
    }

    @AfterClass
    public void tearDown(){
        test.closeBrowserSession();
    }

}