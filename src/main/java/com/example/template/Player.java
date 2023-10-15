package com.example.template;

import java.util.ArrayList;

public class Player {
    private String name;
    private int amountOfTerritories = 0;
    private ArrayList<Territory> allTerritoriesControlled = new ArrayList<>();
    private ArrayList<Territory> attackTerritories = new ArrayList<>();
    private String color;
    private int credits = 100;

    private int playerID;

    private boolean lost = false;

    public Player(String name, int amountOfTerritories, Territory startingTerritory, String color, int playerID) {
        this.name = name;
        this.amountOfTerritories = amountOfTerritories;
        allTerritoriesControlled.add(startingTerritory);
        this.color = color;
        this.playerID = playerID;
    }

    public void playerLost() {
        lost = true;
    }

    public boolean getLost() {
        return lost;
    }

    public void removeFromCredits(int amountToRemove) {
        credits-=amountToRemove;
    }

    public void addToCredits(int amountToAdd) {
        credits+=amountToAdd;
    }

    public int getCredits() {
        return credits;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void addToAttackTerritories(Territory territory) {
        attackTerritories.add(territory);
    }

    public ArrayList<Territory> getAttackTerritories() {
        return attackTerritories;
    }

    public Player(String color) {
        this.color = color;
    }

    public ArrayList<Territory> getAllTerritoriesControlled() {
        return allTerritoriesControlled;
    }

    public String getName() {
        return name;
    }

    public int getAmountOfTerritories() {
        return amountOfTerritories;
    }

    public String getColor() {
        return color;
    }
}
