package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyInfoPage extends BasePage {
    private final By personalDetailsHeader = By.xpath("//h6[normalize-space()='Personal Details']");
    private final By contactDetailsTab = By.xpath("//a[normalize-space()='Contact Details']");
    private final By emergencyTab = By.xpath("//a[normalize-space()='Emergency Contacts']");
    private final By dependentsTab = By.xpath("//a[normalize-space()='Dependents']");
    private final By addButton = By.xpath("//button[normalize-space()='Add']");

    public MyInfoPage(WebDriver driver) {
        super(driver);
    }

    public MyInfoPage waitUntilPersonalDetailsLoaded() {
        visible(personalDetailsHeader);
        return this;
    }

    private By inputByLabel(String label) {
        return By.xpath(String.format("//label[normalize-space()='%s']/following::input[1]", label));
    }

    private By dropdownByLabel(String label) {
        return By.xpath(String.format(
                "//label[normalize-space()='%s']/following::div[contains(@class,'oxd-select-text')][1]",
                label
        ));
    }

    public void selectMaritalStatus(String value) {
        selectDropdownOption(dropdownByLabel("Marital Status"), value);
    }

    public void selectNationality(String value) {
        selectDropdownOption(dropdownByLabel("Nationality"), value);
    }

    public void savePersonalDetails() {
        click(By.xpath("//label[normalize-space()='Nationality']/ancestor::form//button[@type='submit']"));
        waitForSuccessToast();
    }

    public void openContactDetails() {
        jsClick(contactDetailsTab);
    }

    public void updateContactDetails(String street, String cityName,
                                     String mobileNo, String email) {
        clearAndType(inputByLabel("Street 1"), street);
        clearAndType(inputByLabel("City"), cityName);
        clearAndType(inputByLabel("Mobile"), mobileNo);
        clearAndType(inputByLabel("Work Email"), email);
    }

    public void saveContactDetails() {
        click(By.xpath("//label[normalize-space()='Work Email']/ancestor::form//button[@type='submit']"));
        waitForSuccessToast();
    }

    public void openEmergencyContacts() {
        jsClick(emergencyTab);
    }

    public void addEmergencyContact(String name, String relationship, String mobileNumber,
                                    String workTelephone) {
        click(addButton);
        clearAndType(inputByLabel("Name"), name);
        clearAndType(inputByLabel("Relationship"), relationship);
        clearAndType(inputByLabel("Mobile"), mobileNumber);
        clearAndType(inputByLabel("Work Telephone"), workTelephone);
        click(By.xpath("//label[normalize-space()='Work Telephone']/ancestor::form//button[@type='submit']"));
        waitForSuccessToast();
    }

    public void openDependents() {
        jsClick(dependentsTab);
    }

    public void addDependent(String name, String relationship, String dateOfBirth) {
        click(addButton);
        clearAndType(inputByLabel("Name"), name);
        selectDropdownOption(dropdownByLabel("Relationship"), relationship);
        setDateValue(inputByLabel("Date of Birth"), dateOfBirth);
        click(By.xpath("//label[normalize-space()='Date of Birth']/ancestor::form//button[@type='submit']"));
        waitForSuccessToast();
    }

    public String getSelectedMaritalStatus() {
        return text(dropdownByLabel("Marital Status"));
    }

    public String getSelectedNationality() {
        return text(dropdownByLabel("Nationality"));
    }

    public String getInputValueByLabel(String label) {
        return value(inputByLabel(label));
    }

    public boolean hasRecord(String textValue) {
        By recordLocator = By.xpath(String.format(
                "//*[contains(normalize-space(), \"%s\")]",
                textValue
        ));
        return isDisplayed(recordLocator, 10);
    }
}
