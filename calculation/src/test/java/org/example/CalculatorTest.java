package org.example;

import org.example.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    static Worker worker1;
    static Worker worker2;
    static List<Worker> workers = new ArrayList<>();
    @Before
    public void init() {
        worker1 = new Worker("Slave");
        //(1 + 10 + 0 + 5) / 4 = 4
        worker1.setSalary(List.of(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.valueOf(5)));
        worker2 = new Worker("Alex");
        //(1 + 10 + 4 + 5) / 4 = 5
        worker2.setSalary(List.of(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.valueOf(4), BigDecimal.valueOf(5)));
        workers.add(worker1);
        workers.add(worker2);
    }
    @After
    public void finale() {
        worker1 = null;
        worker2 = null;
        workers.clear();
    }

    @Test
    public void calculateAverageSalaryForOneWorker() {
        assertEquals(0, BigDecimal.valueOf(4).compareTo(Calculator.calculateAverageSalary(worker1, 4)));
        assertEquals(0, BigDecimal.valueOf(5).compareTo(Calculator.calculateAverageSalary(worker2, 4)));
    }

    @Test
    public void testCalculateAverageSalaryForWorkers() {
        assertEquals(0, BigDecimal.valueOf(4.5).compareTo(Calculator.calculateAverageSalary(workers, 4)));
    }
}