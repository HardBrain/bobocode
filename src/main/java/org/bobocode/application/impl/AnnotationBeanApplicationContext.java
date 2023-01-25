package org.bobocode.application.impl;

import org.bobocode.annotation.Bean;
import org.bobocode.application.ApplicationContext;
import org.bobocode.exceptions.NoSuchBeanException;
import org.bobocode.exceptions.NoUniqueBeanException;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationBeanApplicationContext implements ApplicationContext {

  private final Map<String, Object> context;

  public AnnotationBeanApplicationContext(String packageName) {
    Reflections reflections = new Reflections(packageName);
    Set<Class<?>> beanTypes = reflections.getTypesAnnotatedWith(Bean.class);
    context = beanTypes.stream()
            .collect(Collectors.toConcurrentMap(this::getBeanName, this::createBeanInstance));
  }

  private String getBeanName(Class<?> aClass) {
    Objects.requireNonNull(aClass);
    String beanName;
    Bean beanAnnotation = aClass.getDeclaredAnnotation(Bean.class);
    String value = beanAnnotation.value();
    if (value.isBlank() || value.isEmpty()) {
      var simpleName = aClass.getSimpleName();
      beanName = simpleName.replaceFirst("^.", String.valueOf(Character.toLowerCase(simpleName.charAt(0))));
    } else {
      beanName = value;
    }
    return beanName;
  }

  private Object createBeanInstance(Class<?> aClass) {
    try {
      return aClass.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException {
    Map<String, T> allBeans = getAllBeans(beanType);
    if (allBeans.size() > 1) {
      throw new NoUniqueBeanException();
    }
    return allBeans.values().stream()
            .findFirst()
            .orElseThrow(NoSuchBeanException::new);
  }

  @Override
  public <T> T getBean(String name, Class<T> beanType) throws NoSuchBeanException {
    return getAllBeans(beanType).entrySet().stream()
            .filter(entry -> entry.getKey().equals(name))
            .map(Map.Entry::getValue)
            .findAny()
            .orElseThrow(NoSuchBeanException::new);
  }

  @Override
  public <T> Map<String, T> getAllBeans(Class<T> beanType) {
    return context.entrySet().stream()
            .filter(entry -> entry.getValue().getClass().isAssignableFrom(beanType))
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
  }

}
