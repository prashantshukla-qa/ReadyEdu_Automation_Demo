package com.qait.automation.keywords;

import java.util.Map;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.ReportMsg;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class RequestDemoPage extends GetPage {

    public RequestDemoPage(WebDriver driver) {
        super(driver, "Request_Demo_Page");
    }

    public void enterContactDetails() {

    }

    public void verifyErrorMessages(Map<String, Object> formFields, String errorMsg) {
        for (String fieldname : formFields.keySet()) {
            if (!fieldname.equalsIgnoreCase("phone")) {
                Assert.assertEquals(element("text_error_msg", fieldname).getText(), errorMsg);
                ReportMsg.pass("Request A demo field " + fieldname + " has correct info message");
            }
        }
    }

    public void fillInFormDetails(Map<String, Object> formFields){
        for (String fieldname : formFields.keySet()) {
            element("input_fields", fieldname).sendKeys(formFields.get(fieldname).toString());
        }
    }

    public void submitform() {
        element("button_submit").click();
    }

    public void verifyUserIsOnRequestADemoPage() {
        verifyPageTitleContains();
        Assert.assertTrue(element("text_form_heading").isDisplayed());
        ReportMsg.pass("User is on the Request Demo Form page.");
    }

    public void verifyFormHeadingTextis(String headingtext) {
        Assert.assertEquals(element("text_form_heading").getText(), headingtext,
                "Request a demo form heading is not correct.");
        ReportMsg.pass("Request a demo form heading is as expected :- " + headingtext);
    }
}