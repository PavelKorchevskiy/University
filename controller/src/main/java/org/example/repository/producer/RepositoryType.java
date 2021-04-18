package org.example.repository.producer;

import java.io.IOException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RepositoryType {

  public static String type;

  static {
    Properties properties = new Properties();
    try {
      properties.load(
          Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    type = properties.getProperty("repository.type");
  }

}
