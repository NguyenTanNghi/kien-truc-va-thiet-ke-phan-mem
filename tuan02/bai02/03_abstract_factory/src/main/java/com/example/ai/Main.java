package com.example.ai;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:04 PM
 */
public class Main {
    public static void main(String[] args) {
        GUIFactory factory = new WinFactory(); // đổi sang new MacFactory()

        Button btn = factory.createButton();
        Checkbox cb = factory.createCheckbox();

        btn.paint();
        cb.paint();
    }
}