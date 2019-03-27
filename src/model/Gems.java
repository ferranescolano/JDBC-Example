
package model;


public class Gems {
    
    private static String name;
    private static User user;
    private static Place place;
    private static String owner;
    

    public Gems() {
        
    }
    
    
    public Gems(String name, User user, Place place, String owner) {
        this.name = name;
        this.user = user;
        this.place = place;
        this.owner = owner;
        
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Gems.name = name;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Gems.user = user;
    }

    public static Place getPlace() {
        return place;
    }

    public static void setPlace(Place place) {
        Gems.place = place;
    }

   
    public static String getOwner() {
        return owner;
    }

    public static void setOwner(String owner) {
        Gems.owner = owner;
    }

 

    
    
    
    
    
}
