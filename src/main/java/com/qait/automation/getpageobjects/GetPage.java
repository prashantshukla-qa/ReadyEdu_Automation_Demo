package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getElementFromFile;
import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import com.qait.automation.utils.LogMessages;
import com.qait.automation.utils.ReportMsg;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class GetPage extends BaseUi {

    protected WebDriver webdriver;
    String pageName;

    public GetPage(WebDriver driver, String pageName) {
        super(driver, pageName);
        this.webdriver = driver;
        this.pageName = pageName;
    }

    private boolean _isElementDisplayed(String locator, String replacement) {
        WebElement elem = element(locator, replacement);
        wait.waitForElementToBeVisible(elem);
        return element(locator, replacement).isDisplayed();
    }

    protected void verifyElementDisplayed(String locator, String replacement) {
        assertTrue(_isElementDisplayed(locator, replacement),
                LogMessages.getVerificationFailedMessage(locator, replacement, "", Verification.DISPLAYED, ""));
        LogMessages.sendVerificationPassedMessage(locator, replacement, "", Verification.DISPLAYED, "");
    }

    protected void click(String locator) {
        verifyElementDisplayed(locator);
        element(locator).click();
        logMessage("Action", "Click action performed on element with locator '" + locator + "'");
    }

    protected void click(String locator, String replacement) {
        verifyElementDisplayed(locator, replacement);

        element(locator, replacement).click();
        logMessage("Action", "Click action performed on element with locator '" + locator + "' and replacement '"
                + replacement + "'");
    }

    public void fillTextField(String locator, String value) {
        verifyElementDisplayed(locator);
        WebElement elem = element(locator);
        _fillText(elem, value);
        logMessage("Action", "Filling TextField having locator '" + locator + "' with value '" + value + "'");
    }

    public void clickElementUsingJavaScript(String locator, String replacement) {
        verifyElementDisplayed(locator, replacement);
        WebElement elem = element(locator, replacement);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", elem);
        logMessage("Action", "Clicked element with locator '" + locator + "' and replacement '" + replacement
                + "' using Javascript");
    }

    private void _fillText(WebElement elem, String value) {
        elem.click();
        elem.clear();
        elem.sendKeys(value);
    }

    // TODO: put this in right place, create dedicated class for frame and window handlers
    protected void switchToNestedFrames(String frameNames) {
        switchToDefaultContent();
        String[] frameIdentifiers = frameNames.split(":");
        for (String frameId : frameIdentifiers) {
            wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId
                    .trim()));
        }
    }

    private boolean _isElementDisplayed(String locator) {
		WebElement elem = element(locator);
		wait.waitForElementToBeVisible(elem);
		return element(locator).isDisplayed();
	}
    
    protected void verifyElementDisplayed(String locator) {
		assertTrue(_isElementDisplayed(locator), LogMessages.getVerificationFailedMessage(locator,
				"", "", Verification.DISPLAYED, ""));
		LogMessages.sendVerificationPassedMessage(locator, "", "",
				Verification.DISPLAYED, "");
	}
    
    protected WebElement element(String elementToken) {
        return element(elementToken, "");
    }

    protected WebElement element(String elementToken, String replacement)
            throws NoSuchElementException {
        WebElement elem = null;
        Long starttime =  System.currentTimeMillis();
        try {
            elem = wait.waitForElementToBeVisible(webdriver
                    .findElement(getLocator(elementToken, replacement)));
        } catch (NoSuchElementException excp) {
            Long endtime = System.currentTimeMillis();
            float sec = (endtime - starttime) / 1000F;
            fail("FAILED: Element " + elementToken + " not found on the " + this.pageName + " after " + sec +  " seconds !!!");
        }
        catch (NullPointerException npe){
            
        }
        return elem;
    }

    protected List<WebElement> elements(String elementToken, String replacement) {
        return wait.waitForElementsToBeVisible(webdriver.findElements(getLocator(
                elementToken, replacement)));
    }

    protected List<WebElement> elementsInDOM(String elementToken, String replacement) {
        return wait.waitForElementsToBePresent(getLocator(
                elementToken, replacement));
    }

    protected List<WebElement> elementsInDOM(String elementToken) {
        return elementsInDOM(elementToken, "");
    }

    protected List<WebElement> elements(String elementToken) {
        return elements(elementToken, "");
    }

    protected void isStringMatching(String actual, String expected) {
        assertEquals(actual, expected);
        logMessage("ACTUAL STRING : " + actual);
        logMessage("EXPECTED STRING :" + expected);
        logMessage("String compare Assertion passed.");
    }

    protected boolean isElementDisplayed(String elementName,
            String elementTextReplace) {
        wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
        boolean result = element(elementName, elementTextReplace).isDisplayed();
        assertTrue(result, "TEST FAILED: element '" + elementName
        + "with text " + elementTextReplace + "' is not displayed.");
        logMessage("[PASS]: element " + elementName + " with text "
                + elementTextReplace + " is displayed.");
        return result;
    }

    protected void verifyElementText(String elementName, String expectedText) {
        wait.waitForElementToBeVisible(element(elementName));
        assertEquals(  "TEST FAILED: element '" + elementName
                + "' Text is not as expected: ", element(elementName).getText().trim(), expectedText);
        logMessage("[PASS]: element " + elementName
                + " is visible and Text is " + expectedText);
    }

    protected void verifyElementTextContains(String elementName, String expectedText) {
        wait.waitForElementToBeVisible(element(elementName));
        assertThat("TEST FAILED: element '" + elementName + "' Text is not as expected: ",
                element(elementName).getText().trim(), containsString(expectedText));
        logMessage("[PASS]: element " + elementName
                + " is visible and Text is " + expectedText);
    }

    protected boolean isElementDisplayed(String elementName) {
        wait.waitForElementToBeVisible(element(elementName));
        boolean result = element(elementName).isDisplayed();
        assertTrue(result,  "TEST FAILED: element '" + elementName
        + "' is not displayed.");
        logMessage("{PASS}: element " + elementName
                + " is displayed.");
        return result;
    }

    protected boolean isElementEnabled(String elementName, boolean expected) {
        wait.waitForElementToBeVisible(element(elementName));
        boolean result = expected && element(elementName).isEnabled();
        assertTrue( result,  "{FAIL}: element '" + elementName
        + "' is  ENABLED :- " + !expected);
        logMessage("[PASS]: element " + elementName
                + " is enabled :- " + expected);
        return result;
    }

    protected By getLocator(String elementToken) {
        return getLocator(elementToken, "");
    }

    protected By getLocator(String elementToken, String replacement) {
        String[] locator = getElementFromFile(this.pageName, elementToken);
        locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
        return getBy(locator[1].trim(), locator[2].trim());
    }

    protected By getLocator(String elementToken, String replacement1, String replacement2) {
        String[] locator = getElementFromFile(this.pageName, elementToken);
        locator[2] = StringUtils.replace(locator[2], "$", replacement1);
        locator[2] = StringUtils.replace(locator[2], "%", replacement2);
        return getBy(locator[1].trim(), locator[2].trim());
    }

    private By getBy(String locatorType, String locatorValue) {
        switch (Locators.valueOf(locatorType)) {
            case id:
                return By.id(locatorValue);
            case xpath:
                return By.xpath(locatorValue);
            case css:
                return By.cssSelector(locatorValue);
            case name:
                return By.name(locatorValue);
            case classname:
                return By.className(locatorValue);
            case linktext:
                return By.linkText(locatorValue);
            default:
                return By.id(locatorValue);
        }
    }
    public void clickUsingActionScript(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}
    public static String getBrowser(){
		if(System.getProperty("browser")==null){
			return  getProperty("./Config.properties", "browser");
		}else
			return System.getProperty("browser");
	}
    protected void enterText(WebElement element, String text) {
		wait.waitForElementToBeClickable(element);
		if (getBrowser().equalsIgnoreCase("chrome")) {
			clickUsingActionScript(element);
		} else
		click(element);
		element.clear();
		element.sendKeys(text);
		logMessage("Entered Text " + text);
	}
    
    protected void enterTextWithoutClear(WebElement element, String text) {
		wait.waitForElementToBeClickable(element);
		if (getBrowser().equalsIgnoreCase("chrome")) {
			clickUsingActionScript(element);
		} else
			click(element);
		//element.clear();
		element.sendKeys(text);
		logMessage("Entered Text " + text);
	}
	
	protected void enterDigit(WebElement element, int arg1) {
		wait.waitForElementToBeClickable(element);
		if (getBrowser().equalsIgnoreCase("chrome")) {
			clickUsingActionScript(element);
		} else
			element.click();
		element.clear();
		element.sendKeys(String.valueOf(arg1));
		logMessage("Entered digit " + arg1);
	}
	
	protected void selectDropDownValueByValue(WebElement el, String value) {
		try {
			
			
			wait.waitForElementToBeVisible(el);
			//scrollDown2(el);
			Select sel = new Select(el);
			sel.selectByVisibleText(value);

		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeVisible(el);
			// scrollDown(el);
			Select sel = new Select(el);
			sel.selectByVisibleText(value);
			logMessage("select Element " + el
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			((JavascriptExecutor) driver).executeScript("arguments[0].selectByValue='"+value+"'", el);
			logMessage("Element " + el + " could not be clicked! "
					+ ex2.getMessage());
		}
	}
	
	 protected void scrollDown2(WebElement element) {
		  JavascriptExecutor jse = (JavascriptExecutor) driver;
		  jse.executeScript("window.scrollBy(0, -100000)");
		  hardWait(5);
		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		  hardWait(1);
		  jse.executeScript("window.scrollBy(0,-350)");
		  hardWait(3);
		 }
	 
	 public void hardWait(int seconds) {
			try {
				Thread.sleep(seconds * 1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	 
	 public void switchToNewTab(String url)
	 {
		 //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));                  
		 driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");          
		 ArrayList<?> tabs = new ArrayList<> (driver.getWindowHandles());          
		 driver.switchTo().window((String) tabs.get(0)); //switches to new tab          
		 driver.get(url);
	 }
}
