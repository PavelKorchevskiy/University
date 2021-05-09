package org.example.repository.memory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;

public class RepositoryForTeachersInMemory implements RepositoryForTeachersInterface {

  private static volatile RepositoryForTeachersInMemory instance;
  private final Map<Integer, Teacher> map = new ConcurrentHashMap<>();

  private RepositoryForTeachersInMemory() {
    for (Teacher teacher : initTeachers()) {
      map.put(teacher.getId(), teacher);
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
    teachers.add(new Teacher(1, "t1", "t1", "Ivanov Ivan", 34, initSalary()));
    teachers.add(new Teacher(2, "t2", "t2", "Galileo Galilei", 64, initSalary()));
    teachers.add(new Teacher(3, "t3", "t3", "Albert Einstein", 54, initSalary()));
    teachers.add(new Teacher(4, "t4", "t4", "Master Yoda", 334, initSalary()));
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
  public Optional<Teacher> findById(int id) {
    return Optional.ofNullable(map.get(id));
  }

  @Override
  public Teacher save(Teacher teacher) {
    map.put(teacher.getId(), teacher);
    return teacher;
  }

  public Teacher remove(Teacher teacher) {
    return map.remove(teacher.getId());
  }

  @Override
  public Optional<Teacher> findByLoginAndPassword(String login, String password) {
    Teacher result = null;
    for (Map.Entry<Integer, Teacher> entry : map.entrySet()) {
      if (entry.getValue().getLogin().equals(login) && entry.getValue().getPassword()
          .equals(password)) {
        result = entry.getValue();
      }
    }
    return Optional.ofNullable(result);
  }
}
