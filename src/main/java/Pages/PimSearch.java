package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PimSearch extends BasePage {
    WebDriver driver;
    private final By EmployeeName = By.xpath("(//input['Type for hints...'])[2]");
    private final By searchButton = By.xpath("//button[@type='submit']");
    private final By PIM = By.linkText("PIM");
    private final By employeeFirstNameRecord = By.xpath("//input[@name='firstName']");
    private final By employeeMiddleNameRecord = By.xpath("//input[@name='middleName']");
    private final By employeeLastNameRecord = By.xpath("//input[@name='lastName']");
    private final By createLoginDetailsLoc = By.xpath("//label[.//input[@type=\"checkbox\"]]");
    private final By UsernameLoc = By.xpath("//label[text()='Username']/following::input[1]");
    private final By passwordLoc = By.xpath("//label[text()='Password']/following::input[1]");
    private final By confirmPasswordLoc = By.xpath("//label[text()='Confirm Password']/following::input[1]");
    private final By saveButtonLoc = By.xpath("//button[@type='submit']");

    public PimSearch(WebDriver driver) {
        super(driver);
    }
    public void setEmployeeName(String employee) {
        driver.findElement(EmployeeName).sendKeys(employee);
    }
    public void pressSearchButton() {
        driver.findElement(searchButton).click();
    }
    public PimPage addUser() {
        wait.until(ExpectedConditions.elementToBeClickable(PIM)).click();
        return new PimPage(driver);
    }
    public void clickPIM() {
        wait.until(ExpectedConditions.elementToBeClickable(PIM)).click();

    }
    public void employeeFirstNameRecord() {
        wait.until(ExpectedConditions.elementToBeClickable(employeeFirstNameRecord)).sendKeys("John");
        wait.until(ExpectedConditions.elementToBeClickable(employeeMiddleNameRecord)).sendKeys("Ali");
        wait.until(ExpectedConditions.elementToBeClickable(employeeLastNameRecord)).sendKeys("Smith");
    }
    public void clickCreateLoginDetails(){
        wait.until(ExpectedConditions.elementToBeClickable(createLoginDetailsLoc)).click();
    }
    public void creatLoginDetails(){
        wait.until(ExpectedConditions.elementToBeClickable(UsernameLoc)).sendKeys("JohnSmith");
        wait.until(ExpectedConditions.elementToBeClickable(passwordLoc)).sendKeys("John@1234");
        wait.until(ExpectedConditions.elementToBeClickable(confirmPasswordLoc)).sendKeys("John@1234");
    }
    public void clickSaveButton(){
        wait.until(ExpectedConditions.elementToBeClickable(saveButtonLoc)).click();
    }

}
