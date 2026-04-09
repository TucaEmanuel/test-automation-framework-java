# Test Automation Framework - AI Agent Guidelines

## Project Overview
A **production-ready test automation framework** built with **Java 17**, **Selenium WebDriver 4.41.0**, **JUnit 5**, and **Cucumber 7**. Features enterprise-grade architecture with **thread-safe parallel execution**, **professional Allure reporting**, **comprehensive logging** (SLF4J + Log4j 2), and **centralized Allure management**.

Follows the **Page Object Model (POM)** pattern enhanced with **TestContext**, **PageManager**, **Flows**, and **Assertions** layers. All test infrastructure is production-grade, CI/CD ready, and fully documented.

## 🎯 Critical Architecture

### Core Components
- **DriverFactory** (`core/driver/`): ThreadLocal WebDriver management with automatic driver setup via WebDriverManager. Handles Chrome/Firefox browser initialization with system property overrides.
- **ConfigReader** (`core/config/`): Properties loader for environment-specific values with system property override support (-Ddriver, -Dheadless).
- **BasePage** (`ui/pages/`): Abstract base class with WebDriver, WebDriverWait, PageNavigator, and common Selenium operations. No PageFactory - direct findElement() calls.
- **Page Objects** (`ui/pages/`): Encapsulate page locators (By selectors) and action methods, extending BasePage with fluent interface.
- **PageManager** (`utils/`): Generic reflection-based page instantiation with **HashMap** caching for thread-safe parallel execution through isolation.
- **TestContext** (`bdd/context/`): Manages currentPage state and provides type-safe page access and transitions through isolation.
- **Flows** (`bdd/flows/`): Orchestrate high-level business logic sequences using TestContext with **@Step annotations** for Allure reporting.
- **Assertions** (`bdd/assertions/`): Centralized validation methods using TestContext for page state checks.
- **AllureManager** (`utils/reporting/`): **NEW** - Centralized management for Allure Reports setup, environment configuration, and test metadata orchestration.
- **AllureReportUtils** (`utils/reporting/`): **NEW** - Utility methods for Allure API interactions (screenshots, logs, labels, severity, features, stories).

### Data Flow: Test Execution
1. **Hooks.java** `@Before`: AllureManager.initialize() + AllureManager.setupTestMetadata(), DriverFactory.initializeDriver()
2. **Step Definitions** use injected TestContext to access Flows and Assertions
3. **Flows** orchestrate business logic using TestContext with @Step annotations for Allure
4. **Page Objects** instantiated via PageManager with HashMap caching
5. **Hooks.java** `@After`: Screenshot attachment via AllureManager, cleanup TestContext, DriverFactory.quitDriver()

## 📦 Critical Dependencies & Versions (Maven Central)
```
Core Testing:
- org.junit.jupiter:junit-jupiter:5.11.0 (includes platform, engine, api)
- io.cucumber:cucumber-java:7.18.0
- io.cucumber:cucumber-junit-platform-engine:7.18.0 (Cucumber → JUnit Platform bridge)
- io.cucumber:cucumber-picocontainer:7.18.0 (Dependency injection for test context)
- org.junit.platform:junit-platform-suite:1.11.0 (Suite annotations)

Selenium & Browser Management:
- org.seleniumhq.selenium:selenium-java:4.41.0
- io.github.bonigarcia:webdrivermanager:6.3.3

Allure Reports & AspectJ:
- io.qameta.allure:allure-cucumber7-jvm:2.33.0 (@Step annotation support)
- org.aspectj:aspectjweaver:1.9.25.1 (Runtime weaving for @Step)

Utilities:
- org.projectlombok:lombok:1.18.42
- org.slf4j:slf4j-api:2.0.13
- org.apache.logging.log4j:log4j-slf4j2-impl:2.25.4
- org.apache.logging.log4j:log4j-core:2.25.4
```

**⚠️ CRITICAL**: Version alignment (prevents "TestEngine with ID 'cucumber' failed to discover tests"):
- JUnit 5.11.0 pairs with JUnit Platform 1.11.0
- Cucumber 7.18.0 requires `cucumber-junit-platform-engine` for JUnit Platform integration
- AspectJ 1.9.25.1 processes @Step annotations at runtime
- Do NOT mix JUnit 6.x with Cucumber 7.x

## Build & Execution

### Maven Commands
```bash
mvn clean compile           # Compile only
mvn test                   # Run all tests (Cucumber via RunCucumberTest)
mvn test -Dtest=RunCucumberTest  # Explicit Cucumber runner
mvn test -Ddriver=chrome   # Run tests with Chrome browser
mvn test -Ddriver=firefox  # Run tests with Firefox browser
mvn test -Dheadless=false  # Run tests with visible browser window
mvn test -Dheadless=true   # Run tests in headless mode
mvn test -Djunit.jupiter.execution.parallel.enabled=true  # Run tests in parallel
mvn clean test && allure generate target/allure-results -o target/allure-report --clean && allure open target/allure-report  # Run tests and generate Allure report
mvn clean                  # Remove target directory
```

