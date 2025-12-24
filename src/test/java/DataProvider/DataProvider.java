package DataProvider;

public class DataProvider {
    @org.testng.annotations.DataProvider(name = "LoginDataProvider")
    public Object[][] getLoginData() {
        return new Object[][]{
                {"Admin","admin123"}
        };
    }

}

