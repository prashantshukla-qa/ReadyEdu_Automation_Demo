Feature: Test error message on login page

    @login-page-test @smoke-test
    Scenario: I see error message on entring wrong credentials
        Given I am on "US Campuses" login page
        When I try to login using "username" and "password"
        Then Application throws login error message
        And The error message states "Please check your credentials and try again."

    @reset-password
    Scenario: I am able to reset passord from login page
        Given I am on "US Campuses" login page
        And I opt to reset my password
        When I provide "prashantshukla@qainfotech.com" as recovery mail
        Then Application throws login error message
        And The error message states "There is no user with that login information." 