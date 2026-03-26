package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    private final By formLoader = By.xpath("//div[contains(@class,'oxd-form-loader')]");
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final WebDriverWait shortWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected List<WebElement> visibleAll(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void click(By locator) {
        waitForLoadersToDisappear();
        WebElement element = clickable(locator);
        scrollIntoView(element);
        element.click();
    }

    protected void jsClick(By locator) {
        waitForLoadersToDisappear();
        WebElement element = visible(locator);
        scrollIntoView(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void type(By locator, String text) {
        visible(locator).sendKeys(text);
    }

    protected void clearAndType(By locator, String text) {
        waitForLoadersToDisappear();
        WebElement element = visible(locator);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        element.sendKeys(text);
    }

    protected String text(By locator) {
        return visible(locator).getText().trim();
    }

    protected String value(By locator) {
        return visible(locator).getAttribute("value").trim();
    }

    protected boolean isDisplayed(By locator, int timeoutInSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    protected void waitForUrlContains(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    protected void waitFor(ExpectedCondition<?> condition) {
        wait.until(condition);
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    protected void selectDropdownOption(By dropdownLocator, String optionText) {
        waitForLoadersToDisappear();
        click(dropdownLocator);
        By optionLocator = By.xpath(String.format(
                "//div[@role='listbox']//span[normalize-space()='%s']",
                optionText
        ));
        click(optionLocator);
    }

    protected void selectAutocompleteOption(By inputLocator, String desiredValue) {
        waitForLoadersToDisappear();
        clearAndType(inputLocator, desiredValue);

        By optionsLocator = By.xpath("//div[@role='listbox']//span");
        List<WebElement> options = visibleAll(optionsLocator);
        String normalizedDesiredValue = normalize(desiredValue);

        for (WebElement option : options) {
            String optionText = normalize(option.getText());
            if (optionText.equalsIgnoreCase(normalizedDesiredValue)
                    || optionText.contains(normalizedDesiredValue)
                    || normalizedDesiredValue.contains(optionText)) {
                option.click();
                return;
            }
        }

        options.get(0).click();
    }

    protected void setDateValue(By inputLocator, String dateValue) {
        waitForLoadersToDisappear();
        WebElement element = visible(inputLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly');", element);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        element.sendKeys(dateValue);
    }

    protected void waitForLoadersToDisappear() {
        try {
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(formLoader));
        } catch (TimeoutException ignored) {
            // Continue when the loader is not present or disappears too quickly to observe.
        }
    }

    protected void waitForSuccessToast() {
        By successToast = By.xpath("//div[contains(@class,'oxd-toast--success')]");
        try {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(successToast));
        } catch (TimeoutException ignored) {
            // The public demo does not always render a stable toast before navigation.
        }
    }

    protected String normalize(String value) {
        return value == null ? "" : value.trim().replaceAll("\\s+", " ");
    }
}
