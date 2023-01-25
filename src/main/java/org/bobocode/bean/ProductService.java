package org.bobocode.bean;

import org.bobocode.annotation.Bean;

@Bean
public class ProductService {

  @Override
  public String toString() {
    String identifier = "I'm ProductService";
    return "ProductService{" +
            "identifier='" + identifier + '\'' +
            '}';
  }
}
