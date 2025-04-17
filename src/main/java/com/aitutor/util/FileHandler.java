package com.aitutor.util;

import com.aitutor.model.*;
import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + "/users.txt";
    private static final String COURSES_FILE = DATA_DIR + "/courses.txt";

    public static void initialize() throws IOException {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        // Create files if they don't exist
        new File(USERS_FILE).createNewFile();
        new File(COURSES_FILE).createNewFile();
    }

    public static void saveUser(User user) throws IOException {
        List<User> existingUsers = new ArrayList<>();
        File file = new File(USERS_FILE);

        // Load existing users if file exists and is not empty
        if (file.exists() && file.length() > 0) {
            existingUsers = loadUsers();
        }

        // Add or update user
        boolean userExists = false;
        for (int i = 0; i < existingUsers.size(); i++) {
            if (existingUsers.get(i).getUserId().equals(user.getUserId())) {
                existingUsers.set(i, user);
                userExists = true;
                break;
            }
        }
        if (!userExists) {
            existingUsers.add(user);
        }

        // Save all users
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (User u : existingUsers) {
                if (u instanceof Tutor) {
                    Tutor tutor = (Tutor) u;
                    writer.println(String.format("TUTOR,%s,%s,%s,%s,%s,%d",
                            tutor.getUserId(),
                            tutor.getUsername(),
                            tutor.getPassword(),
                            tutor.getEmail(),
                            tutor.getSpecialization(),
                            tutor.getYearsOfExperience()));
                } else {
                    writer.println(String.format("STUDENT,%s,%s,%s,%s",
                            u.getUserId(),
                            u.getUsername(),
                            u.getPassword(),
                            u.getEmail()));
                }
            }
        }
    }

    public static void saveCourse(Course course) throws IOException {
        List<String> existingLines = readFile(COURSES_FILE);
        List<String> newLines = new ArrayList<>();
        boolean courseUpdated = false;

        // Convert enrolled student IDs to string
        List<String> enrolledStudentIds = new ArrayList<>();
        for (Student student : course.getEnrolledStudents()) {
            enrolledStudentIds.add(student.getUserId());
        }
        String studentIds = String.join(";", enrolledStudentIds);

        // Create course line
        String courseLine = String.join(",",
                course.getCourseId(),
                course.getCourseName(),
                course.getDescription(),
                course.getTutor().getUserId()) +
                (studentIds.isEmpty() ? "" : "," + studentIds);

        // Update existing course or add new one
        for (String line : existingLines) {
            String[] parts = line.split(",", 4); // Split only first 4 parts
            if (parts[0].equals(course.getCourseId())) {
                newLines.add(courseLine);
                courseUpdated = true;
            } else {
                newLines.add(line);
            }
        }

        if (!courseUpdated) {
            newLines.add(courseLine);
        }

        // Write all lines back to file
        writeFile(COURSES_FILE, newLines);
    }

    public static List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);

        if (!file.exists() || file.length() == 0) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;

                String[] parts = line.split(",");
                if (parts.length < 5)
                    continue;

                String type = parts[0];
                String userId = parts[1];
                String username = parts[2];
                String password = parts[3];
                String email = parts[4];

                if (type.equals("STUDENT")) {
                    users.add(new Student(userId, username, password, email));
                } else if (type.equals("TUTOR") && parts.length >= 7) {
                    String specialization = parts[5];
                    int experience = Integer.parseInt(parts[6]);
                    users.add(new Tutor(userId, username, password, email, specialization, experience));
                }
            }
        }
        return users;
    }

    public static List<Course> loadCourses(List<User> users) throws IOException {
        List<Course> loadedCourses = new ArrayList<>();
        List<String> lines = readFile(COURSES_FILE);
        Map<String, Student> studentMap = new HashMap<>();

        // First, create a map of students for faster lookup
        for (User user : users) {
            if (user instanceof Student) {
                Student student = (Student) user;
                student.getEnrolledCourses().clear(); // Clear only once at the start
                studentMap.put(student.getUserId(), student);
            }
        }

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String courseId = parts[0];
                String courseName = parts[1];
                String description = parts[2];
                String tutorId = parts[3];

                // Find tutor by ID
                Tutor tutor = null;
                for (User user : users) {
                    if (user instanceof Tutor && user.getUserId().equals(tutorId)) {
                        tutor = (Tutor) user;
                        break;
                    }
                }

                if (tutor != null) {
                    Course course = new Course(courseId, courseName, description, tutor);

                    // Load enrolled students if any
                    if (parts.length >= 5 && !parts[4].isEmpty()) {
                        String[] studentIds = parts[4].split(";");
                        for (String studentId : studentIds) {
                            Student student = studentMap.get(studentId);
                            if (student != null) {
                                course.enrollStudent(student);
                                student.enrollInCourse(course);
                            }
                        }
                    }

                    loadedCourses.add(course);
                    tutor.addCourse(course);
                }
            }
        }

        return loadedCourses;
    }

    public static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    public static void writeFile(String filePath, List<String> lines) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }

    public static void updateUser(User user) throws IOException {
        saveUser(user); // We can reuse saveUser as it now handles updates
    }
}
