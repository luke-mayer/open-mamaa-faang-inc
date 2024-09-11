package gameObjects;

import java.util.ArrayList;
import java.util.HashMap;

public class Location {
    private static final HashMap<LocationName,Location> gameLocations = new HashMap<>(); // List of all Locations

    private String description;
    private final LocationName name;
    private final ArrayList<LocationName> neighbors; // Locations you can visit from the current location.
    private final ArrayList<Action> actions; // Actions that the player can take in this location

    // Constructor - adds the new Location to gameLocations
    public Location(LocationName name, String description){
        this.name = name;
        this.description = description;
        neighbors = new ArrayList<>();
        actions = new ArrayList<>();
        gameLocations.put(name,this);
    }

    // Updates the description with the provided one
    public void updateDescription(String description){
        this.description = description;
    }

    // Returns the indicated Location object from the gameLocations HashMap or null if not in gameLocations
    public static Location getLocation(LocationName locName){
        return gameLocations.get(locName);
    }

    // Get this Location's name
    public LocationName getLocationName(){
        return this.name;
    }

    // Get the number of neighbors
    public int numNeighbors(){
        return neighbors.size();
    }

    // Get the name of the neighbor at the given index
    public LocationName getNeighbor(int indx){
        return neighbors.get(indx);
    }

    // Add a neighboring Location
    public void addNeighbor(LocationName location){
        if(!neighbors.contains(location)){
            neighbors.add(location);
        }
    }

    // Remove a neighboring Location, returns -1 if it is not a neighbor
    public int removeNeighbor(LocationName location){
        if(!neighbors.contains(location)){
            return -1;
        }
        neighbors.remove(location);
        return 1;
    }

    // Get the number of actions
    public int numActions(){
        return actions.size();
    }

    // Get the name of the action at the given index
    public Action getAction(int indx){
        return actions.get(indx);
    }

    // Add an action
    public void addAction(Action action){
        if(!actions.contains(action)){
            actions.add(action);
        }
    }

    // Remove an action, returns -1 if it is not in actions
    public int removeAction(Action action){
        if(!actions.contains(action)){
            return -1;
        }
        actions.remove(action);
        return 1;
    }

}
