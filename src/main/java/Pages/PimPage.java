package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PimPage extends BasePage {
    private final By pageHeader = By.xpath("//h6[normalize-space()='PIM']");
    private final By employeeListTab = By.xpath("//a[normalize-space()='Employee List']");
    private final By addEmployeeTab = By.xpath("//a[normalize-space()='Add Employee']");
    private final By reportsTab = By.xpath("//a[normalize-space()='Reports']");
    private final By employeeNameSearchInput =
            By.xpath("//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By employeeIdSearchInput =
            By.xpath("//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By resultsRows = By.xpath("//div[contains(@class,'oxd-table-card')]");
    private final By noRecordsFoundMessage = By.xpath("//*[normalize-space()='No Records Found']");

    public PimPage(WebDriver driver) {
        super(driver);
    }

    public PimPage waitUntilLoaded() {
        visible(pageHeader);
        return this;
    }

    public PimPage openEmployeeList() {
        click(employeeListTab);
        return this;
    }

    public AddEmployeePage goToAddEmployee() {
        click(addEmployeeTab);
        return new AddEmployeePage(driver).waitUntilLoaded();
    }

    public ReportsPage goToReports() {
        click(reportsTab);
        return new ReportsPage(driver).waitUntilLoaded();
    }

    public PimPage searchEmployeeByName(String employeeName) {
        openEmployeeList();
        selectAutocompleteOption(employeeNameSearchInput, employeeName);
        click(searchButton);
        waitForSearchResults();
        return this;
    }

    public PimPage searchEmployeeById(String employeeId) {
        openEmployeeList();
        clearAndType(employeeIdSearchInput, employeeId);
        click(searchButton);
        waitForSearchResults();
        return this;
    }

    public boolean isEmployeeListed(String identifier) {
        By matchLocator = By.xpath(String.format(
                "//div[contains(@class,'oxd-table-body')]//*[contains(normalize-space(), \"%s\")]",
                identifier
        ));
        return isDisplayed(matchLocator, 10);
    }

    public MyInfoPage openEmployeeProfile(String employeeId, String employeeName) {
        searchEmployeeById(employeeId);

        By employeeNameCell = By.xpath(String.format(
                "//div[contains(@class,'oxd-table-body')]//*[contains(normalize-space(), \"%s\")]",
                employeeName
        ));
        click(employeeNameCell);
        return new MyInfoPage(driver).waitUntilPersonalDetailsLoaded();
    }

    private void waitForSearchResults() {
        waitFor(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(resultsRows),
                ExpectedConditions.visibilityOfElementLocated(noRecordsFoundMessage)
        ));
    }
}
