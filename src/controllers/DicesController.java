/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Project
  Created date: 01/01/2020
  By: Group 10 (3426353,3791159,3742774,3748575,3695662)
  Last modified: 14/01/2020
  By: Group 10 (3426353,3791159,3742774,3748575,3695662)
  Acknowledgement: none.
*/

package controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import models.Dice;
import models.Horse;
import models.Move;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DicesController {
    // fields
    private MainController mainController;
    private GameBoardController gameBoardController;
    private Dice dice1, dice2;
    private Button diceArrow1;
    private Button diceArrow2;
    private Animation diceArrow1Animation;
    private Animation diceArrow2Animation;
    private static List<Image> images = new ArrayList<>(); //array of animation for rolling dices

    @FXML private HBox dices; //HBox to store 2 dices

    //method to load images into array images
    static{
        for (int i = 1; i < 7; i++){
            Image rollAnimationImage = new Image("resources/images/" + i +"a.png");
            images.add(rollAnimationImage);
        }
    }

    // constructor
    public DicesController(){
    }

    //populate HBox with 2 dices
    @FXML public void initialize() {
        dice1 = new Dice();
        dice2 = new Dice();
        createDiceArrow1();
        createDiceArrow2();
        setEventHandlerForDiceRoll();
        dices.getChildren().addAll(diceArrow1,dice1, dice2,diceArrow2);
    }

    // inject main controller into this
    public void injectMainController(MainController mainController){
        this.mainController = mainController;
    }

    // inject game board controller into this
    public void injectGameBoardController(GameBoardController gameBoardController){this.gameBoardController = gameBoardController;}

    // event handler for dice 1 pick
    public void setEventHandlerForDice1Pick(GameBoardController gameBoardController, Horse horse){
        eventHandlerForDicePick(gameBoardController, horse, 0);
    }

    // event handler for dice 2 pick
    public void setEventHandlerForDice2Pick(GameBoardController gameBoardController, Horse horse){
        eventHandlerForDicePick(gameBoardController, horse, 1);
    }

    // event handler for dice pick
    public void eventHandlerForDicePick(GameBoardController gameBoardController, Horse horse, int dicePickIndex){
        if (horse.getPossibleStepsListByIndex(dicePickIndex) == 0) return;
        Dice dicePick, otherDice;
        if (dicePickIndex == 0) {
            showSideArrow1();
            dicePick = dice1;
            otherDice = dice2;
        } else {
            showSideArrow2();
            dicePick = dice2;
            otherDice = dice1;
        }

        String endPosition;
        if (horse.isInHome() || horse.isInHomeDoorPosition())
            endPosition = gameBoardController.calculateNextHomePosition(horse.getPossibleStepsListByIndex(dicePickIndex), horse);
        else
            endPosition = gameBoardController.calculateNextPosition(horse.getPossibleStepsListByIndex(dicePickIndex), horse.getTempPosition(), null);
        StackPane endPositionNodeSP = (StackPane) gameBoardController.getGameBoard().lookup("#" + endPosition);

        //When hovering, highlight end position
        dicePick.setOnMouseEntered(event -> onMouseEnteredDiceEventHandler(endPosition, endPositionNodeSP, horse));

        //When not hovering, unhighlight end position
        dicePick.setOnMouseExited(event -> onMouseExitedDiceEventHandler(endPositionNodeSP, horse));

        //When click, execute animation, check possible moves for all other horse again
        dicePick.setOnMouseClicked(event -> {
            try {
                onMouseClickedDiceEventHandler(endPosition, horse, dicePick, otherDice);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // event handler for on mouse entered
    private void onMouseEnteredDiceEventHandler(String endPosition, StackPane endPositionNodeSP , Horse horse){
        if (!horse.isInHome() && !horse.isInHomeDoorPosition() &&
                GameBoardController.getHorseIdOfPositionByIndex(gameBoardController.convertPositionToIntegerForm(endPosition)) != null)
            endPositionNodeSP.setStyle("-fx-background-color: red");
        endPositionNodeSP.getChildren().get(0).setStyle("-fx-fill: yellow");
    }

    // event handler for on mouse exited dice
    private void onMouseExitedDiceEventHandler(StackPane endPositionNodeSP , Horse horse){
        gameBoardController.resetFillColorOfPosition(endPositionNodeSP, horse);
    }

    // event handler for mouse clicked dice
    private void onMouseClickedDiceEventHandler(String endPosition, Horse horse, Dice dicePick, Dice otherDice) throws IOException {
        dicePick.setUsable(false); //dice 1 can no longer be chosen for a horse move
        unsetEventHandlerForDices();
        gameBoardController.unhighlightHorsesInsideNest();
        if (horse.isInHome()) {
            gameBoardController.unhighlightHorseOutsideNest();
            if (gameBoardController.isOnlineGame()) gameBoardController.createHorseMovingInsideHomeAnimation(horse.getTempPosition(), endPosition ,horse, dicePick.getRollNumber(), true,gameBoardController.getTempPlayerIdTurn());
            else gameBoardController.createHorseMovingInsideHomeAnimation(horse.getTempPosition(), endPosition ,horse, dicePick.getRollNumber(), false,gameBoardController.getTempPlayerIdTurn());
            gameBoardController.setHorseGoingOutsideNest(false);
        }
        else if (horse.isInHomeDoorPosition()) {
            gameBoardController.unhighlightHorseOutsideNest();
            if (gameBoardController.isOnlineGame()) gameBoardController.createHorseMovingInsideHomeAnimation(horse.getTempPosition(), endPosition ,horse, dicePick.getRollNumber(), true,gameBoardController.getTempPlayerIdTurn());
            else gameBoardController.createHorseMovingInsideHomeAnimation(horse.getTempPosition(), endPosition ,horse, dicePick.getRollNumber(), false,gameBoardController.getTempPlayerIdTurn());
            gameBoardController.setHorseGoingOutsideNest(false);
        }
        else {
            if (gameBoardController.isOnlineGame()) gameBoardController.createHorseMovingAnimation(horse.getTempPosition(), horse.getTempPosition(), endPosition, horse, dicePick.getRollNumber(), true, gameBoardController.getTempPlayerIdTurn());
            else gameBoardController.createHorseMovingAnimation(horse.getTempPosition(), horse.getTempPosition(), endPosition, horse, dicePick.getRollNumber(), false, gameBoardController.getTempPlayerIdTurn());
            gameBoardController.setHorseGoingOutsideNest(false);
        }
    }

    // unset event handler for dices
    public void unsetEventHandlerForDices(){
        dice1.setOnMouseClicked(null);
        dice2.setOnMouseClicked(null);
        dice1.setOnMouseEntered(null);
        dice2.setOnMouseEntered(null);
        dice1.setOnMouseExited(null);
        dice2.setOnMouseExited(null);
        hideSideArrow1();       //hide dice 1 side arrow animation
        hideSideArrow2();       //hide dice 2 side arrow animation
    }

    // show side arrow 1
    public void showSideArrow1(){
        diceArrow1.setVisible(true);
        diceArrow1Animation.play();
    }

    // hide side arrow 1
    public void hideSideArrow1(){
        diceArrow1Animation.stop();
        diceArrow1.setVisible(false);
    }

    // show side arrow 2
    public void showSideArrow2(){
        diceArrow2.setVisible(true);
        diceArrow2Animation.play();
    }

    // hide side arrow 2
    public void hideSideArrow2(){
        diceArrow2Animation.stop();
        diceArrow2.setVisible(false);
    }

    // create dice arrow 1
    private void createDiceArrow1(){
        diceArrow1 = new Button();
        diceArrow1.getStyleClass().add("leftArrow");
        diceArrow1.prefWidth(41);
        diceArrow1.prefHeight(25);
        diceArrow1.setVisible(false);
        diceArrow1Animation = new Timeline(new KeyFrame(Duration.millis(50), e -> moveLeftSideArrow()));
        diceArrow1Animation.setCycleCount(Timeline.INDEFINITE);
    }

    // create dice arrow 2
    private void createDiceArrow2(){
        diceArrow2 = new Button();
        diceArrow2.getStyleClass().add("rightArrow");
        diceArrow2.prefWidth(41);
        diceArrow2.prefHeight(25);
        diceArrow2.setVisible(false);
        diceArrow2Animation = new Timeline(new KeyFrame(Duration.millis(50), e -> moveRightSideArrow()));
        diceArrow2Animation.setCycleCount(Timeline.INDEFINITE);
    }

    // move left side arrow
    private void moveLeftSideArrow(){
        if (diceArrow1.getTranslateX() == 0) diceArrow1.setTranslateX(10);
        else diceArrow1.setTranslateX(0);
    }

    // move right side arrow
    private void moveRightSideArrow(){
        if (diceArrow2.getTranslateX() == 0) diceArrow2.setTranslateX(-10);
        else diceArrow2.setTranslateX(0);
    }

    //create rolling effect
    public void rollWithAnimation(Dice dice) {
        if (gameBoardController.isFreeze() || !gameBoardController.isRollingDiceTurn()) return;
        if (dice == dice2) gameBoardController.setFreeze(true);
        SoundController.playDiceRollSound();
        //If the dice rolling is temporarily allowed
            ImageView imageView = new ImageView();
            Transition rollAnimation = new Transition() {
                {
                    setCycleDuration(Duration.millis(500));
                } //duration of the animation

                @Override
                protected void interpolate(double v) {  //the method of creating the animation with v increasing
                    int index = (int) (v * (images.size() - 1));
                    imageView.setFitWidth(80);
                    imageView.setPreserveRatio(true);
                    imageView.setImage(images.get(index));
                    dice.setGraphic(imageView);
                }
            };
            rollAnimation.setOnFinished(event -> { //after finishing the rolling animation, actual roll
                SoundController.stopDiceRollSound();
                dice.roll();
                int i = dice.getRollNumber();
                dice.setRollImage(i);
                gameBoardController.setFreeze(false);
                if (dice == dice2) {
                    //If this is an online game, send all dices value to other players
                    if (gameBoardController.isOnlineGame()){
                        Move move = new Move(Move.type.DICES_VALUE, dice1.getRollNumber(), dice2.getRollNumber());
                        try {
                            gameBoardController.sendMessageToServer(move);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    gameBoardController.updateDiceNumView();
                    try {
                        gameBoardController.processPostDiceRolling();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            rollAnimation.play();
        }

    // set dices from move message
    public void setDicesFromMoveMessage(Move move){
        Platform.runLater(() -> {
            dice1.setRollImage(move.getDice1());
            dice1.setRollNumber(move.getDice1());
            dice2.setRollImage(move.getDice2());
            dice2.setRollNumber(move.getDice2());
            gameBoardController.updateDiceNumView();
        });
    }

    //add event handler for each dice, clicking one dice will result in 2 dices being rolled
    public void setEventHandlerForDiceRoll(){
        dice1.setOnMouseClicked(event -> {
            rollWithAnimation(dice1);
            rollWithAnimation(dice2);
        });
        dice2.setOnMouseClicked(event -> {
            rollWithAnimation(dice1);
            rollWithAnimation(dice2);
        });
    }

    public Dice getDice1() {
        return dice1;
    }

    public Dice getDice2() {
        return dice2;
    }
}
