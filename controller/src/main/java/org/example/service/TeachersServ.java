package org.example.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.example.constans.Tags;
import org.example.exceptions.IllegalDataException;
import org.example.group.Group;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.example.repository.producer.GroupProducer;
import org.example.repository.producer.StudentProducer;
import org.example.repository.producer.TeacherProducer;
import org.springframework.stereotype.Service;

@Service
public class TeachersServ {

  private final RepositoryForTeachersInterface repository = TeacherProducer.getRepository();
  private final RepositoryForGroupInterface repositoryGroup = GroupProducer.getRepository();
  private final RepositoryForStudentsInterface repositoryStudent = StudentProducer.getRepository();

  public Student save(Student student) {
    return repositoryStudent.save(student);
  }
  public Teacher saveTeacher(Teacher teacher) {
    return repository.save(teacher);
  }

  public Teacher getTeacherWithLoginAngPassword(String login, String password) {
    return repository.findByLoginAndPassword(login, password)
        .orElseThrow(
            () -> new IllegalDataException("Teacher with this login and password doesn't exist"));
  }

  public Teacher getTeacherWithId(int id) {
    return repository.findById(id)
        .orElseThrow(
            () -> new IllegalDataException("Teacher with id - " + id + " doesn't exist"));
  }

  public Optional<Group> getGroup(Teacher teacher) {
    List<Group> groups = repositoryGroup.findAll();
    Group group = null;
    for (Group g : groups) {
      if (g.getTeacher().getId() == teacher.getId()) {
        group = g;
      }
    }
    return Optional.ofNullable(group);
  }

  public Set<Student> getALLStudents(Teacher teacher) {
    Set<Student> students = new HashSet<>();
    if (getGroup(teacher).isPresent()) {
      students = getGroup(teacher).get().getStudents();
    }
    return students;
  }

  public String showGroup(Teacher teacher) {
    Set<Student> students = getALLStudents(teacher);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(teacher.getName()).append(", in your group ").append(students.size())
        .append(" students:").append(Tags.BR);
    for (Student s : students) {
      stringBuilder.append(" name - ").append(StudentService.getRatingAsString(s)).append(", id - ")
          .append(s.getId()).append(Tags.BR);
    }
    return stringBuilder.toString();
  }

  public Optional<Student> getStudentById(Teacher teacher, int id) {
    Student student = null;
    for (Student s : getALLStudents(teacher)) {
      if (s.getId() == id) {
        student = s;
      }
    }
    return Optional.ofNullable(student);
  }

  public static BigDecimal calculateAverageSalary(Teacher teacher, int numberOfMonths) {
    List<BigDecimal> salaries = new ArrayList<>(teacher.getSalary());
    Collections.reverse(salaries);
    BigDecimal sum = BigDecimal.ZERO;
    try {
      for (int i = 0; i < numberOfMonths; i++) {
        sum = sum.add(salaries.get(i));
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalDataException("Number of months is too big");
    }
    return sum.divide(BigDecimal.valueOf(numberOfMonths), 2, RoundingMode.HALF_UP);
  }

  //calculating average salary for list of teachers
  public BigDecimal calculateAverageSalary(List<Teacher> teachers, int numberOfMonths) {
    BigDecimal sum = BigDecimal.ZERO;
    for (Teacher teacher : teachers) {
      sum = sum.add(calculateAverageSalary(teacher, numberOfMonths));
    }
    return sum.divide(BigDecimal.valueOf(teachers.size()), 2, RoundingMode.HALF_UP);
  }

  public String showAllTeachers() {
    List<Teacher> teachers = repository.findAll();
    StringBuilder sb = new StringBuilder();
    for (Teacher teacher : teachers) {
      sb.append(showSalary(teacher));
    }
    return sb.toString();
  }

  public static String showSalary(Teacher teacher) {
    StringBuilder sb = new StringBuilder();
    sb.append("Teacher - ").append(teacher.getName()).append(", with id - ")
        .append(teacher.getId()).append(Tags.BR).append("His salary: ");
    for (BigDecimal b : teacher.getSalary()) {
      sb.append(b.setScale(2, RoundingMode.HALF_UP)).append(", ");
    }
    sb.deleteCharAt(sb.lastIndexOf(","));
    sb.append(Tags.BR);
    return sb.toString();
  }

  public String showAverageSalaryForAllTeacher(int numberOfMonths) {
    List<Teacher> teachers = repository.findAll();
    BigDecimal averageSalary = calculateAverageSalary(teachers, numberOfMonths);
    StringBuffer sb = new StringBuffer();
    sb.append("Average salary for ").append(teachers.size()).append(" teachers, for ")
        .append(numberOfMonths)
        .append(" months - ").append(averageSalary.setScale(2, RoundingMode.HALF_UP))
        .append(Tags.BR);
    for (Teacher teacher : teachers) {
      sb.append("Average salary for ").append(teacher.getName()).append(" - ")
          .append(calculateAverageSalary(teacher, numberOfMonths)
              .setScale(2, RoundingMode.HALF_UP)).append(Tags.BR);
    }
    return String.valueOf(sb);
  }

}
