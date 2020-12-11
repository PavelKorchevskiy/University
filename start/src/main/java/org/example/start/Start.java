package org.example.start;

import org.example.Calculator;
import org.example.model.Worker;

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

        double averageSalary = Calculator.calculateAverageSalary(workers, 6);
        System.out.format("%.3f", averageSalary);
    }

    public static List<Double> initSalary() {
        List<Double> salary = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            salary.add(Math.random() * 200 + 300);
        }
        return salary;
    }

}
