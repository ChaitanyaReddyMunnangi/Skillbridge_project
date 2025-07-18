package Project_skillbridge;

import java.util.*;

public class MainApp {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        UserAuth userAuth = new UserAuth();
        SkillGapModule skillGapModule = new SkillGapModule();
        MotivationModule motivationModule = new MotivationModule();
        QuizModule quizModule = new QuizModule();
        PDFModule pdfModule = new PDFModule();
        ActivityTracker activityTracker = new ActivityTracker();

        System.out.println("\u001B[36m\n==============================");
        System.out.println("🌉 Welcome to SkillBridge App 🌉");
        System.out.println("==============================\u001B[0m");

        userAuth.loginSignup();
        String currentUser = userAuth.getCurrentUser();
        activityTracker.setCurrentUser(currentUser);

        int choice = -1;

        while (true) {
            System.out.println("\n\u001B[34m=========== MAIN MENU ===========\u001B[0m");
            System.out.println("\u001B[33m1. 🔍 Find Missing Skills (IT / Non-IT)\u001B[0m");
            System.out.println("\u001B[32m2. 🧠 Take Skill Quiz\u001B[0m");
            System.out.println("\u001B[36m3. 💡 Get Motivational Tip\u001B[0m");
            System.out.println("\u001B[35m4. 📝 Export Missing Skills to PDF\u001B[0m");
            System.out.println("\u001B[33m5. 🔁 Logout / Switch User\u001B[0m");
            System.out.println("\u001B[36m6. 📈 View Activity & Progress Report\u001B[0m");
            System.out.println("\u001B[31m7. ❌ Exit App\u001B[0m");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {
                        skillGapModule.findSkillGaps();
                        activityTracker.recordSkillGapCheck();
                    }
                    case 2 -> {
                        quizModule.startQuiz(currentUser);  // ✅ Pass username to avoid compile error
                        activityTracker.recordQuizAttempt(quizModule.getLastScore());
                    }
                    case 3 -> {
                        motivationModule.showTip();
                        activityTracker.recordMotivationalTip();
                    }
                    case 4 -> {
                        pdfModule.exportToPDF(skillGapModule.getLastMissingSkills(), currentUser);
                        activityTracker.recordPDFGenerated();
                    }
                    case 5 -> {
                        userAuth.loginSignup();
                        currentUser = userAuth.getCurrentUser();
                        activityTracker.setCurrentUser(currentUser);
                    }
                    case 6 -> {
                        activityTracker.showActivityReport();
                        userAuth.showUserProgress();
                    }
                    case 7 -> {
                        System.out.println("\u001B[35m👋 Thank you for using SkillBridge!\u001B[0m");
                        return;
                    }
                    default -> System.out.println("\u001B[31m⚠️ Invalid choice! Try again.\u001B[0m");
                }
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m⚠️ Please enter a valid number!\u001B[0m");
            }
        }
    }
}