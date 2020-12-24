package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Teacher extends AbstractPerson {

  public Teacher(String login, String password, String fullName, int age,
      List<BigDecimal> salary) {
    super(login, password, fullName, age);
    this.salary = salary;
  }

  private List<BigDecimal> salary = new ArrayList<>();


}
