package Tests;

import Pages.LoginPage;
import Pages.PimPage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PIMTests extends BaseTest {

    @Test
    public void addPimTestEmployee() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin","admin123");
        PimPage pimPage = new PimPage(driver);
        pimPage.clickAddEmployee();
    }
}
