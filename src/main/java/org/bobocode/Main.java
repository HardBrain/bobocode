package org.bobocode;

import org.bobocode.application.ApplicationContext;
import org.bobocode.application.impl.AnnotationBeanApplicationContext;
import org.bobocode.bean.DatabaseService;
import org.bobocode.bean.ProductService;
import org.bobocode.exceptions.NoSuchBeanException;
import org.bobocode.exceptions.NoUniqueBeanException;

import java.util.Map;

public class Main {

  public static void main(String[] args) throws NoSuchBeanException, NoUniqueBeanException {
    ApplicationContext applicationContext = new AnnotationBeanApplicationContext("org.bobocode");

    Map<String, ProductService> productServiceBeans = applicationContext.getAllBeans(ProductService.class);
    productServiceBeans.forEach((key, value) -> System.out.printf("Key: %s, Value: %s", key, value));
    System.out.println();

    Map<String, DatabaseService> databaseServiceBeans = applicationContext.getAllBeans(DatabaseService.class);
    databaseServiceBeans.forEach((key, value) -> System.out.printf("Key: %s, Value: %s", key, value));
    System.out.println();

    ProductService productService = applicationContext.getBean(ProductService.class);
    System.out.println(productService);

    DatabaseService databaseService = applicationContext.getBean("dataService", DatabaseService.class);
    System.out.println(databaseService);

//    Throwing NoSuchBeanException
//    applicationContext.getBean(TestClass.class);
  }
}