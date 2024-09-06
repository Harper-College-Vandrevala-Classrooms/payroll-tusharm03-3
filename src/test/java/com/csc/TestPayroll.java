package com.csc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPayroll {

    private Payroll payroll;

    @BeforeEach
    void setUp() {
        payroll = new Payroll();
    }

    @Test
    public void testCalculateGrossPayWithOvertime() {
        // Test for 45 hours of work
        float hours = 45;
        float expectedGrossPay = (40 * Payroll.HOURLY_RATE) + ((5) * 1.5f * Payroll.HOURLY_RATE);
        assertEquals(expectedGrossPay, payroll.calculateGrossPay(hours));
    }

    @Test
    public void testCalculateGrossPayWithoutOvertime() {
        // Test for 30 hours of work
        float hours = 30;
        float expectedGrossPay = hours * Payroll.HOURLY_RATE;
        assertEquals(expectedGrossPay, payroll.calculateGrossPay(hours));
    }

    @Test
    public void testDetermineInsuranceCostWithThreeDependents() {
        // Test for 3 dependents
        int dependents = 3;
        int expectedInsuranceCost = 35;
        assertEquals(expectedInsuranceCost, payroll.determineInsuranceCost(dependents));
    }

    @Test
    public void testDetermineInsuranceCostWithLessThanThreeDependents() {
        // Test for 2 dependents
        int dependents = 2;
        int expectedInsuranceCost = 15;
        assertEquals(expectedInsuranceCost, payroll.determineInsuranceCost(dependents));
    }
}