### Key Configuration
- **Surefire Plugin**: Includes `RunCucumberTest.java` to execute Cucumber scenarios with AspectJ weaving
- **Compiler**: Java 17 source/target
- **Properties file**: `src/main/resources/config/test.properties` (loaded by ConfigReader static initializer)
- **Allure Plugin**: Configured in junit-platform.properties for @Step annotation processing

### Parallel Execution Configuration
The framework supports parallel test execution with thread-safe isolation:
- **Thread Safety**: Each test thread gets isolated TestContext, PageManager, and WebDriver (ThreadLocal)
- **Isolation Strategy**: No shared state between threads - each test has dedicated resources
- **Default**: Parallelization disabled (use `-Djunit.jupiter.execution.parallel.enabled=true` to enable)
- **Thread Strategy**: Fixed parallelism configurable via `junit.jupiter.execution.parallel.config.fixed.parallelism`
- **See**: `THREAD_SAFETY.md` for comprehensive parallel execution guide

## Cucumber Test Discovery (JUnit Platform Engine)

### RunCucumberTest Annotations
```java
@Suite                                    // Marks as JUnit Platform Suite
@IncludeEngines("cucumber")              // Use Cucumber engine for discovery
@SelectClasspathResource("features")     // Features directory on classpath
@ConfigurationParameter(
  key = GLUE_PROPERTY_NAME,              // Glue packages for step definitions & hooks
  value = "com.emanueltuca.automation.bdd.stepdefinitions," +
          "com.emanueltuca.automation.bdd.hooks"
)
@ConfigurationParameter(
  key = PLUGIN_PROPERTY_NAME,
  value = "pretty"                       // Pretty console output
)
```

**IMPORTANT**: Glue value MUST be full package names (comma-separated, NO spaces).

## Conventions and Patterns

### Page Object Model
```java
public class LoginPage extends BasePage {
    private final By username = By.id("user-name");      // Locators as private final
    
    public LoginPage(WebDriver driver, PageNavigator navigator) {
        super(driver, navigator);                         // Call parent constructor
    }
    
    public void loginAs(String user, String pass) {      // Action methods return void
        driver.findElement(username).sendKeys(user);     // Use locators directly
    }
}
```

**Key Points**:
- Inherit from BasePage (provides driver, wait, navigator, openUrl methods)
- Store locators as `private final By` fields
- Use direct `findElement()` calls (PageFactory.initElements commented out in BasePage)
- Action methods use descriptive names

### Step Definitions
```java
public class LoginSteps {
    private final LoginFlow loginFlow;
    private final LoginAssertions loginAssertions;
    
    public LoginSteps(TestContext context) {
        this.loginFlow = new LoginFlow(context);
        this.loginAssertions = new LoginAssertions(context);
    }
    
    @Given("the login page is opened")
    public void openLoginPage() {
        loginFlow.openLoginPage();
    }
    
    @When("user logs in with username {string} and password {string}")
    public void userLogsIn(String username, String password) {
        loginFlow.loginAs(username, password);
    }
    
    @Then("the error message with {string} text is displayed")
    public void verifyErrorMessage(String expectedText) {
        loginAssertions.verifyErrorMessage(expectedText);
    }
}
```

**Key Points**:
- One step file per feature
- Use dependency injection (TestContext injected by Cucumber Picocontainer)
- Flows handle business logic, Assertions handle validations
- Annotations from `io.cucumber.java.en.*` define step text matching

### Hooks for WebDriver Lifecycle
```java
@Before
public void setUp() {
    DriverFactory.initializeDriver();
}

@After
public void tearDown(Scenario scenario) {
    // Handle screenshots on failure
    if (scenario.isFailed()) {
        // Take and attach screenshot
    }
    pageContext.cleanup();  // Reset currentPage
    DriverFactory.quitDriver();
}
```

**Key Points**:
- @Before: Initialize WebDriver via DriverFactory
- @After: Cleanup TestContext (resets currentPage), then quit driver
- Screenshot handling on test failure

## Configuration Management

### Properties: `src/main/resources/config/test.properties`
```properties
base.url=https://www.saucedemo.com/
page.timeout=10
element.timeout=10

# Browser options: chrome | firefox
browser=chrome

#headless options: false - windows is visible | true - window is hidden
headless=true
```

### Config Class Pattern
- Static initializer loads properties on JVM startup
- Getter methods for base URL, browser, timeouts (page and element)
- **System Property Override**: Browser can be overridden via `-Ddriver=chrome` command line argument
- **System Property Override**: Headless mode can be overridden via `-Dheadless=true` command line argument
- Throws RuntimeException if properties not found

## Logging System

### SLF4J with Log4j 2 Backend
- **SLF4J API**: Logging facade for abstraction
- **Log4j 2 Implementation**: High-performance logging backend
- **Configuration**: `src/main/resources/log4j2.xml` with console and rolling file appenders

### Logging Levels
- **INFO**: Important events (e.g., "Starting valid login flow")
- **DEBUG**: Technical details (e.g., "Clicking locator: {}", loginButton)
- **WARN**: Suspicious but non-failing events (e.g., "Retrying click action")
- **ERROR**: Failures (e.g., "Scenario failed: {}", scenarioName) with exceptions

