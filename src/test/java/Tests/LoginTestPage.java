package Tests;

import DataProvider.DataProvider;
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
@Feature("Authentication")
public class LoginTestPage extends BaseTest {

    @Test(dataProviderClass = DataProvider.class, dataProvider = "LoginDataProvider")
    @Story("Valid admin login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a valid OrangeHRM admin user can sign in successfully.")
    public void validLoginShouldOpenDashboard(String username, String password) {
        DashboardPage dashboardPage = openLoginPage().login(username, password);
        Assert.assertTrue(dashboardPage.isDashboardPageTitleDisplayed(), "Dashboard page title is not displayed.");
    }

    @Test
    @Story("Invalid login handling")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that invalid credentials return the expected authentication error.")
    public void invalidLoginShouldShowErrorMessage() {
        LoginPage loginPage = openLoginPage().loginExpectingFailure(getAdminUsername(), "invalidPassword");
        Assert.assertTrue(
                loginPage.getInvalidCredentialsMessage().contains("Invalid credentials"),
                "The invalid credentials message was not displayed."
        );
    }
}
