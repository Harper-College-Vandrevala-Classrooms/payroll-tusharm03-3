package com.csc;

import java.util.Scanner;

public class Payroll {

    // Constants
    public static final float HOURLY_RATE = 16.78f;
    public static final float UNION_FEES = 10.00f;
    public static final float SOCIAL_SECURITY_TAX_RATE = 0.06f;
    public static final float FEDERAL_TAX_RATE = 0.14f;
    public static final float STATE_TAX_RATE = 0.05f;
    public static final int INSURANCE_COST_OVER_3_DEPENDENTS = 35;
    public static final int INSURANCE_COST_UNDER_3_DEPENDENTS = 15;
    public static final float LIFE_INSURANCE_NO_PLAN = 0.00f;
    public static final float LIFE_INSURANCE_SINGLE_PLAN = 5.00f;
    public static final float LIFE_INSURANCE_MARRIED_PLAN = 10.00f;
    public static final float LIFE_INSURANCE_MARRIED_WITH_CHILDREN_PLAN = 15.00f;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Payroll Program!\n");

        float hours = checkFloat("\nHow many hours did you work this week? ",
                                 "\nInput was invalid. Please input a positive number.\n");
        int dependents = checkInt("\nHow many children do you have? ",
                                  "\nInput was invalid. Please input a non-negative number.\n");
        dependents = Math.max(dependents, 0);  // Treat negative children as zero
        
        float insuranceCost = determineInsuranceCost(dependents);

        float lifeInsuranceCost = selectLifeInsurancePlan(dependents);

        float pay = calculateGrossPay(hours);
        float socSec = pay * SOCIAL_SECURITY_TAX_RATE;
        float fedTax = pay * FEDERAL_TAX_RATE;
        float stateTax = pay * STATE_TAX_RATE;
        float netPayAfterTaxes = pay - socSec - fedTax - stateTax;

        float unionFees = UNION_FEES;
        float netPay;

        if (netPayAfterTaxes >= (unionFees + insuranceCost + lifeInsuranceCost)) {
            netPay = netPayAfterTaxes - unionFees - insuranceCost - lifeInsuranceCost;
        } else {
            netPay = netPayAfterTaxes;
        }

        System.out.println("\nPayroll Stub:\n");
        System.out.printf("    Hours:   %.2f\n", hours);
        System.out.printf("    Rate:    %.2f $/hr\n", HOURLY_RATE);
        System.out.printf("    Gross:   $%.2f\n", pay);
        System.out.println("");
        System.out.printf("    SocSec:  $%.2f\n", socSec);
        System.out.printf("    FedTax:  $%.2f\n", fedTax);
        System.out.printf("    StTax:   $%.2f\n", stateTax);
        System.out.printf("    Union:   $%.2f\n", unionFees);
        System.out.printf("    Ins:     $%.2f\n", (float) insuranceCost);
        System.out.printf("    LifeIns: $%.2f\n", lifeInsuranceCost);
        System.out.println("");
        System.out.printf("    Net:    $%.2f\n", netPay);

        if (netPayAfterTaxes < (unionFees + insuranceCost + lifeInsuranceCost)) {
            System.out.println("\nThe employee still owes:");
            if (unionFees > 0) {
                System.out.printf("   Union: $%.2f\n", unionFees);
            }
            if (insuranceCost > 0) {
                System.out.printf("     Ins: $%.2f\n", (float) insuranceCost);
            }
            if (lifeInsuranceCost > 0) {
                System.out.printf("  LifeIns: $%.2f\n", lifeInsuranceCost);
            }
        }

        System.out.println("\nThank you for using the Payroll Program!");
    }

    public static int checkInt(String prompt, String errorMessage) {
        int userInput;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                userInput = scanner.nextInt();
                if (userInput >= 0) {
                    break;
                } else {
                    System.out.println(errorMessage);
                }
            } else {
                System.out.println(errorMessage);
                scanner.next(); // Clear invalid input
            }
        }
        return userInput;
    }

    public static float checkFloat(String prompt, String errorMessage) {
        float userInput;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextFloat()) {
                userInput = scanner.nextFloat();
                if (userInput >= 0) {
                    break;
                } else {
                    System.out.println(errorMessage);
                }
            } else {
                System.out.println(errorMessage);
                scanner.next(); 
            }
        }
        return userInput;
    }

    public static float calculateGrossPay(float hoursWorked) {
        if (hoursWorked > 40) {
            float overtimeHours = hoursWorked - 40;
            return (40 * HOURLY_RATE) + (overtimeHours * HOURLY_RATE * 1.5f);
        } else {
            return hoursWorked * HOURLY_RATE;
        }
    }

    public static int determineInsuranceCost(int dependents) {
        if (dependents >= 3) {
            return INSURANCE_COST_OVER_3_DEPENDENTS;
        } else {
            return INSURANCE_COST_UNDER_3_DEPENDENTS;
        }
    }

    public static float selectLifeInsurancePlan(int dependents) {
        int choice;
        while (true) {
            System.out.println("Which life insurance plan do you want to select?");
            System.out.println("  (1) no plan");
            System.out.println("  (2) single plan");
            System.out.println("  (3) married plan");
            System.out.println("  (4) married with children plan");
            System.out.print("Enter your choice (1-4): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        return LIFE_INSURANCE_NO_PLAN;
                    case 2:
                        return LIFE_INSURANCE_SINGLE_PLAN;
                    case 3:
                        return LIFE_INSURANCE_MARRIED_PLAN;
                    case 4:
                        if (dependents > 0) {
                            return LIFE_INSURANCE_MARRIED_WITH_CHILDREN_PLAN;
                        } else {
                            System.out.println("\nSorry! You need at least one child to select that plan.");
                            // Allow user to re-select the plan
                            continue;
                        }
                    default:
                        System.out.println("Invalid choice. Please select a valid plan.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.next(); 
            }
        }
    }
}
