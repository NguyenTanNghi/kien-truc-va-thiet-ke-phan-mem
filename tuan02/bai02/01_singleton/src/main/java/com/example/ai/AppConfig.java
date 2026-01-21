package com.example.ai;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 1:53 PM
 */
public class AppConfig {
    private static AppConfig instance;

    private AppConfig() {}

    public static AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public void print() {
        System.out.println("AppConfig instance: " + this);
    }
}