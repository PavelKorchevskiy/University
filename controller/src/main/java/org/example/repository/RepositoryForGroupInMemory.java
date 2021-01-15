package org.example.repository;

import org.example.group.Group;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.subject.Subject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryForGroupInMemory implements RepositoryInterface<Group> {

    private static volatile RepositoryForGroupInMemory instance;
    private final Map<Integer, Group> map = new ConcurrentHashMap<>();

    private RepositoryForGroupInMemory() {
        for (Group group: initGroups()) {
            map.put(group.getId(), group);
        }
    }

    public static RepositoryForGroupInMemory getInstance() {
        if (instance == null) {
            synchronized (RepositoryForGroupInMemory.class) {
                if (instance == null) {
                    instance = new RepositoryForGroupInMemory();
                }
            }
        }
        return instance;
    }

    private List<Group> initGroups() {
        RepositoryForTeachersInterface repositoryForTeachers = RepositoryForTeachersInMemory.getInstance();
        List<Teacher> teachers = repositoryForTeachers.findAll();
        RepositoryForStudentsInterface repositoryForStudents = RepositoryForStudentsInMemory.getInstance();
        List<Student> students = repositoryForStudents.findAll();
    Group firstGroup = new Group(1, teachers.get(0)
            , Set.of(students.get(0), students.get(1), students.get(2), students.get(3))
            , Set.of(Subject.Biology, Subject.Chemistry));
    Group secondGroup = new Group(2, teachers.get(1)
            , Set.of(students.get(3), students.get(4), students.get(5), students.get(6))
            , Set.of(Subject.Math, Subject.Geography));
    Group thirdGroup = new Group(3, teachers.get(2)
            , Set.of(students.get(6), students.get(7))
            , Set.of(Subject.Art, Subject.History));
        return List.of(firstGroup, secondGroup, thirdGroup);
    }
    @Override
    public List<Group> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Group> findById(int id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Group save(Group group) {
        map.put(group.getId(), group);
        return group;
    }

    @Override
    public Group remove(Group group) {
        return map.remove(group.getId());
    }
}
