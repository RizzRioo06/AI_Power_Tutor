package com.aitutor.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Course> enrolledCourses;

    public Student(String userId, String username, String password, String email) {
        super(userId, username, password, email);
        this.enrolledCourses = new ArrayList<>();
    }

    @Override
    public void displayProfile() {
        System.out.println("\n=== Student Profile ===");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Enrolled Courses: " + enrolledCourses.size());
    }

    @Override
    public void viewDashboard() {
        System.out.println("\n=== Student Dashboard ===");
        System.out.println("\nEnrolled Courses:");
        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            for (Course course : enrolledCourses) {
                System.out.println("- " + course.getCourseName() + " (ID: " + course.getCourseId() + ")");
                System.out.println("  Description: " + course.getDescription());
                System.out.println("  Tutor: " + course.getTutor().getUsername());
            }
        }
    }

    public void enrollInCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    // Getters and setters
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
}
