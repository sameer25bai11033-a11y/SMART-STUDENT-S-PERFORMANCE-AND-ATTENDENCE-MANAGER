package com.smartstudent.storage;

import com.smartstudent.model.Student;
import com.smartstudent.service.StudentService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

public class CsvStorage {
    private final Path path;

    public CsvStorage(Path path) {
        this.path = path;
    }

    public void save(StudentService service) throws IOException {
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            writer.write("id,name,email,course,attendancePercent,averageMarks,updatedDate");
            writer.newLine();

            for (Student s : service.getAllStudents()) {
                writer.write(String.join(",",
                        clean(s.getId()),
                        clean(s.getName()),
                        clean(s.getEmail()),
                        clean(s.getCourse()),
                        String.format(java.util.Locale.US, "%.2f", s.getAttendancePercent()),
                        String.format(java.util.Locale.US, "%.2f", s.getAverageMarks()),
                        s.getUpdatedDate().toString()));
                writer.newLine();
            }
        }
    }

    public void load(StudentService service) throws IOException {
        if (!Files.exists(path)) return;

        service.clear();
        var lines = Files.readAllLines(path);

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            String[] p = line.split(",", -1);
            if (p.length < 7) continue;

            Student s = new Student(p[0], p[1], p[2], p[3]);
            s.setAttendancePercent(Double.parseDouble(p[4]));

            s.setAverageMarks(Double.parseDouble(p[5]));
            service.addStudent(s);
        }
    }

    private String clean(String value) {
        return value.replace(",", " ");
    }
}
