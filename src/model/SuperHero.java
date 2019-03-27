
package model;


public class SuperHero {
    private static String name;
    private static String superpower;
    
    public SuperHero() {
    }
    
    public SuperHero(String name) {
        this.name = name;
    }
    

      public SuperHero(String name, String superpower) {
          this.name = name;
          this.superpower = superpower;
          
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        SuperHero.name = name;
    }

    public static String getSuperpower() {
        return superpower;
    }

    public static void setSuperpower(String superpower) {
        SuperHero.superpower = superpower;
    }

  

}
