package Project_skillbridge;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class QuizModule {

    private final Map<String, Map<String, String>> fileMap = new LinkedHashMap<>();
    private final Scanner sc = new Scanner(System.in);
    private int lastScore = 0;
    private final List<Question> wrongAnswers = new ArrayList<>();
    private final List<Question> allQuestionsAttempted = new ArrayList<>();

    public QuizModule() {
        Map<String, String> it = new LinkedHashMap<>();
        it.put("Software Developer", "software_development_cleaned.txt");
        it.put("Data Scientist", "data_scientist_cleaned.txt");
        it.put("System Administrator", "system_administrator_cleaned.txt");
        it.put("Cyber Security Analyst", "cyber_security_analyst_cleaned.txt");
        it.put("Mobile App Developer", "mobile_app_developer_cleaned.txt");
        it.put("Web Developer", "web_development_cleaned.txt");
        it.put("IT Officer (Govt Banks)", "it_officer_govt_banks_cleaned.txt");
        it.put("Programmer (NIC)", "programmer_nic_cleaned.txt");
        it.put("AI/ML Engineer", "ai_ml_engineer_cleaned.txt");
        it.put("Cloud Engineer", "cloud_computing_cleaned.txt");
        fileMap.put("IT Sector", it);

        Map<String, String> nonIt = new LinkedHashMap<>();
        nonIt.put("Marketing", "marketing_cleaned.txt");
        nonIt.put("Management", "management_cleaned.txt");
        nonIt.put("Accountant", "accountant_cleaned.txt");
        nonIt.put("Police Officer", "police_officer_cleaned.txt");
        nonIt.put("Administrative Officer (IAS/IPS)", "administrative_officer_ias_ips_cleaned.txt");
        nonIt.put("Bank Clerk (Govt Banks)", "banking_cleaned.txt");
        nonIt.put("Hotel Management", "chef_cleaned.txt");
        nonIt.put("Lawyer", "lawyer_cleaned.txt");
        nonIt.put("Civil Engineer (Govt Projects)", "civil_engineer_govt_projects_cleaned.txt");
        nonIt.put("Journalist", "journalist_cleaned.txt");
        fileMap.put("Non-IT Sector", nonIt);
    }

    public void startQuiz(String username) {
        wrongAnswers.clear();
        System.out.println("\n📚 SkillBridge – Sector‑Aware Quiz");

        String sector = choose("sector", new ArrayList<>(fileMap.keySet()));
        if (sector == null) return;

        String sub = choose("subcategory", new ArrayList<>(fileMap.get(sector).keySet()));
        if (sub == null) return;

        String resourcePath = fileMap.get(sector).get(sub);
        List<Question> questions = loadFromPackageResource(resourcePath);
        if (questions.isEmpty()) {
            System.out.println("⚠️  No questions loaded for " + sub);
            return;

        }

        Collections.shuffle(questions);
        int total = Math.min(20, questions.size());
        int score = 0;

        for (int i = 0; i < total; i++) {
            Question q = questions.get(i);
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("Q" + (i + 1) + ": " + q.q);
            System.out.println("A) " + q.a);
            System.out.println("B) " + q.b);
            System.out.println("C) " + q.c);
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            char ans = readABC();
            q.userAnswer = ans;
            allQuestionsAttempted.add(q);

            if (ans == q.correct) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Incorrect. Correct: " + q.correct);
                wrongAnswers.add(q);
            }

            System.out.println("💡 Explanation: " + q.expl);
        }

        System.out.println("\n🎯 You scored " + score + " / " + total);
        lastScore = score;

        System.out.print("📁 Do you want to save this quiz report? (y/n): ");
        String saveChoice = sc.nextLine().trim().toLowerCase();
        if (saveChoice.equals("y")) saveQuizReport(username);

        if (!wrongAnswers.isEmpty()) {
            System.out.print("🔍 Review incorrect answers? (y/n): ");
            if (sc.nextLine().trim().equalsIgnoreCase("y")) {
                int i = 1;
                for (Question q : wrongAnswers) {
                    System.out.println("\nQ" + (i++) + ": " + q.q);
                    System.out.println("A) " + q.a);
                    System.out.println("B) " + q.b);
                    System.out.println("C) " + q.c);
                    System.out.println("Correct Answer: " + q.correct);
                    System.out.println("💡 " + q.expl);
                }
            }
        }
    }

    private void saveQuizReport(String username) {
        String filename = username + "_QuizReport.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))){  // ✅ appends{
            writer.write("\n\n🗓️ Quiz Session: " +
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            writer.newLine();
            writer.write("──────────────────────────────────────────");
            writer.newLine();
            writer.write("📄 SkillBridge Quiz Report");
            writer.newLine();
            writer.write("👤 User: " + username);
            writer.newLine();
            writer.write("🎯 Score: " + lastScore);
            writer.newLine();
            writer.write("──────────────────────────────────────────");
            writer.newLine();
            writer.write("🧠 Full Quiz Log:");
            writer.newLine();

            int i = 1;
            for (Question q : allQuestionsAttempted) {
                writer.newLine();
                writer.write("Q" + (i++) + ": " + q.q);
                writer.newLine();
                writer.write("A) " + q.a);
                writer.newLine();
                writer.write("B) " + q.b);
                writer.newLine();
                writer.write("C) " + q.c);
                writer.newLine();
                writer.write("✔ Correct Answer: " + q.correct);
                writer.newLine();
                writer.write("📝 Your Answer: " + q.userAnswer);
                writer.newLine();
                writer.write(q.userAnswer == q.correct ? "✅ Correct" : "❌ Incorrect");
                writer.newLine();
                writer.write("💡 Explanation: " + q.expl);
                writer.newLine();
                writer.write("------------------------------------------");
            }

            System.out.println("✅ Quiz report saved as " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error saving report: " + e.getMessage());
        }
    }
    public int getLastScore() {
        return lastScore;
    }

    private String choose(String what, List<String> opts) {
        System.out.println("\nChoose " + what + ":");
        for (int i = 0; i < opts.size(); i++) {
            System.out.println((i + 1) + ". " + opts.get(i));
        }
        System.out.print("Enter choice: ");
        try {
            int c = Integer.parseInt(sc.nextLine().trim());
            if (c >= 1 && c <= opts.size()) return opts.get(c - 1);
        } catch (Exception ignored) {
        }
        System.out.println("❌ Invalid choice.");
        return null;
    }

    private char readABC() {
        while (true) {
            System.out.print("Your answer (A/B/C): ");
            String in = sc.nextLine().trim().toUpperCase();
            if (in.matches("[ABC]")) return in.charAt(0);
            System.out.println("⚠️  Enter A, B or C only.");
        }
    }

    private List<Question> loadFromPackageResource(String resourceName) {
        List<Question> list = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(
                resourceName.startsWith("/") ? resourceName
                        : "/Project_skillbridge/" + resourceName)) {

            if (is == null) {
                System.out.println("⚠️  Could not find resource: " + resourceName);
                return list;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            List<String> lines = reader.lines().toList();

            for (int i = 0; i + 5 < lines.size(); i += 6) {
                String q = lines.get(i).trim();
                String a = lines.get(i + 1).trim();
                String b = lines.get(i + 2).trim();
                String c = lines.get(i + 3).trim();
                char correct = lines.get(i + 4).trim().toUpperCase().charAt(0);
                String expl = lines.get(i + 5).trim();

                Set<String> optionSet = new HashSet<>(Arrays.asList(a, b, c));
                if (optionSet.size() < 3) {
                    System.out.println("⚠️  Skipped question due to duplicate options: " + q);
                    continue;
                }

                list.add(new Question(q, a, b, c, correct, expl));
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading resource: " + e.getMessage());
        }

        return list;
    }

    public static class Question {
        String q, a, b, c, expl;
        char correct;
        char userAnswer;

        Question(String q, String a, String b, String c, char correct, String expl) {
            this.q = q;
            this.a = a;
            this.b = b;
            this.c = c;
            this.correct = correct;
            this.expl = expl;
        }
    }
}
