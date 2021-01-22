package org.example.repository.interfaces;

import org.example.group.Group;

public interface RepositoryForGroupInterface extends RepositoryInterface<Group> {

    void initRatingForAllStudents();
}
