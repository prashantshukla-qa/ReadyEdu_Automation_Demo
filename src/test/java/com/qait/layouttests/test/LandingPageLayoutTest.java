package com.qait.layouttests.test;

import org.testng.annotations.Test;

import java.io.IOException;

import com.qait.automation.getpageobjects.ObjectFileReader;
import com.qait.layouttests.components.GalenTestBase;
import static com.qait.automation.utils.YamlReader.getYamlValue;


public class LandingPageLayoutTest extends GalenTestBase {

    @Test(dataProvider = "devices")
    public void welcomePage_shouldLookGood_onDevice(TestDevice device) throws IOException {
        load(getYamlValue("base_url"));
        checkLayout(ObjectFileReader.getSpecFilePath("LandingPage"), device.getTags());
    }
}
