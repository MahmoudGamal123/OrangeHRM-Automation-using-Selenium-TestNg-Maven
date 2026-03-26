package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReportsPage extends BasePage {
    private final By pageHeader = By.xpath("//h6[normalize-space()='PIM']");
    private final By reportCriteriaHeader =
            By.xpath("//*[contains(normalize-space(),'Report') or contains(normalize-space(),'Criteria')]");

    public ReportsPage(WebDriver driver) {
        super(driver);
    }

    public ReportsPage waitUntilLoaded() {
        visible(pageHeader);
        return this;
    }

    public boolean openReportByName(String reportNameFragment) {
        By reportLocator = By.xpath(String.format(
                "//*[contains(normalize-space(), \"%s\")]",
                reportNameFragment
        ));

        if (!isDisplayed(reportLocator, 8)) {
            return false;
        }

        click(reportLocator);
        return isDisplayed(reportCriteriaHeader, 8);
    }

    public void applyJobTitle(String jobTitle) {
        By locator = By.xpath("//label[normalize-space()='Job Title']/following::div[contains(@class,'oxd-select-text')][1]");
        if (isDisplayed(locator, 3)) {
            selectDropdownOption(locator, jobTitle);
        }
    }

    public void applyDepartment(String department) {
        By locator = By.xpath("//label[normalize-space()='Sub Unit']/following::div[contains(@class,'oxd-select-text')][1]");
        if (isDisplayed(locator, 3)) {
            selectDropdownOption(locator, department);
        }
    }

    public void applyEmploymentStatus(String employmentStatus) {
        By locator = By.xpath("//label[normalize-space()='Employment Status']/following::div[contains(@class,'oxd-select-text')][1]");
        if (isDisplayed(locator, 3)) {
            selectDropdownOption(locator, employmentStatus);
        }
    }

    public boolean generateReport() {
        By generateButton = By.xpath(
                "//button[normalize-space()='Search' or normalize-space()='View' or normalize-space()='Generate']");

        if (!isDisplayed(generateButton, 5)) {
            return false;
        }

        click(generateButton);
        return hasReportOutput();
    }

    public boolean hasReportOutput() {
        By tableLocator = By.xpath("//div[contains(@class,'oxd-table-card')]");
        By exportLocator = By.xpath(
                "//button[contains(.,'CSV') or contains(.,'Excel') or contains(.,'PDF') or contains(.,'Export')]"
                        + " | //a[contains(.,'CSV') or contains(.,'Excel') or contains(.,'PDF') or contains(.,'Export')]");
        return isDisplayed(tableLocator, 8) || isDisplayed(exportLocator, 8);
    }

    public boolean exportIfAvailable() {
        String[] labels = {"CSV", "Excel", "PDF", "Export"};

        for (String label : labels) {
            By exportLocator = By.xpath(String.format(
                    "//button[contains(normalize-space(),'%1$s')] | //a[contains(normalize-space(),'%1$s')]",
                    label
            ));
            if (isDisplayed(exportLocator, 3)) {
                click(exportLocator);
                return true;
            }
        }

        return false;
    }
}
