package se.gustav.adventureGame.logic;

import se.gustav.adventureGame.logic.*;
import se.gustav.adventureGame.model.*;

import java.util.Scanner;
import java.lang.String;
import java.lang.System;


public class InputHandler {
    private Scanner scanner;

    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    public String getUserInput() {
        System.out.print("Ditt val: ");
        String input = scanner.nextLine().trim().toLowerCase().replaceAll("\\s+", " ");

        while (input.isEmpty()) {
            System.out.println("Du måste göra ett val.");
            System.out.print("Ditt val: ");
            input = scanner.nextLine().trim().toLowerCase().replaceAll("\\s+", " ");
        }
        return input;
    }

    public boolean getYesNoInput() {
        while (true) {
            String input = getUserInput();
            if (input.equals("1") || input.equals("ja")) {
                return true;
            }
            if (input.equals("2") || input.equals("nej")) {
                return false;
            }
            System.out.println("Ogiltigt val. Svara med '1'/'ja' eller '2'/'nej'.");
        }
    }

    public int getRoomChoice() {
        while (true) {
            String input = getUserInput();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 5) {
                    return choice;
                }
                System.out.println("Välj ett rum mellan 1-5.");
            } catch (NumberFormatException e) {
                if (input.equals("gå till sovrummet")) return 1;
                if (input.equals("gå till hallen")) return 2;
                if (input.equals("gå till köket")) return 3;
                if (input.equals("gå till kontoret")) return 4;
                if (input.equals("avsluta")) return 5;
                System.out.println("Ogiltigt val. Använd siffror 1-5 eller skriv 'gå till' följt av rumsnamnet.");
            }
        }
    }
}
