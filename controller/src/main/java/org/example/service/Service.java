package org.example.service;

import org.example.model.Worker;
import org.example.repository.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Service {
    //calculating average salary for one worker
    public BigDecimal calculateAverageSalary(Worker worker, int numberOfMonths) {
        List<BigDecimal> salaries = worker.getSalary();
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < numberOfMonths; i++) {
            sum = sum.add(salaries.get(salaries.size() - 1 - i));
        }
        return sum.divide(BigDecimal.valueOf(numberOfMonths), 2, RoundingMode.HALF_UP);
    }

    //calculating average salary for list of workers
    public BigDecimal calculateAverageSalary(List<Worker> workers, int numberOfMonths) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Worker worker : workers) {
            sum = sum.add(calculateAverageSalary(worker, numberOfMonths));
        }
        return sum.divide(BigDecimal.valueOf(workers.size()), 2, RoundingMode.HALF_UP);
    }

    public String report(int countOfMonth) {
        Repository repository = new Repository();
        List<Worker> workersList = repository.getWorkersList();
        BigDecimal averageSalary = calculateAverageSalary(workersList, countOfMonth);
        return "Average salary for " + workersList.size() + " workers - " + averageSalary.setScale(2, RoundingMode.HALF_UP);

    }
}
