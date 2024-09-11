package core;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameUtility {
    final public static int SEP_LENGTH = 60; // The default separator length
    final private static int PRINT_DELAY = 50; // The delay in ms between chars when printLine is called

    public static Scanner scan = new Scanner(System.in);

    // Simulates clearing the console
    public static void clearConsole(){
        for(int i = 0; i < 100; i++){
            System.out.println();
        }
    }

    // Prints a separator with length n
    public static void printSeparator(int n){
        for(int i = 0; i < n; i++) {
            System.out.print('-');
        }
    }

    // Prints heading of current section
    public static void printHeading(String title){
        int sepLength = 60 + title.length();
        System.out.println();
        printSeparator(sepLength);
        System.out.println();
        printSeparator(30);
        System.out.print(title);
        printSeparator(30);
        System.out.println();
        printSeparator(sepLength);
        System.out.println();
        System.out.println();
    }

    // Prints the provided error message
    public static void printErrorMessage(String errorMessage){
        printSeparator(errorMessage.length());
        System.out.println(errorMessage);
        printSeparator(errorMessage.length());
    }

    // Pauses for the specified number of milliseconds
    public static void delayMs(int delay) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            String errorMessage = "***ERROR in delayMs***";
            printErrorMessage(errorMessage);
            System.out.println(e.getMessage());
        }
    }

    // Prints the provided line one character at a time.
    public static void printLine(String line){
        boolean issue = false;
        do {
            for (int i = 0; i < line.length(); i++) {
                System.out.print(line.charAt(i));
                System.out.flush();

                try {
                    TimeUnit.MILLISECONDS.sleep(PRINT_DELAY);
                } catch (InterruptedException e) {
                    String errorMessage = "***Sorry, there was a game bug. Reprinting line***";
                    printErrorMessage(errorMessage);
                    issue = true;
                    break;
                }
            }
            System.out.println();
        }while(issue);
    }

    // Waits for player to input anything to continue
    public static void anyInput() {
        System.out.print("\nInput anything to continue... ");
        scan.nextLine();
    }
}
