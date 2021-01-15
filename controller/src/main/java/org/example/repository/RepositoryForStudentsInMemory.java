package org.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.example.group.Group;
import org.example.model.Student;
import org.example.subject.Subject;

public class RepositoryForStudentsInMemory implements RepositoryForStudentsInterface {

  private static volatile RepositoryForStudentsInMemory instance;
  private final Map<Integer, Student> map = new ConcurrentHashMap<>();

  private RepositoryForStudentsInMemory() {
    for (Student student : initStudents()) {
      map.put(student.getId(), student);
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
    students.add(new Student(1,"s1", "s1", "Lena", 24));
    students.add(new Student(2,"s2", "s2", "Dima", 25));
    students.add(new Student(3,"s3", "s3", "Tolik", 26));
    students.add(new Student(4,"s4", "s4", "Bill", 27));
    students.add(new Student(5,"s5", "s5", "Tom", 28));
    students.add(new Student(6,"s6", "s6", "Kim", 23));
    students.add(new Student(7,"s7", "s7", "Pasha", 24));
    students.add(new Student(8, "s8", "s8", "Dasha", 34));
    return students;
  }

  public List<Student> getRandomStudents(int count) {
    List<Student> students = new ArrayList<>();
    for (Map.Entry<Integer, Student> entry : map.entrySet()) {
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
  public Optional<Student> findById(int id) {
    return Optional.ofNullable(map.get(id));
  }

  @Override
  public Student save(Student student) {
    map.put(student.getId(), student);
    return student;
  }

  @Override
  public Student remove(Student student) {
    return map.remove(student.getId());
  }

  @Override
  public Optional<Student> findByLoginAndPassword(String login, String password) {
    Student result = null;
    for (Map.Entry<Integer, Student> entry : map.entrySet()) {
      if (entry.getValue().getLogin().equals(login) && entry.getValue().getPassword()
          .equals(password)) {
        result = entry.getValue();
      }
    }
    return Optional.ofNullable(result);
  }
}
