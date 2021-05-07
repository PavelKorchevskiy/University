package org.example.repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface RepositoryInterface<T> {

  List<T> findAll();

  Optional<T> findById(int id);

  <S extends T> S save(S s);

  //T remove(T t);
}
