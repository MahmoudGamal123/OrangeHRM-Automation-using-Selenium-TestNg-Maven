package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class PimPage extends BasePage {
    WebDriver driver;

    private By addEmployeeLoc = By.xpath("//button[text()=' Add ']");
    private final By firstName = By.xpath("//input[@placeholder='First Name']");
    private final By MiddleName = By.xpath("//input[@placeholder='Middle Name']");
    private final By LastName = By.xpath("//input[@placeholder='Last Name']");
    private final By saveButton = By.xpath("//button[@type='submit']");

    public PimPage(WebDriver driver) {
        super(driver);
    }

    public void clickAddEmployee(){
        wait
                .until(ExpectedConditions.elementToBeClickable(addEmployeeLoc))
                .click();
    }
    public void setFirstName(String FirstName) {
        wait
                .until(ExpectedConditions.visibilityOfElementLocated(firstName))
                .sendKeys(FirstName);
    }

    public void setMiddleName(String MidName) {
        wait
                .until(ExpectedConditions.visibilityOfElementLocated(MiddleName))
                .sendKeys(MidName);
    }

    public void setLastName(String lastName) {
        wait
                .until(ExpectedConditions.visibilityOfElementLocated(LastName))
                .sendKeys(lastName);

    }

    public void clickSaveButton(){
        wait
                .until(ExpectedConditions.elementToBeClickable(saveButton))
                .click();
    }


}
