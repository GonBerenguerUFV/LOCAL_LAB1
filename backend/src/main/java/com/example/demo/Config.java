package com.example.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public String getProperty(String property) {
        String rootPath = "./backend";
		String appConfigPath = rootPath + "/config.properties";
		Properties appProps = new Properties();
		try {
			appProps.load(new FileInputStream(appConfigPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        String prop = appProps.getProperty(property);
        return prop;
    }
    
}
