# Ready Education Automation Demo

## Requirements

* Java (JDK and JRE) 8+ (_preferably 11_)
* Maven 3+
  * Download and install from <http://maven.apache.org/download.cgi>
  * Setup classpath so that mvn --version works on terminal / command promt
    * [optional]: setup M2_Home
  * Installation instructions: <https://maven.apache.org/install.html>
* nodejs and npm (_latest version_)
  * for appium

## Instructions

### Install selenium drivers in classpath

  1. download following and extract them in a folder   chromedriver: <https://chromedriver.chromium.org/downloads>
    1. geckodriver for firefox: <https://github.com/mozilla/geckodriver/releases>
  2. Put the folder in you path in user environment variables

### To Execute Tests

  1. cucumber test: mvn clean test -Dtest=RunCucumberTest
  1. testng test: mvn clean test -Dtest=**test_name**
    1. e.g. for login test test_name = ReadyEducationLoginTest

### Object repository

Have a look at the pageobject repository in the resources folder [./src/test/resources/PageObjectRepository]
