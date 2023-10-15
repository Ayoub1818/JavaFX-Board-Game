package com.example.template;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloController {
    @FXML
    public Button skipTurnButton;
    @FXML
    public Button addToPlayersButton;
    @FXML
    public Button removeFromPlayersButton;
    @FXML
    public Label amountOfPlayersLabel;
    @FXML
    public Button confirmPlayerCountButton;
    @FXML
    public Label SecondaryDisplayLabel;
    private ArrayList<Territory> allBaseTerritories = new ArrayList<>();
    public HelloController() {
        allBaseTerritories.add(new Territory(40,10,"Helps Heal Soldiers (Generates Soldiers)","src/main/resources/images/hospital.png",0,10,true,1,"Hospital - 100 Credits",100,2,50,100,150));
        allBaseTerritories.add(new Territory(100,15,"Generates a good amount of credits","src/main/resources/images/powerPlant.png",0,0,false,1,"Power Plant - 50 Credits",50,3,75,150,300));
        allBaseTerritories.add(new Territory(75,20,"Many benefits including generating credits and transport workers and soldiers.","src/main/resources/images/airport.png",10,0,false,1,"Airport - 100 Credits",100,4,100,200,400));
        allBaseTerritories.add(new Territory(0,25,"Fight against raiders","src/main/resources/images/defenseTower1.png",0,5,true,1,"Defense Tower - 25 Credits",25,5,25,50,100));
        allBaseTerritories.add(new Territory(0,30,"Holds Soldiers and trains new ones","src/main/resources/images/militarybarracks.png",0,10,true,1,"Military Barracks - 50 Credits",50,6,50,100,150));
        allBaseTerritories.add(new Territory(0,50,"contribues to condinated attacks around the map with a far range.","src/main/resources/images/militarybase1.png",0,0,true,1,"Military Base - 100 Credits",100,7,150,300,600));
        allBaseTerritories.add(new Territory(150,5,"Provides massive amounts of credit and many workers","src/main/resources/images/city.png",20,0,false,1,"City - 100 Credits",100,8,100,150,300));
        allBaseTerritories.add(new Territory(100,0,"Generates good amounts of credits but starts with little amounts of soldiers","src/main/resources/images/livingquarters.png",15,0,false,1,"Living Quarters - 50 Credits",50,9,50,100,200));
    }
    @FXML
    public GridPane mainGridPane;
    @FXML
    public Button startButton;
    @FXML
    public Label attacksLeftLabel;
    @FXML
    public Label MainDisplayLabel;
    @FXML
    public Button turnDisplayButton;
    @FXML
    public Label currentTurnLabel;
    @FXML
    public Label creditsLabel;
    @FXML
    public CheckBox possibleOptionsCheckBox;
    @FXML
    public Rectangle blurRectangle;
    @FXML
    public GridPane secondaryGridPane;
    @FXML
    public Rectangle menuRectangle;
    @FXML
    public Label operationBoxLabel3;
    @FXML
    public Label operationBoxLabel2;
    @FXML
    public GridPane AttackGridPane;
    @FXML
    public Label operationBoxLabel1;
    @FXML
    public Label operationBoxHeaderLabel;
    @FXML
    public Button operationBoxCancelButton;
    @FXML
    public Button operationBoxAcceptButton;
    @FXML
    public AnchorPane operationBOX;
    @FXML
    public AnchorPane editTerritoryBOX; public Rectangle editTerritoryRectangle; public Button editTerritoryConfirmButton; public Button editTerritoryCancelButton; public Label editTerritoryActionLabel; public ListView editTerritoryConfigureListVIew; public ListView editTerritoryUpgradeListView; public Label editTerritoryHeaderLabel;
    //    @FXML
//    public ImageView tempImageView;
    Button[][] buttonArray = new Button[10][15];
    Button[][] attackButtonArray = new Button[3][3];
    Button[][] displayButtonArray = new Button[10][15];
    ImageView[][] imageViewArray = new ImageView[10][15];
    Territory[][] territoryArray = new Territory[10][15];
    private int numPlayers = 2;
    private int turn;
    private ArrayList<Player> allPlayers = new ArrayList<>();
    private boolean editingMode = false;
    private int attacksLeft = 5;
    private int editsLeft = 5;
    private int row;
    private int column;

    private boolean upgradingTerritory = false;

    private Territory territoryChangingTo;
    @FXML
    public void startSetup() {
        startButton.setVisible(false);
        startButton.setDisable(true);
        playerConfigUI(true,false);
    }

    public void playerConfigUI(boolean visible,boolean disable) {
        removeFromPlayersButton.setVisible(visible);
        removeFromPlayersButton.setDisable(disable);
        addToPlayersButton.setDisable(disable);
        addToPlayersButton.setVisible(visible);
        confirmPlayerCountButton.setVisible(visible);
        confirmPlayerCountButton.setDisable(disable);
        amountOfPlayersLabel.setDisable(disable);
        amountOfPlayersLabel.setVisible(visible);
        if (!visible) {
            removeFromPlayersButton.toBack();
            addToPlayersButton.toBack();
            confirmPlayerCountButton.toBack();
            amountOfPlayersLabel.toBack();
        }
        else {
            removeFromPlayersButton.toFront();
            addToPlayersButton.toFront();
            confirmPlayerCountButton.toFront();
            amountOfPlayersLabel.toFront();
        }
    }
    @FXML
    public void addToPlayers() {
        if (numPlayers<4) {
            numPlayers++;
            amountOfPlayersLabel.setText(numPlayers+" Players");
        }
    }


    public void removeFromPlayers() {
        if (numPlayers>2) {
            numPlayers--;
            amountOfPlayersLabel.setText(numPlayers+" Players");
        }
    }

    public void start() {
        mainGridPane.setVisible(true);
        playerConfigUI(false,true);
        for(int row =0;row<buttonArray.length;row++) {
            for(int column=0;column<buttonArray[0].length;column++) {
                territoryArray[row][column] = new Territory(row,column, new Player("-fx-background-color: LightBlue;"),5,0);
                buttonArray[row][column] = new Button("");
                buttonArray[row][column].setPrefHeight(100);
                buttonArray[row][column].setPrefWidth(100);
                displayButtonArray[row][column] = new Button("");
                displayButtonArray[row][column].setPrefHeight(100);
                displayButtonArray[row][column].setPrefWidth(100);
                imageViewArray[row][column] = new ImageView();
                imageViewArray[row][column].setFitHeight(60);
                imageViewArray[row][column].setFitWidth(60);
//                Font font = new Font(0);
//                buttonArray[row][column].setFont(font);
                buttonArray[row][column].setStyle("-fx-border-color: LightBlue; -fx-background-color: LightBlue; -fx-border-radius: 0px; -fx-background-radius: 0px; ");
                displayButtonArray[row][column].setStyle("-fx-border-color: transparent; -fx-background-color: transparent; -fx-border-radius: 0px; ");
                mainGridPane.add(buttonArray[row][column],column,row);
                secondaryGridPane.add(displayButtonArray[row][column],column,row);
            }
        }
        setUpPlayers();
        turn = (int) (Math.random()*numPlayers);
        playTurn();
        //printWorldBorders();
        EventHandler z = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                row = GridPane.getRowIndex((Button) event.getSource());
                column = GridPane.getColumnIndex((Button) event.getSource());
                SecondaryDisplayLabel.setText("");
                if (editingMode) {
                    if (validTerritory(row, column, true)) {
                        setUpEditUI(true,false);
                        generateConfigureOptions();
                        generateUpgradeOptions();
                    }
                    else {
                        SecondaryDisplayLabel.setText("You cant edit that territory!");
                    }
                }
                else {
                    if (validTerritory(row, column, false)) {
                        setUpAttackUI();
//                        territoryArray[row][column].setControllingPlayer(allPlayers.get(turn%numPlayers));
//                        allPlayers.get(turn%numPlayers).getAllTerritoriesControlled().add(territoryArray[row][column]);
//                        updateMap();
//                        generateAreasToAttack();
                    }
                    else {
                        SecondaryDisplayLabel.setText("you cant attack that!!!");
                    }
                }
            }
        };
        for(int row =0;row<buttonArray.length;row++) {
            for(int column=0;column<buttonArray[0].length;column++) {
                buttonArray[row][column].setOnAction(z);
            }
        }
    }

    public void generateConfigureOptions() {
        editTerritoryConfigureListVIew.getItems().clear();
        if (territoryArray[row][column].getTerritoryTypeID()==1 && territoryArray[row][column].getControllingPlayer().getColor().equals(allPlayers.get(turn%numPlayers).getColor())) {
            editTerritoryConfigureListVIew.getItems().add("THIS TERRITORY CANNOT BE CHANGED");
        }
        else {
            editTerritoryConfigureListVIew.getItems().add("Hospital - 100 Credits");
            editTerritoryConfigureListVIew.getItems().add("Power Plant - 50 Credits");
            editTerritoryConfigureListVIew.getItems().add("Airport - 100 Credits");
            editTerritoryConfigureListVIew.getItems().add("Defense Tower - 25 Credits");
            editTerritoryConfigureListVIew.getItems().add("Military Barracks - 50 Credits");
            editTerritoryConfigureListVIew.getItems().add("Military Base - 100 Credits");
            editTerritoryConfigureListVIew.getItems().add("City - 100 Credits");
            editTerritoryConfigureListVIew.getItems().add("Living Quarters - 50 Credits");
        }
        for(int i = 0;i<editTerritoryConfigureListVIew.getItems().size();i++) {
            if(editTerritoryConfigureListVIew.getItems().get(i).equals(territoryArray[row][column].getTerritoryType())) {
                editTerritoryConfigureListVIew.getItems().remove(i);
                i--;
            }
        }
    }

    public void generateUpgradeOptions() {
        editTerritoryUpgradeListView.getItems().clear();
        if(territoryArray[row][column].getTerritoryTypeID()==0) {
            editTerritoryUpgradeListView.getItems().add("THIS TERRITORY DOES NOT HAVE UPGRADE OPTIONS");
        }
        else {
            for (int i = territoryArray[row][column].getLevel();i<4;i++) {
                editTerritoryUpgradeListView.getItems().add(getNameWithoutCredits(territoryArray[row][column].getCreditCostsForUpgrade().get(i-1)+" Credits - "+territoryArray[row][column].getTerritoryType())+" Upgrade "+(i+1));
            }
        }
    }

    public void setUpEditUI(boolean visible, boolean disable) {
        boolean printOnLeft = getPrintSide();
        editTerritoryBOX.setVisible(visible);
        editTerritoryBOX.setDisable(disable);
        blurRectangle.setDisable(disable);
        blurRectangle.setVisible(visible);
        if (printOnLeft) {
            editTerritoryBOX.setLayoutX(25);
        }
        else {
            editTerritoryBOX.setLayoutX(25+750);
        }
        if (visible) {
            blurRectangle.toFront();
            editTerritoryBOX.toFront();
        }
        else {
            blurRectangle.toBack();
            editTerritoryBOX.toBack();
        }
    }

    private boolean firstTime = true;
    private Boolean[][] activatedButtons = new Boolean[3][3];
    public void setUpAttackUI() {
        openAttackPlan();
        if (firstTime) {
            firstTime = false;
            setUpAttackGridPane();
        }
        for(int row =0;row<3;row++) {
            for(int column=0;column<3;column++) {
                activatedButtons[row][column] = false;
                attackButtonArray[row][column].setDisable(false);
            }
        }
        displayAttackGridPane();
        tallyUpTotals();
    }

    public void displayAttackGridPane() {
        ArrayList<Boolean> directionsCanGo = findDirections(territoryArray[row][column]);
        if (directionsCanGo.get(0)) {
            attackButtonArray[0][1].setStyle(buttonArray[row-1][column].getStyle());
        }
        else {
            attackButtonArray[0][1].setStyle("-fx-border-color: Black; -fx-background-color: Black;");
            attackButtonArray[0][1].setDisable(true);
        }
        if (directionsCanGo.get(1)) {
            attackButtonArray[0][2].setStyle(buttonArray[row-1][column+1].getStyle());
        }
        else {
            attackButtonArray[0][2].setStyle("-fx-border-color: Black; -fx-background-color: Black;");
            attackButtonArray[0][2].setDisable(true);
        }
        if (directionsCanGo.get(2)) {
            attackButtonArray[1][2].setStyle(buttonArray[row][column+1].getStyle());
        }
        else {
            attackButtonArray[1][2].setStyle("-fx-border-color: Black; -fx-background-color: Black;");
            attackButtonArray[1][2].setDisable(true);
        }
        if (directionsCanGo.get(3)) {
            attackButtonArray[2][2].setStyle(buttonArray[row+1][column+1].getStyle());
        }
        else {
            attackButtonArray[2][2].setStyle("-fx-border-color: Black; -fx-background-color: Black;");
            attackButtonArray[2][2].setDisable(true);
        }
        if (directionsCanGo.get(4)) {
            attackButtonArray[2][1].setStyle(buttonArray[row+1][column].getStyle());
        }
        else {
            attackButtonArray[2][1].setStyle("-fx-border-color: Black; -fx-background-color: Black;");
            attackButtonArray[2][1].setDisable(true);
        }
        if (directionsCanGo.get(5)) {
            attackButtonArray[2][0].setStyle(buttonArray[row+1][column-1].getStyle());
        }
        else {
            attackButtonArray[2][0].setStyle("-fx-border-color: Black; -fx-background-color: Black;");
            attackButtonArray[2][0].setDisable(true);
        }
        if (directionsCanGo.get(6)) {
            attackButtonArray[1][0].setStyle(buttonArray[row][column-1].getStyle());
        }
        else {
            attackButtonArray[1][0].setStyle("-fx-border-color: Black; -fx-background-color: Black;");
            attackButtonArray[1][0].setDisable(true);
        }
        if (directionsCanGo.get(7)) {
            attackButtonArray[0][0].setStyle(buttonArray[row-1][column-1].getStyle());
        }
        else {
            attackButtonArray[0][0].setStyle("-fx-border-color: Black; -fx-background-color: Black;");
            attackButtonArray[0][0].setDisable(true);
        }
        attackButtonArray[1][1].setStyle("-fx--fx-border-color: Navy Blue; -fx-background-color: Navy Blue;");
    }

    public void setUpAttackGridPane() {
        for(int row =0;row<attackButtonArray.length;row++) {
            for(int column=0;column<attackButtonArray[0].length;column++) {
                attackButtonArray[row][column] = new Button("");
                attackButtonArray[row][column].setPrefHeight(200);
                attackButtonArray[row][column].setPrefWidth(200);
                attackButtonArray[row][column].setStyle("-fx-background-radius: 0px; -fx-border-color: LightBlue; -fx-background-color: LightBlue;");
                AttackGridPane.add(attackButtonArray[row][column],column,row);
            }
        }
        EventHandler x = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int row1 = GridPane.getRowIndex((Button) event.getSource());
                int column1 = GridPane.getColumnIndex((Button) event.getSource());
                Coordinates coords = findCoordinatesOnBoard(row1, column1);
                if (buttonClickedIsValid(row1,column1,coords)) {
                    if (activatedButtons[row1][column1]) {
                        attackButtonArray[row1][column1].setStyle(allPlayers.get(turn%numPlayers).getColor() +" -fx-border-color: Black; -fx-border-width: 0px;");
                        activatedButtons[row1][column1] = false;
                    }
                    else {
                        activatedButtons[row1][column1] = true;
                        attackButtonArray[row1][column1].setStyle(allPlayers.get(turn%numPlayers).getColor()+" -fx-border-color: #ff00ab; -fx-border-width: 5px;");
                    }
                    tallyUpTotals();
                }
            }
        };
        for(int row =0;row<attackButtonArray.length;row++) {
            for(int column=0;column<attackButtonArray[0].length;column++) {
                attackButtonArray[row][column].setOnAction(x);
            }
        }
    }
    private double winProbability = .50;
    public void tallyUpTotals() {
        double yourTotal = 0;
        int enemyTotal = territoryArray[row][column].getAmountOfSoldiers();
        for(int row =0;row<3;row++) {
            for(int column=0;column<3;column++) {
                if (activatedButtons[row][column]) {
                    Coordinates coords = findCoordinatesOnBoard(row,column);
                    yourTotal = yourTotal + territoryArray[coords.getRow()][coords.getColumn()].getAmountOfSoldiers();
                }
            }
        }
        winProbability = (yourTotal/(enemyTotal+1))/2;
        if (winProbability>.99 || enemyTotal==0) {
            winProbability = 1;
        }
        if (winProbability<0 || yourTotal==0) {
            winProbability = 0;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        operationBoxLabel1.setText("Your Soldiers: "+df.format(yourTotal));
        operationBoxLabel2.setText("Enemy Soldiers: "+enemyTotal);
        operationBoxLabel3.setText("Win Percentage: "+df.format(winProbability*100)+"%");
    }

    public boolean buttonClickedIsValid(int row2, int column2,Coordinates coords) {
        return territoryArray[coords.getRow()][coords.getColumn()].getControllingPlayer().getPlayerID() == allPlayers.get(turn%numPlayers).getPlayerID();
    }

    public Coordinates findCoordinatesOnBoard(int row3, int column3) {
        if (row3==0 && column3==0) {
            return new Coordinates(row-1,column-1);
        }
        if (row3==0 && column3==1) {
            return new Coordinates(row-1,column);
        }
        if (row3==0 && column3==2) {
            return new Coordinates(row-1,column+1);
        }
        if (row3==1 && column3==0) {
            return new Coordinates(row,column-1);
        }
        if (row3==1 && column3==1) {
            return new Coordinates(row,column);
        }
        if (row3==1 && column3==2) {
            return new Coordinates(row,column+1);
        }
        if (row3==2 && column3==0) {
            return new Coordinates(row+1,column-1);
        }
        if (row3==2 && column3==1) {
            return new Coordinates(row+1,column);
        }
        if (row3==2 && column3==2) {
            return new Coordinates(row+1,column+1);
        }
        return new Coordinates(0,0);
    }

    public void openAttackPlan() {
        setUpSecondary();
        boolean printOnLeft = getPrintSide();
        attackBoxUI(true,false,printOnLeft);
    }

    public boolean getPrintSide() {
        return column>6;
    }

    public void attackBoxUI(boolean visisble, boolean disable,boolean leftSide) {
        blurRectangle.setVisible(visisble);
        blurRectangle.setDisable(disable);
        operationBOX.setVisible(visisble);
        operationBOX.setDisable(disable);
        if (visisble) {
            blurRectangle.toFront();
            operationBOX.toFront();
        }
        else {
            blurRectangle.toBack();
            operationBOX.toBack();
            secondaryGridPane.toBack();
        }
        if (leftSide) {
            operationBOX.setLayoutX(25);
        }
        else {
            operationBOX.setLayoutX(775);
        }

    }

    public void setUpSecondary() {
        secondaryGridPane.toFront();
        for(int row =0;row<displayButtonArray.length;row++) {
            for(int column=0;column<displayButtonArray[0].length;column++) {
                displayButtonArray[row][column].setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
            }
        }
        displayButtonArray[row][column].setStyle(buttonArray[row][column].getStyle());
    }

    public boolean validTerritory(int rowClicked, int columnClicked, boolean editing) {
        if (editing) {
            return territoryArray[rowClicked][columnClicked].getControllingPlayer().getColor().equals(allPlayers.get(turn%numPlayers).getColor());
        }
        else {
            for (int i =0;i<allPlayers.get(turn%numPlayers).getAttackTerritories().size();i++) {
                if (allPlayers.get(turn%numPlayers).getAttackTerritories().get(i).getCoordOfTerritory().getRow()==rowClicked && allPlayers.get(turn%numPlayers).getAttackTerritories().get(i).getCoordOfTerritory().getColumn()==columnClicked) {
                    return true;
                }
            }
        }
        return false;
    }

    public void playTurn() {
        if (allPlayers.get(turn%numPlayers).getLost()) {
            turn++;
            playTurn();
        }
        else {
            updateMap();
            setUpAttackingMode();
            generateAreasToAttack();
            addCreditsToAllPlayers();
            SecondaryDisplayLabel.setText("");
        }
    }

    public void addCreditsToAllPlayers() {
        for (int i = 0;i<allPlayers.size();i++) {
            if (!allPlayers.get(i).getLost()) {
                int creditTotalForPlayer = 0;
                for (int row = 0; row < territoryArray.length; row++) {
                    for (int column = 0; column < territoryArray[0].length; column++) {
                        if (territoryArray[row][column].getControllingPlayer().getColor().equals(allPlayers.get(i).getColor())) {
                            creditTotalForPlayer += territoryArray[row][column].getCreditGain();
                        }
                    }
                }
                allPlayers.get(i).addToCredits(creditTotalForPlayer);
            }
        }
    }

    public void setUpEditingMode() {
        editsLeft = 5;
        MainDisplayLabel.setText("Editing Mode. Click on your territories to upgrade them.");
        currentTurnLabel.setText("Current Turn:");
        turnDisplayButton.setDisable(false);
        turnDisplayButton.setVisible(true);
        editingMode = true;
        turnDisplayButton.setStyle("-fx-background-radius: 0px;");
        attacksLeftLabel.setText("Edits Left: "+editsLeft);
        turnDisplayButton.setStyle(allPlayers.get(turn%numPlayers).getColor());
        skipTurnButton.setDisable(false);
        skipTurnButton.setVisible(true);
        possibleOptionsCheckBox.setVisible(false);
        possibleOptionsCheckBox.setDisable(true);
        possibleOptionsCheckBox.setSelected(false);
    }

    @FXML
    public void onClickEndEditingMode() {
        if (editingMode) {
            turn++;
            playTurn();
            skipTurnButton.setDisable(true);
            skipTurnButton.setVisible(false);
        }
    }
    public void generateAreasToAttack() {
        allPlayers.get(turn%numPlayers).getAttackTerritories().clear();
        for (int i = 0; i<allPlayers.get(turn%numPlayers).getAllTerritoriesControlled().size();i++) {
            ArrayList<Boolean> directionsCanGo = findDirections(allPlayers.get(turn%numPlayers).getAllTerritoriesControlled().get(i));
            convertDirectionsToCoordinates(directionsCanGo, allPlayers.get(turn%numPlayers).getAllTerritoriesControlled().get(i));
        }
        removeRedundantTerritories();
    }

    public void removeRedundantTerritories() {
        for (int i = 0; i<allPlayers.get(turn%numPlayers).getAttackTerritories().size();i++) {
            if(allPlayers.get(turn%numPlayers).getAttackTerritories().get(i).getControllingPlayer().getColor().equals(allPlayers.get(turn%numPlayers).getColor())) {
                allPlayers.get(turn%numPlayers).getAttackTerritories().remove(i);
                i--;
            }
        }
    }

    public void convertDirectionsToCoordinates(ArrayList<Boolean> directions, Territory territoryChecking) {
        if (directions.get(0)) {
            allPlayers.get(turn%numPlayers).getAttackTerritories().add(territoryArray[territoryChecking.getCoordOfTerritory().getRow()-1][territoryChecking.getCoordOfTerritory().getColumn()]);
        }
        if (directions.get(1)) {
            allPlayers.get(turn%numPlayers).getAttackTerritories().add(territoryArray[territoryChecking.getCoordOfTerritory().getRow()-1][territoryChecking.getCoordOfTerritory().getColumn()+1]);
        }
        if (directions.get(2)) {
            allPlayers.get(turn%numPlayers).getAttackTerritories().add(territoryArray[territoryChecking.getCoordOfTerritory().getRow()][territoryChecking.getCoordOfTerritory().getColumn()+1]);
        }
        if (directions.get(3)) {
            allPlayers.get(turn%numPlayers).getAttackTerritories().add(territoryArray[territoryChecking.getCoordOfTerritory().getRow()+1][territoryChecking.getCoordOfTerritory().getColumn()+1]);
        }
        if (directions.get(4)) {
            allPlayers.get(turn%numPlayers).getAttackTerritories().add(territoryArray[territoryChecking.getCoordOfTerritory().getRow()+1][territoryChecking.getCoordOfTerritory().getColumn()]);
        }
        if (directions.get(5)) {
            allPlayers.get(turn%numPlayers).getAttackTerritories().add(territoryArray[territoryChecking.getCoordOfTerritory().getRow()+1][territoryChecking.getCoordOfTerritory().getColumn()-1]);
        }
        if (directions.get(6)) {
            allPlayers.get(turn%numPlayers).getAttackTerritories().add(territoryArray[territoryChecking.getCoordOfTerritory().getRow()][territoryChecking.getCoordOfTerritory().getColumn()-1]);
        }
        if (directions.get(7)) {
            allPlayers.get(turn%numPlayers).getAttackTerritories().add(territoryArray[territoryChecking.getCoordOfTerritory().getRow()-1][territoryChecking.getCoordOfTerritory().getColumn()-1]);
        }
    }

    public ArrayList<Boolean> findDirections(Territory territoryChecking) {
        ArrayList<Boolean> tempList = new ArrayList<>();
        for (int i = 0 ;i<8;i++) {
            tempList.add(false);
        }
        if(territoryChecking.getCoordOfTerritory().getColumn()+1<=(buttonArray[0].length-1)) {
            tempList.set(2,true);
        }
        if(territoryChecking.getCoordOfTerritory().getColumn()>0) {
            tempList.set(6,true);
        }
        if(territoryChecking.getCoordOfTerritory().getRow()+1<=(buttonArray.length-1)) {
            tempList.set(4,true);
        }
        if(territoryChecking.getCoordOfTerritory().getRow()>0) {
            tempList.set(0,true);
        }
        if (tempList.get(0) && tempList.get(2)) {
            tempList.set(1,true);
        }
        if (tempList.get(2) && tempList.get(4)) {
            tempList.set(3,true);
        }
        if (tempList.get(4) && tempList.get(6)) {
            tempList.set(5,true);
        }
        if (tempList.get(0) && tempList.get(6)) {
            tempList.set(7,true);
        }
        return tempList;
    }

    public void setUpAttackingMode() {
        editingMode = false;
        attacksLeft = 5;
        MainDisplayLabel.setText("Attack Mode. Click On The Surrounding Territories To Invade.");
        currentTurnLabel.setText("Current Turn:");
        turnDisplayButton.setDisable(false);
        turnDisplayButton.setVisible(true);
        turnDisplayButton.setStyle("-fx-background-radius: 0px;");
        turnDisplayButton.setStyle(allPlayers.get(turn%numPlayers).getColor());
        attacksLeftLabel.setText("Attacks Left: "+attacksLeft);
        skipTurnButton.setDisable(true);
        skipTurnButton.setVisible(false);
        possibleOptionsCheckBox.setVisible(true);
        possibleOptionsCheckBox.setDisable(false);
    }


    public void setUpPlayers() {
        for (int i =0;i<numPlayers;i++) {
            if (i==0) {
                allPlayers.add(new Player("temp",1, new Territory(0,0,10,1), "-fx-background-color: Blue;", (int) (Math.random()*10000)));
                territoryArray[0][0].setControllingPlayer(allPlayers.get(allPlayers.size()-1));
                territoryArray[0][0].setTerritoryTypeID(1);
                territoryArray[0][0].setCreditGain(100);
                territoryArray[0][0].setAmountOfSoldiers(10);
            }
            else if (i==1) {
                allPlayers.add(new Player("temp",1, new Territory(9,14,10,1), "-fx-background-color: Red;", (int) (Math.random()*10000)));
                territoryArray[9][14].setControllingPlayer(allPlayers.get(allPlayers.size()-1));
                territoryArray[9][14].setTerritoryTypeID(1);
                territoryArray[9][14].setCreditGain(100);
                territoryArray[9][14].setAmountOfSoldiers(10);
            }
            else if (i==2) {
                allPlayers.add(new Player("temp",1, new Territory(9,0,10,1), "-fx-background-color: Yellow;", (int) (Math.random()*10000)));
                territoryArray[9][0].setControllingPlayer(allPlayers.get(allPlayers.size()-1));
                territoryArray[9][0].setTerritoryTypeID(1);
                territoryArray[9][0].setCreditGain(100);
                territoryArray[9][0].setAmountOfSoldiers(10);
            }
            else {
                allPlayers.add(new Player("temp",1, new Territory(0,14,10,1), "-fx-background-color: Green;", (int) (Math.random()*10000)));
                territoryArray[0][14].setControllingPlayer(allPlayers.get(allPlayers.size()-1));
                territoryArray[0][14].setTerritoryTypeID(1);
                territoryArray[0][14].setCreditGain(100);
                territoryArray[0][14].setAmountOfSoldiers(10);
            }
        }
        updateMap();
    }

    public void updateMap() {
        setUpImageViews();
        updateCredits();
        for(int row =0;row<territoryArray.length;row++) {
            for(int column=0;column<territoryArray[0].length;column++) {
                buttonArray[row][column].setStyle(territoryArray[row][column].getControllingPlayer().getColor());
                buttonArray[row][column].setGraphic(imageViewArray[row][column]);
            }
        }
    }

    public void updateCredits() {
        creditsLabel.setText("Credits: "+allPlayers.get(turn%numPlayers).getCredits());
    }

    public void setUpImageViews() {
        for(int row =0;row<imageViewArray.length;row++) {
            for(int column=0;column<imageViewArray[0].length;column++) {
                try {
                    if (territoryArray[row][column].getTerritoryTypeID()==0) {
                        //L Bozo
                    }
                    else if (territoryArray[row][column].getTerritoryTypeID()==1) {
                        FileInputStream input = new FileInputStream("src/main/resources/images/homeBase.png");
                        imageViewArray[row][column].setImage(new Image(input));
                        buttonArray[row][column].setGraphic(imageViewArray[row][column]);
                    }
                    else {
                        FileInputStream input = new FileInputStream(territoryArray[row][column].getImagePath());
                        imageViewArray[row][column].setImage(new Image(input));
                        buttonArray[row][column].setGraphic(imageViewArray[row][column]);
                    }
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void onClickShowAttackOptions() {
        if (!editingMode) {
            if(possibleOptionsCheckBox.isSelected()) {
                for (int i =0;i<allPlayers.get(turn%numPlayers).getAttackTerritories().size();i++) {
                    buttonArray[allPlayers.get(turn%numPlayers).getAttackTerritories().get(i).getCoordOfTerritory().getRow()][allPlayers.get(turn%numPlayers).getAttackTerritories().get(i).getCoordOfTerritory().getColumn()].setStyle("-fx-border-color: Cyan; -fx-background-color: Cyan;");
                }
            }
            else {
                for (int i =0;i<allPlayers.get(turn%numPlayers).getAttackTerritories().size();i++) {
                    buttonArray[allPlayers.get(turn%numPlayers).getAttackTerritories().get(i).getCoordOfTerritory().getRow()][allPlayers.get(turn%numPlayers).getAttackTerritories().get(i).getCoordOfTerritory().getColumn()].setStyle("-fx-border-color: LightBlue; -fx-background-color: LightBlue;");
                }
            }
        }
    }

    @FXML
    public void cancelInvasion() {
        attackBoxUI(false,true,true);
    }

    @FXML
    public void attemptInvasion() {
        double randInt = Math.random();
        if (randInt<winProbability) {
            territoryArray[row][column].setControllingPlayer(allPlayers.get(turn%numPlayers));
            allPlayers.get(turn%numPlayers).getAllTerritoriesControlled().add(territoryArray[row][column]);
            boolean someoneWon = checkIfLoses();
            if (!someoneWon) {
                updateMap();
                generateAreasToAttack();
                attackBoxUI(false,true,true);
                SecondaryDisplayLabel.setText("You have invaded the territory.");
            }
        }
        else {
            SecondaryDisplayLabel.setText("The invasion failed.");
            attackBoxUI(false,true,true);
        }
        attacksLeft--;
        if(attacksLeft==0) {
            setUpEditingMode();
        }
        else {
            attacksLeftLabel.setText("Attacks Left: "+attacksLeft);
        }
    }

    public boolean checkIfLoses() {
        boolean won = false;
        for (int i = 0;i<allPlayers.size();i++) {
            boolean playerLost = true;
            for(int row =0;row<territoryArray.length;row++) {
                for(int column=0;column<territoryArray[0].length;column++) {
                    if (territoryArray[row][column].getControllingPlayer().getColor().equals(allPlayers.get(i).getColor())) {
                        if (territoryArray[row][column].getTerritoryTypeID()==1) {
                            playerLost = false;
                        }
                    }
                }
            }
            if (playerLost) {
                allPlayers.get(i).playerLost();
                for(int row =0;row<territoryArray.length;row++) {
                    for(int column=0;column<territoryArray[0].length;column++) {
                        if (territoryArray[row][column].getControllingPlayer().getColor().equals(allPlayers.get(i).getColor())) {
                            territoryArray[row][column].setControllingPlayer(new Player("-fx-background-color: LightBlue;"));
                        }
                    }
                }
            }
        }
        int numPlayersLeft = 0;
        for (int i =0;i<allPlayers.size();i++) {
            if (!allPlayers.get(i).getLost()) {
                numPlayersLeft++;
            }
        }
        if (numPlayersLeft==1) {
            for (int i = 0;i<allPlayers.size();i++) {
                if (!allPlayers.get(i).getLost()) {
                    blurRectangle.setVisible(true);
                    blurRectangle.setDisable(false);
                    blurRectangle.toFront();
                    won = true;
                    if (i==0) {
                        MainDisplayLabel.setText("Blue Wins!!!");
                    }
                    if (i==1) {
                        MainDisplayLabel.setText("Red Wins!!!");
                    }
                    if (i==2) {
                        MainDisplayLabel.setText("Yellow Wins!!!");
                    }
                    if (i==3) {
                        MainDisplayLabel.setText("Green Wins!!!");
                    }
                }
            }
        }
        return won;
    }

    @FXML
    public void upgradeTerritory() {
        if (territoryChangingTo!=null) {
            if (!upgradingTerritory) {
                if (territoryChangingTo.getTerritoryCost()<=allPlayers.get(turn%numPlayers).getCredits()) {
                    territoryArray[row][column] = territoryChangingTo;
                    territoryArray[row][column].setControllingPlayer(allPlayers.get(turn%numPlayers));
                    territoryArray[row][column].setCoordOfTerritory(new Coordinates(row,column));
                    allPlayers.get(turn%numPlayers).removeFromCredits(territoryChangingTo.getTerritoryCost());
                    updateMap();
                    setUpEditUI(false,true);
                    editsLeft--;
                    attacksLeftLabel.setText("Edits Left: "+editsLeft);
                }
                else {
                    SecondaryDisplayLabel.setText("You Don't Have Enough Credits For That!!!");
                }
            }
            else {
                if (territoryArray[row][column].getLevel()+1==Integer.parseInt(editTerritoryUpgradeListView.getSelectionModel().getSelectedItem().toString().substring(editTerritoryUpgradeListView.getSelectionModel().getSelectedItem().toString().length()-1))) {
                    if (territoryArray[row][column].getCreditCostsForUpgrade().get(territoryArray[row][column].getLevel()-1)<=allPlayers.get(turn%numPlayers).getCredits()) {
                        allPlayers.get(turn%numPlayers).removeFromCredits(territoryArray[row][column].getCreditCostsForUpgrade().get(territoryArray[row][column].getLevel()-1));
                        territoryArray[row][column].upgradeLevel();
                        updateMap();
                        setUpEditUI(false,true);
                        editsLeft--;
                        attacksLeftLabel.setText("Edits Left: "+editsLeft);
                    }
                    else {
                        SecondaryDisplayLabel.setText("You Don't Have Enough Credits For That!!!");
                    }
                }
                else {
                    SecondaryDisplayLabel.setText("You cant upgrade more than 1 level at a time!");
                }
            }
            if (editsLeft==0) {
                turn++;
                editsLeft = 5;
                playTurn();
            }
        }
    }

    @FXML
    public void cancelUpgrade() {
        setUpEditUI(false,true);
    }

    @FXML
    public void selectTerritoryConfigure() {
        if (editTerritoryConfigureListVIew.getSelectionModel().getSelectedItem()!=null) {
            editTerritoryActionLabel.setText("Action: Change Into "+getNameWithoutCredits((String) editTerritoryConfigureListVIew.getSelectionModel().getSelectedItem()));
            territoryChangingTo = returnTerritoryThatWasClicked();
            editTerritoryConfirmButton.setText("CHANGE TERRITORY");
            upgradingTerritory = false;
        }
    }

    public Territory returnTerritoryThatWasClicked() {
        for (int i = 0; i<allBaseTerritories.size();i++) {
            if(allBaseTerritories.get(i).getTerritoryType().equals(editTerritoryConfigureListVIew.getSelectionModel().getSelectedItem())) {
                return new Territory(allBaseTerritories.get(i));
            }
        }
        return new Territory(row,column,5,0);
    }

    public String getNameWithoutCredits(String str) {
        String tempStr = "";
        for (int i =0;i<str.length();i++) {
            if (str.charAt(i) == '-') {
                break;
            }
            else {
                tempStr = tempStr + str.charAt(i);
            }
        }
        tempStr = tempStr.substring(0,tempStr.length()-1);
        return tempStr;
    }

    @FXML
    public void selectTerritoryUpgrade() {
        if (editTerritoryUpgradeListView.getSelectionModel().getSelectedItem()!=null) {
            editTerritoryActionLabel.setText("Action: Upgrade Into Level "+editTerritoryUpgradeListView.getSelectionModel().getSelectedItem().toString().substring(editTerritoryUpgradeListView.getSelectionModel().getSelectedItem().toString().length()-1));
            upgradingTerritory = true;
            editTerritoryConfirmButton.setText("UPGRADE TERRITORY");
        }
    }
}