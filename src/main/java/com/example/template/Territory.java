package com.example.template;

import java.util.ArrayList;

public class Territory {
    private String territoryType;
    private int territoryTypeID;
    private Player controllingPlayer;
    private Coordinates coordOfTerritory;
    private int amountOfSoldiers = 0;

    private int creditGain;
    private int baseAmountOfSoldiers;
    private int level = 1;
    private String description;
    private String imagePath;
    private int workerGain;
    private boolean soldierGainBool;
    private int soldierGain;

    private int territoryCost;

    private ArrayList<Integer> creditCostsForUpgrade = new ArrayList<>();

    public Territory(int creditGain, int baseAmountOfSoldiers, String description, String imagePath, int workerGain, int soldierGain, boolean soldierGainBool, int level, String nameWithCredits, int cost, int territoryTypeID, int Level2Cost, int Level3Cost, int Level4Cost) {
        this.creditGain = creditGain;
        this.amountOfSoldiers = baseAmountOfSoldiers;
        this.description = description;
        this.imagePath = imagePath;
        this.soldierGain = soldierGain;
        this.workerGain = workerGain;
        this.level = level;
        this.soldierGainBool = soldierGainBool;
        this.territoryCost = cost;
        this.territoryType = nameWithCredits;
        this.territoryTypeID = territoryTypeID;
        creditCostsForUpgrade.add(Level2Cost);
        creditCostsForUpgrade.add(Level3Cost);
        creditCostsForUpgrade.add(Level4Cost);
    }

    public Territory(int row, int column, int amountOfSoldiers, int territoryTypeID) {
        coordOfTerritory = new Coordinates(row,column);
        this.amountOfSoldiers = amountOfSoldiers;
        this.territoryTypeID = territoryTypeID;
    }

    public Territory(int row, int column, Player controllingPlayer, int amountOfSoldiers,int territoryTypeID) {
        coordOfTerritory = new Coordinates(row,column);
        this.controllingPlayer = controllingPlayer;
        this.amountOfSoldiers = amountOfSoldiers;
        this.territoryTypeID = territoryTypeID;
    }

    public Territory(Territory territory) {
        this.creditGain = territory.creditGain;
        this.amountOfSoldiers = territory.amountOfSoldiers;
        this.description = territory.description;
        this.imagePath = territory.imagePath;
        this.soldierGain = territory.soldierGain;
        this.workerGain = territory.workerGain;
        this.level = territory.level;
        this.soldierGainBool = territory.soldierGainBool;
        this.territoryCost = territory.territoryCost;
        this.territoryType = territory.territoryType;
        this.territoryTypeID = territory.territoryTypeID;
        creditCostsForUpgrade.add(territory.creditCostsForUpgrade.get(0));
        creditCostsForUpgrade.add(territory.creditCostsForUpgrade.get(1));
        creditCostsForUpgrade.add(territory.creditCostsForUpgrade.get(2));
    }

    public void upgradeLevel() {
        level++;
        amountOfSoldiers = amountOfSoldiers + (level*5);
        creditGain = creditGain + (level*15);
        if (soldierGainBool) {
            amountOfSoldiers+=20;
        }
    }

    public void setCreditGain(int creditGain) {
        this.creditGain = creditGain;
    }

    public ArrayList<Integer> getCreditCostsForUpgrade() {
        return creditCostsForUpgrade;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public int getBaseAmountOfSoldiers() {
        return baseAmountOfSoldiers;
    }

    public int getCreditGain() {
        return creditGain;
    }

    public int getSoldierGain() {
        return soldierGain;
    }

    public boolean getSoldierGainBool() {
        return soldierGainBool;
    }

    public int getTerritoryCost() {
        return territoryCost;
    }

    public int getWorkerGain() {
        return workerGain;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTerritoryType() {
        return territoryType;
    }

    public void setTerritoryTypeID(int territoryTypeID) {
        this.territoryTypeID = territoryTypeID;
    }

    public void setTerritoryType(String territoryType) {
        this.territoryType = territoryType;
    }

    public void setCoordOfTerritory(Coordinates coordOfTerritory) {
        this.coordOfTerritory = coordOfTerritory;
    }

    public void setAmountOfSoldiers(int amountOfSoldiers) {
        this.amountOfSoldiers = amountOfSoldiers;
    }

    public int getTerritoryTypeID() {
        return territoryTypeID;
    }

    public int getAmountOfSoldiers() {
        return amountOfSoldiers;
    }

    public Coordinates getCoordOfTerritory() {
        return coordOfTerritory;
    }

    public void setControllingPlayer(Player controllingPlayer) {
        this.controllingPlayer = controllingPlayer;
    }

    public Player getControllingPlayer() {
        return controllingPlayer;
    }
}
