package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import types.Application;
import util.ApplicationParser;

/**
 * Main application for Scholarship Evaluation System.
 * Evaluates three types of scholarships: Merit, Need, and Research.
 */
public class ScholarshipEvaluationSystem {
    
    /**
     * Main entry point for the scholarship evaluation system.
     * 
     * Process Flow:
     * 1. Read scholarship applications from CSV file
     * 2. Evaluate each application using polymorphic evaluate() method
     * 3. Sort results by applicant ID
     * 4. Display formatted results
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Configuration
        final String CSV_FILENAME = "files/ScholarshipApplications.csv";
        
        // Step 1: Read applications from CSV file
        System.out.println("Reading scholarship applications from " + CSV_FILENAME + "...");
        ArrayList<Application> applications = ApplicationParser.parse(CSV_FILENAME);
        
        // Validate data was loaded successfully
        if (applications.isEmpty()) {
            System.err.println("ERROR: No scholarship applications found or file could not be read.");
            System.err.println("Please ensure '" + CSV_FILENAME + "' exists in the current directory.");
            System.exit(1);
        }
        
        System.out.println("Loaded " + applications.size() + " applications.");
        System.out.println("Evaluating applications...\n");
        
        // Step 2: Evaluate all applications
        for (Application application : applications) {
            application.evaluate();
        }
        
        // Step 3: Sort applications by Applicant ID (11xxx, 22xxx, 33xxx)
        Collections.sort(applications, new Comparator<Application>() {
            @Override
            public int compare(Application a1, Application a2) {
                return Integer.compare(a1.getApplicant().getId(), 
                                     a2.getApplicant().getId());
            }
        });
        
        // Step 4: Print results
        for (Application application : applications) {
            System.out.println(application.evaluate());
        }
        
        // Summary statistics (bonus - shows good programming practice)
        printSummary(applications);
    }
    
    /**
     * Prints summary statistics of the evaluation process.
     * Provides overview of acceptance rates and scholarship distribution.
     * 
     * @param applications the list of evaluated applications
     */
    private static void printSummary(ArrayList<Application> applications) {
        int totalApplications = applications.size();
        int acceptedCount = 0;
        int rejectedCount = 0;
        int fullScholarships = 0;
        int halfScholarships = 0;
        
        for (Application application : applications) {
            if (application.isAccepted()) {
                acceptedCount++;
                String typeDisplay = application.getType().getDisplay();
                if ("Full".equals(typeDisplay)) {
                    fullScholarships++;
                } 
                else {
                    halfScholarships++;
                }
            } 
            else {
                rejectedCount++;
            }
        }
        
        // Evaluation Summary
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EVALUATION SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("Total Applications: " + totalApplications);
        System.out.println("Accepted: " + acceptedCount + " (" + 
                          String.format("%.1f%%", 100.0 * acceptedCount / totalApplications) + ")");
        System.out.println("  - Full Scholarships: " + fullScholarships);
        System.out.println("  - Half Scholarships: " + halfScholarships);
        System.out.println("Rejected: " + rejectedCount + " (" + 
                          String.format("%.1f%%", 100.0 * rejectedCount / totalApplications) + ")");
        System.out.println("=".repeat(60));
    }
}
