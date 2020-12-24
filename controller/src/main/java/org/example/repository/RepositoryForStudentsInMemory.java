package org.example.repository;

import org.example.model.Student;
import org.example.subject.Subject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryForStudentsInMemory implements RepositoryForStudentsInterface {

    private static RepositoryForStudentsInMemory instance;
    private Map<String, Student> map;

    private RepositoryForStudentsInMemory() {
        map = new ConcurrentHashMap<>();
        for (Student student: initStudents()) {
            map.put(student.getLogin(), student);
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
        students.add(new Student("student1", "student1", "Lena", 24, Set.of(Subject.Chemistry, Subject.Math)));
        students.add(new Student("student2", "student2", "Dima", 25, Set.of(Subject.Chemistry, Subject.Math)));
        students.add(new Student("student3", "student3", "Tolik", 26, Set.of(Subject.Chemistry, Subject.Biology, Subject.Geography)));
        students.add(new Student("student4", "student4", "Bill", 27, Set.of(Subject.Chemistry, Subject.Biology, Subject.Geography)));
        students.add(new Student("student5", "student5", "Tom", 28, Set.of(Subject.Chemistry, Subject.Biology, Subject.Geography)));
        students.add(new Student("student6", "student6", "Kim", 23, Set.of(Subject.Geography, Subject.Math)));
        students.add(new Student("student7", "student7", "Pasha", 24, Set.of(Subject.Geography, Subject.Math)));
        students.add(new Student("студент8", "student8", "Dasha", 34, Set.of(Subject.Geography, Subject.Math)));
        return students;
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(map.values());
    }

    //кем фигурировать: Person, AbstractPerson, Student ?
    @Override
    public Optional<Student> findByLogin(String login) {
        return Optional.ofNullable(map.get(login));
    }

    @Override
    public Student save(Student student) {
        map.put(student.getLogin(), student);
        return student;
    }

    @Override
    public Student remove(Student student) {
        return map.remove(student.getLogin());
    }

    @Override
    public Optional<Student> findByLoginAndPassword(String login, String password) {
        Student result = null;
        for (Map.Entry<String, Student> entry: map.entrySet()) {
            if (entry.getValue().getLogin().equals(login) && entry.getValue().getPassword().equals(password)) {
                result = entry.getValue();
            }
        }
        return Optional.ofNullable(result);
    }
}
