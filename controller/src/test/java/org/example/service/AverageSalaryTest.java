package org.example.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.example.model.Teacher;
import org.junit.Before;
import org.junit.Test;

public class AverageSalaryTest {

  private static Teacher teacher1;
  private static Teacher teacher2;

  @Before
  public void before() {
    teacher1 = new Teacher();
    teacher1.setSalary(List.of(getScaledBigDecimal(100), getScaledBigDecimal(300)));
    teacher2 = new Teacher();
    teacher2.setSalary(List.of(getScaledBigDecimal(200), getScaledBigDecimal(400)));
  }

  private BigDecimal getScaledBigDecimal(double i) {
    return BigDecimal.valueOf(i).setScale(2, RoundingMode.HALF_UP);
  }

  @Test
  public void calculateAverageSalary() {
    assertEquals(getScaledBigDecimal(200), AverageSalary.calculateAverageSalary(teacher1, 2));
    assertEquals(getScaledBigDecimal(300), AverageSalary.calculateAverageSalary(teacher2, 2));
    assertEquals(getScaledBigDecimal(300), AverageSalary.calculateAverageSalary(teacher1, 1));
    assertEquals(getScaledBigDecimal(400), AverageSalary.calculateAverageSalary(teacher2, 1));
  }

  @Test
  public void testCalculateAverageSalary() {
    List<Teacher> teachers = new ArrayList<>();
    teachers.add(teacher1);
    teachers.add(teacher2);
    assertEquals(getScaledBigDecimal(250), AverageSalary.calculateAverageSalary(teachers, 2));
    assertEquals(getScaledBigDecimal(350), AverageSalary.calculateAverageSalary(teachers, 1));
  }
}