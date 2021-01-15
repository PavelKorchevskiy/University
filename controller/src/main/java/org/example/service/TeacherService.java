package org.example.service;

import org.example.constans.Tags;
import org.example.group.Group;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.RepositoryForGroupInMemory;
import org.example.repository.RepositoryInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class TeacherService {
    public static String showGroup(Teacher teacher) {
        Set<Student> group = getALLStudents(teacher);
        String head = teacher.getFullName() + ", in your group " + getALLStudents(teacher).size() + " students:</br>";
        StringBuilder stringBuilder = new StringBuilder();
        for (Student student : group) {
            stringBuilder.append(" name - ").append(student.getRatingAsString()).append(", id - ")
                    .append(student.getId()).append(";</br>");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(";"));
        return head + stringBuilder.toString();
    }

    public static String showSalary(Teacher teacher) {
        StringBuffer sb = new StringBuffer();
        sb.append("Teacher - ").append(teacher.getFullName()).append(", with id - ")
                .append(teacher.getId()).append(Tags.BR).append("His salary: ");
        for (BigDecimal b : teacher.getSalary()) {
            sb.append(b.setScale(2, RoundingMode.HALF_UP)).append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(Tags.BR);
        return sb.toString();
    }
    public static Optional<Group> getGroup(Teacher teacher) {
        RepositoryInterface<Group> groupRepository = RepositoryForGroupInMemory.getInstance();
        List<Group> groups = groupRepository.findAll();
        Group group = null;
        for (Group g: groups) {
            if (g.getTeacher() == teacher) {
                group = g;
            }
        }
        return Optional.ofNullable(group);
    }
    public static Set<Student> getALLStudents(Teacher teacher) {
        Set<Student> students = new HashSet<>();
        if (getGroup(teacher).isPresent()) {
            students = getGroup(teacher).get().getStudents();
        }
        return students;
    }

    public static Optional<Student> getStudentById(Teacher teacher, int id) {
        Student student = null;
        for (Student s : getALLStudents(teacher)) {
            if (s.getId() == id) {
                student = s;
            }
        }
        return Optional.ofNullable(student);
    }
}
