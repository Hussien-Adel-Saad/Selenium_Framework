package org.hussien.core.utils;

import org.hussien.core.exceptions.FrameworkException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties prop = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FrameworkException("config.properties file not found in classpath");
            }
            prop.load(input);
        } catch (IOException e) {
            throw new FrameworkException("Error loading config file", e);
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        try {
            return Integer.parseInt(get(key));
        } catch (NumberFormatException e) {
            throw new FrameworkException("Invalid integer value for key: " + key, e);
        }
    }
}