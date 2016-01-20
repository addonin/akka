package com.luxoft.akkalabs.clients;

import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    public static String get(String key) {
        return properties.getProperty(key);
    }

    static {
        properties.put("twitter.customer.key", "uRFtaE1D9lS6e9roFm9KCahem");
        properties.put("twitter.customer.secret", "MLbAyL6d9DrPbcPljiy2xMX8mWVrtiL5z2hrE8iPZkIMP3aVo0");
        properties.put("twitter.access.token", "2228247589-nTk2L4NBmeas1u91KUKINgrm8XAwb3fW6XT1lKU");
        properties.put("twitter.access.secret", "vlFJWRZTHm6J8DZE7xKRz3IpFADVLB3XIwX1fj06nvHk1");
    }
}
