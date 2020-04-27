package com.qait.automation.utils;

import com.qait.automation.getpageobjects.Verification;
import org.testng.Reporter;


public class LogMessages {


    private static void logMessage(String msgType, String message) {
        Reporter.log("[" + msgType + "]: " + message, true);
    }

    public static void sendVerificationPassedMessage(String locator, String replacement1,
                                                     String replacement2, Verification verification,
                                                     String expectedData) {
        String message = "";
        String locatorDesc = getLocatorDescription(locator, replacement1, replacement2);
        if(verification.equals(Verification.DISPLAYED)) {
            message = "Element with " + locatorDesc + " is Displayed";
        }else if(verification.equals(Verification.EXACT_TEXT)) {
            message = "Text of Element with " + locatorDesc + " is '" + expectedData + "'";
        }else if(verification.equals(Verification.PARTIAL_TEXT)) {
            message = "Element with " + locatorDesc + " contains Text '" + expectedData + "'";
        }else if(verification.equals(Verification.NOT_DISPLAYED)) {
            message = "Element with " + locatorDesc + " is not Displayed";
        }
        logMessage("Verified", message);
    }

    public static String getVerificationFailedMessage(String locator, String replacement1,
                                                      String replacement2, Verification verification,
                                                      String expectedData) {
        String message = "";
        String locatorDesc = getLocatorDescription(locator, replacement1, replacement2);
        if(verification.equals(Verification.DISPLAYED)) {
            message = "Element with " + locatorDesc + " is not Displayed";
        }else if(verification.equals(Verification.EXACT_TEXT)) {
            message = "Text of Element with " + locatorDesc + " is not '" + expectedData + "'";
        }else if(verification.equals(Verification.PARTIAL_TEXT)) {
            message = "Element with " + locatorDesc + " does not contain Text '" + expectedData + "'";
        }else if(verification.equals(Verification.NOT_DISPLAYED)) {
            message = "Element with " + locatorDesc + " is Displayed";
        }
        return message;
    }

    private static String getLocatorDescription(String locator, String replacement1, String replacement2) {
        String locatorDesc = "Locator '" + locator + "'";
        if(!replacement1.isEmpty() && replacement2.isEmpty()) locatorDesc += " and replacement '" +
                replacement1 + "'";
        if(!replacement1.isEmpty() && !replacement2.isEmpty()) locatorDesc += ", replacement1 '" +
                replacement1 + "' and replacement2 '" + replacement2 + "'";
        return locatorDesc;
    }


}
