# Test Automation Framework - AI Agent Guidelines

## Project Overview
A scalable, maintainable test automation framework built with **Java 17**, **Selenium WebDriver 4.41.0**, **JUnit 5**, and **Cucumber 7**. Uses Maven for dependency management with all dependencies sourced from **Maven Central Repository**. Follows the **Page Object Model (POM)** pattern with **TestContext**, **PageManager**, **Flows**, and **Assertions** layers for maintainability and scalability. Includes structured logging with **SLF4J** and **Log4j 2** for scenario lifecycle, failure diagnostics, and debug information.

## Critical Architecture

### Core Components
- **DriverFactory** (`core/driver/`): ThreadLocal WebDriver management with automatic driver setup via WebDriverManager. Handles Chrome/Firefox browser initialization.
- **Config** (`core/config/`): Properties loader for environment-specific values (base URL, browser choice, timeouts).
- **BasePage** (`ui/pages/`): Abstract base class with WebDriver, WebDriverWait, PageNavigator, and common Selenium operations.
- **Page Objects** (`ui/pages/`): Encapsulate page locators (By selectors) and action methods, extending BasePage.
- **PageManager** (`utils/`): Generic reflection-based page instantiation and caching, ensures single instance per page class.
- **TestContext** (`bdd/context/`): Implements PageNavigator, manages currentPage state, provides type-safe page access and transitions.
- **Flows** (`bdd/flows/`): Orchestrate high-level business logic sequences using TestContext.
- **Assertions** (`bdd/assertions/`): Centralized validation methods using TestContext for page state checks.

### Data Flow: Test Execution
1. **Hooks.java** `@Before`: Initializes DriverFactory and creates TestContext with PageManager
2. **Step Definitions** use injected TestContext to access Flows and Assertions
3. **Flows** orchestrate business logic using TestContext for page navigation and actions
4. **Page Objects** instantiated via PageManager, receive driver and PageNavigator (TestContext) in constructor
5. **Hooks.java** `@After`: Cleans up TestContext (resets currentPage) and DriverFactory

## Critical Dependencies & Versions (Maven Central)
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

Utilities:
- org.projectlombok:lombok:1.18.42
- org.slf4j:slf4j-api:2.0.13
- org.apache.logging.log4j:log4j-slf4j2-impl:2.23.1
- org.apache.logging.log4j:log4j-core:2.23.1
```

**⚠️ CRITICAL**: Version alignment (prevents "TestEngine with ID 'cucumber' failed to discover tests"):
- JUnit 5.11.0 pairs with JUnit Platform 1.11.0
- Cucumber 7.18.0 requires `cucumber-junit-platform-engine` for JUnit Platform integration
- Do NOT mix JUnit 6.x with Cucumber 7.x

## Build & Execution

### Maven Commands
```bash
mvn clean compile           # Compile only
mvn test                   # Run all tests (Cucumber via RunCucumberTest)
mvn test -Dtest=RunCucumberTest  # Explicit Cucumber runner
mvn clean                  # Remove target directory
```

### Key Configuration
- **Surefire Plugin**: Includes `RunCucumberTest.java` to execute Cucumber scenarios
- **Compiler**: Java 17 source/target
- **Properties file**: `src/main/resources/config/test.properties` (loaded by Config.java static initializer)

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
    testContext.cleanup();  // Reset currentPage
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
- `pom.xml`: Dependencies, Maven Surefire configuration
- `src/test/java/.../runners/RunCucumberTest.java`: Suite entry point with Cucumber engine config
- `src/test/java/.../hooks/Hooks.java`: WebDriver lifecycle management
- `src/main/java/.../core/config/Config.java`: Property loading
- `src/main/java/.../core/driver/DriverFactory.java`: ThreadLocal WebDriver creation/cleanup
- `src/main/java/.../ui/pages/BasePage.java`: Common page operations
- `src/main/java/.../ui/pages/*.java`: Page objects with locators and actions
- `src/main/java/.../utils/PageManager.java`: Generic page instantiation and caching
- `src/main/java/.../utils/PageNavigator.java`: Interface for page navigation
- `src/main/resources/log4j2.xml`: Logging configuration
- `src/test/java/.../bdd/context/TestContext.java`: Test state management and navigation
- `src/test/java/.../bdd/flows/*.java`: Business logic orchestration
- `src/test/java/.../bdd/assertions/*.java`: Validation methods
- `src/test/java/.../bdd/stepdefinitions/*.java`: Gherkin step implementations
- `src/test/resources/features/*.feature`: Cucumber scenarios

