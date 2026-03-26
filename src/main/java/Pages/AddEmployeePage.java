package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddEmployeePage extends BasePage {
    private final By pageHeader = By.xpath("//h6[normalize-space()='Add Employee']");
    private final By employeeFirstNameRecord = By.name("firstName");
    private final By employeeMiddleNameRecord = By.name("middleName");
    private final By employeeLastNameRecord = By.name("lastName");
    private final By employeeIdRecord = By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");
    private final By saveButtonLoc = By.xpath("//button[normalize-space()='Save']");

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    public AddEmployeePage waitUntilLoaded() {
        visible(pageHeader);
        return this;
    }

    public AddEmployeePage enterEmployeeName(String firstName, String middleName, String lastName) {
        clearAndType(employeeFirstNameRecord, firstName);
        clearAndType(employeeMiddleNameRecord, middleName);
        clearAndType(employeeLastNameRecord, lastName);
        return this;
    }

    public AddEmployeePage setEmployeeId(String employeeId) {
        clearAndType(employeeIdRecord, employeeId);
        return this;
    }

    public String getEmployeeId() {
        return value(employeeIdRecord);
    }

    public MyInfoPage save() {
        click(saveButtonLoc);
        waitForSuccessToast();
        return new MyInfoPage(driver).waitUntilPersonalDetailsLoaded();
    }
}