### Usage in Code
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger logger = LoggerFactory.getLogger(MyClass.class);

logger.info("Starting flow with param: {}", param);
logger.debug("Performing action on element: {}", locator);
logger.warn("Retry attempt {}", attempt);
logger.error("Operation failed", exception);
```

### Log Output
- **Console**: Real-time output during test execution
- **Rolling File**: `logs/test-automation.log` with daily rotation and size limits
- **Failure Diagnostics**: Screenshot paths logged on test failures

## Adding New Test Features

1. Create feature file in `src/test/resources/features/` (Gherkin syntax)
2. Create page object in `src/main/java/com/emanueltuca/automation/ui/pages/` extending BasePage
3. Create flow class in `src/test/java/com/emanueltuca/automation/bdd/flows/` using TestContext
4. Create assertions class in `src/test/java/com/emanueltuca/automation/bdd/assertions/` using TestContext
5. Implement step definitions in `src/test/java/com/emanueltuca/automation/bdd/stepdefinitions/` with @Given/@When/@Then annotations
6. Run `mvn test` to execute

If creating step definitions in NEW package, update RunCucumberTest glue parameter to include the package.

## Troubleshooting

### "TestEngine with ID 'cucumber' failed to discover tests"
- **Cause**: Glue packages not found OR feature files not on classpath root
- **Fix**: Use FULL package names in GLUE_PROPERTY_NAME (no spaces after commas)
- **Fix**: Ensure `src/test/resources/features/` exists with `*.feature` files

### "Cannot invoke findElement because driver is null"
- **Cause**: Page object initialized before @Before hook
- **Fix**: Initialize page objects lazily in step methods
- **Fix**: Verify DriverFactory.getDriver() is called AFTER @Before hook

### JUnit Version Conflicts
- **Cause**: Mismatched JUnit Platform components
- **Fix**: Ensure junit-jupiter, junit-platform-suite, junit-platform-commons all use same version (1.11.0)

### IntelliJ IDEA JUnit Runtime Conflicts
- **Cause**: IntelliJ injects its own JUnit jars (version 6.x) into the classpath, overriding project dependencies
- **Fix**: In Run/Debug Configurations, for the test configuration, ensure "Use classpath of module" is selected and "JUnit platform" uses project default
- **Alternative**: Run tests via Maven from command line: `mvn test`

## Project-Specific Conventions
1. **No PageFactory.initElements**: Use direct `findElement()` calls (see BasePage)
2. **ThreadLocal WebDriver**: Thread-safe driver management
3. **Lazy Page Object Initialization**: Prevents null driver errors
4. **Full Package Names in Glue**: No relative paths or classpath resource syntax
5. **Feature Organization**: One feature file per domain (Login.feature, Checkout.feature, etc.)
6. **TestContext as NavigationHelper**: Manages currentPage state and provides type-safe page transitions
7. **Flows for Business Logic**: Orchestrate sequences using TestContext, separate from step definitions
8. **Assertions for Validations**: Centralized checks using TestContext for page state

## Key Files Reference
- `pom.xml`: Dependencies (Allure 2.33.0, AspectJ 1.9.25.1, Selenium 4.41.0, JUnit 5.11.0, Cucumber 7.18.0), Maven plugins with AspectJ weaving
- `THREAD_SAFETY.md`: Parallel execution guide with thread-safety patterns
- `ALLURE_REPORTS_GUIDE.md`: Comprehensive Allure documentation
- `ALLURE_QUICK_START.md`: Quick reference for report generation
- `src/test/resources/allure.properties`: Allure configuration
- `src/test/resources/junit-platform.properties`: Cucumber glue + Allure plugin
- `src/test/resources/categories.json`: Test categorization for Allure reports
- `src/main/resources/config/test.properties`: Configuration with system overrides
- `src/main/resources/log4j2.xml`: Logging setup
- `src/test/java/.../runners/RunCucumberTest.java`: Suite with Cucumber engine
- `src/test/java/.../hooks/Hooks.java`: Lifecycle + AllureManager integration
- `src/main/java/.../core/config/ConfigReader.java`: Properties loader
- `src/main/java/.../core/driver/DriverFactory.java`: ThreadLocal WebDriver management
- `src/main/java/.../ui/pages/BasePage.java`: Base page with common operations
- `src/main/java/.../ui/pages/*.java`: Page objects with locators
- `src/main/java/.../utils/PageManager.java`: Page instantiation with HashMap caching (thread-safe through isolation)
- `src/main/java/.../utils/PageNavigator.java`: Navigation interface
- `src/test/java/.../bdd/context/TestContext.java`: State management through isolation
- `src/test/java/.../bdd/flows/*.java`: Business logic with @Step annotations
- `src/test/java/.../bdd/assertions/*.java`: Validation methods
- `src/test/java/.../bdd/stepdefinitions/*.java`: Gherkin mappings
- `src/test/java/.../utils/reporting/AllureManager.java`: ✅ Centralized Allure setup + metadata
- `src/test/java/.../utils/reporting/AllureReportUtils.java`: ✅ Allure API wrapper methods
- `src/test/resources/features/*.feature`: Cucumber scenarios

