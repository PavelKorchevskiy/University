package org.example.repository;

import org.example.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface RepositoryForTeachersInterface {

    List<Teacher> findAll();

    Optional<Teacher> findByLogin(String login);

    Teacher save(Teacher teacher);

    Teacher remove(Teacher teacher);

    Optional<Teacher> findByLoginAndPassword(String login, String password);
}
