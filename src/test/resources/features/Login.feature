Feature: Login
  In order to do internet shopping
  As a valid SauceDemo customer
  I want to login successfully

  Scenario: Successful login
    Given I open the login page
    When I login with valid credentials
    Then I should see the home page