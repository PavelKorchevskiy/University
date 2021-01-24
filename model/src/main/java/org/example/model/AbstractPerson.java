package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractPerson implements Person {

  private String login;
  private String password;
  private String name;
  private int age;
  private int id;

  public AbstractPerson(int id, String login, String password, String name, int age) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.name = name;
    this.age = age;
    //TODO: create error page
    if (id == 0) {
      throw new NumberFormatException("not allowed value 0 for id");
    }
  }
}
