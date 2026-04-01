# Test Automation Framework - AI Agent Guidelines

## Project Overview
This is a scalable and maintainable test automation framework using Java, Selenium WebDriver, JUnit 5, and Cucumber. It follows Page Object Model pattern and uses Maven for dependency management. All dependencies are sourced from Maven Central repository.

## Project Structure
- `src/main/java/com/emanueltuca/automation/`: Core framework components
  - `config/`: Configuration management (e.g., `Config.java` for loading properties from `src/test/resources/config/test.properties`)
  - `pages/`: Page Object Model classes (e.g., `LoginPage.java` with WebElement locators and actions)
  - `utils/`: Utility classes (e.g., `WebDriverUtils.java` for common WebDriver operations)
  - `base/`: Base classes (e.g., `BaseTest.java` for setup/teardown, `BasePage.java` for common page methods)

- `src/test/java/com/emanueltuca/automation/`: Test implementation
  - `runners/`: Cucumber test runners (e.g., `RunCucumberTest.java` configured with JUnit 5 platform)
  - `stepdefinitions/`: Cucumber step definitions (e.g., `LoginSteps.java` implementing Gherkin steps)
  - `tests/`: Additional JUnit tests if needed

- `src/test/resources/`: Test resources
  - `features/`: Cucumber feature files (e.g., `login.feature` with Gherkin scenarios)
  - `config/`: Configuration properties (e.g., `test.properties` for URLs, timeouts)

## Key Dependencies (from Maven Central)
- Selenium WebDriver 4.40.0
- JUnit Jupiter 6.0.2
- Cucumber Java 7.34.3
- WebDriverManager 6.3.3 (for automatic driver management)
- Lombok 1.18.42 (for reducing boilerplate)

## Build and Execution
- Compile: `mvn clean compile`
- Run all tests: `mvn test`
- Run Cucumber tests: `mvn test -Dtest=RunCucumberTest`
- Generate reports: Integrated with Surefire plugin for test results

## Conventions and Patterns
- **Page Object Model**: Encapsulate page elements and actions in dedicated classes under `pages/`
- **Step Definitions**: Group by feature, e.g., `LoginSteps.java` for login-related steps
- **Configuration**: Use properties files in `src/test/resources/config/` for environment-specific values
- **Naming**: Classes in PascalCase, methods in camelCase, packages in lowercase
- **Lombok Usage**: Apply `@Data`, `@Builder`, etc., to reduce getter/setter code
- **WebDriver Management**: Use WebDriverManager for automatic driver setup, no manual downloads

## Adding New Functionality
1. Create page object in `src/main/java/com/emanueltuca/automation/pages/` with WebElement fields and action methods
2. Add feature file in `src/test/resources/features/` using Gherkin syntax
3. Implement step definitions in `src/test/java/com/emanueltuca/automation/stepdefinitions/`
4. Update runner configuration if needed for new tags or features
5. Run `mvn test` to validate

## Maintenance Tips
- Keep page objects focused on single pages/screens
- Use data-driven testing with Cucumber examples for multiple test data
- Regularly update dependencies to latest stable versions from Maven Central
- Use meaningful names for elements in page objects (e.g., `usernameField` instead of `input1`)
