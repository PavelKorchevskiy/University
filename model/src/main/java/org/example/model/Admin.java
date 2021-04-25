package org.example.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Admin extends AbstractPerson {

  private int id;
  private String login;
  private String password;
  private String name;
  private int age;

  private static Admin instance;

  public Admin(String login, String password, String name, int age) {
    this.id = 1;
    this.login = login;
    this.password = password;
    this.name = name;
    this.age = age;
  }

  public static Admin getInstance() {
    if (instance == null) {
      instance = new Admin("a", "a", "Pasha Korchevskiy", 23);
    }
    return instance;
  }
}
