package characters;

import gameObjects.LocationName;


import java.util.HashSet;

public class NPC extends Character {
    private int opinion; // Character's opinion of the player. A value between 1 and 10.

    // Constructor for NPC
    public NPC(String name, LocationName location, int opinion) {
        super(name, location);
        this.opinion = opinion;
    }

    // Used to improve opinion
    public void improveOpinion(int amount){
        this.opinion = Math.min(10, this.opinion + amount);
    }

    // Used to worsen opinion
    public void worsenOpinion(int amount){
        this.opinion = Math.max(1, this.opinion - amount);
    }
}
