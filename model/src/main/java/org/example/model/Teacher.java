package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class Teacher extends AbstractPerson {

    private Set<Student> group = new HashSet<>();
    private List<BigDecimal> salary = new ArrayList<>();

    public Teacher(String login, String password, String fullName, int age,
                   List<BigDecimal> salary) {
        super(login, password, fullName, age);
        this.salary = salary;
    }

    public void addStudentInGroup(Student student) {
        group.add(student);
    }

    public String showGroup() {
        String head = getFullName() + ", in your group " + group.size() + " students:</br>";
        StringBuilder stringBuilder = new StringBuilder();
        for (Student student : group) {
            stringBuilder.append(" name - ").append(student.getRatingAsString()).append(", login - ").append(student.getLogin()).append(";</br>");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(";"));
        return head + stringBuilder.toString();
    }

    //получить студента из группы
    public Optional<Student> getStudentByLogin(String login) {
        Student student = null;
        for (Student s : group) {
            if (s.getLogin().equalsIgnoreCase(login)) {
                student = s;
            }
        }
        return Optional.ofNullable(student);
    }

    public String showSalary() {
        StringBuilder sb = new StringBuilder();
        for (BigDecimal b : salary) {
            sb.append(b.setScale(2, RoundingMode.HALF_UP)).append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return "Teacher - " + getFullName() + ", with login - " + getLogin() + ".<br/> His salary: " + sb.toString() + "<br/>";
    }
}
