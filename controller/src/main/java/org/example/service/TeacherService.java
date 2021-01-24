package org.example.service;

import org.example.constans.Tags;
import org.example.group.Group;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.example.repository.producer.GroupProducer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class TeacherService {

    public static String showGroup(Teacher teacher) {
        try {
            Set<Student> students = getALLStudents(teacher);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(teacher.getName()).append(", in your group ").append(students.size())
                    .append(" students:").append(Tags.BR);
            for (Student s : students) {
                stringBuilder.append(" name - ").append(s.getRatingAsString()).append(", id - ")
                        .append(s.getId()).append(Tags.BR);
            }
            return stringBuilder.toString();
        } catch (IndexOutOfBoundsException e) {
            return "kakto tak IndexOutOfBoundsException";
        }
    }

    public static String showSalary(Teacher teacher) {
        StringBuffer sb = new StringBuffer();
        sb.append("Teacher - ").append(teacher.getName()).append(", with id - ")
                .append(teacher.getId()).append(Tags.BR).append("His salary: ");
        for (BigDecimal b : teacher.getSalary()) {
            sb.append(b.setScale(2, RoundingMode.HALF_UP)).append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(Tags.BR);
        return sb.toString();
    }
    public static Optional<Group> getGroup(Teacher teacher) {
        RepositoryForGroupInterface groupRepository = GroupProducer.getRepository();
        List<Group> groups = groupRepository.findAll();
        Group group = null;
        for (Group g: groups) {
            if (g.getTeacher().getId() == teacher.getId()) {
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
