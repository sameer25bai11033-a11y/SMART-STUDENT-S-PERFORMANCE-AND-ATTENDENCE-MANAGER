package com.smartstudent.util;

import java.util.Scanner;
import java.util.regex.Pattern;

public final class InputValidator {
    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private InputValidator() {}

    public static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty.");
        }
    }

    public static String readEmail(Scanner sc, String prompt) {
        while (true) {
            String email = readNonEmpty(sc, prompt);
            if (EMAIL.matcher(email).matches()) return email;
            System.out.println("Please enter a valid email address.");
        }
    }

    public static double readRange(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            try {
                double number = Double.parseDouble(value);
                if (number >= min && number <= max) return number;
            } catch (NumberFormatException ignored) {
                // handled below
            }
            System.out.printf("Enter a number between %.0f and %.0f.%n", min, max);
        }
    }

    public static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int number = Integer.parseInt(sc.nextLine().trim());
                if (number >= min && number <= max) return number;
            } catch (NumberFormatException ignored) {
                // handled below
            }
            System.out.printf("Enter an integer between %d and %d.%n", min, max);
        }
    }
}
