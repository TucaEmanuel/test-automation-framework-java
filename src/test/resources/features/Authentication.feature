@login
Feature: Authentication
  In order to access the SauceDemo application
  As a user
  I want to authenticate with valid and invalid credentials and observe the expected behavior

  Background:
    Given the authentication page is displayed

  @happy-path
  Scenario: A standard user accesses the product catalog
    Given a standard user
    When he authenticates
    Then he gains access to the product catalog

  @negative
  Scenario: A locked out user is denied access
    Given a locked out user
    When he authenticates
    Then he remains unauthenticated
    And he is informed that the account is locked

  @negative
  Scenario Outline: A user is denied access with invalid credentials
    Given the user provides username "<username>"
    And the user provides password "<password>"
    When he authenticates
    Then he remains unauthenticated
    And he is informed that the credentials are invalid

    Examples:
      | username      | password       |
      | standard_user | wrong_password |
      | invalid_user  | secret_sauce   |

  @validation
  Scenario Outline: A user cannot authenticate with missing credentials
    Given the user provides username "<username>"
    And the user provides password "<password>"
    When he authenticates
    Then he remains unauthenticated
    And he is informed that the <field> is required

    Examples:
      | username      | password     | field    |
      |               | secret_sauce | username |
      | standard_user |              | password |
      |               |              | username |
