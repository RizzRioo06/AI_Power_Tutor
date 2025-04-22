package com.aitutor.ai;

import com.aitutor.model.*;
import java.util.*;

public class AIResponseEngine {
    private static final Random random = new Random();


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

        // Limit to top 2 recommendations
        if (recommendations.size() > 2) {
            recommendations = recommendations.subList(0, 2);
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
        List<String> responses = Arrays.asList(
                "Based on my analysis, " + question
                        + " is an interesting question. I would suggest exploring the core concepts first.",
                "That's a great question! " + question
                        + " can be approached in several ways. Let's break it down step by step.",
                "To answer '" + question + "', let's consider the fundamental principles involved.",
                "This is a common question in learning. For '" + question + "', I recommend starting with the basics.",
                "Regarding '" + question + "', here's what I think: focus on understanding the key concepts first.",
                "Let me help you with that. For '" + question + "', you should consider multiple perspectives.",
                "Interesting question! When it comes to '" + question + "', practice is key to understanding.",
                "From my experience, '" + question + "' requires a systematic approach to learn effectively.");

        return responses.get(random.nextInt(responses.size()));
    }
}
