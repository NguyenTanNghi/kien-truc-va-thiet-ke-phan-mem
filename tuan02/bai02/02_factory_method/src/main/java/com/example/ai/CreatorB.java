package com.example.ai;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:01 PM
 */
public class CreatorB extends Creator {
    public Product createProduct() { return new ConcreteProductB(); }
}