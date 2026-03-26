package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PimSearchPage extends BasePage {
    private final By employeeName =
            By.xpath("//input[@placeholder='Type for hints...']");
    private final By searchButton =
            By.xpath("//button[@type='submit']");

    public PimSearchPage(WebDriver driver) {
        super(driver);
    }

    public void searchByEmployeeName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeName)).sendKeys(name);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
}
