package com.example.ai;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 1:52 PM
 */
public class Main {
    public static void main(String[] args) {
        AppConfig a = AppConfig.getInstance();
        AppConfig b = AppConfig.getInstance();
        a.print();
        b.print();
        System.out.println("Same? " + (a == b));
    }
}