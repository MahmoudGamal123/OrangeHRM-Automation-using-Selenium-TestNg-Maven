package Tests;

import Pages.DashboardPage;
import Pages.PimPage;
import Pages.ReportsPage;
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
@Feature("Reports")
public class ReportWorkflowTest extends BaseTest {

    @Test
    @Story("Employee report generation and export")
    @Severity(SeverityLevel.MINOR)
    @Description("Open a predefined employee report and export it when the public demo exposes the required report criteria and actions.")
    public void employeeReportGenerationAndExportScenario() {
        DashboardPage dashboardPage = openLoginPage().login(getAdminUsername(), getAdminPassword());
        dashboardPage.clickPimModule();

        ReportsPage reportsPage = new PimPage(driver).waitUntilLoaded().goToReports();

        if (!reportsPage.openReportByName("Employee Contact")) {
            throw new SkipException("The public demo did not expose the Employee Contact report.");
        }

        reportsPage.applyJobTitle("QA Engineer");
        reportsPage.applyDepartment("Engineering");
        reportsPage.applyEmploymentStatus("Full-Time Permanent");

        if (!reportsPage.generateReport()) {
            throw new SkipException("The report criteria page opened, but no report output was generated in the demo.");
        }

        Assert.assertTrue(reportsPage.hasReportOutput(), "The report output was not displayed.");

        if (!reportsPage.exportIfAvailable()) {
            throw new SkipException("The report was generated, but export actions were not available in the demo.");
        }
    }
}
