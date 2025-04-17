package com.aitutor.ai;

import com.aitutor.model.*;
import java.util.*;

public class AIResponseEngine {
    private static final Random random = new Random();

    public static String generateCourseSummary(Course course) {
        return course.generateSummary();
    }

    public static List<Course> recommendCourses(Student student, List<Course> availableCourses) {
        List<Course> recommendations = new ArrayList<>();
        Set<String> enrolledCourseIds = new HashSet<>();

        // Get IDs of enrolled courses
        for (Course course : student.getEnrolledCourses()) {
            enrolledCourseIds.add(course.getCourseId());
        }

        // Simple recommendation: suggest courses not yet enrolled
        for (Course course : availableCourses) {
            if (!enrolledCourseIds.contains(course.getCourseId())) {
                recommendations.add(course);
            }
        }

        // Limit to top 3 recommendations
        if (recommendations.size() > 3) {
            recommendations = recommendations.subList(0, 3);
        }

        return recommendations;
    }

    public static String answerQuestion(String question) {
        // Simple Q&A simulation
        List<String> responses = Arrays.asList(
                "Based on the course material, ",
                "According to best practices, ",
                "From what we've learned, ");

        return responses.get(random.nextInt(responses.size())) +
                "I would suggest reviewing the relevant module content and practicing the concepts.";
    }

    public static String getResponse(String question) {
        // Simple response for now
        return "This is a sample response to: " + question;
    }
}
