package org.example.model;

import lombok.Data;

@Data
public class Admin extends AbstractPerson {

  private int id;
  private String login;
  private String password;
  private String name;
  private int age;

  private static Admin instance;

  private Admin(String login, String password, String name, int age) {
    super(1, login, password, name, age);
  }

  public static Admin getInstance() {
    if (instance == null) {
      instance = new Admin("a", "a", "Pasha Korchevskiy", 23);
    }
    return instance;
  }
}
