package com.qait.automation;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static com.qait.automation.utils.YamlReader.setYamlFilePath;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.qait.automation.keywords.CampusLoginPage;
import com.qait.automation.keywords.LandingPage;
import com.qait.automation.keywords.RequestDemoPage;
import com.qait.automation.keywords.ResetPassword;
import com.qait.automation.utils.ReportMsg;
import com.qait.automation.utils.TakeScreenshot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestSessionInitiator {

	protected WebDriver driver;
	private final WebDriverFactory wdfactory;
	String browser;
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;

	/**
	 * Initiating the page objects
	 */

	public TakeScreenshot takescreenshot;

	public LandingPage landingpage;
	public CampusLoginPage campusLoginPage;
	public ResetPassword resetPasswordPage;
	public RequestDemoPage requestDemoPage;

	public WebDriver getDriver() {
		return this.driver;
	}

	private void _initPage() {

		landingpage = new LandingPage(driver);
		campusLoginPage = new CampusLoginPage(driver);
		resetPasswordPage = new ResetPassword(driver);
		requestDemoPage = new RequestDemoPage(driver);
	}

	/**
	 * Page object Initiation done
	 * 
	 *
	 */
	public TestSessionInitiator(String testname) {
		wdfactory = new WebDriverFactory();
		testInitiator(testname);
	}

	public void testInitiator(String testname) {
		setYamlFilePath();
		_configureBrowser();
		_initPage();
		takescreenshot = new TakeScreenshot(testname, this.driver);
	}

	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		if (!getProperty("./Config.properties", "browser").equalsIgnoreCase("mobile")
				&& !getProperty("./Config.properties", "browser").equalsIgnoreCase("android")) {
			driver.manage().window().maximize();
		}

		driver.manage().timeouts().implicitlyWait(Integer.parseInt(getProperty("timeout")), TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser", "androidName", "seleniumserver", "seleniumserverhost", "timeout" };
		Map<String, String> config = new HashMap<>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config;
	}

	public void launchApplication() {
		launchApplication(getYamlValue("base_url"));
	}

	public void launchApplication(String base_url) {
		ReportMsg.info("\nThe application url is :- " + base_url);
		ReportMsg.info("The test browser is :- " + _getSessionConfig().get("browser") + "\n");
		driver.manage().deleteAllCookies();
		driver.get(base_url);
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	public void closeBrowserSession() {
		System.out.println("\n");
		driver.quit();
	}

	public void closeTestSession() {
		closeBrowserSession();
	}

}
