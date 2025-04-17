package com.aitutor.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseId;
    private String courseName;
    private String description;
    private Tutor tutor;
    private List<String> modules;
    private List<Student> enrolledStudents;

    public Course(String courseId, String courseName, String description, Tutor tutor) {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be null or empty");
        }
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        if (tutor == null) {
            throw new IllegalArgumentException("Tutor cannot be null");
        }

        // Remove any character validation to allow special characters
        this.courseId = courseId.trim();
        this.courseName = courseName.trim();
        this.description = description != null ? description.trim() : "";
        this.tutor = tutor;
        this.modules = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

    public void addModule(String module) {
        if (module == null || module.trim().isEmpty()) {
            throw new IllegalArgumentException("Module cannot be null or empty");
        }
        modules.add(module.trim());
    }

    public void enrollStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public String generateSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Course Summary: ").append(courseName).append("\n");
        summary.append("Description: ").append(description).append("\n");
        summary.append("Tutor: ").append(tutor.getUsername()).append("\n");
        summary.append("Number of Modules: ").append(modules.size()).append("\n");
        summary.append("Students Enrolled: ").append(enrolledStudents.size());
        return summary.toString();
    }

    // Getters and setters
    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public List<String> getModules() {
        return modules;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public int getEnrolledStudentsCount() {
        return enrolledStudents.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Course course = (Course) o;
        return courseId.equals(course.courseId);
    }

    @Override
    public int hashCode() {
        return courseId.hashCode();
    }
}
