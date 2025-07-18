package Project_skillbridge;

import java.util.*;

public class PDFModule {

    public void exportToPDF(List<String> missingSkills, String username) {
        if (missingSkills == null || missingSkills.isEmpty()) {
            System.out.println("\u001B[31m⚠️  No missing skills to export!\u001B[0m");
            return;
        }

        System.out.println("\n\u001B[36m📄 Exporting Missing Skills to PDF (Simulated)\u001B[0m");
        System.out.println("\u001B[35m===========================================\u001B[0m");
        System.out.println("\u001B[33m👤 User: \u001B[0m" + username);
        System.out.println("\u001B[33m🕵️ Skills to Learn: \u001B[0m");
        for (String skill : missingSkills) {
            System.out.println(" - " + skill);
        }
        System.out.println("\u001B[35m===========================================\u001B[0m");
        System.out.println("\u001B[32m✅ PDF Export Completed (simulation only).\u001B[0m");

    }
}
