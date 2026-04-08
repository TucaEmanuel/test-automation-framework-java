Feature: Login
  In order to do internet shopping
  As a valid SauceDemo customer
  I want to login successfully

  Scenario: Successful login
    Given the login page is opened
    When user logs in with valid credentials: "standard_user" username and "secret_sauce" password
    Then user is redirected to the inventory page


  Scenario Outline: Unsuccessful login with invalid credentials
    Given the login page is opened
    When user logs in with invalid credentials: "<invalid_user>" username and "<invalid_password>" password
    Then the error message with "Epic sadface: Username and password do not match any user in this service" text is displayed

    Examples:
      | invalid_user    | invalid_password |
      | locked_out_user | secret_sauce     |
      | standard_user   | wrong_password   |
      | wrong_user      | secret_sauce     |