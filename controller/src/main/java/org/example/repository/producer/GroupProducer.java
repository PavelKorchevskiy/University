package org.example.repository.producer;

import org.example.group.Group;
import org.example.repository.interfaces.RepositoryInterface;
import org.example.repository.jdbc.RepositoryForGroupJDBC;
import org.example.repository.memory.RepositoryForGroupInMemory;

public class GroupProducer {

    public static RepositoryInterface<Group> getRepository() {
        return RepositoryForGroupInMemory.getInstance();
        // RepositoryForGroupJDBC.getInstance();
    }
}
