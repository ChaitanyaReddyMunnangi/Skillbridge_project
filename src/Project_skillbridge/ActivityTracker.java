package Project_skillbridge;

public class ActivityTracker {

    private String currentUser = "Guest";

    private int skillGapChecks      = 0;
    private int quizzesTaken        = 0;
    private int correctAnswers      = 0;
    private int motivationalTipsViewed = 0;
    private int pdfReportsGenerated = 0;

    public void setCurrentUser(String username) {
        this.currentUser = username != null && !username.isEmpty() ? username : "Guest";
        resetSessionStats();
    }

    private void resetSessionStats() {
        skillGapChecks = 0;
        quizzesTaken = 0;
        correctAnswers = 0;
        motivationalTipsViewed = 0;
        pdfReportsGenerated = 0;
    }

    public void recordSkillGapCheck()          { skillGapChecks++; }
    public void recordQuizAttempt(int correct) { quizzesTaken++; correctAnswers += correct; }
    public void recordMotivationalTip()        { motivationalTipsViewed++; }
    public void recordPDFGenerated()           { pdfReportsGenerated++; }

    public void showActivityReport() {
        System.out.println("\n\u001B[36m📊 Activity & Progress Report for " + currentUser + "\u001B[0m");
        System.out.println("\u001B[34m──────────────────────────────────────────────────────\u001B[0m");
        System.out.printf("\u001B[33m🛠️  Skill-gap analysis run      :\u001B[0m %d\n", skillGapChecks);
        System.out.printf("\u001B[33m📝 Quizzes taken                :\u001B[0m %d\n", quizzesTaken);
        System.out.printf("\u001B[33m✅ Total correct answers        :\u001B[0m %d\n", correctAnswers);
        System.out.printf("\u001B[33m💡 Motivational tips read       :\u001B[0m %d\n", motivationalTipsViewed);
        System.out.printf("\u001B[33m📄 PDF reports generated        :\u001B[0m %d\n", pdfReportsGenerated);

        if (quizzesTaken > 0) {
            double accuracy = (correctAnswers * 100.0) / (quizzesTaken * 20);
            System.out.printf("\u001B[33m📈 Quiz accuracy                :\u001B[0m %.2f%%\n", accuracy);
        }
        System.out.println("\u001B[34m──────────────────────────────────────────────────────\u001B[0m");
    }
}
