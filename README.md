
# AI_Powered_Tutor

### Overview
The AI Tutor System is an educational platform designed to facilitate online learning by connecting students with tutors. The system allows students to enroll in courses, view their profiles, and ask questions, while tutors can create courses and manage their students. The application aims to enhance the learning experience through interactive features.

### Features
- User Registration and Login: Users can register as either students or tutors and log in to access their respective dashboards.  
- Course Management: Tutors can create and manage courses, while students can enroll in available courses.  
- Profile Management: Users can view and update their profiles.  
- Q&A Functionality: Students can ask questions and receive responses based on predefined templates, enhancing their understanding of course material.  
- Course Recommendations: The system suggests courses to students based on their current enrollments, although this feature is basic and may not always provide optimal suggestions.

### Technologies Used
- Java  
- Object-Oriented Programming  
- File Handling for data persistence  
- Exception Handling  
- Collections Framework  
- Polymorphism  
- Inheritance

### Key Concepts Implemented
- Read/Write from File: The application uses file handling to read and write user and course data, ensuring data persistence.  
- Inheritance: The system utilizes inheritance through the `User` class, from which `Student` and `Tutor` classes are derived, allowing for shared functionality and properties.  
- Exception Handling: Custom exceptions, such as `CourseNotFoundException`, are implemented to handle errors gracefully during course operations.  
- Input & Output from Keyboard: The application captures user input through the console, allowing for interactive user experiences during registration, login, and course enrollment.  
- Collections: The system employs collections such as `ArrayList` and `HashSet` to manage lists of users, courses, and enrolled students efficiently.  
- Polymorphism: The application demonstrates polymorphism through method overriding in the `displayProfile` and `viewDashboard` methods in the `Student` and `Tutor` classes.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ai-tutor-system.git
   ```
2. Navigate to the project directory:
   ```bash
   cd ai-tutor-system
   ```
3. Compile the Java files:
   ```bash
   javac MultipleFiles/*.java
   ```
4. Run the application:
   ```bash
   java com.aitutor.MainApp
   ```

### Group Members
- Hein Htut Aung  
- Aung Kyaw Soe  
- Nang Shwe Sin  
- Ye Myat Min  
- Kyaw Hmue San

### Contributing
We welcome contributions to improve the AI Powered Tutor System. Please fork the repository and submit a pull request with your changes.

### License
This project is licensed under the MIT License - see the LICENSE file for details.

### Acknowledgments
We would like to thank our instructors and peers for their support and guidance throughout the development of this project.
