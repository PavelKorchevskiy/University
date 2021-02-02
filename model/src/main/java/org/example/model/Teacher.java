package org.example.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Teacher extends AbstractPerson {

  private List<BigDecimal> salary = new ArrayList<>();

  public Teacher(int id, String login, String password, String name, int age,
      List<BigDecimal> salary) {
    super(id, login, password, name, age);
    this.salary = salary;
  }
}
