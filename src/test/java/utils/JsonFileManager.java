package utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.FileReader;
import java.util.LinkedHashMap;

public class JsonFileManager {
    LinkedHashMap<String, Object> data;
    public JsonFileManager(String filePath) {
        try {
            data = new Gson().fromJson(new FileReader(filePath),new TypeToken<LinkedHashMap<String,Object >>(){}.getType());

        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public Object getValue(String key) {
        return data.get(key);
    }
}
