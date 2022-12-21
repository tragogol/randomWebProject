package com.zato.randomWebProject.util;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() { super("Product not found"); }
}
