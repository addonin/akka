package com.luxoft.akkalabs.clients;

import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    public static String get(String key) {
        return properties.getProperty(key);
    }

    static {
        properties.put("twitter.customer.key", "zQS3cSUXMX0dOjJnW9H4X2mPF");
        properties.put("twitter.customer.secret", "DyDLU1NerwHDfhxTEVkI6rkOKWPeT2fmoqMH0noUWAlIoMcX5c");
        properties.put("twitter.access.token", "2821418188-Kc1rOn0GfEfBW8DAwHgHOkDPGsxiXudRHSfhMal");
        properties.put("twitter.access.secret", "SUIdoBOion4z1oCa19z2Tmm4FzmLfHF0mzPBYSuXhHgHc");
    }
}
