package org.example.repository;

import static org.junit.Assert.assertNotEquals;

import java.util.List;
import org.example.model.Student;
import org.junit.Test;

public class RepositoryForStudentsInMemoryTest {

  //проверяем то что метод даст обязательно разных студентов
  @Test
  public void getRandomStudents() {
    RepositoryForStudentsInMemory repository = RepositoryForStudentsInMemory.getInstance();
    List<Student> students = repository.getRandomStudents(2);
    assertNotEquals("Students are not random", students.get(0), students.get(1));
  }
}