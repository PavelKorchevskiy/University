package org.example.model;

public class Admin extends AbstractPerson {

  private static Admin instance;

  private Admin(String login, String password, String fullName, int age) {
    super(login, password, fullName, age);
  }

  public static Admin getInstance() {
    if (instance == null) {
      instance = new Admin("a", "a", "Pasha Korchevskiy", 23);
    }
    return instance;
  }
}
