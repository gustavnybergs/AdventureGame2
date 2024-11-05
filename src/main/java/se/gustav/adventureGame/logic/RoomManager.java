package se.gustav.adventureGame.model;

import se.gustav.adventureGame.model.*;
import java.util.*;

/**
 * RoomManager hanterar rumsnavigation och händelser som sker i rummen.
 */
public class RoomManager {
    private static final String LIVINGROOM = "vardagsrummet";
    private String currentLocation;
    private boolean hallwayVisited;
    private boolean burglarInLivingRoom;
    private boolean fryingPanFound;
    private List<String> visitedRooms;
    private Resident resident;
    private Burglar burglar;
    private InputHandler inputHandler;
    private CombatSystem combatSystem;

    public RoomManager(Resident resident, Burglar burglar) {
        this.currentLocation = LIVINGROOM;
        this.hallwayVisited = false;
        this.burglarInLivingRoom = false;
        this.fryingPanFound = false;
        this.visitedRooms = new ArrayList<>();
        this.resident = resident;
        this.burglar = burglar;
        this.inputHandler = new InputHandler();
        this.combatSystem = new CombatSystem(resident, burglar);
    }

    /**
     * Skriver ut tillgängliga riktningar som spelaren kan ta.
     */
    public void printOptions() {
        System.out.println("Välj en riktning: \n1. Gå till sovrummet \n2. Gå till hallen \n3. Gå till köket \n4. Gå till kontoret \n5. Avsluta");
    }

    /**
     * Flyttar spelaren till ett angivet rum och hanterar rumsspecifik logik.
     * @param roomName Namnet på rummet att flytta till.
     */
    public void moveToRoom(String roomName) {
        if (currentLocation.equals(LIVINGROOM) && !currentLocation.equals(roomName)) {
            if (!hallwayVisited && !roomName.equals("hallen")) {
                if (visitedRooms.contains(roomName)) {
                    System.out.println("Du har redan kollat i " + roomName + ", är du säker på att du vill gå dit igen? (1. Ja / 2. Nej)");
                    String choice = inputHandler.getUserInput();
                    if (choice.equals("2") || choice.equals("nej")) {
                        System.out.println("Du stannar i vardagsrummet.");
                        printOptions();
                        return;
                    }
                }
                printRoomMessageBeforeBurglarFound(roomName);
                visitedRooms.add(roomName);
            } else if (!hallwayVisited && roomName.equals("hallen")) {
                System.out.println("Du är nu i hallen. Du möter en inbrottstjuv och ett slagsmål utbryter!");
                combatSystem.fightOrFlee();
                hallwayVisited = true;
            } else {
                whenBurglarIsFound(roomName);
            }
            currentLocation = roomName;
        } else if (currentLocation.equals(roomName)) {
            System.out.println("Du är redan i " + roomName + ".");
        } else {
            System.out.println("Du måste vara i vardagsrummet för att kunna gå till ett annat rum.");
        }
    }

    /**
     * Skriver ut ett meddelande för rum som har besökts innan tjuven har hittats.
     * @param roomName Namnet på rummet.
     */
    private void printRoomMessageBeforeBurglarFound(String roomName) {
        if (visitedRooms.contains(roomName)) {
            System.out.println("Du har redan kollat i " + roomName + ". Är du säker på att du vill gå dit igen? (1. Ja / 2. Nej)");
            String revisitChoice = inputHandler.getUserInput();
            if (revisitChoice.equals("1") || revisitChoice.equals("ja")) {
                System.out.println("Du är nu i " + roomName + ", men ljudet verkar inte komma härifrån.");
                askToReturnToLivingRoom(roomName);
            } else {
                System.out.println("Du stannar i vardagsrummet.");
                printOptions();
            }
        } else {
            System.out.println("Du är nu i " + roomName + ", men ljudet verkar inte komma härifrån.");
            askToReturnToLivingRoom(roomName);
        }
    }

    /**
     * Frågar spelaren om de vill gå tillbaka till vardagsrummet efter att ha besökt ett rum.
     * @param roomName Namnet på rummet.
     */
    private void askToReturnToLivingRoom(String roomName) {
        System.out.println("Vill du gå tillbaka till vardagsrummet? (1. Ja / 2. Nej)");
        String choice = inputHandler.getUserInput();
        if (choice.equals("1") || choice.equals("ja")) {
            currentLocation = LIVINGROOM;
            if (visitedRooms.contains(roomName)) {
                burglarInLivingRoom = true;
                System.out.println("Du är tillbaka i vardagsrummet och tjuven är här!");
                combatSystem.fightBurglar();
            } else {
                System.out.println("Du är tillbaka i vardagsrummet");
                printOptions();
            }
        } else {
            System.out.println("Tjuven kommer in i " + roomName + "!");
            combatSystem.fightBurglar();
        }
    }

    /**
     * Hanterar händelser när tjuven redan har hittats.
     * @param roomName Namnet på rummet.
     */
    private void whenBurglarIsFound(String roomName) {
        if (!burglarInLivingRoom) {
            burglarInLivingRoom = true;
        }
        switch (roomName) {
            case "sovrummet", "hallen" -> {
                System.out.println("Här finns inget vapen, testa ett annat rum.");
                askToReturnToLivingRoom(roomName);
            }
            case "kontoret" -> {
                System.out.println("Här finns inget vapen, men en telefon att ringa polisen men du hinner inte medan tjuven är aktiv.");
                askToReturnToLivingRoom(roomName);
            }
            case "köket" -> {
                System.out.println("Du är nu i köket. Här kan du välja att plocka upp en stekpanna som ökar din skada.");
                if (!fryingPanFound) {
                    System.out.println("Vill du plocka upp stekpannan? (1. Ja / 2. Nej)");
                    String choice = inputHandler.getUserInput();
                    if (choice.equals("1") || choice.equals("ja")) {
                        collectFryingPan();
                        askToReturnToLivingRoom(roomName);
                    } else {
                        System.out.println("Du valde att inte plocka upp stekpannan.");
                        askToReturnToLivingRoom(roomName);
                    }
                }
            }
            default -> System.out.println("Ogiltigt val. Försök igen.");
        }
    }

    /**
     * Samlar upp stekpannan och ökar spelarens skada.
     */
    private void collectFryingPan() {
        System.out.println("Du har hittat en stekpanna som ökar din skada.");
        resident.setDamage(resident.getDamage() + 6);
        fryingPanFound = true;
    }
}
