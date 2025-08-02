# 🌉 SkillBridge - Career Guidance Console App

SkillBridge is a **Java-based interactive console application** designed to assist students in discovering suitable career paths based on their interests, motivation, skill levels, and preferences.

This project simulates a **career counseling system** where users can interactively:
- Take motivational advice
- Assess their career fit using quizzes
- Evaluate their current skill levels
- Track their activity
- Export a personalized career improvement report (PDF simulation)

---

## 📌 Key Modules & Features

### 🔐 UserAuth (loginSignup)
- Simulates user registration and login using the console
- Stores active session with username

### 📊 SkillGapModule
- Evaluates the user's skills compared to career benchmarks
- Identifies "missing skills" and displays them for user improvement

### 💡 MotivationModule
- Provides motivational, career, and study tips from multiple categories
- Randomized tips improve engagement

### 🧠 QuizModule
- Asks career-specific questions
- Tracks correct/incorrect answers to determine suitability

### 📑 PDFModule
- Simulates exporting a summary report of missing skills
- Prepares a personalized report with user name and improvement areas

### 📈 ActivityTracker
- Tracks:
  - Number of quizzes taken
  - Skills checked
  - Motivation tips viewed
  - PDF reports generated
- All stats are stored per session

---

## 🛠 How to Set Up and Run the Project

> 📌 Prerequisite: Make sure **Java (JDK 8+)** is installed and properly set in PATH.

### 1. Clone the Repository

```bash
git clone https://github.com/ChaitanyaReddyMunnangi/Skillbridge_project.git
cd Skillbridge_project
```

Or download the ZIP and extract it.

### 2. Compile the Java Files

```bash
javac *.java
```

This compiles all `.java` files in the root.

### 3. Run the Main Application

```bash
java MainApp
```

### 4. Follow Console Instructions

You'll see:
```bash
🌉 Welcome to SkillBridge App 🌉
1. Login or Register
2. Take a Career Quiz
3. Get Motivational Advice
4. Assess Your Skills
5. Export Report
6. View Session Activity
0. Exit
```

Just follow the prompts using your keyboard.

---

## 📂 Project Structure

```
.
├── MainApp.java               # Entry point
├── UserAuth.java              # Handles login/signup
├── MotivationModule.java      # Offers motivational tips
├── SkillGapModule.java        # Checks for missing skills
├── QuizModule.java            # Offers career-specific quizzes
├── ActivityTracker.java       # Tracks usage per session
├── PDFModule.java             # Simulates PDF generation
├── [career]_cleaned.txt       # Career data files (20+ files)
```

---

## 🧠 Sample Careers Supported

- AI/ML Engineer
- Cyber Security Analyst
- Police/IAS/IPS Officer
- Civil Engineer
- Lawyer
- Chef
- Banking Officer
- Journalist
- Marketing Executive
- Mobile App Developer
- Management Roles

---

## 🧾 Example PDF Output (Simulated)

```text
📄 Exporting Missing Skills to PDF (Simulated)
👤 User: JohnDoe
🕵️ Skills to Learn:
 - Public speaking
 - Team collaboration
 - Basic Python
✅ PDF Export Completed (simulation only).
```

---

## 🧭 Future Enhancements

- GUI version with JavaFX or Swing
- Real PDF export using iText or Apache PDFBox
- User database integration (file or SQL)
- Admin dashboard to monitor users
- Web version with Spring Boot or React

---

## 🙌 Authors

- **Chaitanya Reddy Munnangi**  
  [GitHub Profile](https://github.com/ChaitanyaReddyMunnangi)

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

> Created as part of the **SkillBridge career mentorship initiative**, aiming to guide students towards informed professional decisions.
