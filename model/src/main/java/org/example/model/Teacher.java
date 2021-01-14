package org.example.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.example.group.Group;

@Getter
@Setter
@NoArgsConstructor
public class Teacher extends AbstractPerson {

  private Group group;
  private List<BigDecimal> salary = new ArrayList<>();

  public Teacher(int id, String login, String password, String fullName, int age, List<BigDecimal> salary) {
    super(id, login, password, fullName, age);
    if (!StringUtils.isAlpha(fullName)) {
      setFullName("Ivan Ivanovich");
    }
    this.salary = salary;
  }

  //получить студента из группы
  public Optional<Student> getStudentById(int id) {
    Student student = null;
    for (Student s : group.getStudents()) {
      if (s.getId() == id) {
        student = s;
      }
    }
    return Optional.ofNullable(student);
  }
}
