package gameObjects;

import java.util.HashMap;

public class Thing {
    private static final HashMap<String,Thing> allThings = new HashMap<>();
    private final String name;
    private String description;

    public Thing(String name, String description){
        this.name = name;
        this.description = description;
        allThings.put(name,this);
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public void updateDescription(String description){
        this.description = description;
    }

    public static Thing getThing(String name){
        return allThings.get(name);
    }

    // Remove a Thing from allThings, returns -1 if it is not in allThings
    public int removeThing(String name){
        if(!allThings.containsKey(name)){
            return -1;
        }
        allThings.remove(name);
        return 1;
    }
}
