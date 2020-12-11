package org.example.model;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Worker {
    private String name;
    private List<Double> salary;

    public Worker(String name) {
        if (StringUtils.isAlpha(name)) {
            this.name = name;
        } else {
            this.name = "Slave";
        }
        salary = new ArrayList<>();
    }
}
