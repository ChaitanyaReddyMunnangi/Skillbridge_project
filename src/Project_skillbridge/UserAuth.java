package Project_skillbridge;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class UserAuth {

    private static class User {
        String password;
        String securityQuestion;
        String securityAnswer;
        Map<String, Integer> quizScores = new HashMap<>();

        public User(String password, String securityQuestion, String securityAnswer) {
            this.password = password;
            this.securityQuestion = securityQuestion;
            this.securityAnswer = securityAnswer;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(password).append(";;")
                    .append(securityQuestion).append(";;")
                    .append(securityAnswer).append(";;");
            for (Map.Entry<String, Integer> entry : quizScores.entrySet()) {
                sb.append(entry.getKey()).append("::").append(entry.getValue()).append(";;");
            }
            return sb.toString();
        }

        public static User fromString(String data) {
            String[] parts = data.split(";;");
            if (parts.length < 3) return null;
            User user = new User(parts[0], parts[1], parts[2]);
            for (int i = 3; i < parts.length; i++) {
                if (parts[i].contains("::")) {
                    String[] kv = parts[i].split("::");
                    if (kv.length == 2) {
                        user.quizScores.put(kv[0], Integer.parseInt(kv[1]));
                    }
                }
            }
            return user;
        }
    }

    private static final String DATA_FILE = "users.txt";
    private Map<String, User> users = new HashMap<>();
    private Scanner sc = new Scanner(System.in);
    private String currentUser = null;

    public UserAuth() {
        loadUsers();
    }

    public void loginSignup() {
        while (true) {
            System.out.println("\u001B[34m╔══════════════════════════════════════════╗\u001B[0m");
            System.out.println("\u001B[34m║         🔐  USER AUTHENTICATION MENU     ║\u001B[0m");
            System.out.println("\u001B[34m╚══════════════════════════════════════════╝\u001B[0m");
            System.out.println("\u001B[33m1. ✍️  Sign Up\u001B[0m");
            System.out.println("\u001B[32m2. 🔓 Log In\u001B[0m");
            System.out.println("\u001B[36m3. ❓ Forgot Password\u001B[0m");
            System.out.println("\u001B[31m4. ❌ Exit\u001B[0m");
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
                case "3": forgotPassword(); break;
                case "4": System.out.println("Exiting..."); System.exit(0); break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void signUp() {
        String username;
        while (true) {
            System.out.print("Enter username (min 8 chars, no spaces): ");
            username = sc.nextLine().trim();
            if (username.length() >= 8 && !username.contains(" ")) {
                if (!users.containsKey(username)) break;
                System.out.println("\u001B[31m⚠️  Username already taken!\u001B[0m");
            } else {
                System.out.println("\u001B[31m⚠️  Username must be at least 8 characters and contain no spaces.\u001B[0m");
            }
        }

        String password;
        while (true) {
            System.out.print("Enter strong password (min 8 chars, 1 uppercase, 1 lowercase, 1 special char): ");
            password = sc.nextLine().trim();
            if (isStrongPassword(password)) break;
            System.out.println("\u001B[31m⚠️  Weak password! Follow the password policy.\u001B[0m");
        }

        List<String> securityQuestions = Arrays.asList(
                "What is your pet's name?",
                "What is your mother's maiden name?",
                "What was the name of your first school?",
                "What is your favorite color?",
                "What is your birth city?"
        );

        for (int i = 0; i < securityQuestions.size(); i++) {
            System.out.println((i + 1) + ". " + securityQuestions.get(i));
        }
        System.out.print("Choose a security question (1-" + securityQuestions.size() + "): ");
        int questionIndex = Integer.parseInt(sc.nextLine().trim()) - 1;
        if (questionIndex < 0 || questionIndex >= securityQuestions.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        String question = securityQuestions.get(questionIndex);
        System.out.print("Enter answer to the security question: ");
        String answer = sc.nextLine().trim().toLowerCase();

        users.put(username, new User(password, question, answer));
        saveUsers();
        System.out.println("\u001B[32m✅ User registered successfully!\u001B[0m");
    }

    private boolean login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();

        if (!users.containsKey(username)) {
            System.out.println("\u001B[31m⚠️  Username not found!\u001B[0m");
            return false;
        }

        System.out.print("Enter password: ");
        String password = sc.nextLine().trim();

        if (users.get(username).password.equals(password)) {
            currentUser = username;
            return true;
        } else {
            System.out.println("\u001B[31m❌ Incorrect password.\u001B[0m");
            return false;
        }
    }

    private void forgotPassword() {
        System.out.print("Enter your username: ");
        String username = sc.nextLine().trim();

        if (!users.containsKey(username)) {
            System.out.println("Username not found!");
            return;
        }

        User user = users.get(username);
        System.out.println("Security Question: " + user.securityQuestion);
        System.out.print("Your answer: ");
        String answer = sc.nextLine().trim().toLowerCase();

        if (answer.equals(user.securityAnswer)) {
            String newPassword;
            while (true) {
                System.out.print("Enter new strong password (min 8 chars, 1 uppercase, 1 lowercase, 1 special char): ");
                newPassword = sc.nextLine().trim();
                if (isStrongPassword(newPassword)) break;
                System.out.println("\u001B[31m⚠️  Weak password! Follow the password policy.\u001B[0m");
            }
            user.password = newPassword;
            saveUsers();
            System.out.println("\u001B[32m🔑 Password updated successfully!\u001B[0m");
        } else {
            System.out.println("\u001B[31m❌ Incorrect answer to security question.\u001B[0m");
        }
    }

    private boolean isStrongPassword(String password) {
        if (password.length() < 8) return false;
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasLower = !password.equals(password.toUpperCase());
        boolean hasSpecial = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
        return hasUpper && hasLower && hasSpecial;
    }

    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(DATA_FILE)) {
            for (Map.Entry<String, User> entry : users.entrySet()) {
                writer.println(entry.getKey() + ">>" + entry.getValue().toString());
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to save users: " + e.getMessage());
        }
    }

    private void loadUsers() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(">>");
                if (parts.length == 2) {
                    User user = User.fromString(parts[1]);
                    if (user != null) {
                        users.put(parts[0], user);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to load users: " + e.getMessage());
        }
    }

    public void updateQuizScore(String category, int score) {
        if (currentUser != null && users.containsKey(currentUser)) {
            users.get(currentUser).quizScores.put(category, score);
            saveUsers();
        }
    }

    public void showUserProgress() {
        if (currentUser != null && users.containsKey(currentUser)) {
            Map<String, Integer> scores = users.get(currentUser).quizScores;
            if (scores.isEmpty()) {
                System.out.println("📉 No quiz history available yet.");
                return;
            }
            System.out.println("📊 Skill Progress for " + currentUser + ":");
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                System.out.printf("%s → %d/20 (%.1f%%)\n", entry.getKey(), entry.getValue(), (entry.getValue() * 5.0));
            }
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
