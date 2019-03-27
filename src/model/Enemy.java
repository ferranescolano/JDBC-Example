
package model;


public class Enemy {
    private static String name;
    private static String debility;
    private static int level;
    private static Place place;

    public Enemy() {
        
    }
    
       public Enemy(String name, String debility, int level, Place place) {
           
           this.name = name;
           this.debility = debility;
           this.level = level;
           this.place = place;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Enemy.name = name;
    }

    public static String getDebility() {
        return debility;
    }

    public static void setDebility(String debility) {
        Enemy.debility = debility;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        Enemy.level = level;
    }

    public static Place getPlace() {
        return place;
    }

    public static void setPlace(Place place) {
        Enemy.place = place;
    }

    
       
}
