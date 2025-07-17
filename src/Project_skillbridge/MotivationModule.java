package Project_skillbridge;

import java.util.*;

public class MotivationModule {

    private Map<String, List<String>> tipCategories = new LinkedHashMap<>();
    private Map<String, String> categoryEmojis = new HashMap<>();
    private Scanner sc = new Scanner(System.in);
    private Random rand = new Random();

    public MotivationModule() {
        categoryEmojis.put("Study Tip", "\uD83D\uDCDA");
        categoryEmojis.put("Career Tip", "\uD83D\uDCBC");
        categoryEmojis.put("Mindfulness", "\uD83E\uDDB8");
        categoryEmojis.put("Life Advice", "\uD83C\uDF1F");

        tipCategories.put("Study Tip", Arrays.asList(
                "Break your study time into small chunks with breaks.",
                "Teach someone else to better understand the topic.",
                "Review regularly instead of cramming.",
                "Start with difficult subjects first.",
                "Use active recall when revising."
        ));

        tipCategories.put("Career Tip", Arrays.asList(
                "Update your resume and LinkedIn regularly.",
                "Learn one new industry skill every month.",
                "Network with professionals in your field.",
                "Ask for feedback to improve your growth.",
                "Set short and long-term career goals."
        ));

        tipCategories.put("Mindfulness", Arrays.asList(
                "Take 5 deep breaths to reduce stress.",
                "Practice gratitude daily.",
                "Spend 10 minutes meditating each day.",
                "Avoid multitasking — focus on one task at a time.",
                "Go for mindful walks without devices."
        ));

        tipCategories.put("Life Advice", Arrays.asList(
                "Don’t compare your journey to others.",
                "Failure is part of success — learn from it.",
                "Celebrate small wins daily.",
                "Surround yourself with positive people.",
                "Focus on progress, not perfection."
        ));
    }

    public void showTip() {
        System.out.println("\n\u001B[36m✨ Choose a category for motivation:\u001B[0m");
        int i = 1;
        List<String> categories = new ArrayList<>(tipCategories.keySet());

        for (String category : categories) {
            String emoji = categoryEmojis.getOrDefault(category, "\uD83D\uDCA1");
            System.out.println("\u001B[33m" + i + ". " + emoji + " " + category + "\u001B[0m");
            i++;
        }

        System.out.print("\u001B[36mEnter your choice (1-" + categories.size() + "): \u001B[0m");
        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
            if (choice < 1 || choice > categories.size()) {
                System.out.println("\u001B[31m⚠️ Invalid choice. Showing random tip instead.\u001B[0m");
                choice = rand.nextInt(categories.size()) + 1;
            }
        } catch (Exception e) {
            System.out.println("\u001B[31m⚠️ Invalid input. Showing random tip instead.\u001B[0m");
            choice = rand.nextInt(categories.size()) + 1;
        }

        String selectedCategory = categories.get(choice - 1);
        List<String> tips = tipCategories.get(selectedCategory);
        String selectedTip = tips.get(rand.nextInt(tips.size()));
        String emoji = categoryEmojis.getOrDefault(selectedCategory, "\uD83D\uDCA1");

        System.out.println("\n\u001B[32m" + emoji + " [" + selectedCategory + "] Tip: " + selectedTip + "\u001B[0m");
    }
}
