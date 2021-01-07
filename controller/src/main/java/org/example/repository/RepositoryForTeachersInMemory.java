package org.example.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.example.model.Student;
import org.example.model.Teacher;

public class RepositoryForTeachersInMemory implements RepositoryForTeachersInterface {

  private static volatile RepositoryForTeachersInMemory instance;
  private final Map<String, Teacher> map = new ConcurrentHashMap<>();

  private RepositoryForTeachersInMemory() {
    for (Teacher teacher : initTeachers()) {
      map.put(teacher.getLogin(), teacher);
    }
  }

  public static RepositoryForTeachersInMemory getInstance() {
    if (instance == null) {
      synchronized (RepositoryForTeachersInMemory.class) {
        if (instance == null) {
          instance = new RepositoryForTeachersInMemory();
        }
      }
    }
    return instance;
  }

  private List<Teacher> initTeachers() {
    List<Teacher> teachers = new ArrayList<>();
    teachers.add(new Teacher("t1", "t1", "Ivanov Ivan", 34, initSalary()));
    teachers.add(new Teacher("t2", "t2", "Gallieo Galiley", 64, initSalary()));
    teachers.add(new Teacher("t3", "t3", "Albert Einschtein", 54, initSalary()));
    teachers.add(new Teacher("t4", "t4", "Master Ioda", 334, initSalary()));

    //add 2 random students in group
    for (Teacher teacher : teachers) {
      RepositoryForStudentsInMemory repository = RepositoryForStudentsInMemory.getInstance();
      List<Student> students = repository.getRandomStudents(4);
      for (Student student : students) {
        teacher.addStudentInGroup(student);
      }
    }
    return teachers;
  }

  private List<BigDecimal> initSalary() {
      int numberOfMonths = 12;
    List<BigDecimal> salary = new ArrayList<>();
    for (int i = 0; i < numberOfMonths; i++) {
      salary.add(BigDecimal.valueOf(Math.random() * 200 + 300));
    }
    return salary;
  }

  @Override
  public List<Teacher> findAll() {
    return new ArrayList<>(map.values());
  }

  @Override
  public Optional<Teacher> findByLogin(String login) {
    return Optional.ofNullable(map.get(login));
  }

  @Override
  public Teacher save(Teacher teacher) {
    map.put(teacher.getLogin(), teacher);
    return teacher;
  }

  @Override
  public Teacher remove(Teacher teacher) {
    return map.remove(teacher.getLogin());
  }

  @Override
  public Optional<Teacher> findByLoginAndPassword(String login, String password) {
    Teacher result = null;
    for (Map.Entry<String, Teacher> entry : map.entrySet()) {
      if (entry.getValue().getLogin().equals(login) && entry.getValue().getPassword()
          .equals(password)) {
        result = entry.getValue();
      }
    }
    return Optional.ofNullable(result);
  }
}
