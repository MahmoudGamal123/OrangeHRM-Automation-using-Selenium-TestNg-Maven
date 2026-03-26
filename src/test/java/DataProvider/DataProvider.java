package DataProvider;

public class DataProvider {
    @org.testng.annotations.DataProvider(name = "LoginDataProvider")
    public Object[][] getLoginData() {
        return new Object[][]{
                {
                        System.getProperty("orangehrm.admin.username", "Admin"),
                        System.getProperty("orangehrm.admin.password", "admin123")
                }
        };
    }
}
