# UML Design Notes

## Use Case Diagram
Actor: Teacher/Administrator

Use cases:
- Manage Students
- Record Attendance
- Enter Marks
- Search Student
- Generate Reports
- Save/Load Data

## Sequence: Record Marks
```text
Teacher -> Main: choose Marks
Main -> StudentService: find student
StudentService -> Main: Student
Main -> PerformanceService: addMark()
PerformanceService -> Student: addOrUpdateMark()
Student -> Student: recalculateAverage()
Student --> Main: updated average/grade
Main --> Teacher: display result
```

## Class Diagram
```text
Student
- id
- name
- email
- course
- attendancePercent
- averageMarks
- subjectMarks
+ addOrUpdateMark()
+ recalculateAverage()
+ grade()

StudentService
+ addStudent()
+ updateStudent()
+ deleteStudent()
+ search()

AttendanceService
+ record()
+ recalculate()
+ belowThreshold()

PerformanceService
+ addMark()
+ topStudents()
+ studentsNeedingAttention()

CsvStorage
+ save()
+ load()
```
