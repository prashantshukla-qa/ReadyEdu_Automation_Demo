package com.qait.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(plugin = {"pretty", "html:target/cucumber-report/cucumber-report.html"}, 
                features = "src/test/resources/features")
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}