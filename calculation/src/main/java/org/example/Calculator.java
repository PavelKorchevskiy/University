package org.example;

import org.example.model.Worker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Calculator {

    //calculating average salary for one worker
    public static BigDecimal calculateAverageSalary(Worker worker, int numberOfMonths) {
        List<BigDecimal> salaries = worker.getSalary();
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < numberOfMonths; i++) {
            BigDecimal res = sum.add(salaries.get(salaries.size() - 1 - i));
            sum = res;
        }
        return sum.divide(BigDecimal.valueOf(numberOfMonths), 2, RoundingMode.HALF_UP);
    }

    //calculating average salary for list of workers
    public static BigDecimal calculateAverageSalary(List<Worker> workers, int numberOfMonths) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < workers.size(); i++) {
            BigDecimal res = sum.add(calculateAverageSalary(workers.get(i), numberOfMonths));
            sum = res;
        }
        return sum.divide(BigDecimal.valueOf(workers.size()), 2, RoundingMode.HALF_UP);
    }
}
