package org.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.example.model.Student;
import org.example.subject.Subject;

public class RepositoryForStudentsInMemory implements RepositoryForStudentsInterface {

  private static RepositoryForStudentsInMemory instance;
  private final Map<String, Student> map = new ConcurrentHashMap<>();

  private RepositoryForStudentsInMemory() {
    for (Student student : initStudents()) {
      map.put(student.getLogin(), student);
    }
  }

  public static RepositoryForStudentsInMemory getInstance() {
    if (instance == null) {
      synchronized (RepositoryForStudentsInMemory.class) {
        if (instance == null) {
          instance = new RepositoryForStudentsInMemory();
        }
      }
    }
    return instance;
  }

  private List<Student> initStudents() {
    List<Student> students = new ArrayList<>();
    students.add(new Student("s1", "s1", "Lena", 24,
        Set.of(Subject.Chemistry, Subject.Math)));
    students.add(new Student("s2", "s2", "Dima", 25,
        Set.of(Subject.Chemistry, Subject.Math)));
    students.add(new Student("s3", "s3", "Tolik", 26,
        Set.of(Subject.Chemistry, Subject.Biology, Subject.Geography)));
    students.add(new Student("s4", "s4", "Bill", 27,
        Set.of(Subject.Chemistry, Subject.Biology, Subject.Geography)));
    students.add(new Student("s5", "s5", "Tom", 28,
        Set.of(Subject.Chemistry, Subject.Biology, Subject.Geography)));
    students.add(new Student("s6", "s6", "Kim", 23,
        Set.of(Subject.Geography, Subject.Math)));
    students.add(new Student("s7", "s7", "Pasha", 24,
        Set.of(Subject.Geography, Subject.Math)));
    students.add(new Student("s8", "s8", "Dasha", 34,
        Set.of(Subject.Geography, Subject.Math)));
    return students;
  }

  public List<Student> getRandomStudents(int count) {
    List<Student> students = new ArrayList<>();
    for (Map.Entry<String, Student> entry : map.entrySet()) {
      students.add(entry.getValue());
    }
    List<Student> result = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      result.add(students.remove((int) (Math.random() * students.size())));
    }
    return result;
  }

  @Override
  public List<Student> findAll() {
    return new ArrayList<>(map.values());
  }

  @Override
  public Optional<Student> findByLogin(String login) {
    return Optional.ofNullable(map.get(login));
  }

  @Override
  public Student save(Student student) {
    map.put(student.getLogin(), student);
    return student;
  }

  @Override
  public Student remove(Student student) {
    return map.remove(student.getLogin());
  }

  @Override
  public Optional<Student> findByLoginAndPassword(String login, String password) {
    Student result = null;
    for (Map.Entry<String, Student> entry : map.entrySet()) {
      if (entry.getValue().getLogin().equals(login) && entry.getValue().getPassword()
          .equals(password)) {
        result = entry.getValue();
      }
    }
    return Optional.ofNullable(result);
  }
}
