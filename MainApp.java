package com.aitutor;

import com.aitutor.model.*;
import com.aitutor.ai.AIResponseEngine;
import com.aitutor.util.FileHandler;
import com.aitutor.exception.CourseNotFoundException;

import java.util.*;
import java.io.IOException;

public class MainApp {
    private static Scanner scanner = new Scanner(System.in);
    private static List<User> users = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static User currentUser = null;

    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final long LOGIN_TIMEOUT = 300000; // 5 minutes in milliseconds
    private static Map<String, Integer> loginAttempts = new HashMap<>();
    private static Map<String, Long> lockoutTimes = new HashMap<>();
    private static long sessionStartTime;
    private static final long SESSION_TIMEOUT = 1800000; // 30 minutes in milliseconds

    // Helper method to validate numeric string
    private static boolean isValidNumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Helper method to safely read integer input
    private static int readIntInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Please enter a number.");
                continue;
            }
            if (!isValidNumeric(input)) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }
            return Integer.parseInt(input);
        }
    }

    // Helper method to validate string input
    private static String readStringInput(String fieldName, boolean allowEmpty, boolean isLogin) {
        while (true) {
            String input = scanner.nextLine().trim();

            if (!allowEmpty && input.isEmpty()) {
                System.out.println(fieldName + " cannot be empty. Please try again.");
                continue;
            }

            // Check if input contains only numbers
            if (input.matches("\\d+")) {
                System.out.println(fieldName + " cannot be only numbers. Please enter a valid " + fieldName + ".");
                continue;
            }

            // Allow letters, numbers, underscores, hyphens, and spaces for username with
            // minimum length check
            if (fieldName.equals("Username")) {
                if (input.length() < 3) {
                    System.out.println("Username must be at least 3 characters long.");
                    continue;
                }
                if (input.length() > 20) {
                    System.out.println("Username cannot be longer than 20 characters.");
                    continue;
                }
                if (!input.matches("^[a-zA-Z0-9_-][a-zA-Z0-9_ -]*$")) {
                    System.out.println(
                            "Username can only contain letters, numbers, underscores, hyphens, and spaces. It cannot start with a space.");
                    continue;
                }
                // Check if username starts with a letter
                if (!input.matches("^[a-zA-Z].*")) {
                    System.out.println("Username must start with a letter.");
                    continue;
                }
                // Check if username is already taken (only during registration)
                if (!isLogin) {
                    for (User user : users) {
                        if (user.getUsername().equals(input)) {
                            System.out.println("Username is already taken. Please choose another one.");
                            continue;
                        }
                    }
                }
            }

            // Allow email format
            if (fieldName.equals("Email") && !input.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                System.out.println("Please enter a valid email address (e.g., user@example.com)");
                continue;
            }

            // Allow more characters for questions
            if (fieldName.equals("Question") && !input.matches("^[\\p{L}\\p{N}\\p{P}\\p{Z}]+$")) {
                System.out.println("Question can contain letters, numbers, spaces, and common punctuation marks.");
                continue;
            }

            // Allow more characters for course names
            if (fieldName.equals("Course name") && !input.matches("^[\\p{L}\\p{N}\\p{P}\\p{Z}]+$")) {
                System.out.println("Course name can contain letters, numbers, spaces, and common punctuation marks.");
                continue;
            }

            // Allow more characters for passwords with minimum length check
            if (fieldName.equals("Password")) {
                if (input.length() < 8) {
                    System.out.println("Password must be at least 8 characters long.");
                    continue;
                }
                if (!input.matches("^[\\p{L}\\p{N}\\p{P}\\p{Z}]+$")) {
                    System.out.println("Password can contain letters, numbers, spaces, and common punctuation marks.");
                    continue;
                }
                // Check for at least one uppercase letter
                if (!input.matches(".*[A-Z].*")) {
                    System.out.println("Password must contain at least one uppercase letter.");
                    continue;
                }
                // Check for at least one lowercase letter
                if (!input.matches(".*[a-z].*")) {
                    System.out.println("Password must contain at least one lowercase letter.");
                    continue;
                }
                // Check for at least one number
                if (!input.matches(".*\\d.*")) {
                    System.out.println("Password must contain at least one number.");
                    continue;
                }
                // Check for at least one special character
                if (!input.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                    System.out.println("Password must contain at least one special character.");
                    continue;
                }
            }

            // Allow more characters for specialization
            if (fieldName.equals("Specialization") && !input.matches("^[\\p{L}\\p{N}\\p{P}\\p{Z}]+$")) {
                System.out
                        .println("Specialization can contain letters, numbers, spaces, and common punctuation marks.");
                continue;
            }

            // Allow more characters for course description
            if (fieldName.equals("Course description") && !input.matches("^[\\p{L}\\p{N}\\p{P}\\p{Z}]+$")) {
                System.out.println(
                        "Course description can contain letters, numbers, spaces, and common punctuation marks.");
                continue;
            }

            // Allow letters, numbers, and spaces for other fields
            if (!fieldName.equals("Username") && !fieldName.equals("Email") && !fieldName.equals("Question")
                    && !fieldName.equals("Course name") && !fieldName.equals("Password")
                    && !fieldName.equals("Specialization") && !fieldName.equals("Course description")
                    && !input.matches("^[a-zA-Z0-9\\s]+$")) {
                System.out.println(fieldName + " cannot contain special characters. Please try again.");
                continue;
            }

            return input;
        }
    }

    private static void register() {
        while (true) {
            System.out.println("\n=== Registration ===");
            System.out.println("1. Register as Student");
            System.out.println("2. Register as Tutor");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = readIntInput();
            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice! Please choose 1, 2, or 3.");
                continue;
            }

            if (choice == 3) {
                return;
            }

            System.out.print("Enter username: ");
            String username = readStringInput("Username", false, false);

            // Check if username is already taken
            boolean usernameTaken = false;
            try {
                List<User> existingUsers = FileHandler.loadUsers();
                for (User user : existingUsers) {
                    if (user.getUsername().equals(username)) {
                        usernameTaken = true;
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error checking username: " + e.getMessage());
                continue;
            }

            if (usernameTaken) {
                System.out.println("\nUsername is already taken.");
                System.out.println("1. Login with this username");
                System.out.println("2. Try another username");
                System.out.println("3. Back to Main Menu");
                System.out.print("Choose an option: ");

                int option = readIntInput();
                if (option == 1) {
                    login();
                    return;
                } else if (option == 2) {
                    continue;
                } else if (option == 3) {
                    return;
                } else {
                    System.out.println("Invalid choice! Please choose 1, 2, or 3.");
                    continue;
                }
            }

            System.out.print("Enter password: ");
            String password = readStringInput("Password", false, false);
            System.out.print("Enter email: ");
            String email = readStringInput("Email", false, false);

            String userId = "U" + UUID.randomUUID().toString().substring(0, 8);

            try {
                if (choice == 1) {
                    Student student = new Student(userId, username, password, email);
                    users.add(student);
                    FileHandler.saveUser(student);
                } else if (choice == 2) {
                    System.out.print("Enter specialization: ");
                    String specialization = readStringInput("Specialization", false, false);
                    System.out.print("Enter years of experience: ");
                    int experience = readYearsOfExperience();
                    Tutor tutor = new Tutor(userId, username, password, email, specialization, experience);
                    users.add(tutor);
                    FileHandler.saveUser(tutor);
                }
                // Reload users from file to ensure consistency
                users = FileHandler.loadUsers();
                System.out.println("Registration successful!");
                return;
            } catch (IOException e) {
                System.out.println("Error saving user data: " + e.getMessage());
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = readStringInput("Username", false, true);

        // Check if user is locked out
        if (isUserLockedOut(username)) {
            long remainingTime = (lockoutTimes.get(username) + LOGIN_TIMEOUT - System.currentTimeMillis()) / 1000;
            System.out.println("Account is temporarily locked. Please try again in " + remainingTime + " seconds.");
            return;
        }

        System.out.print("Enter password: ");
        String password = readStringInput("Password", false, true);

        try {
            List<User> loadedUsers = FileHandler.loadUsers();
            for (User user : loadedUsers) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    loginAttempts.remove(username); // Reset attempts on successful login
                    currentUser = user;
                    sessionStartTime = System.currentTimeMillis();
                    if (currentUser instanceof Student) {
                        showStudentMenu();
                    } else {
                        showTutorMenu();
                    }
                    return;
                }
            }
            // Handle failed login with forgot password option
            handleFailedLoginWithOptions(username);
        } catch (IOException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }

    private static void handleFailedLoginWithOptions(String username) {
        int attempts = loginAttempts.getOrDefault(username, 0) + 1;
        loginAttempts.put(username, attempts);

        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            lockoutTimes.put(username, System.currentTimeMillis());
            System.out.println("Too many failed attempts. Account temporarily locked for 5 minutes.");
            return;
        }

        System.out.println("\nInvalid credentials! Attempts remaining: " + (MAX_LOGIN_ATTEMPTS - attempts));
        System.out.println("1. Try again");
        System.out.println("2. Forgot password");
        System.out.println("3. Back to main menu");
        System.out.print("Choose an option: ");

        int choice = readIntInput();
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                handleForgotPassword(username);
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void handleForgotPassword(String username) {
        try {
            List<User> loadedUsers = FileHandler.loadUsers();
            User user = loadedUsers.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);

            if (user == null) {
                System.out.println("User not found!");
                return;
            }

            System.out.print("Enter your email for verification: ");
            String email = readStringInput("Email", false, false);

            if (!user.getEmail().equals(email)) {
                System.out.println("Email does not match our records!");
                return;
            }

            System.out.print("Enter new password: ");
            String newPassword = readStringInput("Password", false, false);
            user.setPassword(newPassword);
            FileHandler.updateUser(user);

            System.out.println("Password successfully reset! Please login with your new password.");
            loginAttempts.remove(username);
        } catch (IOException e) {
            System.out.println("Error processing password reset: " + e.getMessage());
        }
    }

    private static boolean isUserLockedOut(String username) {
        Long lockoutTime = lockoutTimes.get(username);
        if (lockoutTime != null) {
            if (System.currentTimeMillis() - lockoutTime < LOGIN_TIMEOUT) {
                return true;
            } else {
                // Lockout period expired
                lockoutTimes.remove(username);
                loginAttempts.remove(username);
            }
        }
        return false;
    }

    private static boolean checkSessionTimeout() {
        if (currentUser != null && System.currentTimeMillis() - sessionStartTime > SESSION_TIMEOUT) {
            System.out.println("\nSession timed out. Please login again.");
            currentUser = null;
            return true;
        }
        return false;
    }

    private static void showStudentMenu() {
        while (true) {
            if (checkSessionTimeout())
                return;

            System.out.println("\n=== Student Menu ===");
            System.out.println("1. View Profile");
            System.out.println("2. Enroll in Course");
            System.out.println("3. View Enrolled Courses");
            System.out.println("4. Ask Question");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = readIntInput();
            switch (choice) {
                case 1:
                    viewProfile();
                    break;
                case 2:
                    enrollInCourse();
                    break;
                case 3:
                    viewEnrolledCourses();
                    break;
                case 4:
                    askQuestion();
                    break;
                case 5:
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void showTutorMenu() {
        while (true) {
            if (checkSessionTimeout())
                return;

            System.out.println("\n=== Tutor Menu ===");
            System.out.println("1. View Profile");
            System.out.println("2. Create Course");
            System.out.println("3. View Created Courses");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = readIntInput();
            switch (choice) {
                case 1:
                    viewProfile();
                    break;
                case 2:
                    createCourse();
                    break;
                case 3:
                    viewCreatedCourses();
                    break;
                case 4:
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void viewProfile() {
        System.out.println("\n=== Profile ===");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Email: " + currentUser.getEmail());
        if (currentUser instanceof Tutor) {
            Tutor tutor = (Tutor) currentUser;
            System.out.println("Specialization: " + tutor.getSpecialization());
            System.out.println("Years of Experience: " + tutor.getYearsOfExperience());
        }
    }

    private static void enrollInCourse() {
        try {
            // Reload courses to ensure we have the latest data
            courses = FileHandler.loadCourses(users);

            if (courses.isEmpty()) {
                System.out.println("No courses available at the moment.");
                return;
            }

            System.out.println("\n=== Available Courses ===");
            System.out.println("ID\tCourse Name\t\tTutor\t\tDescription");
            System.out.println("--------------------------------------------------");

            for (Course course : courses) {
                System.out.printf("%s\t%-15s\t%-10s\t%s\n",
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getTutor().getUsername(),
                        course.getDescription());
            }

            System.out.print("\nEnter course ID to enroll: ");
            String courseId = readStringInput("Course ID", false, false);

            try {
                Course course = findCourseById(courseId);
                if (course == null) {
                    throw new CourseNotFoundException("Course not found with ID: " + courseId);
                }

                if (currentUser instanceof Student) {
                    Student student = (Student) currentUser;
                    course.enrollStudent(student);
                    System.out.println("Successfully enrolled in course: " + course.getCourseName());
                }
            } catch (CourseNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }

    private static Course findCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    private static void viewEnrolledCourses() {
        try {
            // Reload courses to ensure we have the latest data
            courses = FileHandler.loadCourses(users);

            if (currentUser instanceof Student) {
                Student student = (Student) currentUser;
                List<Course> enrolledCourses = new ArrayList<>();

                for (Course course : courses) {
                    if (course.getEnrolledStudents().contains(student)) {
                        enrolledCourses.add(course);
                    }
                }

                if (enrolledCourses.isEmpty()) {
                    System.out.println("You haven't enrolled in any courses yet.");
                    return;
                }

                System.out.println("\n=== Your Enrolled Courses ===");
                for (Course course : enrolledCourses) {
                    System.out.println("\nCourse ID: " + course.getCourseId());
                    System.out.println("Course Name: " + course.getCourseName());
                    System.out.println("Description: " + course.getDescription());
                    System.out.println("Tutor: " + course.getTutor().getUsername());
                    System.out.println("Progress: " + course.getProgress() + "%");
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }

    private static void createCourse() {
        if (!(currentUser instanceof Tutor)) {
            System.out.println("Only tutors can create courses.");
            return;
        }

        System.out.println("\n=== Create New Course ===");
        System.out.println("Please enter the following details:");

        System.out.print("Course Name: ");
        String courseName = readStringInput("Course name", false, false);

        System.out.print("Course Description: ");
        String description = readStringInput("Course description", false, false);

        String courseId = "C" + UUID.randomUUID().toString().substring(0, 8);
        Course course = new Course(courseId, courseName, description, (Tutor) currentUser);
        courses.add(course);

        try {
            FileHandler.saveCourse(course);
            System.out.println("\nCourse created successfully!");
            System.out.println("Course ID: " + courseId);
            System.out.println("Course Name: " + courseName);
            System.out.println("Description: " + description);
            System.out.println("Tutor: " + currentUser.getUsername());
        } catch (IOException e) {
            System.out.println("Error saving course: " + e.getMessage());
            courses.remove(course);
        }
    }

    private static void viewCreatedCourses() {
        try {
            // Reload courses from file to ensure we have the latest data
            courses = FileHandler.loadCourses(users);

            if (currentUser instanceof Tutor) {
                Tutor tutor = (Tutor) currentUser;
                List<Course> createdCourses = new ArrayList<>();

                for (Course course : courses) {
                    if (course.getTutor().getUserId().equals(tutor.getUserId())) {
                        createdCourses.add(course);
                    }
                }

                if (createdCourses.isEmpty()) {
                    System.out.println("You haven't created any courses yet.");
                    return;
                }

                System.out.println("\n=== Your Created Courses ===");
                for (Course course : createdCourses) {
                    System.out.println("\nCourse ID: " + course.getCourseId());
                    System.out.println("Course Name: " + course.getCourseName());
                    System.out.println("Description: " + course.getDescription());
                    System.out.println("Enrolled Students: " + course.getEnrolledStudentsCount());
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }

    private static int readYearsOfExperience() {
        while (true) {
            int years = readIntInput();
            if (years < 0) {
                System.out.println("Years of experience cannot be negative. Please try again.");
                continue;
            }
            return years;
        }
    }

    private static void askQuestion() {
        System.out.print("Enter your question: ");
        String question = readStringInput("Question", false, false);

        String response = AIResponseEngine.getResponse(question);
        System.out.println("\nAI Response: " + response);
    }

    public static void main(String[] args) {
        try {
            // Load users and courses from file
            users = FileHandler.loadUsers();
            courses = FileHandler.loadCourses(users);
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n=== AI Tutor System ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = readIntInput();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}