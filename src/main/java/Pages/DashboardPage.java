package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends BasePage{

    private final By pageTitleDashboardPageLoc = By.xpath("//h6[text()='Dashboard']");
    private final By pimModuleLoc = By.xpath("//span[text()='PIM']");


    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    public String getDashboardPageTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitleDashboardPageLoc)).getText();
    }
    public boolean isDashboardPageTitleDisplayed(){
        return wait
                .until(ExpectedConditions.visibilityOfElementLocated(pageTitleDashboardPageLoc))
                .isDisplayed();
    }
    public void clickPimModule(){
        wait
                .until(ExpectedConditions.elementToBeClickable(pimModuleLoc))
                .click();
    }


}
