package org.bobocode.bean;

import org.bobocode.annotation.Bean;

@Bean("dataService")
public class DatabaseService {

  @Override
  public String toString() {
    String identifier = "I'm DatabaseService";
    return "DatabaseService{" +
            "identifier='" + identifier + '\'' +
            '}';
  }

}
