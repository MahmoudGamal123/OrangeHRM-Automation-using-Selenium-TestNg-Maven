package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("OrangeHRM Demo")
@Feature("Employee Provisioning")
public class EmployeeE2ETest extends BaseTest {

    @Test
    @Story("Create employee and assign ESS role")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create an employee, provision an ESS login, and verify the employee can access the expected module set.")
    public void employeeCreationRoleAssignmentAndEssAccessFlow() {
        EmployeeAccount employeeAccount = createEssEmployeeAccount();

        LoginPage loginPage = new DashboardPage(driver).logout();
        DashboardPage employeeDashboard = loginPage.login(
                employeeAccount.getUsername(),
                employeeAccount.getPassword()
        );

        Assert.assertTrue(employeeDashboard.isDashboardPageTitleDisplayed(), "The ESS user could not log in.");
        Assert.assertFalse(employeeDashboard.isAdminModuleVisible(), "ESS users should not see the Admin module.");
        Assert.assertTrue(employeeDashboard.isModuleVisible("My Info"),
                "ESS users should have access to their My Info module.");
    }
}
