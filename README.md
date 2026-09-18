# Smart Student Performance & Attendance Manager

A command-line Java project for managing student records, attendance, marks, and performance reports.

## 1. Project Overview
The system provides a simple menu-driven interface for an academic administrator/teacher to:
- Add, view, search, update, and delete student records
- Record attendance and calculate attendance percentage
- Record subject marks and calculate average/grade
- Generate performance summaries and identify students below attendance threshold
- Save and load data using CSV files
- Validate input and handle common runtime/file errors

The project is designed around core Programming in Java concepts such as classes and objects, encapsulation, packages, collections, exception handling, file handling, Java Streams, and `java.time`.

## 2. Main Modules
1. **Student Management** — CRUD operations for students.
2. **Attendance Management** — attendance recording and percentage calculation.
3. **Marks & Performance** — marks entry, average, grade, and reports.
4. **File Storage** — persistent CSV storage.
5. **Validation & Error Handling** — input validation and safe exception handling.

## 3. Technologies
- Java 17+
- Standard Java SE libraries only
- Collections (`Map`, `List`)
- Streams API
- `java.time`
- File I/O (`java.nio.file`)
- No external dependencies

## 4. Project Structure
```text
src/
└── main/java/com/smartstudent/
    ├── app/Main.java
    ├── model/Student.java
    ├── model/AttendanceRecord.java
    ├── model/MarkRecord.java
    ├── service/StudentService.java
    ├── service/AttendanceService.java
    ├── service/PerformanceService.java
    ├── storage/CsvStorage.java
    ├── util/InputValidator.java
    └── util/ConsoleUI.java
data/
└── students.csv
```

## 5. Requirements
Install Java Development Kit (JDK) 17 or later.

Check:
```bash
java -version
javac -version
```

## 6. Compile
From the project root:

### Windows PowerShell
```powershell
New-Item -ItemType Directory -Force out
javac -d out (Get-ChildItem -Recurse -Filter *.java src/main/java | ForEach-Object { $_.FullName })
```

### Linux/macOS
```bash
mkdir -p out
find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt
```

## 7. Run
```bash
java -cp out com.smartstudent.app.Main
```

## 8. Test
The project includes a lightweight validation test class that uses only Java's built-in assertions.

Compile it together with the main source:
```bash
# Linux/macOS
javac -d out $(find src/main/java -name "*.java") src/test/java/com/smartstudent/ProjectValidationTest.java
java -ea -cp out com.smartstudent.ProjectValidationTest
```

On Windows PowerShell:
```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java src/main/java,src/test/java | ForEach-Object { $_.FullName })
java -ea -cp out com.smartstudent.ProjectValidationTest
```

Expected output:
```text
All validation tests passed.
```

## 9. Data Storage
Student records are stored in:
```text
data/students.csv
```
The application creates the file if it does not exist.

CSV format:
```text
id,name,email,course,attendancePercent,averageMarks,updatedDate
```

## 10. Typical Workflow
1. Start the application.
2. Add students.
3. Record attendance.
4. Enter subject marks.
5. View student details.
6. Generate performance report.
7. Exit. Data is saved automatically.

## 11. Notes for Evaluation
This is a command-line project and does not require a GUI, database server, or external library. The source code is separated into model, service, storage, utility, and application layers to demonstrate modular design.

## 12. Academic Use
This project is intended as a course project. Before submission, review every class, change sample data and wording where appropriate, and make sure you can explain the implementation during evaluation.
