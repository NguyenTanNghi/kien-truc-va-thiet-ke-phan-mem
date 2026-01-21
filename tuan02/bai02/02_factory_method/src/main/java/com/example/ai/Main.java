package com.example.ai;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 1:58 PM
 */
public class Main {
    public static void main(String[] args) {
        Creator c1 = new CreatorA();
        c1.doSomething();

        Creator c2 = new CreatorB();
        c2.doSomething();
    }
}