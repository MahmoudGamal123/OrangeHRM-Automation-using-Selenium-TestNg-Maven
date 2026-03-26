package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestDataUtils {
    private static final Random RANDOM = new Random();

    public static String uniqueSuffix() {
        return new SimpleDateFormat("MMddHHmmss").format(new Date());
    }

    public static String generateEmployeeId() {
        return String.valueOf(100000 + RANDOM.nextInt(900000));
    }

    public static String generateUsername() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(RANDOM.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String generateUsername(String prefix) {
        return (prefix + uniqueSuffix() + generateUsername()).toLowerCase();
    }

    public static String generateEmail(String prefix) {
        return (prefix + "." + uniqueSuffix() + "@example.com").toLowerCase();
    }
}
