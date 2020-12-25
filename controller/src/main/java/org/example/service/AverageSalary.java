package org.example.service;

import org.example.model.Teacher;
import org.example.repository.RepositoryForTeachersInMemory;
import org.example.repository.RepositoryForTeachersInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AverageSalary {



  //calculating average salary for one teacher
  public static BigDecimal calculateAverageSalary(Teacher teacher, int numberOfMonths) {
    List<BigDecimal> salaries = teacher.getSalary();
    BigDecimal sum = BigDecimal.ZERO;
    for (int i = 0; i < numberOfMonths; i++) {
      sum = sum.add(salaries.get(salaries.size() - 1 - i));
    }
    return sum.divide(BigDecimal.valueOf(numberOfMonths), 2, RoundingMode.HALF_UP);
  }

  //calculating average salary for list of teachers
  public static BigDecimal calculateAverageSalary(List<Teacher> teachers, int numberOfMonths) {
    BigDecimal sum = BigDecimal.ZERO;
    for (Teacher teacher : teachers) {
      sum = sum.add(calculateAverageSalary(teacher, numberOfMonths));
    }
    return sum.divide(BigDecimal.valueOf(teachers.size()), 2, RoundingMode.HALF_UP);
  }

  public static String report(int countOfMonth) {
    RepositoryForTeachersInterface repository = RepositoryForTeachersInMemory.getInstance();
    List<Teacher> teachersList = repository.findAll();
    BigDecimal averageSalary = calculateAverageSalary(teachersList, countOfMonth);
    return "Average salary for " + teachersList.size() + " teachers - " + averageSalary
        .setScale(2, RoundingMode.HALF_UP);
  }

  public static String showAllTeachers() {
    RepositoryForTeachersInterface repository = RepositoryForTeachersInMemory.getInstance();
    List<Teacher> teachers = repository.findAll();
    StringBuilder sb = new StringBuilder();
    for (Teacher teacher: teachers) {
      sb.append(teacher.showSalary());
    }
    return sb.toString();
  }
}
