package com.qait.layouttests.components;

import com.galenframework.testng.GalenTestNgTestBase;
import com.qait.automation.TestSessionInitiator;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

import java.util.List;

import static java.util.Arrays.asList;

public abstract class GalenTestBase extends GalenTestNgTestBase {

    @Override
    public WebDriver createDriver(Object[] args) {
        WebDriver driver = new TestSessionInitiator("Layout Test").getDriver();
        if (args.length > 0) {
            if (args[0] != null && args[0] instanceof TestDevice) {
                TestDevice device = (TestDevice)args[0];
                if (device.getScreenSize() != null) {
                    driver.manage().window().setSize(device.getScreenSize());
                }
            }
        }
        return driver;
    }

    public void load(String URL) {
        getDriver().get(URL);
    }

    @DataProvider(name = "devices")
    public Object [][] devices () {
        return new Object[][] {
                // {new TestDevice("mobile", new Dimension(450, 800), asList("mobile"))},
                {new TestDevice("desktop", new Dimension(1920, 1080), asList("desktop"))},
                {new TestDevice("ipad-landscape", new Dimension(1024, 800), asList("ipad-landscape"))}
        };
    }

    public static class TestDevice {
        private final String name;
        private final Dimension screenSize;
        private final List<String> tags;

        public TestDevice(String name, Dimension screenSize, List<String> tags) {
            this.name = name;
            this.screenSize = screenSize;
            this.tags = tags;
        }

        public String getName() {
            return name;
        }

        public Dimension getScreenSize() {
            return screenSize;
        }

        public List<String> getTags() {
            return tags;
        }

        @Override
        public String toString() {
            return String.format("%s %dx%d", name, screenSize.width, screenSize.height);
        }
    }
}
