package org.validation.shpp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {


    private final int TIME;
    private final String BROKE_URL;
    private final String NAME;
    private final String PASSWORD;
    private final String QUEUE;

    public Config() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TIME = Integer.parseInt(properties.getProperty("time"));
        BROKE_URL = properties.getProperty("url");
        NAME = properties.getProperty("name");
        PASSWORD = properties.getProperty("password");
        QUEUE = properties.getProperty("queue");
    }

    public String getQUEUE() {
        return QUEUE;
    }

    public int getTIME() {
        return TIME;
    }

    public String getBROKE_URL() {
        return BROKE_URL;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }


}
