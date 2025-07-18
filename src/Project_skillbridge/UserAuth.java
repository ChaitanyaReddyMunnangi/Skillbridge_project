package Project_skillbridge;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class UserAuth {

    private static class QuizAttempt implements Serializable {
        String category;
        int score;
        Date attemptDate;

        public QuizAttempt(String category, int score) {
            this.category = category;
            this.score = score;
            this.attemptDate = new Date();
        }
    }

    private static class User implements Serializable {
        String username;
        String password;
        String securityQuestion;
        String securityAnswer;
        String educationalBackground;
        String fullName;
        int age;
        String email;
        String address;
        String qualification;
        Set<String> skills = new HashSet<>();

        List<QuizAttempt> quizHistory = new ArrayList<>();

        public User(String username, String password, String securityQuestion, String securityAnswer,
                    String educationalBackground, String fullName, int age, String email,
                    String address, String qualification, Set<String> skills) {
            this.username = username;
            this.password = password;
            this.securityQuestion = securityQuestion;
            this.securityAnswer = securityAnswer;
            this.educationalBackground = educationalBackground;
            this.fullName = fullName;
            this.age = age;
            this.email = email;
            this.address = address;
            this.qualification = qualification;
            this.skills = skills;

        }
    }

    private Map<String, User> users = new HashMap<>();
    private Scanner sc = new Scanner(System.in);
    private String currentUser = null;
    private static final String DATA_FILE = "users_data.txt";

    public UserAuth() {
        loadUserData();
    }

    public void loginSignup() {
        while (true) {
            System.out.println("\u001B[34m╔══════════════════════════════════════════╗\u001B[0m");
            System.out.println("\u001B[34m║         🔐  USER AUTHENTICATION MENU     ║\u001B[0m");
            System.out.println("\u001B[34m╚══════════════════════════════════════════╝\u001B[0m");
            System.out.println("\u001B[33m1. ✍️  Sign Up\u001B[0m");
            System.out.println("\u001B[32m2. 🔓 Log In\u001B[0m");
            System.out.println("\u001B[31m3. ❌ Exit\u001B[0m");
            System.out.print("Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": signUp(); break;
                case "2":
                    if (login()) {
                        System.out.println("\u001B[32m✅ Login successful!\u001B[0m");
                        return;
                    }
                    break;
                case "3": saveUserData(); System.out.println("Exiting..."); System.exit(0); break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void signUp() {
        System.out.print("\u001B[34mEnter Full Name: ");
        String fullName = sc.nextLine().trim();

        int age;
        while (true) {
            System.out.print("Enter Age: ");
            try {
                age = Integer.parseInt(sc.nextLine().trim());
                if (age > 0) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("❌ Invalid age.");
        }

        String email;
        while (true) {
            System.out.print("\u001B[32mEnter Email ID: ");
            email = sc.nextLine().trim();
            if (email.contains("@") && email.contains(".")) break;
            System.out.println("❌ Invalid email format.");
        }

        String address;
        while (true) {
            System.out.print("\u001B[33mEnter Address: ");
            address = sc.nextLine().trim();
            if (!address.isEmpty()) break;
            System.out.println("❌ Address can't be empty.");
        }

        System.out.print("\u001B[31mEnter Qualification: ");
        String qualification = sc.nextLine().trim();

        Set<String> skills = new HashSet<>();
        System.out.print("\u001B[36mEnter number of skills: ");
        int numSkills = Integer.parseInt(sc.nextLine().trim());
        for (int i = 1; i <= numSkills; i++) {
            System.out.print("Enter skill " + i + ": ");
            skills.add(sc.nextLine().trim());
        }



        String username;
        while (true) {
            System.out.print("\u001B[35mSet Username (min 8 chars, no spaces): ");
            username = sc.nextLine().trim();
            if (username.length() < 8 || username.contains(" ")) {
                System.out.println("❌ Username must be at least 8 characters and contain no spaces.");
            } else if (users.containsKey(username)) {
                System.out.println("🚫 You are already registered with this username.");
                return; // Exit the signUp() method
            } else {
                break;
            }
        }

        String password;
        while (true) {
            System.out.print("Set Password (min 8 chars, 1 uppercase, 1 lowercase, 1 special char): ");
            password = sc.nextLine();
            if (isStrongPassword(password)) break;
            System.out.println("❌ Weak password.");
        }
        System.out.print("Enter Educational Background: ");
        String education = sc.nextLine().trim();

        List<String> questions = Arrays.asList(
                "Your pet’s name?", "Mother’s maiden name?", "First school name?",
                "Favorite color?", "Birth city?", "Custom Question"
        );
        for (int i = 0; i < questions.size(); i++)
            System.out.println((i + 1) + ". " + questions.get(i));
        System.out.print("Choose security question (1-6): ");
        int qIndex = Integer.parseInt(sc.nextLine()) - 1;
        String question = (qIndex == 5) ? sc.nextLine().trim() : questions.get(qIndex);
        System.out.print("Enter answer: ");
        String answer = sc.nextLine().trim().toLowerCase();



        User newUser = new User(username, password, question, answer, education,
                fullName, age, email, address, qualification, skills);
        users.put(username, newUser);
        saveUserData();
        System.out.println("\u001B[32m✅ You are registered! Welcome, " + fullName + " 🎉\u001B[0m");
    }

    private boolean login() {
        System.out.print("Enter Username: ");
        String username = sc.nextLine().trim();

        if (!users.containsKey(username)) {
            System.out.println("\u001B[31m⚠ Username not found!\u001B[0m");
            return false;
        }

        User user = users.get(username);
        int tries = 0;

        while (true) {
            System.out.print((tries >= 1 ? "Enter password or type 'forgot': " : "Enter password: "));
            String input = sc.nextLine().trim();

            if (tries >= 1 && input.equalsIgnoreCase("forgot")) {
                handleForgotPassword(user);
                return false;
            }

            if (user.password.equals(input)) {
                currentUser = username;
                return true;
            } else {
                System.out.println("❌ Incorrect password.");
                if (++tries >= 10) {
                    System.out.println("⚠ Too many attempts. Try later.");
                    return false;
                }
            }
        }
    }

    private void handleForgotPassword(User user) {
        System.out.println("Security Question: " + user.securityQuestion);
        System.out.print("Answer: ");
        String answer = sc.nextLine().trim().toLowerCase();

        if (answer.equals(user.securityAnswer)) {
            String newPassword;
            while (true) {
                System.out.print("Set new strong password: ");
                newPassword = sc.nextLine();
                if (isStrongPassword(newPassword)) break;
                System.out.println("❌ Weak password.");
            }
            user.password = newPassword;
            saveUserData();
            System.out.println("🔑 Password reset successfully!");
        } else {
            System.out.println("❌ Incorrect answer.");
        }
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                !password.equals(password.toLowerCase()) &&
                !password.equals(password.toUpperCase()) &&
                Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
    }

    public void updateQuizScore(String category, int score) {
        if (currentUser != null && users.containsKey(currentUser)) {
            users.get(currentUser).quizHistory.add(new QuizAttempt(category, score));
            saveUserData();
        }
    }

    public void showUserProgress() {
        if (currentUser != null && users.containsKey(currentUser)) {
            List<QuizAttempt> history = users.get(currentUser).quizHistory;
            if (history.isEmpty()) {
                System.out.println("📉 No quiz history yet.");
                return;
            }

            System.out.println("📊 Full Quiz History for " + currentUser + ":");
            for (QuizAttempt attempt : history) {
                System.out.printf("%s | %tF %tT → %d/20 (%.1f%%)\n",
                        attempt.category, attempt.attemptDate, attempt.attemptDate,
                        attempt.score, attempt.score * 5.0);
            }
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getEducationalBackground() {
        if (currentUser != null && users.containsKey(currentUser)) {
            return users.get(currentUser).educationalBackground;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void loadUserData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            users = (Map<String, User>) ois.readObject();
        } catch (Exception e) {
            users = new HashMap<>();
        }
    }

    private void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("⚠ Failed to save user data.");
        }
    }
}