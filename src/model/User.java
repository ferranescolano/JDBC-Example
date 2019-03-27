/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author alu2014044
 */
public class User {

   
   
    
    private static String username;
    private static String password;
    private static int level;
    private SuperHero hero;
    private Place place;
    private static int points;
    
    
    public User(){
        
    }
    
    public User(String username, String password, SuperHero hero){
        this.username = username;
        this.password = password;
        this.hero = hero;
    }
    
    
     public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public SuperHero getHero() {
        return hero;
    }

    public void setHero(SuperHero hero) {
        this.hero = hero;
    }
    
     public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        User.level = level;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public static int getPoints() {
        return points;
    }

    public static void setPoints(int points) {
        User.points = points;
    }


    
    @Override
    public String toString() {
        return "User{" + "hero=" + hero + '}';
    }
    
    
    
}
