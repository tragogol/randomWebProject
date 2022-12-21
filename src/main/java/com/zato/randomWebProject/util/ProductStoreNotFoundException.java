package com.zato.randomWebProject.util;

public class ProductStoreNotFoundException extends RuntimeException{
    public ProductStoreNotFoundException() { super("Product store not found"); }
}
