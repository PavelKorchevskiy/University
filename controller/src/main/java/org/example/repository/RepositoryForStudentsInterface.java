package org.example.repository;

import org.example.model.Student;

import java.util.List;
import java.util.Optional;

public interface RepositoryForStudentsInterface {

    List<Student> findAll();

    Optional<Student> findByLogin(String login);

    Student save(Student student);

    Student remove(Student student);

    Optional<Student> findByLoginAndPassword(String login, String password);
}
