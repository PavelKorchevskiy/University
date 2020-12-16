package org.example.start;

import org.example.Calculator;
import org.example.model.Worker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Start {

    public static void main(String[] args) {
        List<Worker> workers = new ArrayList<>();
        Worker worker1 = new Worker("Pavel");
        worker1.setSalary(initSalary());
        workers.add(worker1);
        Worker worker2 = new Worker("Sem");
        worker2.setSalary(initSalary());
        workers.add(worker2);
        Worker worker3 = new Worker("pasha2");
        worker3.setSalary(initSalary());
        workers.add(worker3);

        BigDecimal averageSalary = Calculator.calculateAverageSalary(workers, 6);
        System.out.format("Average salary - %.2f ", averageSalary);
    }

    public static List<BigDecimal> initSalary() {
        List<BigDecimal> salary = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            salary.add(BigDecimal.valueOf(Math.random() * 200 + 300));
        }
        return salary;
    }

}
