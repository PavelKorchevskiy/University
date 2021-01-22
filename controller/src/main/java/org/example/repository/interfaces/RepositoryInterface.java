package org.example.repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface RepositoryInterface<T> {

    List<T> findAll();

    Optional<T> findById(int id);

    T save(T t);

    T remove(T t);
}
