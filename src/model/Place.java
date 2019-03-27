
package model;

public class Place {
    
    private static String name;
    private static String description;
    private static String north;
    private static String south;
    private static String east;
    private static String west;

    public Place() {
    }
    
       public Place(String name, String description, String north, String south, String east, String west) {
           this.name = name;
           this.description = description;
           this.north = north;
           this.south = south;
           this.east = east;
           this.west = west;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Place.name = name;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        Place.description = description;
    }

    public static String getNorth() {
        return north;
    }

    public static void setNorth(String north) {
        Place.north = north;
    }

    public static String getSouth() {
        return south;
    }

    public static void setSouth(String south) {
        Place.south = south;
    }

    public static String getEast() {
        return east;
    }

    public static void setEast(String east) {
        Place.east = east;
    }

    public static String getWest() {
        return west;
    }

    public static void setWest(String west) {
        Place.west = west;
    }

    @Override
    public String toString() {
        
        
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}
