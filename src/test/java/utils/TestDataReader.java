package utils;

import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.util.Properties;

public class TestDataReader {
    private Properties properties;
    public TestDataReader(String filePath) {
        loadDataFromFile(filePath);
    }
    private void loadDataFromFile(String filePath) {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
        }catch (Exception e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }
    public String  getvalue(String key) {
        return properties.getProperty(key);
    }
}

