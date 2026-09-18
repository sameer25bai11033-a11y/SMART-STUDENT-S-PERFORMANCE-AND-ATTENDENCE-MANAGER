package com.smartstudent;

import com.smartstudent.model.Student;
import com.smartstudent.service.StudentService;

public class ProjectValidationTest {
    public static void main(String[] args) {
        StudentService service = new StudentService();
        Student s = new Student("T001", "Test User", "test@example.com", "BCA");

        service.addStudent(s);
        assert service.count() == 1 : "Student was not added";

        s.addOrUpdateMark("Java", 80);
        s.addOrUpdateMark("Math", 60);
        assert Math.abs(s.getAverageMarks() - 70.0) < 0.001 : "Average calculation failed";
        assert s.grade().equals("B") : "Grade calculation failed";

        s.setAttendancePercent(74);
        assert s.getAttendancePercent() == 74 : "Attendance update failed";

        assert service.search("test").size() == 1 : "Search failed";
        assert service.deleteStudent("T001") : "Delete failed";

        System.out.println("All validation tests passed.");
    }
}
