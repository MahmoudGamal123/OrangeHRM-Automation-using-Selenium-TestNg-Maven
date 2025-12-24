package Tests;

import DataProvider.DataProvider;
import Pages.DashboardPage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTestPage extends BaseTest {

    @Test(dataProviderClass = DataProvider.class,dataProvider = "LoginDataProvider")
    public void  ValidLoginTest(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.login(username, password);
        Assert.assertTrue(dashboardPage.isDashboardPageTitleDisplayed(), "Dashboard page title is not displayed.");




    }


}
