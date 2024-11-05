package se.gustav.adventureGame;

import se.gustav.adventureGame.logic.*;
import se.gustav.adventureGame.model.*;
import java.util.*;

public class Game {
    private static final String LIVINGROOM = "vardagsrummet";
    private boolean running = true;
    private Resident resident;
    private Burglar burglar;
    private RoomManager roomManager;
    private InputHandler inputHandler;
    private CombatSystem combatSystem;

    public Game() {
        resident = new Resident("Resident", 16, 3);
        burglar = new Burglar("Burglar", 16, 4);
        inputHandler = new InputHandler();
        roomManager = new RoomManager(resident, burglar, inputHandler, null); // Temporärt null för combatSystem
        combatSystem = new CombatSystem(resident, burglar, roomManager);
        roomManager.setCombatSystem(combatSystem);
    }

    public void start() {
        System.out.println("Välkommen till Adventure Game! Ditt mål är att överleva genom att utforska olika områden och samla föremål.");
        System.out.println("Du vaknar av ett ljud. Det verkar som om något har hänt. Du måste undersöka vilket rum ljudet kommer ifrån.");
        roomManager.printOptions();
        while (running && resident.isConscious()) {
            processInput(inputHandler.getRoomChoice());  // Ta bort getUserInput och använd getRoomChoice direkt
        }
    }

    private void processInput(int choice) {  // Ändra till int parameter istället för String
        switch (choice) {
            case 1 -> roomManager.moveToRoom("sovrummet");
            case 2 -> roomManager.moveToRoom("hallen");
            case 3 -> roomManager.moveToRoom("köket");
            case 4 -> roomManager.moveToRoom("kontoret");
            case 5 -> {
                System.out.println("Spelet avslutas. Tack för att du spelade!");
                running = false;
            }
        }
    }
}
