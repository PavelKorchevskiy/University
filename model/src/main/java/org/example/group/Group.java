package org.example.group;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.subject.Subject;

@Getter
@Setter
@NoArgsConstructor
public class Group {
    private List<Student> students;
    private Teacher teacher;
    private List<Subject> subjects;
}
