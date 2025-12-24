package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Pages.PimPage;
import Pages.PimSearch;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashBoardTests extends BaseTest {

    @Test
    public void verifyDashboardAndNavigateToPIM() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(
                dashboardPage.isDashboardPageTitleDisplayed(),
                "Dashboard page title is not displayed."
        );
        dashboardPage.clickPimModule();
        PimPage pimPage = new PimPage(driver);
        pimPage.clickAddEmployee();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/pim/addEmployee"),
                "Failed to navigate to Add Employee page."
        );
        PimSearch pimSearch = new PimSearch(driver);
        pimSearch.employeeFirstNameRecord();
        pimSearch.clickCreateLoginDetails();
        pimSearch.creatLoginDetails();
        pimSearch.clickSaveButton();
    }


}
