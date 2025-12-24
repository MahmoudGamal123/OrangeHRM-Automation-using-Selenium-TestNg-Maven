package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private final By usernameFieldLoc = By.xpath("//input[@name='username']");
    private final By passwordFieldLoc = By.xpath("//input[@name='password']");
    private final By loginButtonLoc = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver) {
    super(driver);
    }

    public DashboardPage login(String username, String password) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFieldLoc))
                .sendKeys(username);

        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordFieldLoc))
                .sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginButtonLoc))
                .click();

        // wait until dashboard is loaded
        return new DashboardPage(driver);
    }
}
