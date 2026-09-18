package com.smartstudent.util;

import com.smartstudent.model.Student;

import java.util.Collection;

public final class ConsoleUI {
    private ConsoleUI() {}

    public static void header(String title) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println(" " + title);
        System.out.println("============================================================");
    }

    public static void printStudents(Collection<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        students.forEach(System.out::println);
    }

    public static void pause(java.util.Scanner sc) {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }
}
