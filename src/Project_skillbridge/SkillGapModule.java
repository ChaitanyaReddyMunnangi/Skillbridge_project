package Project_skillbridge;

import java.util.*;

public class SkillGapModule {

    private List<String> lastMissingSkills = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void findSkillGaps() {
        lastMissingSkills.clear();

        Map<String, Map<String, List<String>>> sectors = new LinkedHashMap<>();

        Map<String, List<String>> itSubcategories = new LinkedHashMap<>();
        itSubcategories.put("Software Developer", Arrays.asList("Java", "Python", "Data Structures", "OOPs"));
        itSubcategories.put("Data Scientist", Arrays.asList("Python", "Statistics", "Machine Learning", "Pandas"));
        itSubcategories.put("System Administrator", Arrays.asList("Linux", "Networking", "Shell Scripting", "DNS"));
        itSubcategories.put("Cyber Security Analyst", Arrays.asList("Network Security", "Ethical Hacking", "Firewalls", "Encryption"));
        itSubcategories.put("Mobile App Developer", Arrays.asList("Java", "Kotlin", "Android SDK", "Flutter"));
        itSubcategories.put("Web Developer", Arrays.asList("HTML", "CSS", "JavaScript", "React"));
        itSubcategories.put("IT Officer (Govt Banks)", Arrays.asList("Networking", "Database", "Cybersecurity", "Banking Systems"));
        itSubcategories.put("Programmer (NIC)", Arrays.asList("C", "Java", "Algorithms", "Database"));
        itSubcategories.put("AI/ML Engineer", Arrays.asList("Python", "TensorFlow", "Deep Learning", "Data Preprocessing"));
        itSubcategories.put("Cloud Engineer", Arrays.asList("AWS", "Azure", "Docker", "Kubernetes"));

        Map<String, List<String>> nonItSubcategories = new LinkedHashMap<>();
        nonItSubcategories.put("Marketing", Arrays.asList("Digital Marketing", "Communication", "SEO", "Sales Techniques"));
        nonItSubcategories.put("Management", Arrays.asList("Leadership", "Team Management", "Project Planning", "Decision Making"));
        nonItSubcategories.put("Accountant", Arrays.asList("Accounting Principles", "Tally", "GST", "Financial Reporting"));
        nonItSubcategories.put("Police Officer", Arrays.asList("Law Knowledge", "Reasoning", "General Awareness", "Constitution"));
        nonItSubcategories.put("Administrative Officer (IAS/IPS)", Arrays.asList("Polity", "Current Affairs", "Governance", "Decision Making"));
        nonItSubcategories.put("Bank Clerk (Govt Banks)", Arrays.asList("Quantitative Aptitude", "Reasoning", "English", "Banking Awareness"));
        nonItSubcategories.put("Hotel Management", Arrays.asList("Culinary Skills", "Food Safety", "Recipe Development", "Nutrition"));
        nonItSubcategories.put("Lawyer", Arrays.asList("IPC", "CPC", "Legal Drafting", "Constitutional Law"));
        nonItSubcategories.put("Civil Engineer (Govt Projects)", Arrays.asList("AutoCAD", "Concrete Technology", "Surveying", "Estimation"));
        nonItSubcategories.put("Journalist", Arrays.asList("Reporting", "Writing Skills", "Ethics", "Media Law"));

        sectors.put("IT Sector", itSubcategories);
        sectors.put("Non-IT Sector", nonItSubcategories);

        System.out.println("\n\u001B[36m📊 SKILL GAP ANALYSIS\u001B[0m");

        int index = 1;
        List<String> sectorKeys = new ArrayList<>(sectors.keySet());
        for (String sector : sectorKeys) {
            System.out.println(index + ". " + sector);
            index++;
        }
        System.out.print("Choose Sector: ");
        int sectorChoice = sc.nextInt();
        sc.nextLine();

        if (sectorChoice < 1 || sectorChoice > sectorKeys.size()) {
            System.out.println("\u001B[31m❌ Invalid sector choice.\u001B[0m");
            return;
        }

        String selectedSector = sectorKeys.get(sectorChoice - 1);
        Map<String, List<String>> subcategories = sectors.get(selectedSector);
        List<String> subcategoryKeys = new ArrayList<>(subcategories.keySet());

        System.out.println("\n\u001B[34m📁 Choose Subcategory in " + selectedSector + ":\u001B[0m");
        index = 1;
        for (String sub : subcategoryKeys) {
            System.out.println(index + ". " + sub);
            index++;
        }
        System.out.print("Enter choice: ");
        int subChoice = sc.nextInt();
        sc.nextLine();

        if (subChoice < 1 || subChoice > subcategoryKeys.size()) {
            System.out.println("\u001B[31m❌ Invalid subcategory choice.\u001B[0m");
            return;
        }

        String selectedSubcategory = subcategoryKeys.get(subChoice - 1);
        List<String> requiredSkills = subcategories.get(selectedSubcategory);

        System.out.println("\nEnter your current skills (comma separated): ");
        String[] userSkillsInput = sc.nextLine().toLowerCase().split(",");
        List<String> userSkills = new ArrayList<>();
        for (String skill : userSkillsInput) {
            userSkills.add(skill.trim());
        }

        System.out.println("\n\u001B[33m📌 Missing Skills in " + selectedSubcategory + ":\u001B[0m");
        boolean hasMissing = false;
        for (String skill : requiredSkills) {
            if (!userSkills.contains(skill.toLowerCase())) {
                System.out.println("❗ " + skill + " → Learn at: https://www.learn" + skill.replace(" ", "").toLowerCase() + ".com");
                lastMissingSkills.add(skill);
                hasMissing = true;
            }
        }

        if (!hasMissing) {
            System.out.println("\u001B[32m🎉 You have all required skills for " + selectedSubcategory + "!\u001B[0m");
        }
    }

    public List<String> getLastMissingSkills() {
        return lastMissingSkills;
    }
}
