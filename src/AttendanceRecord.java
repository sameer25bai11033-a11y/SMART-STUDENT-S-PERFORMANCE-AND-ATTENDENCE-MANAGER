package com.smartstudent.model;

import java.time.LocalDate;

public record AttendanceRecord(
        String studentId,
        LocalDate date,
        boolean present) {
}
