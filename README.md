# 🚀 Enterprise Test Automation Framework

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.java.net/projects/jdk/17/)
[![Selenium](https://img.shields.io/badge/Selenium-4.41.0-43B02A?style=for-the-badge&logo=Selenium&logoColor=white)](https://www.selenium.dev/)
[![JUnit](https://img.shields.io/badge/JUnit5-5.11.0-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.18.0-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)](https://cucumber.io/)
[![Allure](https://img.shields.io/badge/Allure-2.33.0-FF6B35?style=for-the-badge&logo=allure&logoColor=white)](https://docs.qameta.io/allure/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![AspectJ](https://img.shields.io/badge/AspectJ-1.9.25.1-00A3FF?style=for-the-badge&logo=aspectj&logoColor=white)](https://www.eclipse.org/aspectj/)

Demo UI test automation framework built as a QA Engineer portfolio project demonstrating advanced QA engineering skills with enterprise-grade architecture, thread-safe parallel execution, and professional Allure reporting.

---

## 🎯 **QA Engineering Skills Demonstrated**

### **Core Automation Competencies**
- ✅ **Selenium WebDriver 4** - Advanced browser automation with modern APIs
- ✅ **Page Object Model (POM)** - Scalable UI automation with fluent interfaces
- ✅ **BDD with Cucumber** - Behavior-driven development and Gherkin scenarios
- ✅ **Test-Driven Development** - Comprehensive test coverage and validation layers

### **Advanced Technical Skills**
- ✅ **Thread-Safe Architecture** - Concurrent execution with ThreadLocal and synchronized blocks
- ✅ **Enterprise Patterns** - Dependency injection, layered architecture, design patterns
- ✅ **CI/CD Integration** - Jenkins - to be implemented

### **Quality Assurance Expertise**
- ✅ **Comprehensive Logging** - SLF4J + Log4j 2 with structured logging levels
- ✅ **Screenshot Automation** - Automatic failure capture and reporting
- ✅ **Cross-Browser Testing** - Chrome, Firefox with headless support
- ✅ **Configuration Management** - System property overrides and environment handling

### ⚡ **Performance & Scalability**
- ✅ **Parallel Execution**
- ✅ **Thread-Safe Cache** - HashMap for page objects (isolation-based)
- ✅ **Memory Efficiency** - Lazy loading and intelligent caching

### ✨ **Allure Reports Integration** ✨
- ✅ **Professional HTML Reports** - Auto-generated with step execution
- ✅ **Environment Information** - Browser, OS, Java version captured
- ✅ **Screenshot Capture** - Automatic on test failure
- ✅ **Step Categorization** - @Step annotations in Flows with Allure
- ✅ **Test Categorization** - Features, stories, severity levels
- ✅ **Centralized Management** - AllureManager + AllureReportUtils

<img width="637" height="572" alt="image" src="https://github.com/user-attachments/assets/25c8c507-2034-4958-b68f-50ed2a32b571" />

---

## 🏗️ **Architecture Excellence**

### **Layered Architecture Design**
```
🎯 TEST EXECUTION LAYER
    🥒 CUCUMBER BDD (Gherkin Scenarios)
        📝 STEP DEFINITIONS (Dependency Injection)
            🔄 FLOWS LAYER (@Step Annotations)
                ✅ ASSERTIONS LAYER (Validation Logic)

🎯 STATE MANAGEMENT LAYER
    🎯 TEST CONTEXT (Thread-Safe Navigation)
        📄 PAGE MANAGER (HashMap Caching)
            🖥️ PAGE OBJECTS (Fluent POM)
                🌐 BASE PAGE (Common Selenium Operations)

🎯 INFRASTRUCTURE LAYER
    🖥️ WEBDRIVER MANAGER (ThreadLocal Isolation)
        🌐 SELENIUM WEBDRIVER (Browser Automation)
            🧵 THREAD-SAFE EXECUTION (Parallel Processing)

🎯 REPORTING LAYER
    📊 ALLURE REPORTS (Professional Visualization)
        📸 SCREENSHOTS (Automatic Failure Capture)
            📋 ENVIRONMENT INFO (System Context)
```
### **Design Patterns Implemented**
| Pattern | Implementation | Benefit |
|---------|----------------|---------|
| **Page Object Model** | Fluent interface with method chaining | Maintainable, readable UI automation |
| **Factory Pattern** | PageManager with lazy instantiation | Efficient resource management |
| **Singleton Pattern** | ThreadLocal WebDriver instances | Thread-safe browser isolation |
| **Strategy Pattern** | Configurable browser selection | Flexible cross-browser testing |
| **Observer Pattern** | Hooks for lifecycle management | Clean setup/teardown operations |
| **Decorator Pattern** | Allure @Step annotations | Enhanced reporting without code changes |

---

---

## 📊 **Professional Reporting Suite**

### **Allure Reports Features**
- 🎨 **Visual Dashboard** - Pass/fail statistics with trend analysis
- 📋 **Detailed Test Results** - Step-by-step execution with @Step annotations
- 📸 **Automatic Screenshots** - Failure capture with browser context
- 🌍 **Environment Information** - Browser, OS, Java version (auto-captured)
- 🏷️ **Test Categorization** - Features, stories, severity levels
- 📈 **Historical Trends** - Execution timeline and performance metrics
- ✅ **AllureManager** - Centralized Allure setup (environment, categories, metadata)
- ✅ **AllureReportUtils** - Convenient API for screenshots, logs, labels

### **Sample Report Structure**
```
📊 OVERVIEW DASHBOARD
├── 📈 Pass Rate: 85%
├── ⏱️ Average Duration: 2.8s
├── 📊 Total Tests: 12
└── 📈 Trend: Improving

📋 TEST EXECUTION DETAILS
├── ✅ Login with valid credentials
│   ├── ✅ Open Login Page
│   ├── ✅ Enter username: "standard_user"
│   ├── ✅ Enter password: "secret_sauce"
│   └── ✅ Click Login button
├── ❌ Login with invalid credentials
│   ├── ✅ Open Login Page
│   ├── ✅ Enter username: "invalid_user"
│   ├── ✅ Enter password: "wrong_pass"
│   ├── ❌ Click Login button
│   └── 📸 Screenshot: Failure evidence
└── 🌍 ENVIRONMENT
    ├── Browser: Chrome 120.0
    ├── OS: Windows 11
    ├── Java: 17.0.8
    └── Framework: JUnit 5.11.0
```
<img width="1912" height="886" alt="image" src="https://github.com/user-attachments/assets/47c3f90f-5909-4e5d-9fee-331923a68cc7" />

---

## 🛠️ **Technical Implementation Highlights**

### **Advanced Java Features**
- ✅ **Java 17** - Modern language features and performance
- ✅ **Generics** - Type-safe page object management
- ✅ **Lambda Expressions** - Functional programming patterns
- ✅ **Stream API** - Efficient data processing
- ✅ **Optional** - Null-safe operations

### **Build & Dependency Management**
- ✅ **Maven 3.6+** - Industry-standard build tool
- ✅ **AspectJ Weaving** - Runtime @Step annotation processing
- ✅ **Version Management** - Centralized dependency versions
- ✅ **Plugin Configuration** - Surefire, Compiler, AspectJ plugins

### **Configuration Management**
- ✅ **Properties Files** - Environment-specific configuration
- ✅ **System Property Overrides** - Runtime configuration flexibility
- ✅ **JUnit Platform Properties** - Test execution customization
- ✅ **Allure Configuration** - Report generation settings

---
### **Architecture & Design Patterns**
- **Page Object Model** - Fluent interfaces, method chaining, maintainability
- **TestContext Pattern** - State management with thread-safe navigation through isolation
- **PageManager with HashMap** - Efficient caching for thread-safe parallel execution
- **Dependency Injection** - Cucumber Picocontainer for test isolation
- **AllureManager Integration** - Centralized Allure setup (environment, categories, metadata)
- **Factory & Singleton Patterns** - Efficient resource management with ThreadLocal

### **Best Practices Implemented**
- ✅ **SOLID Principles** - Single responsibility, open/closed, dependency inversion
- ✅ **DRY Principle** - Reusable components, centralized configuration
- ✅ **KISS Principle** - Simple, maintainable code structure
- ✅ **YAGNI Principle** - Focused on essential features, extensible design

---

## 🏆 **Professional Skills Portfolio**

### **QA Automation Expertise**
- 🎯 **Test Strategy & Planning** - BDD scenarios, test categorization, risk assessment
- 🔧 **Framework Design** - Architecture patterns, scalability, maintainability
- ⚡ **Performance Optimization** - Parallel execution, resource management, efficiency
- 📊 **Reporting & Analytics** - Stakeholder communication, trend analysis, insights

### **Technical Proficiency**
- 💻 **Programming Languages** - Java 17, SQL, Bash scripting
- 🛠️ **Build Tools** - Maven, dependency management, plugin configuration
- 🧪 **Testing Frameworks** - JUnit 5, Cucumber, TestNG concepts
- 🤖 **Automation Tools** - Selenium WebDriver 4, browser automation, cross-browser testing

### **DevOps & CI/CD**
- 🔄 **Continuous Integration** - Jenkins, GitHub Actions, automated pipelines
- 📦 **Containerization** - Docker integration, environment consistency
- 📊 **Monitoring & Reporting** - Allure integration, trend analysis, alerting
- 🚀 **Deployment Automation** - Maven builds, artifact management, release processes

### **Quality Assurance**
- 🎯 **Test Design** - Equivalence partitioning, boundary analysis, edge cases
- 📋 **Test Management** - Test organization, execution tracking, result analysis
- 🔍 **Defect Management** - Screenshot automation, failure analysis, root cause identification
- 📈 **Quality Metrics** - Pass rates, execution times, stability trends, coverage analysis

---

## 🤝 **Contributing & Best Practices**

### **Code Standards**
- 📝 **Java Naming Conventions** - Consistent, readable code structure
- 📚 **Comprehensive Documentation** - Inline comments, JavaDoc, README files
- 🧪 **Test-Driven Development** - Unit tests, integration tests, BDD scenarios
- 🔒 **Thread-Safe Programming** - Concurrent programming, synchronization patterns

### **Quality Assurance**
- ✅ **Code Reviews** - Peer review process, quality gates
- 🐛 **Bug Tracking** - Issue management, root cause analysis
- 📊 **Test Metrics** - Coverage reports, performance benchmarks
- 🔄 **Continuous Improvement** - Framework evolution, best practice adoption

---

## 📞 **Contact & Professional Profile**

This framework demonstrates advanced QA engineering capabilities suitable for:

- **Senior QA Engineer** positions requiring automation expertise
- **Test Automation Architect** roles focusing on framework design
- **SDET (Software Development Engineer in Test)** positions
- **DevOps QA** roles with CI/CD pipeline experience

### **Professional Summary**
> "Experienced QA Engineer with expertise in building scalable test automation frameworks using Java, Selenium, and modern testing practices. Demonstrated ability to design thread-safe, enterprise-grade solutions with comprehensive reporting and CI/CD integration."

### **Key Technologies**
- **Languages**: Java 17, SQL, Bash scripting
- **Testing**: Selenium WebDriver 4, JUnit 5, Cucumber, TestNG
- **Tools**: Maven, Jenkins, GitHub Actions, Docker, Allure Reports
- **Methodologies**: BDD, TDD, Agile, Scrum, CI/CD

---


## Current Status
The current implementation automates the SauceDemo authentication flow with Java 17, Selenium WebDriver 4, Cucumber 7, JUnit Platform, Maven, Log4j 2, and Allure Reports. The framework is intentionally structured to demonstrate maintainable BDD test automation, layered execution, centralized page management, browser configuration, and practical failure diagnostics.

This project is implemented as a working demo framework, not as a full end-to-end test suite for the entire SauceDemo application.

Implemented:

| Area | Current implementation |
| --- | --- |
| Test scope | SauceDemo authentication scenarios and product catalog access verification |
| BDD | Cucumber feature file, step definitions, scenario outlines, tags, and custom `@ParameterType` mappings |
| Execution architecture | Feature -> Step definitions -> Flows -> Assertions/Page objects -> Selenium actions |
| Page objects | Fluent Page Object Model using `BasePage`, `LoginPage`, and `ProductCatalogPage` |
| Browser management | `DriverFactory` with Chrome/Firefox support, headless mode, `BrowserOptions`, WebDriverManager, and `ThreadLocal<WebDriver>` |
| Context management | PicoContainer-injected `PageContext` and `TestContext` per scenario |
| Page management | Reflection-based `PageManager` with lazy page creation and per-context page cache |
| Logging | Global framework log and per-scenario log files via Log4j 2 routing |
| Failure artifacts | Screenshot, `context.txt`, and `page-source.html` for failed scenarios |
| Reporting | Allure result generation, Allure attachments, environment metadata, categories, and Cucumber JSON output |
| Output structure | Runtime artifacts organized under `target/execution-output` |

Partially implemented or prepared:

| Area | Current state |
| --- | --- |
| Parallel execution | The framework is designed for scenario isolation through `ThreadLocal<WebDriver>`, PicoContainer contexts, and per-scenario output directories. Parallel execution is not enabled by default in committed configuration. |
| Allure labels | Feature and Cucumber tags are added. Utility methods for severity, story, JSON, text, HTML, and log attachments exist, but not all are used by current scenarios. |
| Navigation utilities | `WindowManager` exists for browser navigation/tab switching, but current tests do not need it. |
| Product catalog coverage | The catalog page is only validated as the landing page after successful login. Product sorting, cart, checkout, and logout flows are not implemented yet. |

Not implemented yet:

- CI/CD pipeline files such as GitHub Actions or Jenkinsfile.
- Selenium Grid, remote browser execution, or Dockerized browser execution.
- Retry/rerun mechanism for flaky scenarios.
- External test data management through JSON, CSV, database, or API.
- Screenshots for passed scenarios.
- Full SauceDemo business flow coverage beyond authentication.
- Persisted Allure history/trend reporting between test runs.

## Execution Architecture

The framework uses a layered BDD execution model:

```text
src/test/resources/features/Authentication.feature
    -> Step definitions
       LoginSteps / ProductCatalogSteps / ParameterTypes
        -> Flow layer
           LoginFlow
            -> Assertion layer
               LoginAssertions / CatalogAssertions
            -> Page state
               PageContext + TestContext
            -> Page object layer
               LoginPage / ProductCatalogPage
                -> BasePage
                   Selenium WebDriver actions and explicit waits
```

The main flow is:

1. Cucumber reads the Gherkin scenario from `Authentication.feature`.
2. Step definitions translate business-readable steps into framework calls.
3. `@ParameterType` methods convert readable values such as `standard` or `account is locked` into strongly typed enums.
4. `LoginFlow` orchestrates user actions and page transitions.
5. Assertions validate the expected page state or error message.
6. Page objects expose fluent page actions such as `open()`, `enterUsername()`, and `enterPassword()`.
7. `BasePage` performs Selenium operations with explicit waits.
8. `DriverFactory` provides the browser instance for the current execution thread.

## BDD Test Structure

The implemented feature is:

```text
src/test/resources/features/Authentication.feature
```

It contains:

- A `Background` that opens the authentication page before every scenario.
- One happy-path scenario for a standard user.
- One negative scenario for a locked-out user.
- Scenario outlines for invalid credentials and missing required fields.
- Tags such as `@login`, `@happy-path`, `@negative`, and `@validation`.

Custom Cucumber parameter types are implemented in:

```text
src/test/java/com/emanueltuca/automation/bdd/stepdefinitions/ParameterTypes.java
```

Implemented parameter conversions:

| Parameter type | Example text | Java type |
| --- | --- | --- |
| `{demoUser}` | `standard`, `locked out`, `problem` | `DemoUser` |
| `{loginError}` | `account is locked`, `credentials are invalid`, `username is required`, `password is required` | `LoginErrorMessage` |

This keeps feature files readable while avoiding hard-coded credentials and expected messages inside the step definitions.

## Context And Page Management

Current context responsibilities are intentionally separated:

| Class | Responsibility |
| --- | --- |
| `TestContext` | Stores scenario test data such as the selected `DemoUser`, username, and password |
| `PageContext` | Stores the current active page and owns the `PageManager` |
| `ExecutionContext` | Creates the run output directory, scenario output directory, and Log4j thread context values |
| `PageManager` | Lazily creates and caches page objects for the current scenario context |

`PageManager` creates page objects by reflection:

```text
pageClass.getDeclaredConstructor(WebDriver.class).newInstance(driver)
```

The cache is a regular `HashMap` because it is owned by a scenario-specific `PageContext`. It is not shared globally between scenarios. This design supports parallel execution by isolation instead of shared synchronization.

## Driver Factory And Browser Options

Browser lifecycle is centralized in:

```text
src/main/java/com/emanueltuca/automation/core/driver/DriverFactory.java
src/main/java/com/emanueltuca/automation/core/driver/BrowserOptions.java
```

Implemented behavior:

- Uses `ThreadLocal<WebDriver>` so each execution thread can own its own browser.
- Supports Chrome and Firefox.
- Uses WebDriverManager to resolve browser drivers.
- Applies browser-specific options in `BrowserOptions`.
- Supports headless and visible browser execution.
- Disables implicit waits and uses explicit waits through `BasePage`.
- Applies page load timeout from configuration.
- Quits and removes the driver in the Cucumber `@After` hook.

Browser options currently include:

| Browser | Options |
| --- | --- |
| Chrome | Incognito, disabled notifications, disabled password manager, disabled GPU, optional headless mode |
| Firefox | Private browsing, disabled notifications, disabled geolocation popup, disabled password/autofill features, optional headless mode |

Configuration file:

```text
src/main/resources/config/test.properties
```

Runtime overrides currently implemented:

```bash
mvn test -Ddriver=chrome
mvn test -Ddriver=firefox
mvn test -Dheadless=true
mvn test -Dheadless=false
```

## Debugging And Reporting

The debugging mechanism is implemented around logs, scenario artifacts, Allure attachments, and Cucumber JSON output.

### Logging

Log4j 2 configuration:

```text
src/main/resources/log4j2.xml
```

Generated logs:

| Output | Purpose |
| --- | --- |
| `target/logs/framework.log` | Full framework execution log |
| `target/execution-output/<run-id>/<scenario-folder>/scenario.log` | Log file scoped to one scenario |
| Console output | Live execution feedback |

Per-scenario logging is implemented through Log4j `RoutingAppender` and `ThreadContext`. `ExecutionContext.initialize()` sets the scenario output directory before the browser starts.

### Failure Artifacts

For failed scenarios, the `@After` hook creates:

| Artifact | Content |
| --- | --- |
| Screenshot PNG | Browser screenshot at failure time |
| `context.txt` | Scenario name, status, current URL, page title, and thread id |
| `page-source.html` | Browser page source at failure time |
| `scenario.log` | Scenario-specific execution log |

The same failure artifacts are attached to Allure where applicable:

- Failure screenshot as `image/png`.
- Failure context as text.
- Page source as HTML.
- Scenario log as log attachment.

### Allure Reporting

Allure integration is configured through:

```text
src/test/resources/junit-platform.properties
src/test/resources/allure.properties
src/test/java/com/emanueltuca/automation/utils/reporting/AllureManager.java
src/test/java/com/emanueltuca/automation/utils/reporting/AllureReportUtils.java
```

Implemented Allure behavior:

- Uses `io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm`.
- Writes results to `target/allure-results`.
- Adds environment metadata.
- Writes categories for common failure types.
- Adds feature and tag metadata for scenarios.
- Uses `@Step` annotations in `LoginFlow`.
- Attaches failure screenshot, context, page source, and scenario log.

Generate and open an Allure report:

```bash
mvn clean test
allure generate target/allure-results -o target/allure-report --clean
allure open target/allure-report
```

### Cucumber JSON Report

The Cucumber JSON reporter is enabled in:

```text
src/test/resources/junit-platform.properties
```

Generated file:

```text
target/cucumber-report.json
```

This file can be consumed by other reporting tools or CI plugins.

## Runtime Output Structure

After a test run, the main generated outputs are:

```text
target/
  allure-results/
    environment.properties
    categories.json
    *.json
    *-attachment.*
  cucumber-report.json
  logs/
    framework.log
  execution-output/
    <run-id>/
      <scenario-name>_<timestamp>_<thread-id>/
        scenario.log
        context.txt          # failed scenarios only
        page-source.html     # failed scenarios only
        <scenario>_T<thread>_<timestamp>.png  # failed scenarios only
```

The scenario folder name is sanitized and includes a timestamp and thread id to avoid collisions during repeated or parallel executions.

## Running Tests

Prerequisites:

- Java 17.
- Maven.
- Chrome or Firefox installed locally.
- Allure CLI installed only if you want to generate/open the HTML report locally.

Compile:

```bash
mvn clean compile
```

Run all tests:

```bash
mvn clean test
```

Run with a specific browser:

```bash
mvn clean test -Ddriver=chrome
mvn clean test -Ddriver=firefox
```

Run with visible browser window:

```bash
mvn clean test -Dheadless=false
```

Run with Cucumber parallel execution enabled:

```bash
mvn clean test -Dcucumber.execution.parallel.enabled=true -Dcucumber.execution.parallel.config.strategy=fixed -Dcucumber.execution.parallel.config.fixed.parallelism=4
```

Parallel execution is supported by the framework design, but it is not enabled by default in `junit-platform.properties`.

## Project Structure

```text
src/
  main/
    java/com/emanueltuca/automation/
      core/config/ConfigReader.java
      core/driver/BrowserOptions.java
      core/driver/DriverFactory.java
      domain/DemoUser.java
      domain/LoginErrorMessage.java
      ui/pages/BasePage.java
      ui/pages/LoginPage.java
      ui/pages/ProductCatalogPage.java
      utils/PageManager.java
      utils/ScreenshotUtils.java
      utils/WindowManager.java
    resources/
      config/test.properties
      log4j2.xml
  test/
    java/com/emanueltuca/automation/
      bdd/assertions/
      bdd/context/
      bdd/flows/
      bdd/hooks/
      bdd/runners/
      bdd/stepdefinitions/
      utils/reporting/
    resources/
      features/Authentication.feature
      allure.properties
      categories.json
      junit-platform.properties
```

## Key Design Choices

- No Selenium PageFactory is used. Page objects use direct `By` locators and explicit wait helpers from `BasePage`.
- Page object methods are fluent where it improves readability.
- Step definitions stay thin and delegate business actions to flow classes.
- Assertions are centralized in dedicated assertion classes.
- Browser state is isolated through `ThreadLocal<WebDriver>`.
- Page instances are isolated through Cucumber object injection and `PageContext`.
- Failure evidence is stored both in the filesystem and, where supported, in Allure.
- `target/execution-output` is designed for local debugging and CI artifact archiving.

## Suggested Next Improvements

The next useful improvements for this demo framework would be:

1. Add committed CI configuration that runs tests and archives Allure, Cucumber JSON, logs, and `execution-output`.
2. Add Selenium Grid or remote WebDriver support.
3. Add retry/rerun support for failed Cucumber scenarios.
4. Expand SauceDemo coverage with cart, checkout, sorting, logout, and menu scenarios.
5. Add external test data loading for larger scenario coverage.
6. Persist Allure history between CI runs.
7. Add README screenshots generated from the actual current report after a clean successful run.

## Portfolio Summary

This framework demonstrates practical QA automation skills:

- BDD test design with Cucumber.
- Selenium WebDriver browser automation.
- Layered framework architecture.
- Fluent Page Object Model implementation.
- Scenario-scoped context and page management.
- Cross-browser browser factory design.
- Thread-aware browser isolation.
- Log-driven debugging.
- Failure artifact collection.
- Allure and Cucumber reporting outputs.
