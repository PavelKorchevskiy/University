package org.example.group;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.subject.Subject;

import java.util.Set;

@Getter
@Setter
public class Group {

    private int id;
    private Teacher teacher;
    private volatile Set<Student> students;
    private volatile Set<Subject> subjects;

    public Group(int id, Teacher teacher, Set<Student> students, Set<Subject> subjects) {
        this.id = id;
        this.teacher = teacher;
        this.students = students;
        this.subjects = subjects;

        for (Student student: students) {
            for (Subject subject: subjects) {
                student.getRatings().putIfAbsent(subject, 0);
            }
        }
    }
}
