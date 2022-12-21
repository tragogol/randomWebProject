package com.zato.randomWebProject.util;

public class ProductRequestNotFoundException extends  RuntimeException{
  public ProductRequestNotFoundException() {
    super("Product request not found");
  }
}
