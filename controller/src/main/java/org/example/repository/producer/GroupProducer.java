package org.example.repository.producer;

import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.example.repository.jdbc.RepositoryForGroupJDBC;
import org.example.repository.memory.RepositoryForGroupInMemory;

public class GroupProducer {

    public static RepositoryForGroupInterface getRepository() {
        //return RepositoryForGroupInMemory.getInstance();
        return RepositoryForGroupJDBC.getInstance();
    }
}
