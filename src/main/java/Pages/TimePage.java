package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimePage extends BasePage {
    private final By timePageMarker =
            By.xpath("//*[normalize-space()='Timesheets' or normalize-space()='Time']");
    private final By submitButton = By.xpath("//button[normalize-space()='Submit']");
    private final By editButton = By.xpath("//button[normalize-space()='Edit']");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");
    private final By employeeNameInput =
            By.xpath("//label[normalize-space()='Employee Name']/following::input[1]");

    public TimePage(WebDriver driver) {
        super(driver);
    }

    public TimePage waitUntilLoaded() {
        visible(timePageMarker);
        return this;
    }

    public boolean hasMyTimesheetWorkspace() {
        By workspaceMarkers = By.xpath(
                "//button[normalize-space()='Edit' or normalize-space()='Submit' or normalize-space()='Create Timesheet']"
                        + " | //button[normalize-space()='Add Row']"
                        + " | //div[contains(@class,'orangehrm-timesheet')]"
                        + " | //*[contains(normalize-space(),'No Records Found')]");
        return isDisplayed(workspaceMarkers, 8);
    }

    public boolean submitCurrentTimesheetIfPossible() {
        if (isDisplayed(submitButton, 3)) {
            click(submitButton);
            waitForSuccessToast();
            return true;
        }

        if (isDisplayed(editButton, 3)) {
            click(editButton);

            if (isDisplayed(saveButton, 5)) {
                click(saveButton);
                waitForSuccessToast();
            }

            if (isDisplayed(submitButton, 5)) {
                click(submitButton);
                waitForSuccessToast();
                return true;
            }
        }

        return false;
    }

    public boolean canReviewEmployeeTimesheets() {
        return isDisplayed(employeeNameInput, 8);
    }

    public void searchEmployeeTimesheet(String employeeName) {
        selectAutocompleteOption(employeeNameInput, employeeName);
        click(By.xpath("//button[normalize-space()='View' or normalize-space()='Search']"));
    }

    public boolean hasEmployeeTimesheet(String employeeName) {
        By rowLocator = By.xpath(String.format(
                "//*[contains(normalize-space(), \"%s\")]",
                employeeName
        ));
        return isDisplayed(rowLocator, 8);
    }

    public boolean approveFirstAvailableTimesheet() {
        By approveButton = By.xpath("//button[normalize-space()='Approve']");
        By viewButton = By.xpath("//button[normalize-space()='View']");

        if (isDisplayed(approveButton, 5)) {
            click(approveButton);
            waitForSuccessToast();
            return true;
        }

        if (isDisplayed(viewButton, 5)) {
            click(viewButton);
            if (isDisplayed(approveButton, 5)) {
                click(approveButton);
                waitForSuccessToast();
                return true;
            }
        }

        return false;
    }

    public boolean isApprovalConfirmed() {
        By approvedMarker = By.xpath("//*[contains(normalize-space(),'Approved')]");
        return isDisplayed(approvedMarker, 8);
    }
}
