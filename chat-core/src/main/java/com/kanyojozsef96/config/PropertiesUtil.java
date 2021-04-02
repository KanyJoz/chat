package com.kanyojozsef96.config;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties props = new Properties();

    static {
        try {
            props.load(PropertiesUtil.class.getResourceAsStream("/application.properties"));
        } catch (IOException ex) {
            System.out.println("Couldn't find the properties file to load!");
            ex.printStackTrace();
        }
    }

    private PropertiesUtil() {}

    public static Properties getProps() { return props; }
    public static String getUtilPropValue(String key) { return props.getProperty(key); }
}
