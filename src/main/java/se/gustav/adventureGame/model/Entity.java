package se.gustav.adventureGame.model;

import se.gustav.adventureGame.logic.*;
import se.gustav.adventureGame.model.*;

public abstract class Entity {
    public String role;
    public int health;
    public int damage;

    public Entity(String role, int health, int damage) {
        this.role = role;
        this.health = health;
        this.damage = damage;
    }

    public String getRole() {
        return role;
    }

    public int getHealth() {
        return health;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    public int getDamage() {
        return damage;
    }

    public void takeHit(int damage) {
        this.health -= damage;
    }

    public void attack(Entity toAttack) {
        toAttack.takeHit(damage);
    }

    public boolean isConscious() {
        return health > 0;
    }
}
