package org.example;

import org.example.model.Worker;

import java.util.List;
import java.util.OptionalDouble;

public class Calculator {

    //calculating average salary for one worker
    public static double calculateAverageSalary(Worker worker, int numberOfMonths) {
        double averageSalary = 0;
        List<Double> salaries = worker.getSalary();
        OptionalDouble optionalAverageSalary = salaries
                .subList(salaries.size() - numberOfMonths, salaries.size())
                .stream()
                .mapToDouble(d -> d)
                .average();
        if (optionalAverageSalary.isPresent()) {
            averageSalary = optionalAverageSalary.getAsDouble();
        }
        return averageSalary;
    }

    //calculating average salary for list of workers
    public static double calculateAverageSalary(List<Worker> workers, int numberOfMonths) {
        double sum = 0;
        for (Worker w : workers) {
            sum += calculateAverageSalary(w, numberOfMonths);
        }
        return sum / workers.size();
    }
}
