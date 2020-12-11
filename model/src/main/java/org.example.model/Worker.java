package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Worker {
    private String name;
    private List<Double> salary;

    public Worker(String name) {
        this.name = name;
        salary = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getSalary() {
        return salary;
    }

    public void setSalary(List<Double> salary) {
        this.salary = salary;
    }
}
