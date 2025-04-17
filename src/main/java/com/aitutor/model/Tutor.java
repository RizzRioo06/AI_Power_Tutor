package com.aitutor.model;

import java.util.ArrayList;
import java.util.List;

public class Tutor extends User {
    private List<Course> managedCourses;
    private String specialization;
    private int yearsOfExperience;

    public Tutor(String userId, String username, String password, String email, 
                 String specialization, int yearsOfExperience) {
        super(userId, username, password, email);
        this.specialization = specialization;
        this.yearsOfExperience = yearsOfExperience;
        this.managedCourses = new ArrayList<>();
    }

    @Override
    public void displayProfile() {
        System.out.println("\n=== Tutor Profile ===");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Specialization: " + specialization);
        System.out.println("Years of Experience: " + yearsOfExperience);
        System.out.println("Courses Managed: " + managedCourses.size());
    }

    @Override
    public void viewDashboard() {
        System.out.println("\n=== Tutor Dashboard ===");
        System.out.println("Managed Courses:");
        for (Course course : managedCourses) {
            System.out.println("- " + course.getCourseName());
            System.out.println("  Students Enrolled: " + course.getEnrolledStudentsCount());
        }
    }

    public void addCourse(Course course) {
        managedCourses.add(course);
    }

    public void removeCourse(Course course) {
        managedCourses.remove(course);
    }

    // Getters and setters
    public List<Course> getManagedCourses() { return managedCourses; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
}
