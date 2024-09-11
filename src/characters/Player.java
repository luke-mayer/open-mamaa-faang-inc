package characters;
import gameObjects.LocationName;

import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Character{
    private final ArrayList<String> inventory, objectives; // Package visibility for ease of access and manipulation

    // Constructor for the Player
    public Player(String name, LocationName location){
        super(name,location);
        inventory = new ArrayList<>();
        objectives = new ArrayList<>();
    }

    // Adds the provided item to the player's inventory
    public void addToInventory(String item){
        inventory.add(item);
    }

    // Removes the indicated item from the player's inventory, returns -1 if item is not in inventory
    public int removeItem(String item){
        if(!inventory.contains(item)){
            return -1;
        }
        inventory.remove(item);
        return 1;
    }

    // Returns true if inventory is empty, else false
    public boolean inventoryIsEmpty(){
        return inventory.isEmpty();
    }

    // Gets an iterator for the player's inventory
    public Iterator<String> getInventory(){
        return inventory.iterator();
    }

    // Adds the provided objective to the player's objectives
    public void addToObjectives(String objective){
        objectives.add(objective);
    }

    // Removes the indicated objective from the player's objectives, returns -1 if objective is not in objectives
    public int removeObjective(String objective){
        if(!objectives.contains(objective)){
            return -1;
        }
        objectives.remove(objective);
        return 1;
    }

    // Returns true if objectives is empty, else false
    public boolean objectivesIsEmpty(){
        return objectives.isEmpty();
    }

    // Gets an iterator for the player's objectives
    public Iterator<String> getObjectives(){
        return objectives.iterator();
    }

}
