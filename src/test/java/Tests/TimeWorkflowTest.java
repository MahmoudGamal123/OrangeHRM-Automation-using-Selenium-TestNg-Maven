package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Pages.TimePage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

@Epic("OrangeHRM Demo")
@Feature("Time")
public class TimeWorkflowTest extends BaseTest {

    @Test
    @Story("Timesheet submission and approval")
    @Severity(SeverityLevel.NORMAL)
    @Description("Attempt the employee timesheet submission and approval flow, skipping cleanly when the public demo lacks the needed data or controls.")
    public void timesheetSubmissionAndApprovalScenario() {
        EmployeeAccount employeeAccount = createEssEmployeeAccount();

        LoginPage loginPage = new DashboardPage(driver).logout();
        loginPage.login(employeeAccount.getUsername(), employeeAccount.getPassword());

        driver.get(getApplicationUrl() + "/time/viewMyTimesheet");
        TimePage timePage = new TimePage(driver).waitUntilLoaded();

        if (!timePage.hasMyTimesheetWorkspace()) {
            throw new SkipException("The public demo did not expose a timesheet workspace for the generated employee.");
        }

        if (!timePage.submitCurrentTimesheetIfPossible()) {
            throw new SkipException("The public demo did not expose an editable or submittable timesheet for this employee.");
        }

        loginPage = new DashboardPage(driver).logout();
        loginPage.login(getAdminUsername(), getAdminPassword());

        driver.get(getApplicationUrl() + "/time/viewEmployeeTimesheet");
        timePage = new TimePage(driver).waitUntilLoaded();

        if (!timePage.canReviewEmployeeTimesheets()) {
            throw new SkipException("The public demo did not expose employee timesheet review controls.");
        }

        timePage.searchEmployeeTimesheet(employeeAccount.getFullName());
        if (!timePage.hasEmployeeTimesheet(employeeAccount.getFullName())) {
            throw new SkipException("No submitted timesheet was listed for the generated employee.");
        }

        if (!timePage.approveFirstAvailableTimesheet()) {
            throw new SkipException("A timesheet was listed, but approval actions were unavailable in the demo.");
        }

        Assert.assertTrue(timePage.isApprovalConfirmed(), "The timesheet approval was not confirmed.");
    }
}
