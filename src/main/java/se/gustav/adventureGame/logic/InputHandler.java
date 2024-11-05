package se.gustav.adventureGame.model;

import java.util.Scanner;

/**
 * InputHandler hanterar spelarens inmatning.
 */
public class InputHandler {
    private Scanner scanner;

    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    /**
     * Hämtar inmatning från spelaren.
     * @return En sanerad version av spelarens inmatning.
     */
    public String getUserInput() {
        try {
            System.out.print("Ditt val: ");
            return scanner.nextLine().trim().toLowerCase().replaceAll("\\s+", " ");
        } catch (Exception e) {
            System.out.println("Ogiltigt val. Försök igen.");
            return getUserInput(); // Be om inmatning igen
        }
    }
}
