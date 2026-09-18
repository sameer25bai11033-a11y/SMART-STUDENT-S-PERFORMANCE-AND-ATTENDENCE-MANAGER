# System Architecture

```text
+----------------------+
|   Console User/CLI   |
+----------+-----------+
           |
           v
+----------------------+       +----------------------+
|     Main / UI        |------>|    InputValidator    |
+----------+-----------+       +----------------------+
           |
           v
+----------------------+ 
| Service Layer        |
| StudentService       |
| AttendanceService    |
| PerformanceService   |
+----------+-----------+
           |
           v
+----------------------+
| Model Layer          |
| Student              |
| AttendanceRecord     |
| MarkRecord           |
+----------+-----------+
           |
           v
+----------------------+
| CsvStorage / Files   |
+----------------------+
```

## Workflow
```text
Start
  |
Load CSV
  |
Main Menu
  |
  +--> Student CRUD
  |
  +--> Attendance
  |
  +--> Marks & Performance
  |
  +--> Reports
  |
Save Data
  |
Exit
```

## Class Relationships
```text
Main
 |-- StudentService ----> Student
 |-- AttendanceService -> AttendanceRecord
 |-- PerformanceService -> Student
 |-- CsvStorage -------> StudentService
 |-- ConsoleUI
 |-- InputValidator
```
