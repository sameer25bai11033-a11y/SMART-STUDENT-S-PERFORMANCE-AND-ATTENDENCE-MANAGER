package com.smartstudent.model;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Student {
    private final String id;
    private String name;
    private String email;
    private String course;
    private double attendancePercent;
    private double averageMarks;
    private LocalDate updatedDate;

    private final Map<String, Double> subjectMarks = new LinkedHashMap<>();

    public Student(String id, String name, String email, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.updatedDate = LocalDate.now();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
    public double getAttendancePercent() { return attendancePercent; }
    public double getAverageMarks() { return averageMarks; }

    public void setAverageMarks(double averageMarks) {
        this.averageMarks = averageMarks;
        touch();
    }
    public LocalDate getUpdatedDate() { return updatedDate; }
    public Map<String, Double> getSubjectMarks() { return subjectMarks; }

    public void setName(String name) {
        this.name = name;
        touch();
    }

    public void setEmail(String email) {
        this.email = email;
        touch();
    }

    public void setCourse(String course) {
        this.course = course;
        touch();
    }

    public void setAttendancePercent(double attendancePercent) {
        this.attendancePercent = attendancePercent;
        touch();
    }

    public void addOrUpdateMark(String subject, double mark) {
        subjectMarks.put(subject, mark);
        recalculateAverage();
        touch();
    }

    public void removeMark(String subject) {
        subjectMarks.remove(subject);
        recalculateAverage();
        touch();
    }

    public void recalculateAverage() {
        averageMarks = subjectMarks.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public String grade() {
        if (averageMarks >= 90) return "A+";
        if (averageMarks >= 80) return "A";
        if (averageMarks >= 70) return "B";
        if (averageMarks >= 60) return "C";
        if (averageMarks >= 50) return "D";
        return "F";
    }

    private void touch() {
        updatedDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return String.format(
                "%s | %-18s | %-24s | %-12s | Attendance: %6.2f%% | Avg: %6.2f | Grade: %s",
                id, name, email, course, attendancePercent, averageMarks, grade());
    }
}
