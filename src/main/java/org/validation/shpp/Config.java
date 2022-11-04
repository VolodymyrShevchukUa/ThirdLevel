package org.validation.shpp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private int time ;

    Config(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        time = (int) properties.get("time");
    }

}
