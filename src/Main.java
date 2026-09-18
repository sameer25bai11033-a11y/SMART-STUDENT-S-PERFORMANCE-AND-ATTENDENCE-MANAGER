package com.smartstudent.app;

import com.smartstudent.model.Student;
import com.smartstudent.service.AttendanceService;
import com.smartstudent.service.PerformanceService;
import com.smartstudent.service.StudentService;
import com.smartstudent.storage.CsvStorage;
import com.smartstudent.util.ConsoleUI;
import com.smartstudent.util.InputValidator;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final double ATTENDANCE_THRESHOLD = 75.0;

    private final Scanner scanner = new Scanner(System.in);
    private final StudentService studentService = new StudentService();
    private final AttendanceService attendanceService = new AttendanceService();
    private final PerformanceService performanceService = new PerformanceService();
    private final CsvStorage storage = new CsvStorage(Path.of("data", "students.csv"));

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        loadData();

        boolean running = true;
        while (running) {
            ConsoleUI.header("SMART STUDENT PERFORMANCE & ATTENDANCE MANAGER");
            System.out.println("1. Student Management");
            System.out.println("2. Attendance Management");
            System.out.println("3. Marks & Performance");
            System.out.println("4. Reports");
            System.out.println("5. Save Data");
            System.out.println("0. Exit");

            int choice = InputValidator.readInt(scanner, "Choose an option: ", 0, 5);

            try {
                switch (choice) {
                    case 1 -> studentMenu();
                    case 2 -> attendanceMenu();
                    case 3 -> marksMenu();
                    case 4 -> reportMenu();
                    case 5 -> saveData();
                    case 0 -> {
                        saveData();
                        running = false;
                        System.out.println("Goodbye!");
                    }
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private void studentMenu() {
        boolean back = false;
        while (!back) {
            ConsoleUI.header("STUDENT MANAGEMENT");
            System.out.println("1. Add student");
            System.out.println("2. View all students");
            System.out.println("3. Search student");
            System.out.println("4. Update student");
            System.out.println("5. Delete student");
            System.out.println("0. Back");

            int choice = InputValidator.readInt(scanner, "Choose: ", 0, 5);
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> ConsoleUI.printStudents(studentService.getAllStudents());
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 0 -> back = true;
            }
            if (!back) ConsoleUI.pause(scanner);
        }
    }

    private void addStudent() {
        ConsoleUI.header("ADD STUDENT");
        String id = InputValidator.readNonEmpty(scanner, "Student ID: ");
        String name = InputValidator.readNonEmpty(scanner, "Name: ");
        String email = InputValidator.readEmail(scanner, "Email: ");
        String course = InputValidator.readNonEmpty(scanner, "Course: ");

        studentService.addStudent(new Student(id, name, email, course));
        System.out.println("Student added successfully.");
    }

    private void searchStudent() {
        String keyword = InputValidator.readNonEmpty(scanner, "Search ID/name/course: ");
        ConsoleUI.printStudents(studentService.search(keyword));
    }

    private void updateStudent() {
        String id = InputValidator.readNonEmpty(scanner, "Student ID to update: ");
        Student s = studentService.getStudent(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        String name = InputValidator.readNonEmpty(scanner, "New name: ");
        String email = InputValidator.readEmail(scanner, "New email: ");
        String course = InputValidator.readNonEmpty(scanner, "New course: ");

        studentService.updateStudent(id, name, email, course);
        System.out.println("Student updated.");
    }

    private void deleteStudent() {
        String id = InputValidator.readNonEmpty(scanner, "Student ID to delete: ");
        System.out.println(studentService.deleteStudent(id)
                ? "Student deleted."
                : "Student not found.");
    }

    private void attendanceMenu() {
        ConsoleUI.header("ATTENDANCE MANAGEMENT");
        String id = InputValidator.readNonEmpty(scanner, "Student ID: ");
        Student s = studentService.getStudent(id);
        if (s == null) {
            System.out.println("Student not found.");
            ConsoleUI.pause(scanner);
            return;
        }

        int present = InputValidator.readInt(scanner, "Present classes: ", 0, 1000);
        int total = InputValidator.readInt(scanner, "Total classes: ", 1, 1000);

        if (present > total) {
            System.out.println("Present classes cannot exceed total classes.");
        } else {
            // Add one record per class to make the calculation transparent.
            LocalDate date = LocalDate.now();
            for (int i = 0; i < total; i++) {
                attendanceService.record(s, date.minusDays(total - i - 1), i < present);
            }
            System.out.printf("Attendance updated: %.2f%%%n", s.getAttendancePercent());
        }
        ConsoleUI.pause(scanner);
    }

    private void marksMenu() {
        ConsoleUI.header("MARKS & PERFORMANCE");
        String id = InputValidator.readNonEmpty(scanner, "Student ID: ");
        Student s = studentService.getStudent(id);
        if (s == null) {
            System.out.println("Student not found.");
            ConsoleUI.pause(scanner);
            return;
        }

        String subject = InputValidator.readNonEmpty(scanner, "Subject: ");
        double marks = InputValidator.readRange(scanner, "Marks (0-100): ", 0, 100);
        performanceService.addMark(s, subject, marks);

        System.out.printf("Average: %.2f | Grade: %s%n",
                s.getAverageMarks(), s.grade());
        ConsoleUI.pause(scanner);
    }

    private void reportMenu() {
        ConsoleUI.header("REPORTS");
        System.out.println("Total students: " + studentService.count());

        System.out.println("\nStudents below 75% attendance:");
        List<Student> lowAttendance =
                attendanceService.belowThreshold(studentService.getAllStudents(), ATTENDANCE_THRESHOLD);
        ConsoleUI.printStudents(lowAttendance);

        System.out.println("\nTop 3 students by average marks:");
        ConsoleUI.printStudents(performanceService.topStudents(studentService.getAllStudents(), 3));

        System.out.println("\nStudents needing attention:");
        ConsoleUI.printStudents(performanceService.studentsNeedingAttention(studentService.getAllStudents()));

        ConsoleUI.pause(scanner);
    }

    private void loadData() {
        try {
            storage.load(studentService);
            System.out.println("Loaded " + studentService.count() + " student(s).");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Could not load saved data: " + e.getMessage());
        }
    }

    private void saveData() {
        try {
            storage.save(studentService);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }
}
