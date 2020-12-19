package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class Worker implements Serializable {
    private String name;
    private List<BigDecimal> salary;

    public Worker(String name) {
        if (StringUtils.isAlpha(name)) {
            this.name = name;
        } else {
            this.name = "Slave";
        }
        salary = new ArrayList<>();
    }

    public Worker(String name, List<BigDecimal> salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Worker : name = " + name + " , salary = " + salary;
    }
}
