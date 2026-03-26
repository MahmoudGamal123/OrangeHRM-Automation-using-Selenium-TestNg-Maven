package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    private final By pageTitleDashboardPageLoc = By.xpath("//h6[normalize-space()='Dashboard']");
    private final By userDropdown = By.xpath("//span[contains(@class,'oxd-userdropdown-tab')]");
    private final By logoutButton = By.xpath("//a[normalize-space()='Logout']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public DashboardPage waitUntilLoaded() {
        visible(pageTitleDashboardPageLoc);
        return this;
    }

    private By moduleLocator(String moduleName) {
        return By.xpath(String.format("//span[normalize-space()='%s']", moduleName));
    }

    public String getDashboardPageTitle() {
        return text(pageTitleDashboardPageLoc);
    }

    public boolean isDashboardPageTitleDisplayed() {
        return isDisplayed(pageTitleDashboardPageLoc, 20);
    }

    public void clickModule(String moduleName) {
        click(moduleLocator(moduleName));
    }

    public void clickPimModule() {
        clickModule("PIM");
    }

    public void clickAdminModule() {
        clickModule("Admin");
    }

    public void clickMyInfoModule() {
        clickModule("My Info");
    }

    public void clickTimeModule() {
        clickModule("Time");
    }

    public LoginPage logout() {
        click(userDropdown);
        click(logoutButton);
        return new LoginPage(driver).waitUntilLoaded();
    }

    public boolean isAdminModuleVisible() {
        return isModuleVisible("Admin");
    }

    public boolean isPimModuleVisible() {
        return isModuleVisible("PIM");
    }

    public boolean isModuleVisible(String moduleName) {
        return isDisplayed(moduleLocator(moduleName), 5);
    }
}
