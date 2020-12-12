package org.example.start;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class StartTest {

    @Test
    public void initSalary() {
        List<BigDecimal> list = Start.initSalary();
        for (BigDecimal bd: list) {
            long salary = bd.longValue();
            assertTrue(salary >= 300 && salary <= 500);
        }
    }
}