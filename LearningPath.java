package com.aitutor.model;

import java.util.ArrayList;
import java.util.List;

public class LearningPath {
    private String pathId;
    private String pathName;
    private String description;
    private List<Course> courses;
    private double completionPercentage;

    public LearningPath(String pathId, String pathName, String description) {
        this.pathId = pathId;
        this.pathName = pathName;
        this.description = description;
        this.courses = new ArrayList<>();
        this.completionPercentage = 0.0;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void updateProgress(Student student) {
        if (courses.isEmpty()) {
            completionPercentage = 0.0;
            return;
        }

        int completedCourses = 0;
        for (Course course : courses) {
            if (student.getEnrolledCourses().contains(course) && course.getProgress() >= 100.0) {
                completedCourses++;
            }
        }
        completionPercentage = (double) completedCourses / courses.size() * 100.0;
    }

    // Getters and setters
    public String getPathId() { return pathId; }
    public String getPathName() { return pathName; }
    public String getDescription() { return description; }
    public List<Course> getCourses() { return courses; }
    public double getCompletionPercentage() { return completionPercentage; }
}
