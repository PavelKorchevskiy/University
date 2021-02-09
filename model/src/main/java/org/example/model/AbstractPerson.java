package org.example.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
@NoArgsConstructor

public abstract class AbstractPerson implements Person, Serializable {

  private int id;
  private String login;
  private String password;
  private String name;
  private int age;


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
