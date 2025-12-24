package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private Properties properties;

    public ConfigLoader(String filepath) {

        loadProperties(filepath);
    }

    private void loadProperties(String filepath) {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(filepath);
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String[] getArrayValue(String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            return value.split(",", -1);
        }
        return new String[0];
    }
}
