package com.aitutor.exception;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String courseId) {
        super("Course not found with ID: " + courseId);
    }
}
