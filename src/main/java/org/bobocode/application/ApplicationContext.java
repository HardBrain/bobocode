package org.bobocode.application;

import org.bobocode.exceptions.NoSuchBeanException;
import org.bobocode.exceptions.NoUniqueBeanException;

import java.util.Map;

public interface ApplicationContext {

  <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException;

  <T> T getBean(String name, Class<T> beanType) throws NoSuchBeanException;

  <T> Map<String, T> getAllBeans(Class<T> beanType);

}
