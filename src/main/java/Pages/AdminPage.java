package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AdminPage extends BasePage {
    private final By pageHeader = By.xpath("//h6[normalize-space()='Admin']");
    private final By addButtonLoc = By.xpath("//button[normalize-space()='Add']");
    private final By userRoleDropdown =
            By.xpath("//label[normalize-space()='User Role']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By employeeNameInput =
            By.xpath("//label[normalize-space()='Employee Name']/following::input[1]");
    private final By statusDropdown =
            By.xpath("//label[normalize-space()='Status']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By usernameInput =
            By.xpath("//label[normalize-space()='Username']/following::input[1]");
    private final By passwordInput =
            By.xpath("//label[normalize-space()='Password']/following::input[1]");
    private final By confirmPasswordInput =
            By.xpath("//label[normalize-space()='Confirm Password']/following::input[1]");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By noRecordsFoundMessage = By.xpath("//*[normalize-space()='No Records Found']");
    private final By userRows = By.xpath("//div[contains(@class,'oxd-table-card')]");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public AdminPage waitUntilLoaded() {
        visible(pageHeader);
        return this;
    }

    public AdminPage clickAddButton() {
        click(addButtonLoc);
        return this;
    }

    public AdminPage createSystemUser(String role, String employeeName,
                                      String status, String username, String password) {
        clickAddButton();
        selectDropdownOption(userRoleDropdown, role);
        selectAutocompleteOption(employeeNameInput, employeeName);
        selectDropdownOption(statusDropdown, status);
        clearAndType(usernameInput, username);
        clearAndType(passwordInput, password);
        clearAndType(confirmPasswordInput, password);
        click(saveButton);
        waitForSuccessToast();
        return waitUntilLoaded();
    }

    public AdminPage searchByUsername(String username) {
        clearAndType(usernameInput, username);
        click(searchButton);
        waitFor(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(userRows),
                ExpectedConditions.visibilityOfElementLocated(noRecordsFoundMessage)
        ));
        return this;
    }

    public boolean isUserPresent(String username) {
        By userCell = By.xpath(String.format(
                "//div[contains(@class,'oxd-table-body')]//*[normalize-space()='%s']",
                username
        ));
        return isDisplayed(userCell, 10);
    }
}
