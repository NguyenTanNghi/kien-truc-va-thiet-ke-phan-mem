package com.example.ai;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:00 PM
 */
public abstract class Creator {
    public abstract Product createProduct(); // factory method

    public void doSomething() {
        Product p = createProduct();
        p.use();
    }
}