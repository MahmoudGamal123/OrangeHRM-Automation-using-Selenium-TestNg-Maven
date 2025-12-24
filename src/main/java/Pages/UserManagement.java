package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserManagement extends BasePage {
    private final By userRoleLocator = By.xpath("//label[text()='User Role']/following::div[@class='oxd-select-wrapper'][1]");
    private final By EmployeeNameLocator = By.xpath("//input[@placeholder='Type for hints...']");
    private final By statusLocator = By.xpath("//label[text()='Status']/following::div[@class='oxd-select-wrapper']");
    private final By usernameLocator = By.xpath("//label[text()='Username']/following::input[1]");
    private final By passwordLocator = By.xpath("//label[text()='Password']/following::input[1]");
    private final By confirmPasswordLocator = By.xpath("//label[text()='Confirm Password']/following::input[1]");
    private final By saveButtonLocator = By.xpath("//button[@type='submit']");
    public UserManagement(WebDriver driver) {
        super(driver);
    }
}
