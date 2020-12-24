package org.example.repository;

import org.example.model.Teacher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryForTeachersInMemory implements RepositoryForTeachersInterface {

    private static RepositoryForTeachersInMemory instance;
    private Map<String, Teacher> map;

    private RepositoryForTeachersInMemory() {
        map = new ConcurrentHashMap<>();
        for (Teacher teacher: initTeachers()) {
            map.put(teacher.getLogin(), teacher);
        }
    }

    public static RepositoryForTeachersInMemory getInstance() {
        if (instance == null) {
            synchronized (RepositoryForTeachersInMemory.class) {
                if (instance == null) {
                    instance = new RepositoryForTeachersInMemory();
                }
            }
        }
        return instance;
    }

    private List<Teacher> initTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("teacher1", "teacher1", "Ivanov Ivan", 34, initSalary()));
        teachers.add(new Teacher("teacher3", "teacher3", "Gallieo Galiley", 64, initSalary()));
        teachers.add(new Teacher("teacher4", "teacher4", "Albert Einschtein", 54, initSalary()));
        teachers.add(new Teacher("teacher5", "teacher5", "Master Ioda", 334, initSalary()));
        return teachers;
    }

    private List<BigDecimal> initSalary() {
        List<BigDecimal> salary = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            salary.add(BigDecimal.valueOf(Math.random() * 200 + 300));
        }
        return salary;
    }

    @Override
    public List<Teacher> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Teacher> findByLogin(String login) {
        return Optional.ofNullable(map.get(login));
    }

    @Override
    public Teacher save(Teacher teacher) {
        map.put(teacher.getLogin(), (Teacher) teacher);
        return teacher;
    }

    @Override
    public Teacher remove(Teacher teacher) {
        return map.remove(teacher.getLogin());
    }

    @Override
    public Optional<Teacher> findByLoginAndPassword(String login, String password) {
        Teacher result = null;
        for (Map.Entry<String, Teacher> entry: map.entrySet()) {
            if (entry.getValue().getLogin().equals(login) && entry.getValue().getPassword().equals(password)) {
                result = entry.getValue();
            }
        }
        return Optional.ofNullable(result);
    }
}
