package se.gustav.adventureGame.model;

import se.gustav.adventureGame.logic.*;
import se.gustav.adventureGame.model.*;

public class Resident extends Entity {
    private boolean armed;

    public Resident(String name, int health, int damage) {
        super(name, health, damage);
        this.armed = false;
    }

    public void setDamage(int damage) {
        this.damage = damage;
        armed = true;
    }

    public boolean isArmed() {
        return armed;
    }
}


