package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final String DEFAULT_LOGIN_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    private final By usernameFieldLoc = By.name("username");
    private final By passwordFieldLoc = By.name("password");
    private final By loginButtonLoc = By.xpath("//button[@type='submit']");
    private final By invalidCredentialsMessageLoc =
            By.xpath("//p[contains(@class,'oxd-alert-content-text')]");
    private final By requiredValidationLoc =
            By.xpath("//span[normalize-space()='Required']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(System.getProperty("orangehrm.baseUrl", DEFAULT_LOGIN_URL));
        return waitUntilLoaded();
    }

    public LoginPage waitUntilLoaded() {
        visible(usernameFieldLoc);
        return this;
    }

    public DashboardPage login(String username, String password) {
        clearAndType(usernameFieldLoc, username);
        clearAndType(passwordFieldLoc, password);
        click(loginButtonLoc);
        return new DashboardPage(driver).waitUntilLoaded();
    }

    public LoginPage loginExpectingFailure(String username, String password) {
        clearAndType(usernameFieldLoc, username);
        clearAndType(passwordFieldLoc, password);
        click(loginButtonLoc);
        return this;
    }

    public LoginPage submitWithoutCredentials() {
        click(loginButtonLoc);
        return this;
    }

    public String getInvalidCredentialsMessage() {
        return text(invalidCredentialsMessageLoc);
    }

    public boolean hasRequiredFieldValidation() {
        return driver.findElements(requiredValidationLoc).size() >= 2;
    }
}
