package org.example.subject;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subject")
public class Sub {

  @Id
  private int id;
  private String name;

  public Sub() {
  }

  public Sub(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Sub{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
