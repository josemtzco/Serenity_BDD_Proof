# Serenity BDD Testing Framework

A comprehensive framework template for Java-based test automation using Serenity BDD.

While **Serenity BDD with Java** offers many compelling features and is used in a few companies, 
 I've discovered some significant limitations that would make me hesitant to choose it for modern web applications.

**What Serenity BDD does well:**

- **Living Documentation**: Tests serve as executable specifications with rich HTML reports
- **Multiple Testing Patterns**: Support for Page Object, Step Libraries, and Screenplay patterns
- **BDD Integration**: Seamless Cucumber integration for stakeholder collaboration
- **Enterprise Ready**: Built-in reporting, parallel execution, and CI/CD integration
- **Long-term Support**: Mature framework with active community support

**However, the critical limitation I encountered:**

❌ **Shadow DOM Support**: No straightforward way to interact with Shadow DOM elements using Screenplay pattern and WebElementFacade without significant refactoring or method overloading  
❌ **Modern Web Components**: Limited support for modern web technologies that heavily rely on Shadow DOM  
❌ **Framework Rigidity**: Difficult to extend core functionality without breaking existing patterns  

**Despite these limitations, this project has been invaluable for:**

✅ **Expanding my automation toolkit** and understanding different testing philosophies  
✅ **Learning the Screenplay pattern** and its benefits for maintainable test code  
✅ **Exploring comprehensive reporting capabilities** that go beyond basic test results  
✅ **Understanding enterprise-grade test framework architecture** and configuration management  
✅ **Gaining new perspectives** on test organization and stakeholder communication  

While I wouldn't choose Serenity BDD for projects requiring extensive Shadow DOM interaction, this exploration has significantly broadened my understanding of test automation approaches and will inform my future framework decisions.

## 🎯 Motivation

As a **Software Engineer in Test**, I'm showcasing a comprehensive Serenity BDD implementation that demonstrates:
- **Modern testing patterns** and best practices
- **Real-world enterprise scenarios** with proper configuration management
- **Multiple testing approaches** from simple to advanced
- **Complete CI/CD integration** with Maven and reporting

This serves as both a **proof of concept** and a **reference implementation** for me to use in the future that i can work with serenity. 

## 🗂 What It Covers

### **Thread-Safe WebDriver Management**
- Uses Serenity's `@Managed` annotation for automatic WebDriver lifecycle management
- Configurable browser settings through `serenity.conf`
- Support for Chrome, Firefox, and headless execution
- Environment-specific configurations (dev, staging, production)

```java
@ExtendWith(SerenityJUnit5Extension.class)
public class BaseJUnitTest {
    @Managed(driver = "chrome", uniqueSession = true)
    WebDriver driver;
}
```

### **Multiple Testing Patterns**

#### **1. Simple Selenium Tests**
Direct WebDriver usage with Serenity reporting:
```java
@Test
void userCanLoginWithValidCredentials() {
    driver.get("https://example.com/login");
    driver.findElement(By.id("username")).sendKeys("testuser");
    driver.findElement(By.id("password")).sendKeys("password123");
    driver.findElement(By.id("login")).click();
    assertTrue(driver.getCurrentUrl().contains("dashboard"));
}
```

#### **2. Step-Based Testing**
Reusable step libraries with `@Step` annotations:
```java
public class LoginSteps {
    @Step("Navigate to login page")
    public void navigateToLoginPage() {
        getDriver().get("https://example.com/login");
    }
    
    @Step("Enter username: {0}")
    public void enterUsername(String username) {
        // Implementation with enhanced reporting
    }
}
```

#### **3. Screenplay Pattern (Advanced)**
Actor-centric approach for complex scenarios:
```java
public class LoginTask implements Task {
    public static LoginTask withCredentials(String username, String password) {
        return instrumented(LoginTask.class, username, password);
    }
    
    @Override
    @Step("{0} logs in with username: {1}")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Open.url("https://example.com/login"),
            Enter.theValue(username).into(USERNAME_FIELD),
            Click.on(LOGIN_BUTTON)
        );
    }
}
```

### **BDD Integration with Cucumber**
```gherkin
Feature: Dungeon Finder Queue Tests
  Scenario: Actor can queue for a dungeon
    Given Actor logged in with admin account
    When Actor clicks on the first dungeon available
    And Actor fills their name
    Then Actor should see the provided name in the queued players
```

### **Comprehensive Configuration Management**
Environment-specific settings in `serenity.conf`:
```hocon
webdriver {
    driver = chrome
    autodownload = true
}

environments {
    default {
        webdriver.base.url = "http://127.0.0.1:5500"
    }
    staging {
        webdriver.base.url = "https://staging.example.com"
    }
}
```

### **Parallel Execution Support**
Pre-configured Maven Surefire for parallel test execution:
```xml
<configuration>
    <parallel>methods</parallel>
    <threadCount>4</threadCount>
    <perCoreThreadCount>true</perCoreThreadCount>
</configuration>
```

### **API Testing Integration**
Built-in REST API testing capabilities:
```java
actor.attemptsTo(
    Get.resource("/api/users/{id}").with(request -> request.pathParam("id", userId)),
    Ensure.that(LastResponse.received(), response -> response.statusCode(200))
);
```

### **Maven Integration**
Complete Maven lifecycle integration:
```bash
# Run all tests with reports
mvn clean verify

# Run specific environment
mvn test -Denvironment=staging

# Parallel execution
mvn test -Dparallel.tests=true
```

## 🚀 Getting Started

### **Prerequisites**
- Java 11 or higher
- Maven 3.6+
- Chrome/Firefox browser

## 🔧 Configuration Options

### **Browser Configuration**
```hocon
webdriver {
    driver = chrome
    chrome.switches = "--start-maximized;--incognito;--disable-gpu"
}
```

## 📚 Documentation

For comprehensive documentation and advanced usage patterns, see:
- [Serenity BDD Complete Guide](Serenity_BDD_Complete_Guide.md) - Detailed implementation guide
- [Official Serenity Documentation](https://serenity-bdd.info/)
- [Screenplay Pattern Guide](https://serenity-bdd.info/docs/screenplay/screenplay_fundamentals)


**Created by Jose Martinez**  
*SDET | Automation Advocate*

Feel free to open an issue or ping me for feedback! 🚀 