package gameObjects;

import java.util.ArrayList;
import java.util.HashMap;

// A Thing that hold Items
public class Container extends Thing{

    private final static HashMap<Action,Container> allContainers = new HashMap<>(); // HashMap of all containers which is accessed with an Action
    private final ArrayList<Item> contents; // The items stored in the container

    public Container(String name, String description, Action action) {
        super(name, description);
        this.contents = new ArrayList<>();
        allContainers.put(action,this);
    }

    // Returns the indicated Container object from the allContainers HashMap or null if not in allContainers
    public static Container getContainer(Action action){
        return allContainers.get(action);
    }

    // Get the number of items
    public int numItems(){
        return contents.size();
    }

    // Get the name of the item at the given index
    public Item getItem(int indx){
        return contents.get(indx);
    }

    // Add an item
    public void addItem(Item item){
        if(!contents.contains(item)){
            contents.add(item);
        }
    }

    // Remove an item, returns -1 if it is not in contents
    public int removeItem(Item item){
        if(!contents.contains(item)){
            return -1;
        }
        contents.remove(item);
        return 1;
    }
}
