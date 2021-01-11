package org.example.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class Teacher extends AbstractPerson {

  private volatile Set<Student> group = new HashSet<>();
  private List<BigDecimal> salary = new ArrayList<>();

  public Teacher(int id, String login, String password, String fullName, int age, List<BigDecimal> salary) {
    super(id, login, password, fullName, age);
    if (!StringUtils.isAlpha(fullName)) {
      setFullName("Ivan Ivanovich");
    }
    this.salary = salary;
  }

  public void addStudentInGroup(Student student) {
    group.add(student);
  }

  //получить студента из группы
  public Optional<Student> getStudentById(int id) {
    Student student = null;
    for (Student s : group) {
      if (s.getId() == id) {
        student = s;
      }
    }
    return Optional.ofNullable(student);
  }
}
