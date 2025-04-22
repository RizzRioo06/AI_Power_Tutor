# 🎓 AI Powered Tutor System

### 📖 Overview
The **AI Tutor System** is a Java-based educational platform designed to enhance online learning by connecting students with tutors. Students can enroll in courses, view recommendations, ask questions, and manage their profiles. Tutors can create and manage courses while monitoring student engagement. The system aims to improve learning experiences through simple AI-powered features and interactive functionality.

---

### ✨ Features

- 🔐 **User Registration & Login**  
  Users can register as **Students** or **Tutors**, and log in to access personalized menus.

- 📚 **Course Management**  
  Tutors can create, view, and manage courses. Students can enroll in available courses.

- 👤 **Profile Management**  
  Users can view their personal information and tutors can manage their specialization and experience.

- 💬 **Q&A System**  
  Students can ask questions and receive basic AI-generated responses to enhance learning.

- 🎯 **Course Recommendations**  
  Students receive course suggestions based on their enrollment history (mock AI logic).

---

### 🛠️ Technologies Used

- Java SE  
- Object-Oriented Programming (OOP)  
- File I/O (for data persistence)  
- Exception Handling  
- Java Collections Framework (`List`, `Map`, `Set`)  
- Polymorphism & Inheritance  
- Simple AI response engine (mocked logic)  

---

## 🧠 Concepts Applied (Section 2.2)

| Concept               | How It's Used |
|------------------------|---------------|
| **2.1 File I/O**       | User and course data are saved and loaded using the `FileHandler` class, ensuring persistence across sessions. |
| **2.2 Inheritance**    | `Student` and `Tutor` both inherit from the `User` superclass, promoting reuse and shared logic. |
| **2.3 Exception Handling** | Custom and built-in exceptions (e.g., `IOException`, `CourseNotFoundException`) ensure safe and robust execution. |
| **2.4 Keyboard I/O**   | The `Scanner` class is used for interactive console-based input from users. |
| **2.5 Collections**    | Lists and maps store dynamic user, course, and session data efficiently. |
| **2.6 Polymorphism**   | The system uses `User` references to handle both `Student` and `Tutor` objects, enabling dynamic behavior. |

---

### 🧩 Key Concepts in Action

- **Read/Write from File**  
  All user and course data is read from and written to files using a dedicated file utility class.

- **Inheritance**  
  The `Student` and `Tutor` classes inherit from `User`, allowing shared attributes and behavior while enabling specialization.

- **Exception Handling**  
  The system uses `try-catch` blocks and defines custom exceptions to handle scenarios like file errors and missing courses.

- **Keyboard Input/Output**  
  All user input is collected using the `Scanner` class, ensuring real-time interactivity during login, registration, and course interaction.

- **Collections**  
  Data structures like `ArrayList` and `HashMap` are used to manage users, courses, login attempts, and student enrollments.

- **Polymorphism**  
  Menus and logic flow are controlled dynamically based on the user type (`Student` or `Tutor`) using polymorphic behavior.

---

### 🔑 Why We Use `UUID.randomUUID()` for IDs

We use `UUID.randomUUID()` to generate unique identifiers for each student and course in the system. This ensures that:

- **🆔 IDs are globally unique** – There's no risk of duplication, even if data is created across different systems or at different times.

- **⚙️ No manual tracking needed** – We don’t need to maintain a counter or worry about collisions.

- **🔐 Security** – UUIDs are hard to guess, making them safer in public URLs or APIs.

- **📈 Scalability** – It works well even as the number of users and courses grows over time, without changes to the ID generation logic.

---

## 🚀 Installation & Running

1. **Clone the repository**  
   ```bash
   git clone https://github.com/yourusername/ai-tutor-system.git
   ```

2. **Navigate to the project directory**  
   ```bash
   cd ai-tutor-system
   ```

3. **Compile the Java files**  
   ```bash
   javac MultipleFiles/*.java
   ```

4. **Run the application**  
   ```bash
   java com.aitutor.MainApp
   ```

---

## 🧑‍🤝‍🧑 Group Members

- Hein Htut Aung  
- Aung Kyaw Soe  
- Nang Shwe Sin  
- Ye Myat Min  
- Kyaw Hmue San

---

## 🤝 Contributing

We welcome contributions to improve the AI Powered Tutor System!  
Fork the repository, make your changes, and submit a pull request.

---

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## 🙏 Acknowledgments

Special thanks to our instructors and peers for their valuable support and guidance throughout the development of this project.
---
