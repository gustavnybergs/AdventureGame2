package se.gustav.adventureGame.model;

import se.gustav.adventureGame.model.*;

/**
 * CombatSystem hanterar stridslogik mellan spelaren och tjuven.
 */
public class CombatSystem {
    private Resident resident;
    private Burglar burglar;
    private InputHandler inputHandler;

    public CombatSystem(Resident resident, Burglar burglar) {
        this.resident = resident;
        this.burglar = burglar;
        this.inputHandler = new InputHandler();
    }

    /**
     * Frågar spelaren om de vill slåss eller fly från tjuven.
     */
    public void fightOrFlee() {
        System.out.println("Vill du slåss eller fly? (1. Slåss / 2. Fly)");
        String choice = inputHandler.getUserInput();
        if (choice.equals("slåss") || choice.equals("1")) {
            fightBurglar();
        } else if (choice.equals("fly") || choice.equals("2")) {
            System.out.println("Du flyr tillbaka till vardagsrummet.");
        } else {
            System.out.println("Ogiltigt val, tjuven attackerar dig!");
            burglar.attack(resident);
            if (!resident.isConscious()) {
                System.out.println("Du förlorade striden mot tjuven. Spelet är över.");
            }
        }
    }

    /**
     * Hanterar striden mellan spelaren och tjuven.
     */
    public void fightBurglar() {
        if (!resident.isArmed()) {
            System.out.println("Du är obeväpnad och kan inte försvara dig mot tjuven!");
            System.out.println("Tjuven attackerar dig och du förlorar striden.");
            resident.takeHit(resident.getHealth()); // Gör att Resident automatiskt förlorar medvetandet
            System.out.println("Spelet är över.");
        } else {
            System.out.println("Du attackerar tjuven!");
            resident.attack(burglar);

            if (burglar.isConscious()) {
                System.out.println("Tjuven attackerar dig!");
                burglar.attack(resident);
            }

            if (resident.isConscious() && burglar.isConscious()) {
                System.out.println("Du attackerar tjuven igen!");
                resident.attack(burglar);
            }

            if (!burglar.isConscious()) {
                System.out.println("Du har knockat tjuven!");
            }
        }
    }
}
