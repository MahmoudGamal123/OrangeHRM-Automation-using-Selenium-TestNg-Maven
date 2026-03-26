# OrangeHRM Automation Using Selenium TestNG Maven

UI automation framework for the [OrangeHRM demo site](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login) using Java, Selenium WebDriver, TestNG, Maven, and Allure Report.

## Tech Stack

- Java 11+
- Selenium WebDriver
- TestNG
- Maven
- Allure Report

## Project Structure

```text
src
|-- main
|   |-- java/Pages
|   `-- resources
`-- test
    |-- java
    |   |-- Tests
    |   |-- DataProvider
    |   `-- utils
    `-- resources
```

## Automated Scenarios

1. Valid and invalid login
2. Employee creation and ESS role assignment
3. Employee personal information update
4. Emergency contacts and dependents management
5. Timesheet submission and approval
6. Employee report generation and export

## Notes About the Public Demo

Some OrangeHRM demo flows are not fully stable for generated users.  
Where the public site does not consistently expose or persist data, the suite uses clean skip behavior instead of false failures.

This is especially relevant for:

- admin-side verification of some generated employee profile fields
- timesheet approval data availability
- report/export availability in the public demo

## Prerequisites

- JDK installed
- Maven installed
- Google Chrome installed
- ChromeDriver available through Selenium Manager or local environment
- Allure CLI installed if you want to open HTML reports

## Clone the Repository

```bash
git clone https://github.com/MahmoudGamal123/OrangeHRM-Automation-using-Selenium-TestNg-Maven.git
cd OrangeHRM-Automation-using-Selenium-TestNg-Maven
```

## Run the Tests

Run the full suite:

```bash
mvn test
```

Run one test class:

```bash
mvn "-Dtest=Tests.LoginTestPage" test
```

## Allure Reporting

The project is already configured to generate Allure results in:

```text
target/allure-results
```

Run the tests, then open the report:

```bash
mvn test
allure serve target/allure-results
```

Generate static report files:

```bash
allure generate target/allure-results --clean -o target/allure-report
```

## Allure Features Included

- TestNG integration
- test metadata annotations
- screenshot attachment on failure
- page source attachment on failure
- current URL attachment on failure
- skip reason attachment for demo-limited scenarios

## Main Test Classes

- `Tests.LoginTestPage`
- `Tests.EmployeeE2ETest`
- `Tests.EmployeeProfileUpdateTest`
- `Tests.TimeWorkflowTest`
- `Tests.ReportWorkflowTest`

## Configuration

Default credentials and messages are stored in:

```text
src/main/resources/testdata.properties
```

You can also override runtime values with JVM properties, for example:

```bash
mvn "-Dorangehrm.admin.username=Admin" "-Dorangehrm.admin.password=admin123" test
```

## GitHub Repository

Repository URL:

[https://github.com/MahmoudGamal123/OrangeHRM-Automation-using-Selenium-TestNg-Maven](https://github.com/MahmoudGamal123/OrangeHRM-Automation-using-Selenium-TestNg-Maven)
