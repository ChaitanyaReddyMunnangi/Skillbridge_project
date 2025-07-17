package Project_skillbridge;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class QuizModule {

    private final Map<String, Map<String, String>> fileMap = new LinkedHashMap<>();
    private final Scanner sc = new Scanner(System.in);
    private int lastScore = 0;
    private final List<Question> wrongAnswers = new ArrayList<>();

    /* ───────────────────────────────────────────────────────────
       Constructor: maps every sub‑category to its question file
       (Make sure these 20 text files are present in
        resources/Project_skillbridge/ before running the quiz.)
       ─────────────────────────────────────────────────────────── */
    public QuizModule() {

        /* ---------- IT Sector (10) ---------- */
        Map<String, String> it = new LinkedHashMap<>();
        it.put("Software Developer",               "software_development_cleaned.txt");
        it.put("Data Scientist",                   "data_scientist_cleaned.txt");
        it.put("System Administrator",             "system_administrator_cleaned.txt");
        it.put("Cyber Security Analyst",           "cyber_security_analyst_cleaned.txt");
        it.put("Mobile App Developer",             "mobile_app_developer_cleaned.txt");
        it.put("Web Developer",                    "web_development_cleaned.txt");
        it.put("IT Officer (Govt Banks)",          "it_officer_govt_banks_cleaned.txt");
        it.put("Programmer (NIC)",                 "programmer_nic_cleaned.txt");
        it.put("AI/ML Engineer",                   "ai_ml_engineer_cleaned.txt");
        it.put("Cloud Engineer",                   "cloud_computing_cleaned.txt");
        fileMap.put("IT Sector", it);

        /* ---------- Non‑IT Sector (10) ---------- */
        Map<String, String> nonIt = new LinkedHashMap<>();
        nonIt.put("Marketing",         "marketing_cleaned.txt");
        nonIt.put("Management",         "management_cleaned.txt");
        nonIt.put("Accountant",                    "accountant_cleaned.txt");
        nonIt.put("Police Officer",                "police_officer_cleaned.txt");
        nonIt.put("Administrative Officer (IAS/IPS)", "administrative_officer_ias_ips_cleaned.txt");
        nonIt.put("Bank Clerk (Govt Banks)",       "banking_cleaned.txt");
        nonIt.put("Hotel Management",                          "chef_cleaned.txt");
        nonIt.put("Lawyer",                        "lawyer_cleaned.txt");
        nonIt.put("Civil Engineer (Govt Projects)", "civil_engineer_govt_projects_cleaned.txt");
        nonIt.put("Journalist",                    "journalist_cleaned.txt");
        fileMap.put("Non-IT Sector", nonIt);
    }

    /* ───────────────────────────────────────────────────────────
       Everything below is unchanged
       ─────────────────────────────────────────────────────────── */

    public void startQuiz() {
        wrongAnswers.clear();
        System.out.println("\n\u001B[35m📚 SkillBridge – Sector‑Aware Quiz\u001B[0m");

        String sector = choose("sector", new ArrayList<>(fileMap.keySet()));
        if (sector == null) return;

        String sub = choose("subcategory", new ArrayList<>(fileMap.get(sector).keySet()));
        if (sub == null) return;

        String resourcePath = fileMap.get(sector).get(sub);
        List<Question> questions = loadFromPackageResource(resourcePath);
        if (questions.isEmpty()) {
            System.out.println("\u001B[31m⚠️  No questions loaded for " + sub + "\u001B[0m");
            return;
        }

        Collections.shuffle(questions);
        int total = Math.min(20, questions.size());
        int score = 0;

        for (int i = 0; i < total; i++) {
            Question q = questions.get(i);
            System.out.println("\n\u001B[36m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\u001B[0m");
            System.out.println("\u001B[33mQ" + (i + 1) + ": " + q.q + "\u001B[0m");
            System.out.println("\u001B[34mA) " + q.a + "\u001B[0m");
            System.out.println("\u001B[34mB) " + q.b + "\u001B[0m");
            System.out.println("\u001B[34mC) " + q.c + "\u001B[0m");
            System.out.println("\u001B[36m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\u001B[0m");

            char ans = readABC();

            if (ans == q.correct) {
                System.out.println("\u001B[32m✅ Correct!\u001B[0m");
                score++;
            } else {
                System.out.println("\u001B[31m❌ Incorrect. Correct: " + q.correct + "\u001B[0m");
                wrongAnswers.add(q);
            }

            System.out.println("💡 Explanation: " + q.expl);
        }

        System.out.println("\n\u001B[35m🎯 You scored " + score + " / " + total + "\u001B[0m");
        lastScore = score;

        if (!wrongAnswers.isEmpty()) {
            System.out.print("\u001B[36mWould you like to review incorrect answers? (y/n): \u001B[0m");
            String reviewChoice = sc.nextLine().trim().toLowerCase();
            if (reviewChoice.equals("y")) {
                System.out.println("\n\u001B[36m🔍 Review of Incorrect Answers:\u001B[0m");
                int i = 1;
                for (Question q : wrongAnswers) {
                    System.out.println("\n\u001B[33mQ" + (i++) + ": " + q.q + "\u001B[0m");
                    System.out.println("\u001B[34mA) " + q.a);
                    System.out.println("B) " + q.b);
                    System.out.println("C) " + q.c);
                    System.out.println("\u001B[31mCorrect Answer: " + q.correct + "\u001B[0m");
                    System.out.println("💡 " + q.expl);
                }
            }
        }
    }

    public int getLastScore() { return lastScore; }

    private String choose(String what, List<String> opts) {
        System.out.println("\n\u001B[36mChoose " + what + ":\u001B[0m");
        for (int i = 0; i < opts.size(); i++) {
            System.out.println((i + 1) + ". " + opts.get(i));
        }
        System.out.print("Enter choice: ");
        try {
            int c = Integer.parseInt(sc.nextLine().trim());
            if (c >= 1 && c <= opts.size()) return opts.get(c - 1);
        } catch (Exception ignored) {}
        System.out.println("\u001B[31m❌ Invalid choice.\u001B[0m");
        return null;
    }

    private char readABC() {
        while (true) {
            System.out.print("Your answer (A/B/C): ");
            String in = sc.nextLine().trim().toUpperCase();
            if (in.matches("[ABC]")) return in.charAt(0);
            System.out.println("\u001B[33m⚠️  Enter A, B or C only.\u001B[0m");
        }
    }

    private List<Question> loadFromPackageResource(String resourceName) {
        List<Question> list = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(
                resourceName.startsWith("/") ? resourceName
                        : "/Project_skillbridge/" + resourceName)) {

            if (is == null) {
                System.out.println("\u001B[31m⚠️  Could not find resource: " + resourceName + "\u001B[0m");
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

                // ✅ Validate that all three options are distinct
                Set<String> optionSet = new HashSet<>(Arrays.asList(a, b, c));
                if (optionSet.size() < 3) {
                    System.out.println("\u001B[33m⚠️  Skipped question due to duplicate options:\u001B[0m " + q);
                    continue;
                }

                list.add(new Question(q, a, b, c, correct, expl));
            }

        } catch (IOException e) {
            System.out.println("\u001B[31m❌ Error reading resource: " + e.getMessage() + "\u001B[0m");
        }

        return list;
    }


    /* Question DTO */
    public static class Question {
        String q, a, b, c, expl;
        char correct;
        Question(String q, String a, String b, String c,
                 char correct, String expl) {
            this.q = q;
            this.a = a;
            this.b = b;
            this.c = c;
            this.correct = correct;
            this.expl = expl;
        }
    }
}
