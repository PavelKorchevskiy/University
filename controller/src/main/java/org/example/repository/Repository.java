package org.example.repository;

import org.example.model.Worker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    public List<Worker> getWorkersList() {
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker("Pavel", initSalary()));
        workers.add(new Worker("Anton", initSalary()));
        workers.add(new Worker("Alex", initSalary()));
        workers.add(new Worker("Christian", initSalary()));
        workers.add(new Worker("Anna", initSalary()));
        workers.add(new Worker("Gramash!", initSalary()));
        return workers;
    }

    private List<BigDecimal> initSalary() {
        List<BigDecimal> salary = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            salary.add(BigDecimal.valueOf(Math.random() * 200 + 300));
        }
        return salary;
    }
}
