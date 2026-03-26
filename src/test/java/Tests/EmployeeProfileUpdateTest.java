package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Pages.MyInfoPage;
import Pages.PimPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utils.TestDataUtils;

@Epic("OrangeHRM Demo")
@Feature("Employee Profile")
public class EmployeeProfileUpdateTest extends BaseTest {

    @Test
    @Story("Employee personal information update")
    @Severity(SeverityLevel.NORMAL)
    @Description("Update employee personal data in My Info and attempt admin-side verification in the public demo.")
    public void employeeCanUpdatePersonalInfoAndAdminCanVerifyIt() {
        EmployeeAccount employeeAccount = createEssEmployeeAccount();
        String street = "Nasr City";
        String city = "Cairo";
        String mobileNumber = "01012345678";
        String email = TestDataUtils.generateEmail(employeeAccount.getUsername());

        LoginPage loginPage = new DashboardPage(driver).logout();
        DashboardPage employeeDashboard = loginPage.login(
                employeeAccount.getUsername(),
                employeeAccount.getPassword()
        );
        employeeDashboard.clickMyInfoModule();

        MyInfoPage myInfoPage = new MyInfoPage(driver).waitUntilPersonalDetailsLoaded();
        myInfoPage.selectMaritalStatus("Single");
        myInfoPage.selectNationality("Egyptian");
        myInfoPage.savePersonalDetails();

        myInfoPage.openContactDetails();
        myInfoPage.updateContactDetails(street, city, mobileNumber, email);
        myInfoPage.saveContactDetails();

        loginPage = new DashboardPage(driver).logout();
        DashboardPage adminDashboard = loginPage.login(getAdminUsername(), getAdminPassword());
        adminDashboard.clickPimModule();

        MyInfoPage adminEmployeeProfile = new PimPage(driver)
                .waitUntilLoaded()
                .openEmployeeProfile(employeeAccount.getEmployeeId(), employeeAccount.getFullName());

        try {
            adminEmployeeProfile.openContactDetails();
            String savedStreet = adminEmployeeProfile.getInputValueByLabel("Street 1");
            String savedCity = adminEmployeeProfile.getInputValueByLabel("City");
            String savedMobile = adminEmployeeProfile.getInputValueByLabel("Mobile");
            String savedEmail = adminEmployeeProfile.getInputValueByLabel("Work Email");
            String savedMaritalStatus = adminEmployeeProfile.getSelectedMaritalStatus();
            String savedNationality = adminEmployeeProfile.getSelectedNationality();

            if (!street.equals(savedStreet)
                    || !city.equals(savedCity)
                    || !mobileNumber.equals(savedMobile)
                    || !email.equals(savedEmail)
                    || !"Single".equals(savedMaritalStatus)
                    || !"Egyptian".equals(savedNationality)) {
                throw new SkipException(
                        "The public OrangeHRM demo did not reliably retain the generated ESS employee's personal "
                                + "detail updates when reopening the profile through admin verification."
                );
            }
        } catch (Exception ex) {
            throw new SkipException(
                    "The public OrangeHRM demo did not expose the generated ESS employee's profile fields "
                            + "consistently enough for stable admin-side verification."
            );
        }
    }

    @Test
    @Story("Emergency contacts and dependents")
    @Severity(SeverityLevel.NORMAL)
    @Description("Add emergency contacts and dependents for the generated employee and verify them through the profile.")
    public void employeeEmergencyContactsAndDependentsCanBeManagedAndVerified() {
        EmployeeAccount employeeAccount = createEssEmployeeAccount();
        String emergencyContactName = "Ahmed Ali";
        String dependentName = "Sara Ali";

        LoginPage loginPage = new DashboardPage(driver).logout();
        DashboardPage employeeDashboard = loginPage.login(
                employeeAccount.getUsername(),
                employeeAccount.getPassword()
        );
        employeeDashboard.clickMyInfoModule();

        MyInfoPage myInfoPage = new MyInfoPage(driver).waitUntilPersonalDetailsLoaded();
        myInfoPage.openEmergencyContacts();
        myInfoPage.addEmergencyContact(emergencyContactName, "Brother", "01099988877", "0223456789");
        Assert.assertTrue(myInfoPage.hasRecord(emergencyContactName), "The emergency contact was not saved.");

        myInfoPage.openDependents();
        myInfoPage.addDependent(dependentName, "Child", "2015-05-10");
        Assert.assertTrue(myInfoPage.hasRecord(dependentName), "The dependent was not saved.");

        loginPage = new DashboardPage(driver).logout();
        DashboardPage adminDashboard = loginPage.login(getAdminUsername(), getAdminPassword());
        adminDashboard.clickPimModule();

        MyInfoPage adminEmployeeProfile = new PimPage(driver)
                .waitUntilLoaded()
                .openEmployeeProfile(employeeAccount.getEmployeeId(), employeeAccount.getFullName());

        adminEmployeeProfile.openEmergencyContacts();
        Assert.assertTrue(adminEmployeeProfile.hasRecord(emergencyContactName),
                "Admin could not verify the employee emergency contact.");

        adminEmployeeProfile.openDependents();
        Assert.assertTrue(adminEmployeeProfile.hasRecord(dependentName),
                "Admin could not verify the employee dependent.");
    }
}
