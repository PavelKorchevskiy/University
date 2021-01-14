package org.example.group;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.subject.Subject;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Group {

    private int id;
    private Teacher teacher;
    private volatile Set<Student> students = new HashSet<>();
    private volatile Set<Subject> subjects = new HashSet<>();

    public Group(int id, Teacher teacher, Set<Student> students, Set<Subject> subjects) {
        this.id = id;
        this.teacher = teacher;
        this.students = students;
        this.subjects = subjects;

        //при создании группы инициализируем соответствующие поля у учителя и студентов
        teacher.setGroup(this);
        for (Student student: students) {
            student.setGroup(this);
            for (Subject subject: subjects) {
                student.getRatings().put(subject, 0);
            }
        }
    }

}
