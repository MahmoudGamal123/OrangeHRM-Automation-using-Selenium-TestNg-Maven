package Tests;

import Pages.AddEmployeePage;
import Pages.AdminPage;
import Pages.DashboardPage;
import Pages.LoginPage;
import Pages.PimPage;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.TestDataUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;
    protected Path downloadDirectory;

    @BeforeMethod
    public void setUp() {
        downloadDirectory = Paths.get("target", "downloads").toAbsolutePath();
        createDownloadDirectory();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("credentials_enable_service", false);
        prefs.put("download.default_directory", downloadDirectory.toString());
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        openLoginPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected LoginPage openLoginPage() {
        return new LoginPage(driver).open();
    }

    protected String getLoginUrl() {
        return System.getProperty(
                "orangehrm.baseUrl",
                "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"
        );
    }

    protected String getApplicationUrl() {
        return getLoginUrl().replace("/auth/login", "");
    }

    protected String getAdminUsername() {
        return System.getProperty("orangehrm.admin.username", "Admin");
    }

    protected String getAdminPassword() {
        return System.getProperty("orangehrm.admin.password", "admin123");
    }

    protected EmployeeAccount createEssEmployeeAccount() {
        String suffix = TestDataUtils.uniqueSuffix();
        String firstName = "Auto" + suffix.substring(Math.max(0, suffix.length() - 4));
        String lastName = "User";
        String employeeId = TestDataUtils.generateEmployeeId();
        String username = TestDataUtils.generateUsername("ess");
        String password = "Orange@12345";

        Allure.step("Create a fresh ESS employee and system user");

        DashboardPage dashboardPage = openLoginPage().login(getAdminUsername(), getAdminPassword());
        Assert.assertTrue(dashboardPage.isDashboardPageTitleDisplayed(), "Admin login failed.");

        dashboardPage.clickPimModule();
        PimPage pimPage = new PimPage(driver).waitUntilLoaded();
        AddEmployeePage addEmployeePage = pimPage.goToAddEmployee();
        addEmployeePage.enterEmployeeName(firstName, "", lastName)
                .setEmployeeId(employeeId)
                .save();

        new DashboardPage(driver).clickAdminModule();
        AdminPage adminPage = new AdminPage(driver).waitUntilLoaded();
        adminPage.createSystemUser("ESS", firstName + " " + lastName, "Enabled", username, password);
        adminPage.searchByUsername(username);
        Assert.assertTrue(adminPage.isUserPresent(username), "The new ESS system user was not found.");

        return new EmployeeAccount(firstName, lastName, employeeId, username, password);
    }

    public WebDriver getDriver() {
        return driver;
    }

    private void createDownloadDirectory() {
        try {
            Files.createDirectories(downloadDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create download directory: " + downloadDirectory, e);
        }
    }

    protected static class EmployeeAccount {
        private final String firstName;
        private final String lastName;
        private final String employeeId;
        private final String username;
        private final String password;

        protected EmployeeAccount(String firstName, String lastName, String employeeId,
                                  String username, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.employeeId = employeeId;
            this.username = username;
            this.password = password;
        }

        protected String getFullName() {
            return firstName + " " + lastName;
        }

        protected String getEmployeeId() {
            return employeeId;
        }

        protected String getUsername() {
            return username;
        }

        protected String getPassword() {
            return password;
        }
    }
}
