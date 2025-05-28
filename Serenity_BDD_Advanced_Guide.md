# Serenity BDD Advanced Guide: Complex Scenarios and Real-World Solutions

## Table of Contents
1. [withAction API](#withaction-api)
2. [Handling Frames, Windows, Tabs and Pop-ups](#frames-windows)
3. [File Upload Scenarios](#file-upload)
4. [Web Tables and Dynamic Web Tables](#web-tables)
5. [Advanced WebDriver Interactions](#webdriver-interactions)
6. [REST API Testing with Serenity](#rest-api)

---

## 1. withAction API {#withaction-api}

### Theory
The withAction API in Serenity provides a fluent interface for performing complex WebDriver actions like drag-and-drop, hover, right-click, and keyboard combinations.

### Common Interview Questions
**Q: What is the withAction API in Serenity BDD?**
**A:** It's a fluent API that wraps WebDriver's Actions class, providing readable and maintainable code for complex user interactions.

**Q: When would you use withAction instead of regular click methods?**
**A:** For complex interactions like drag-and-drop, hover effects, right-click context menus, or keyboard shortcuts.

### Practical Implementation

#### Actions Page Object
```java
package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

public class ActionsPage extends PageObject {
    
    @FindBy(id = "draggable")
    private WebElementFacade draggableElement;
    
    @FindBy(id = "droppable")
    private WebElementFacade droppableElement;
    
    @FindBy(id = "hover-element")
    private WebElementFacade hoverElement;
    
    @FindBy(id = "context-menu")
    private WebElementFacade contextMenuElement;
    
    @FindBy(id = "text-input")
    private WebElementFacade textInput;
    
    // Drag and Drop action
    public void dragAndDropElement() {
        withAction()
            .dragAndDrop(draggableElement, droppableElement)
            .perform();
    }
    
    // Hover action
    public void hoverOverElement() {
        withAction()
            .moveToElement(hoverElement)
            .perform();
    }
    
    // Right-click context menu
    public void rightClickElement() {
        withAction()
            .contextClick(contextMenuElement)
            .perform();
    }
    
    // Keyboard shortcuts
    public void selectAllText() {
        textInput.click();
        withAction()
            .keyDown(Keys.CONTROL)
            .sendKeys("a")
            .keyUp(Keys.CONTROL)
            .perform();
    }
    
    // Copy and paste action
    public void copyAndPasteText() {
        withAction()
            .keyDown(Keys.CONTROL)
            .sendKeys("c")
            .keyUp(Keys.CONTROL)
            .pause(500)
            .keyDown(Keys.CONTROL)
            .sendKeys("v")
            .keyUp(Keys.CONTROL)
            .perform();
    }
    
    // Double-click action
    public void doubleClickElement() {
        withAction()
            .doubleClick(textInput)
            .perform();
    }
    
    // Complex chain of actions
    public void performComplexActions() {
        withAction()
            .moveToElement(hoverElement)
            .pause(1000)
            .click()
            .keyDown(Keys.SHIFT)
            .sendKeys("HELLO")
            .keyUp(Keys.SHIFT)
            .perform();
    }
}
```

#### Actions Step Library
```java
package steps;

import net.serenitybdd.annotations.Step;
import pages.ActionsPage;

public class ActionsSteps {
    
    private ActionsPage actionsPage;
    
    @Step("Perform drag and drop operation")
    public void performDragAndDrop() {
        actionsPage.dragAndDropElement();
    }
    
    @Step("Hover over element to reveal tooltip")
    public void hoverOverElement() {
        actionsPage.hoverOverElement();
    }
    
    @Step("Right-click to open context menu")
    public void rightClickForContextMenu() {
        actionsPage.rightClickElement();
    }
    
    @Step("Select all text using keyboard shortcut")
    public void selectAllTextWithKeyboard() {
        actionsPage.selectAllText();
    }
    
    @Step("Copy and paste text using keyboard shortcuts")
    public void copyAndPasteText() {
        actionsPage.copyAndPasteText();
    }
    
    @Step("Double-click to select text")
    public void doubleClickToSelect() {
        actionsPage.doubleClickElement();
    }
    
    @Step("Perform complex chain of actions")
    public void performComplexActionChain() {
        actionsPage.performComplexActions();
    }
}
```

### Pain Points and Solutions
**Pain Point:** Actions not executing as expected
**Solution:** Always call `.perform()` at the end of action chains and add pauses between actions if needed

---

## 2. Handling Frames, Windows, Tabs and Pop-ups {#frames-windows}

### Theory
Modern web applications often use iframes, open new windows/tabs, or show pop-ups. Serenity provides methods to handle these scenarios effectively.

### Common Interview Questions
**Q: How do you handle multiple windows in Serenity BDD?**
**A:** Use `getDriver().switchTo().window(windowHandle)` or Serenity's window management methods to switch between windows.

**Q: What's the difference between frames and windows?**
**A:** Frames are embedded within the same page, while windows are separate browser instances.

### Practical Implementation

#### Window and Frame Handler Page
```java
package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;
import java.util.Set;

public class WindowFramePage extends PageObject {
    
    @FindBy(id = "open-window-btn")
    private WebElementFacade openWindowButton;
    
    @FindBy(id = "open-tab-btn")
    private WebElementFacade openTabButton;
    
    @FindBy(css = "iframe#test-frame")
    private WebElementFacade testFrame;
    
    @FindBy(id = "frame-input")
    private WebElementFacade frameInput;
    
    private String originalWindow;
    
    // Store original window handle
    public void storeOriginalWindow() {
        originalWindow = getDriver().getWindowHandle();
    }
    
    // Open new window
    public void openNewWindow() {
        openWindowButton.click();
    }
    
    // Open new tab
    public void openNewTab() {
        openTabButton.click();
    }
    
    // Switch to new window/tab
    public void switchToNewWindow() {
        Set<String> allWindows = getDriver().getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                getDriver().switchTo().window(window);
                break;
            }
        }
    }
    
    // Switch back to original window
    public void switchToOriginalWindow() {
        getDriver().switchTo().window(originalWindow);
    }
    
    // Close current window and switch back
    public void closeCurrentWindowAndSwitchBack() {
        getDriver().close();
        switchToOriginalWindow();
    }
    
    // Switch to frame
    public void switchToFrame() {
        getDriver().switchTo().frame(testFrame);
    }
    
    // Switch back to main content
    public void switchToMainContent() {
        getDriver().switchTo().defaultContent();
    }
    
    // Interact with element inside frame
    public void enterTextInFrame(String text) {
        switchToFrame();
        frameInput.type(text);
        switchToMainContent();
    }
    
    // Get all window handles
    public Set<String> getAllWindowHandles() {
        return getDriver().getWindowHandles();
    }
    
    // Switch to window by title
    public void switchToWindowByTitle(String title) {
        Set<String> allWindows = getDriver().getWindowHandles();
        for (String window : allWindows) {
            getDriver().switchTo().window(window);
            if (getDriver().getTitle().contains(title)) {
                break;
            }
        }
    }
}
```

#### Window Management Steps
```java
package steps;

import net.serenitybdd.annotations.Step;
import pages.WindowFramePage;
import static org.assertj.core.api.Assertions.assertThat;

public class WindowFrameSteps {
    
    private WindowFramePage windowFramePage;
    
    @Step("Store the original window handle")
    public void storeOriginalWindow() {
        windowFramePage.storeOriginalWindow();
    }
    
    @Step("Open a new window")
    public void openNewWindow() {
        windowFramePage.openNewWindow();
    }
    
    @Step("Open a new tab")
    public void openNewTab() {
        windowFramePage.openNewTab();
    }
    
    @Step("Switch to the newly opened window")
    public void switchToNewWindow() {
        windowFramePage.switchToNewWindow();
    }
    
    @Step("Switch back to the original window")
    public void switchToOriginalWindow() {
        windowFramePage.switchToOriginalWindow();
    }
    
    @Step("Close current window and return to original")
    public void closeCurrentWindowAndReturn() {
        windowFramePage.closeCurrentWindowAndSwitchBack();
    }
    
    @Step("Enter text '{0}' in frame input field")
    public void enterTextInFrame(String text) {
        windowFramePage.enterTextInFrame(text);
    }
    
    @Step("Verify multiple windows are open")
    public void verifyMultipleWindowsOpen() {
        assertThat(windowFramePage.getAllWindowHandles().size())
            .isGreaterThan(1);
    }
    
    @Step("Switch to window with title containing '{0}'")
    public void switchToWindowByTitle(String title) {
        windowFramePage.switchToWindowByTitle(title);
    }
}
```

### Pain Points and Solutions
**Pain Point:** Losing track of window handles
**Solution:** Always store the original window handle before opening new windows

---

## 3. File Upload Scenarios {#file-upload}

### Theory
File uploads in web applications can be handled through standard input elements or drag-and-drop interfaces. Serenity provides methods for both scenarios.

### Common Interview Questions
**Q: How do you handle file uploads in Serenity BDD?**
**A:** Use `sendKeys()` for input elements or Robot class for drag-and-drop uploads.

### Practical Implementation

#### File Upload Page Object
```java
package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;
import java.io.File;
import java.nio.file.Paths;

public class FileUploadPage extends PageObject {
    
    @FindBy(css = "input[type='file']")
    private WebElementFacade fileInput;
    
    @FindBy(id = "upload-btn")
    private WebElementFacade uploadButton;
    
    @FindBy(id = "upload-status")
    private WebElementFacade uploadStatus;
    
    @FindBy(css = ".drag-drop-area")
    private WebElementFacade dragDropArea;
    
    @FindBy(css = ".uploaded-file-name")
    private WebElementFacade uploadedFileName;
    
    // Upload file using input element
    public void uploadFile(String fileName) {
        String filePath = getTestResourcePath(fileName);
        fileInput.sendKeys(filePath);
    }
    
    // Click upload button
    public void clickUploadButton() {
        uploadButton.click();
    }
    
    // Get upload status message
    public String getUploadStatus() {
        return uploadStatus.getText();
    }
    
    // Get uploaded file name
    public String getUploadedFileName() {
        return uploadedFileName.getText();
    }
    
    // Helper method to get test resource path
    private String getTestResourcePath(String fileName) {
        return Paths.get("src/test/resources/testfiles", fileName)
                   .toAbsolutePath()
                   .toString();
    }
    
    // Upload multiple files
    public void uploadMultipleFiles(String... fileNames) {
        StringBuilder allPaths = new StringBuilder();
        for (int i = 0; i < fileNames.length; i++) {
            allPaths.append(getTestResourcePath(fileNames[i]));
            if (i < fileNames.length - 1) {
                allPaths.append("\n");
            }
        }
        fileInput.sendKeys(allPaths.toString());
    }
    
    // Verify file input is present
    public boolean isFileInputPresent() {
        return fileInput.isPresent();
    }
    
    // Clear file input
    public void clearFileInput() {
        fileInput.clear();
    }
}
```

#### File Upload Steps
```java
package steps;

import net.serenitybdd.annotations.Step;
import pages.FileUploadPage;
import static org.assertj.core.api.Assertions.assertThat;

public class FileUploadSteps {
    
    private FileUploadPage fileUploadPage;
    
    @Step("Navigate to file upload page")
    public void navigateToFileUploadPage() {
        fileUploadPage.open();
    }
    
    @Step("Upload file '{0}'")
    public void uploadFile(String fileName) {
        fileUploadPage.uploadFile(fileName);
    }
    
    @Step("Click the upload button")
    public void clickUploadButton() {
        fileUploadPage.clickUploadButton();
    }
    
    @Step("Verify file upload was successful")
    public void verifyFileUploadSuccess() {
        String status = fileUploadPage.getUploadStatus();
        assertThat(status).containsIgnoringCase("success");
    }
    
    @Step("Verify uploaded file name is '{0}'")
    public void verifyUploadedFileName(String expectedFileName) {
        String actualFileName = fileUploadPage.getUploadedFileName();
        assertThat(actualFileName).isEqualTo(expectedFileName);
    }
    
    @Step("Upload multiple files")
    public void uploadMultipleFiles(String... fileNames) {
        fileUploadPage.uploadMultipleFiles(fileNames);
    }
    
    @Step("Verify file input is available")
    public void verifyFileInputIsAvailable() {
        assertThat(fileUploadPage.isFileInputPresent()).isTrue();
    }
}
```

#### Test Implementation
```java
package tests;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import steps.FileUploadSteps;

@ExtendWith(SerenityJUnit5Extension.class)
public class FileUploadTest {
    
    @Managed
    WebDriver driver;
    
    @Steps
    FileUploadSteps fileUploadSteps;
    
    @Test
    public void shouldUploadSingleFile() {
        fileUploadSteps.navigateToFileUploadPage();
        fileUploadSteps.verifyFileInputIsAvailable();
        fileUploadSteps.uploadFile("test-image.jpg");
        fileUploadSteps.clickUploadButton();
        fileUploadSteps.verifyFileUploadSuccess();
        fileUploadSteps.verifyUploadedFileName("test-image.jpg");
    }
    
    @Test
    public void shouldUploadMultipleFiles() {
        fileUploadSteps.navigateToFileUploadPage();
        fileUploadSteps.uploadMultipleFiles("test-image.jpg", "test-document.pdf");
        fileUploadSteps.clickUploadButton();
        fileUploadSteps.verifyFileUploadSuccess();
    }
}
```

### Pain Points and Solutions
**Pain Point:** File path issues across different operating systems
**Solution:** Use `Paths.get()` and `toAbsolutePath()` for cross-platform compatibility

---

## 4. Web Tables and Dynamic Web Tables {#web-tables}

### Theory
Web tables are common in enterprise applications. Dynamic tables have content that changes based on user actions, pagination, or real-time data updates.

### Common Interview Questions
**Q: How do you handle dynamic web tables in Serenity BDD?**
**A:** Use flexible locators, wait for elements, and create reusable methods to interact with table rows and columns.

**Q: What's the difference between static and dynamic tables?**
**A:** Static tables have fixed content, while dynamic tables change content based on user interactions or data updates.

### Practical Implementation

#### Web Table Page Object
```java
package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import java.util.ArrayList;

public class WebTablePage extends PageObject {
    
    @FindBy(css = "table#data-table")
    private WebElementFacade dataTable;
    
    @FindBy(css = "table#data-table tbody tr")
    private List<WebElementFacade> tableRows;
    
    @FindBy(css = "table#data-table thead th")
    private List<WebElementFacade> tableHeaders;
    
    @FindBy(css = ".pagination .next")
    private WebElementFacade nextPageButton;
    
    @FindBy(css = ".pagination .previous")
    private WebElementFacade previousPageButton;
    
    @FindBy(css = "input#search-table")
    private WebElementFacade searchInput;
    
    @FindBy(css = "select#rows-per-page")
    private WebElementFacade rowsPerPageSelect;
    
    // Get all table data as list of maps
    public List<List<String>> getAllTableData() {
        List<List<String>> tableData = new ArrayList<>();
        
        for (WebElementFacade row : tableRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            List<String> rowData = new ArrayList<>();
            
            for (WebElement cell : cells) {
                rowData.add(cell.getText());
            }
            tableData.add(rowData);
        }
        return tableData;
    }
    
    // Get specific cell value by row and column index
    public String getCellValue(int rowIndex, int columnIndex) {
        WebElementFacade row = tableRows.get(rowIndex);
        List<WebElement> cells = row.findElements(By.tagName("td"));
        return cells.get(columnIndex).getText();
    }
    
    // Get cell value by row index and column header name
    public String getCellValueByHeader(int rowIndex, String headerName) {
        int columnIndex = getColumnIndexByHeader(headerName);
        return getCellValue(rowIndex, columnIndex);
    }
    
    // Get column index by header name
    private int getColumnIndexByHeader(String headerName) {
        for (int i = 0; i < tableHeaders.size(); i++) {
            if (tableHeaders.get(i).getText().equals(headerName)) {
                return i;
            }
        }
        throw new RuntimeException("Header '" + headerName + "' not found");
    }
    
    // Find row by cell value
    public int findRowByCellValue(String cellValue) {
        for (int i = 0; i < tableRows.size(); i++) {
            List<WebElement> cells = tableRows.get(i).findElements(By.tagName("td"));
            for (WebElement cell : cells) {
                if (cell.getText().equals(cellValue)) {
                    return i;
                }
            }
        }
        return -1; // Not found
    }
    
    // Click on a specific cell
    public void clickCell(int rowIndex, int columnIndex) {
        WebElementFacade row = tableRows.get(rowIndex);
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(columnIndex).click();
    }
    
    // Click on action button in a row (edit, delete, etc.)
    public void clickActionButton(int rowIndex, String actionType) {
        WebElementFacade row = tableRows.get(rowIndex);
        WebElement actionButton = row.findElement(
            By.cssSelector("button[data-action='" + actionType + "']")
        );
        actionButton.click();
    }
    
    // Search in table
    public void searchInTable(String searchTerm) {
        searchInput.clear();
        searchInput.type(searchTerm);
        waitABit(1000); // Wait for search results
    }
    
    // Navigate to next page
    public void goToNextPage() {
        if (nextPageButton.isEnabled()) {
            nextPageButton.click();
            waitForTableToLoad();
        }
    }
    
    // Navigate to previous page
    public void goToPreviousPage() {
        if (previousPageButton.isEnabled()) {
            previousPageButton.click();
            waitForTableToLoad();
        }
    }
    
    // Change rows per page
    public void changeRowsPerPage(String rowCount) {
        rowsPerPageSelect.selectByValue(rowCount);
        waitForTableToLoad();
    }
    
    // Wait for table to load after dynamic operations
    private void waitForTableToLoad() {
        waitFor(dataTable).waitUntilVisible();
        waitABit(500); // Additional wait for content to stabilize
    }
    
    // Get total number of rows
    public int getTotalRows() {
        return tableRows.size();
    }
    
    // Check if table is empty
    public boolean isTableEmpty() {
        return tableRows.isEmpty();
    }
    
    // Sort table by column header
    public void sortByColumn(String headerName) {
        for (WebElementFacade header : tableHeaders) {
            if (header.getText().equals(headerName)) {
                header.click();
                waitForTableToLoad();
                break;
            }
        }
    }
}
```

#### Web Table Steps
```java
package steps;

import net.serenitybdd.annotations.Step;
import pages.WebTablePage;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

public class WebTableSteps {
    
    private WebTablePage webTablePage;
    
    @Step("Navigate to web table page")
    public void navigateToWebTablePage() {
        webTablePage.open();
    }
    
    @Step("Verify table contains {0} rows")
    public void verifyTableRowCount(int expectedRows) {
        assertThat(webTablePage.getTotalRows()).isEqualTo(expectedRows);
    }
    
    @Step("Verify cell value at row {0}, column {1} is '{2}'")
    public void verifyCellValue(int row, int column, String expectedValue) {
        String actualValue = webTablePage.getCellValue(row, column);
        assertThat(actualValue).isEqualTo(expectedValue);
    }
    
    @Step("Search for '{0}' in the table")
    public void searchInTable(String searchTerm) {
        webTablePage.searchInTable(searchTerm);
    }
    
    @Step("Click edit button for row containing '{0}'")
    public void clickEditButtonForRow(String cellValue) {
        int rowIndex = webTablePage.findRowByCellValue(cellValue);
        assertThat(rowIndex).isGreaterThanOrEqualTo(0);
        webTablePage.clickActionButton(rowIndex, "edit");
    }
    
    @Step("Navigate to next page of results")
    public void goToNextPage() {
        webTablePage.goToNextPage();
    }
    
    @Step("Sort table by column '{0}'")
    public void sortTableByColumn(String columnName) {
        webTablePage.sortByColumn(columnName);
    }
    
    @Step("Change table to show {0} rows per page")
    public void changeRowsPerPage(String rowCount) {
        webTablePage.changeRowsPerPage(rowCount);
    }
    
    @Step("Verify table is not empty")
    public void verifyTableIsNotEmpty() {
        assertThat(webTablePage.isTableEmpty()).isFalse();
    }
    
    @Step("Get value from column '{0}' in row {1}")
    public String getValueFromColumnInRow(String columnName, int rowIndex) {
        return webTablePage.getCellValueByHeader(rowIndex, columnName);
    }
}
```

### Pain Points and Solutions
**Pain Point:** Finding the correct element in a large table
**Solution:** Use `findAll()` to find all rows and then iterate through them

---

## 5. Advanced WebDriver Interactions {#webdriver-interactions}

### Theory
Advanced WebDriver interactions include handling alerts, working with cookies, managing browser settings, and executing JavaScript.

### Common Interview Questions
**Q: How do you handle JavaScript alerts in Serenity BDD?**
**A:** Use `getDriver().switchTo().alert()` to interact with alerts, or Serenity's alert handling methods.

**Q: When would you need to execute JavaScript in tests?**
**A:** For actions that WebDriver cannot perform directly, like scrolling to elements, triggering events, or accessing browser APIs.

### Practical Implementation

#### Advanced WebDriver Page Object
```java
package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;
import java.util.Set;

public class AdvancedInteractionsPage extends PageObject {
    
    @FindBy(id = "alert-btn")
    private WebElementFacade alertButton;
    
    @FindBy(id = "confirm-btn")
    private WebElementFacade confirmButton;
    
    @FindBy(id = "prompt-btn")
    private WebElementFacade promptButton;
    
    @FindBy(id = "scroll-target")
    private WebElementFacade scrollTarget;
    
    @FindBy(id = "hidden-element")
    private WebElementFacade hiddenElement;
    
    // Alert handling methods
    public void clickAlertButton() {
        alertButton.click();
    }
    
    public String getAlertText() {
        Alert alert = getDriver().switchTo().alert();
        return alert.getText();
    }
    
    public void acceptAlert() {
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }
    
    public void dismissAlert() {
        Alert alert = getDriver().switchTo().alert();
        alert.dismiss();
    }
    
    public void enterTextInPrompt(String text) {
        Alert alert = getDriver().switchTo().alert();
        alert.sendKeys(text);
        alert.accept();
    }
    
    // JavaScript execution methods
    public void scrollToElement(WebElementFacade element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    public void clickElementWithJS(WebElementFacade element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", element);
    }
    
    public void highlightElement(WebElementFacade element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(
            "arguments[0].style.border='3px solid red';", element
        );
    }
    
    public Object executeJavaScript(String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return js.executeScript(script, args);
    }
    
    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
    
    public void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, 0);");
    }
    
    // Cookie management methods
    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        getDriver().manage().addCookie(cookie);
    }
    
    public String getCookieValue(String cookieName) {
        Cookie cookie = getDriver().manage().getCookieNamed(cookieName);
        return cookie != null ? cookie.getValue() : null;
    }
    
    public void deleteCookie(String cookieName) {
        getDriver().manage().deleteCookieNamed(cookieName);
    }
    
    public void deleteAllCookies() {
        getDriver().manage().deleteAllCookies();
    }
    
    public Set<Cookie> getAllCookies() {
        return getDriver().manage().getCookies();
    }
    
    // Browser management methods
    public void maximizeWindow() {
        getDriver().manage().window().maximize();
    }
    
    public void setWindowSize(int width, int height) {
        getDriver().manage().window().setSize(
            new org.openqa.selenium.Dimension(width, height)
        );
    }
    
    public void refreshPage() {
        getDriver().navigate().refresh();
    }
    
    public void navigateBack() {
        getDriver().navigate().back();
    }
    
    public void navigateForward() {
        getDriver().navigate().forward();
    }
    
    // Wait and timing methods
    public void waitForPageLoad() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        waitUntil(driver -> js.executeScript("return document.readyState").equals("complete"));
    }
    
    public void waitForAjaxToComplete() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        waitUntil(driver -> 
            (Boolean) js.executeScript("return jQuery.active == 0")
        );
    }
    
    // Element state methods
    public boolean isElementInViewport(WebElementFacade element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (Boolean) js.executeScript(
            "var rect = arguments[0].getBoundingClientRect();" +
            "return (rect.top >= 0 && rect.left >= 0 && " +
            "rect.bottom <= window.innerHeight && " +
            "rect.right <= window.innerWidth);", element
        );
    }
    
    public void makeElementVisible(WebElementFacade element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].style.display = 'block';", element);
    }
}
```

#### Advanced Interactions Steps
```java
package steps;

import net.serenitybdd.annotations.Step;
import pages.AdvancedInteractionsPage;
import static org.assertj.core.api.Assertions.assertThat;

public class AdvancedInteractionsSteps {
    
    private AdvancedInteractionsPage advancedPage;
    
    @Step("Handle JavaScript alert and verify message")
    public void handleAlertAndVerifyMessage(String expectedMessage) {
        advancedPage.clickAlertButton();
        String alertText = advancedPage.getAlertText();
        assertThat(alertText).isEqualTo(expectedMessage);
        advancedPage.acceptAlert();
    }
    
    @Step("Enter '{0}' in JavaScript prompt")
    public void enterTextInPrompt(String text) {
        advancedPage.promptButton.click();
        advancedPage.enterTextInPrompt(text);
    }
    
    @Step("Scroll to element and verify it's in viewport")
    public void scrollToElementAndVerify() {
        advancedPage.scrollToElement(advancedPage.scrollTarget);
        assertThat(advancedPage.isElementInViewport(advancedPage.scrollTarget))
            .isTrue();
    }
    
    @Step("Add cookie '{0}' with value '{1}'")
    public void addCookie(String name, String value) {
        advancedPage.addCookie(name, value);
    }
    
    @Step("Verify cookie '{0}' has value '{1}'")
    public void verifyCookieValue(String cookieName, String expectedValue) {
        String actualValue = advancedPage.getCookieValue(cookieName);
        assertThat(actualValue).isEqualTo(expectedValue);
    }
    
    @Step("Execute JavaScript to get page title")
    public String getPageTitleWithJS() {
        return (String) advancedPage.executeJavaScript("return document.title;");
    }
    
    @Step("Wait for page to fully load")
    public void waitForPageToLoad() {
        advancedPage.waitForPageLoad();
    }
    
    @Step("Make hidden element visible")
    public void makeHiddenElementVisible() {
        advancedPage.makeElementVisible(advancedPage.hiddenElement);
    }
}
```

---

## 6. REST API Testing with Serenity {#rest-api}

### Theory
Serenity BDD provides excellent support for REST API testing through integration with REST Assured and the Screenplay pattern.

### Common Interview Questions
**Q: How does Serenity BDD handle REST API testing?**
**A:** Through serenity-rest-assured integration and screenplay pattern with dedicated API interactions.

**Q: What are the advantages of using Serenity for API testing?**
**A:** Rich reporting, request/response logging, easy assertions, and integration with UI tests.

### Practical Implementation

#### API Test Base Class
```java
package api.base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeEach;

public class APITestBase {
    
    protected RequestSpecification requestSpec;
    
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        requestSpec = SerenityRest.given()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json");
    }
}
```

#### API Steps Library
```java
package steps.api;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class APISteps {
    
    private Response response;
    
    @Step("Send GET request to endpoint '{0}'")
    public void sendGetRequest(String endpoint) {
        response = SerenityRest.given()
            .when()
            .get(endpoint);
    }
    
    @Step("Send POST request to '{0}' with body: {1}")
    public void sendPostRequest(String endpoint, String requestBody) {
        response = SerenityRest.given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .when()
            .post(endpoint);
    }
    
    @Step("Send PUT request to '{0}' with body: {1}")
    public void sendPutRequest(String endpoint, String requestBody) {
        response = SerenityRest.given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .when()
            .put(endpoint);
    }
    
    @Step("Send DELETE request to endpoint '{0}'")
    public void sendDeleteRequest(String endpoint) {
        response = SerenityRest.given()
            .when()
            .delete(endpoint);
    }
    
    @Step("Verify response status code is {0}")
    public void verifyStatusCode(int expectedStatusCode) {
        assertThat(response.getStatusCode()).isEqualTo(expectedStatusCode);
    }
    
    @Step("Verify response contains field '{0}' with value '{1}'")
    public void verifyResponseField(String fieldPath, String expectedValue) {
        response.then()
            .body(fieldPath, equalTo(expectedValue));
    }
    
    @Step("Verify response contains field '{0}'")
    public void verifyResponseContainsField(String fieldPath) {
        response.then()
            .body(fieldPath, notNullValue());
    }
    
    @Step("Verify response time is less than {0} milliseconds")
    public void verifyResponseTime(long maxResponseTime) {
        assertThat(response.getTime()).isLessThan(maxResponseTime);
    }
    
    @Step("Verify response header '{0}' contains '{1}'")
    public void verifyResponseHeader(String headerName, String expectedValue) {
        String actualHeaderValue = response.getHeader(headerName);
        assertThat(actualHeaderValue).contains(expectedValue);
    }
    
    @Step("Extract value from response field '{0}'")
    public String extractValueFromResponse(String fieldPath) {
        return response.jsonPath().getString(fieldPath);
    }
    
    @Step("Verify response array size is {0}")
    public void verifyResponseArraySize(String arrayPath, int expectedSize) {
        response.then()
            .body(arrayPath + ".size()", equalTo(expectedSize));
    }
    
    @Step("Verify response schema matches expected schema")
    public void verifyResponseSchema(String schemaPath) {
        response.then()
            .assertThat()
            .body(matchesJsonSchemaInClasspath(schemaPath));
    }
}
```

#### API Test Implementation
```java
package tests.api;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.annotations.Steps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import steps.api.APISteps;

@ExtendWith(SerenityJUnit5Extension.class)
public class APITest {
    
    @Steps
    APISteps apiSteps;
    
    @Test
    public void shouldGetAllPosts() {
        apiSteps.sendGetRequest("/posts");
        apiSteps.verifyStatusCode(200);
        apiSteps.verifyResponseTime(2000);
        apiSteps.verifyResponseArraySize("", 100);
    }
    
    @Test
    public void shouldGetSpecificPost() {
        apiSteps.sendGetRequest("/posts/1");
        apiSteps.verifyStatusCode(200);
        apiSteps.verifyResponseField("id", "1");
        apiSteps.verifyResponseContainsField("title");
        apiSteps.verifyResponseContainsField("body");
        apiSteps.verifyResponseContainsField("userId");
    }
    
    @Test
    public void shouldCreateNewPost() {
        String requestBody = """
            {
                "title": "Test Post",
                "body": "This is a test post",
                "userId": 1
            }
            """;
        
        apiSteps.sendPostRequest("/posts", requestBody);
        apiSteps.verifyStatusCode(201);
        apiSteps.verifyResponseField("title", "Test Post");
        apiSteps.verifyResponseField("body", "This is a test post");
        apiSteps.verifyResponseField("userId", "1");
    }
    
    @Test
    public void shouldUpdatePost() {
        String requestBody = """
            {
                "id": 1,
                "title": "Updated Post",
                "body": "This is an updated post",
                "userId": 1
            }
            """;
        
        apiSteps.sendPutRequest("/posts/1", requestBody);
        apiSteps.verifyStatusCode(200);
        apiSteps.verifyResponseField("title", "Updated Post");
    }
    
    @Test
    public void shouldDeletePost() {
        apiSteps.sendDeleteRequest("/posts/1");
        apiSteps.verifyStatusCode(200);
    }
}
```

#### Screenplay Pattern for API Testing
```java
package screenplay.api;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;

public class APIInteractions {
    
    public static Interaction getPost(int postId) {
        return Get.resource("/posts/" + postId);
    }
    
    public static Interaction createPost(String title, String body, int userId) {
        return Post.to("/posts")
            .with(request -> request
                .header("Content-Type", "application/json")
                .body(String.format("""
                    {
                        "title": "%s",
                        "body": "%s",
                        "userId": %d
                    }
                    """, title, body, userId))
            );
    }
    
    public static ResponseConsequence<Integer> statusCode() {
        return ResponseConsequence.seeThatResponse("status code", 
            response -> response.statusCode());
    }
    
    public static ResponseConsequence<String> fieldValue(String fieldPath) {
        return ResponseConsequence.seeThatResponse("field " + fieldPath,
            response -> response.jsonPath().getString(fieldPath));
    }
}
```

#### Screenplay API Test
```java
package tests.screenplay;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import screenplay.api.APIInteractions;
import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SerenityJUnit5Extension.class)
public class ScreenplayAPITest {
    
    private Actor apiUser = Actor.named("API User")
        .whoCan(CallAnApi.at("https://jsonplaceholder.typicode.com"));
    
    @Test
    public void shouldGetPostUsingScreenplay() {
        givenThat(apiUser).wasAbleTo(APIInteractions.getPost(1));
        
        then(apiUser).should(
            seeThat(APIInteractions.statusCode(), equalTo(200)),
            seeThat(APIInteractions.fieldValue("id"), equalTo("1"))
        );
    }
    
    @Test
    public void shouldCreatePostUsingScreenplay() {
        when(apiUser).attemptsTo(
            APIInteractions.createPost("Test Title", "Test Body", 1)
        );
        
        then(apiUser).should(
            seeThat(APIInteractions.statusCode(), equalTo(201)),
            seeThat(APIInteractions.fieldValue("title"), equalTo("Test Title"))
        );
    }
}
```

### Pain Points and Solutions
**Pain Point:** Managing different environments and base URLs
**Solution:** Use configuration files and environment-specific properties

**Pain Point:** Complex response validation
**Solution:** Use JSON Schema validation and custom matchers

---

## Conclusion

This advanced guide covers complex scenarios you'll encounter in real-world Serenity BDD projects. Each section provides both theoretical knowledge and practical implementations that you can adapt to your specific needs.

### Key Takeaways
1. **withAction API**: Leverage for complex user interactions
2. **Window/Frame Management**: Always track window handles and switch contexts properly
3. **File Uploads**: Use absolute paths and handle different upload mechanisms
4. **Web Tables**: Create flexible, reusable methods for dynamic content
5. **Advanced WebDriver**: Combine JavaScript execution with WebDriver for complex scenarios
6. **REST API**: Use both traditional and Screenplay patterns for comprehensive API testing

### Best Practices
- Always use explicit waits for dynamic content
- Create reusable page objects and step libraries
- Implement proper error handling and meaningful assertions
- Use the Screenplay pattern for complex test scenarios
- Maintain clear separation between test logic and implementation details
```