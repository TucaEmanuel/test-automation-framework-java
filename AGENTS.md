# Test Automation Framework - AI Agent Guidelines

## Project Overview
A scalable, maintainable test automation framework built with **Java 17**, **Selenium WebDriver 4.40.0**, **JUnit 5**, and **Cucumber 7**. Uses Maven for dependency management with all dependencies sourced from **Maven Central Repository**. Follows the **Page Object Model (POM)** pattern for maintainability and scalability.

## Critical Architecture

### Core Components
- **DriverFactory** (`core/driver/`): ThreadLocal WebDriver management with automatic driver setup via WebDriverManager. Handles Chrome/Firefox browser initialization.
- **Config** (`core/config/`): Properties loader for environment-specific values (base URL, browser choice, timeouts).
- **BasePage** (`ui/pages/`): Abstract base class with WebDriver, WebDriverWait, and URL navigation logic.
- **Page Objects** (`ui/pages/`): Encapsulate page locators (By selectors) and action methods.

### Data Flow: Test Execution
1. **Hooks.java** `@Before`: Initializes DriverFactory and creates WebDriver via ThreadLocal
2. **Step Definitions** use `DriverFactory.getDriver()` to access the driver instance
3. **Page Objects** receive driver in constructor and use it for element interaction
4. **Hooks.java** `@After`: Cleans up driver from ThreadLocal to prevent memory leaks

## Critical Dependencies & Versions (Maven Central)
```
Core Testing:
- org.junit.jupiter:junit-jupiter:5.11.0 (includes platform, engine, api)
- io.cucumber:cucumber-java:7.18.0
- io.cucumber:cucumber-junit-platform-engine:7.18.0 (Cucumber → JUnit Platform bridge)
- org.junit.platform:junit-platform-suite:1.11.0 (Suite annotations)

Selenium & Browser Management:
- org.seleniumhq.selenium:selenium-java:4.40.0
- io.github.bonigarcia:webdrivermanager:6.3.3

Utilities:
- org.projectlombok:lombok:1.18.42
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
- **Properties file**: `src/test/resources/config/test.properties` (loaded by Config.java static initializer)

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
    
    public LoginPage(WebDriver driver) {
        super(driver);                                    // Call parent constructor
    }
    
    public void loginAs(String user, String pass) {      // Action methods return void
        driver.findElement(username).sendKeys(user);     // Use locators directly
    }
}
```

**Key Points**:
- Inherit from BasePage (provides driver, wait, openUrl methods)
- Store locators as `private final By` fields
- Use direct `findElement()` calls (PageFactory.initElements commented out in BasePage)
- Action methods use descriptive names

### Step Definitions
```java
public class LoginSteps {
    private LoginPage loginPage;
    
    @Given("I open the login page")
    public void openLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(DriverFactory.getDriver());
        }
        loginPage.open();
    }
}
```

**Key Points**:
- One step file per feature
- Page objects initialized lazily (ensures WebDriver exists)
- Annotations from `io.cucumber.java.en.*` define step text matching

### Hooks for WebDriver Lifecycle
```java
@Before
public void setUp() {
    driverFactory = new DriverFactory();
    driverFactory.initializeDriver();  // Populates ThreadLocal
}

@After
public void tearDown() {
    if (driverFactory != null) {
        driverFactory.quitDriver();    // Cleans ThreadLocal
    }
}
```

## Configuration Management

### Properties: `src/test/resources/config/test.properties`
```properties
base.url=https://www.saucedemo.com/
browser=chrome  # Options: chrome, firefox
timeout=10      # Wait timeout in seconds
```

### Config Class Pattern
- Static initializer loads properties on JVM startup
- Getter methods for base URL, browser, timeout
- Throws RuntimeException if properties not found

## Adding New Test Features

1. Create feature file in `src/test/resources/features/` (Gherkin syntax)
2. Create page object in `src/main/java/com/emanueltuca/automation/ui/pages/` extending BasePage
3. Implement step definitions in `src/test/java/com/emanueltuca/automation/bdd/stepdefinitions/` with @Given/@When/@Then annotations
4. Run `mvn test` to execute

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

## Key Files Reference
- `pom.xml`: Dependencies, Maven Surefire configuration
- `src/test/java/.../runners/RunCucumberTest.java`: Suite entry point with Cucumber engine config
- `src/test/java/.../hooks/Hooks.java`: WebDriver lifecycle management
- `src/main/java/.../core/config/Config.java`: Property loading
- `src/main/java/.../core/driver/DriverFactory.java`: ThreadLocal WebDriver creation/cleanup
- `src/main/java/.../ui/pages/BasePage.java`: Common page operations
- `src/main/java/.../ui/pages/*.java`: Page objects with locators and actions
- `src/test/java/.../bdd/stepdefinitions/*.java`: Gherkin step implementations
- `src/test/resources/features/*.feature`: Cucumber scenarios
