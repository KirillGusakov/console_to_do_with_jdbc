package com.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties(); //используется для представления property файлов

    static {
        loadProreties();
    }

    static public String getKey(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProreties() {
        try (var properties = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PropertiesUtil() {
    }
}
