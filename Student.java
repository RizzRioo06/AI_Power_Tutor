package com.aitutor.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Course> enrolledCourses;
    private LearningPath currentLearningPath;
    private double overallProgress;

    public Student(String userId, String username, String password, String email) {
        super(userId, username, password, email);
        this.enrolledCourses = new ArrayList<>();
        this.overallProgress = 0.0;
    }

    @Override
    public void displayProfile() {
        System.out.println("\n=== Student Profile ===");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Overall Progress: " + String.format("%.2f%%", overallProgress));
        System.out.println("Enrolled Courses: " + enrolledCourses.size());
    }

    @Override
    public void viewDashboard() {
        System.out.println("\n=== Student Dashboard ===");
        System.out.println("Current Learning Path: " + 
            (currentLearningPath != null ? currentLearningPath.getPathName() : "None"));
        System.out.println("\nEnrolled Courses:");
        for (Course course : enrolledCourses) {
            System.out.println("- " + course.getCourseName());
        }
    }

    public void enrollInCourse(Course course) {
        enrolledCourses.add(course);
        updateProgress();
    }

    public void setLearningPath(LearningPath path) {
        this.currentLearningPath = path;
    }

    private void updateProgress() {
        if (enrolledCourses.isEmpty()) {
            overallProgress = 0.0;
            return;
        }

        double totalProgress = 0.0;
        for (Course course : enrolledCourses) {
            totalProgress += course.getProgress();
        }
        overallProgress = totalProgress / enrolledCourses.size();
    }

    // Getters and setters
    public List<Course> getEnrolledCourses() { return enrolledCourses; }
    public LearningPath getCurrentLearningPath() { return currentLearningPath; }
    public double getOverallProgress() { return overallProgress; }
}
