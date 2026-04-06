# test-automation-framework-java
Scalable UI automation framework built with Java, Selenium WebDriver, TestNG, Page Object Model, and Allure reporting. Includes CI integration and configurable execution.


I started with a minimal working setup and then incrementally added abstraction layers once I had a stable flow

“I used a ThreadLocal WebDriver to ensure thread safety and support parallel execution, while keeping access simple across hooks, steps, and page objects.”

**How to run the tests:**
Local Chrome
mvn clean test
Firefox headless
mvn clean test -Dbrowser=firefox -Dheadless=true
Chrome headless
mvn clean test -Dbrowser=chrome -Dheadless=true


**Explicit waits with WebDriverWait:** - for making tests stable, maintainable, and production-ready by handling the unpredictable nature of web applications
WebDriverWait is a Selenium WebDriver utility class that implements explicit waits to handle timing issues in automated testing.
It allows you to wait for specific conditions to occur before proceeding with the next steps in your test, such as waiting for an element to be visible, clickable, or present in the DOM. This helps to ensure that your tests are more reliable and less prone to failures due to timing issues or dynamic content on web pages.

**Page Object Model (POM)** is a design pattern in test automation that promotes maintainability and reusability by encapsulating the structure and behavior of web pages in separate classes. Each page class represents a specific web page and contains locators for web elements and methods to interact with those elements. This abstraction allows test scripts to be more readable and easier to maintain, as changes to the web page only require updates in the corresponding page class, rather than across all test scripts that interact with that page.
**Fluent Page Object Model (Fluent POM)** is an extension of the traditional Page Object Model design pattern in test automation. It emphasizes a more readable and expressive syntax by allowing method chaining, which enables test scripts to flow more naturally and closely resemble user interactions with the web application. In Fluent POM, methods in the page classes return the page object itself or another page object, allowing for a more fluid and intuitive way to write test scripts while still maintaining the benefits of encapsulation and separation of concerns provided by the traditional POM.
**Page Manager** is a design pattern in test automation that serves as a centralized factory for creating and managing instances of page objects. It provides a single point of access to all page objects, ensuring that they are instantiated correctly and efficiently. The Page Manager can handle the lifecycle of page objects, such as lazy initialization, caching, and cleanup, which helps to improve the maintainability and scalability of the test automation framework. By using a Page Manager, test scripts can easily obtain instances of page objects without worrying about their creation or dependencies, leading to cleaner and more organized code.
**Test Context** is a design pattern in test automation that provides a shared data structure for storing and managing information across different steps, hooks, and page objects during test execution. It allows for the storage of test-specific data, such as user credentials, test parameters, or any other relevant information that needs to be accessed throughout the test flow. The Test Context can be implemented as a simple key-value store or as a more complex object that encapsulates various aspects of the test state. By using a Test Context, test scripts can maintain state and share data seamlessly across different components of the test automation framework, improving readability and maintainability.
**Flow Layer** is a design pattern in test automation that focuses on abstracting the high-level business logic and user interactions from the underlying page objects and test scripts. It serves as an intermediary layer that orchestrates the sequence of actions required to perform specific user flows or scenarios. The Flow Layer encapsulates complex interactions, such as multi-step processes or workflows, into reusable methods that can be easily called from test scripts. This separation of concerns allows for cleaner and more maintainable code, as changes to the user flow can be made in one place without affecting the underlying page objects or test scripts. By using a Flow Layer, test automation frameworks can achieve better organization and scalability while maintaining readability and ease of maintenance.
**Dependency Injection (DI)** is a design pattern in software development that promotes loose coupling and separation of concerns by allowing the dependencies of a class to be injected from the outside rather than being created within the class itself. In test automation, DI can be used to manage the creation and lifecycle of objects such as page objects, test context, and other components. By using DI, test scripts can easily obtain instances of required dependencies without worrying about their instantiation or configuration, leading to cleaner and more maintainable code. DI can be implemented using various frameworks or libraries, such as Spring or Guice, or through manual injection techniques.
**Hooks** are special methods in test automation frameworks that are executed before or after certain events during the test execution lifecycle. They allow for setup and teardown activities, such as initializing the WebDriver, setting up test data, or cleaning up resources after tests have run. Hooks can be defined at different levels, such as before/after each scenario, before/after each step, or before/after the entire test suite. By using hooks, test automation frameworks can ensure that necessary setup and cleanup tasks are performed consistently across all tests, improving reliability and maintainability.
**Parallel execution** is a technique in test automation that allows multiple tests to run simultaneously, reducing the overall execution time and improving efficiency. This can be achieved by using a ThreadLocal WebDriver, which ensures that each test thread has its own instance of the WebDriver, preventing conflicts and ensuring thread safety. Parallel execution can be configured in test automation frameworks such as TestNG or JUnit, allowing for concurrent execution of test methods or classes. By leveraging parallel execution, test automation frameworks can significantly speed up the testing process while maintaining reliability and stability.


Framework-ul meu este construit pe Page Object Model, folosesc Page Manager pentru gestionarea instanțelor de pagini, 
Test Context pentru dependency sharing între step definitions, 
Driver Manager cu ThreadLocal pentru parallel execution, 
Hooks pentru setup și teardown, și explicit waits prin BasePage/WaitManager. 

Folosesc Fluent Page Object pentru chaining în interiorul step-urilor, 
iar pentru a gestiona starea între step-uri folosesc un TestContext care păstrează pagina curentă. 
Astfel evit dependența de PageManager pentru flow și modelez mai corect navigarea utilizatorului.



----- Dependency Injection (DI) with Cucumber and JUnit 5:
--- design pattern prin care dependintele unei clase sunt injectate din exterior, de obicei prin constructor, nu create în interiorul clasei
--- In framework-ul nostru folosim DI impreuna cu Cucumber pentru a partaja TestContext, PageManager si FlowClasses intre Step Definitions pentru a gestiona crearea și ciclul de viață al obiectelor precum page objects, test context, și alte componente
Cucumber -> Hooks -> TestContext -> PageManager -> Flows -> Steps -> Page Objects -> WebDriver


Driver → managed by DriverManager
Pages → managed by PageManager
State → managed by TestContext
Business flows → Flows
BDD mapping → Steps






PageManager - create/cache page instances
PageNavigator - expose getPage and transitionTo
TestContext - keep current page state
BasePage - depend on PageNavigator
Page methods - return destination page when navigation happens
That gives a clean separation and removes the fragile “don’t forget to update current page manually” problem.




DriverManager	driver lifecycle
TestContext	state + current page + PageManager
PageManager	page instances
BasePage	common Selenium methods
Pages	UI actions + getters
Flows	business flows
Assertions	validations
Steps	gherkin mapping
Hooks	setup/teardown