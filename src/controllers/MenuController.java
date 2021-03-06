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

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import models.MatchInformation;
import models.Player;

import java.io.IOException;
import java.util.ArrayList;

public class MenuController{
    // fields
    private static MenuController menuController;
    private ClientController clientController;
    private MainController mainController;  //Make connection with mainController
    private int noHumanPlayers;             //Number of human players
    private int noVirtualPlayers;            //Number of virtual players
    private ArrayList<String> playersNameList;
    private static final String UNCHOSEN_COLOR = "#48da40";
    private static final String CHOSEN_COLOR = "#c93b14";
    private static final int MAX_NO_PLAYERS = 4;

    // FXML
    @FXML private Button onlineGameBtn;
    @FXML private Button offlineGameBtn;
    @FXML private VBox onlinePlayMenu;
    @FXML private TextField onlinePlayerTextField;
    @FXML private Button onlinePlayBtn;
    @FXML private VBox userSetNameMenu;
    @FXML private VBox emptyPlayerNameError;
    @FXML private VBox startMenuError;
    @FXML private VBox preGameMenu;
    @FXML private VBox startMenu;
    @FXML private VBox langMenu;
    @FXML private VBox endGameMenu;
    @FXML private VBox stopGameMenu;
    @FXML private VBox playerDisconnectedMenu;
    @FXML private StackPane rootMenu;
    @FXML private Button exitErrorBtn1;
    @FXML private Button backLevelBtn;
    @FXML private Button nextPlaySceneBtn;
    @FXML private Button exitErrorBtn;
    @FXML private VBox cantConnectToServerError;
    @FXML private Button cantConnectToServerBtn;
    @FXML private Button backBtn;
    @FXML private Button nextBtn;
    @FXML private Button exitGameBtn;
    @FXML private Button changeLanguageBtn;
    @FXML private Button vietnameseLangBtn;
    @FXML private Button englishLangBtn;
    @FXML private Button langBackBtn;
    @FXML private Button EGPlayAgainBtn;
    @FXML private Button EGNewGameBtn;
    @FXML private Button EGQuitBtn;
    @FXML private Button SGPlayAgainBtn;
    @FXML private Button SGNewGameBtn;
    @FXML private Button SGQuitBtn;
    @FXML private Button backToMainOnlinePlayBtn;
    @FXML private Button playerDisconnectedBtn;
    @FXML private Label noHumanPlayersLabel;
    @FXML private Label noVirtualPlayersLabel;
    @FXML private Label noPlayersErrLabel;
    @FXML private Label emptyPlayerNameLabel;
    @FXML private Label langSelectLabel;
    @FXML private Label player1NameLabel;
    @FXML private Label player2NameLabel;
    @FXML private Label player3NameLabel;
    @FXML private Label player4NameLabel;
    @FXML private Label EGTitleLabel;
    @FXML private Label SGTitleLabel;
    @FXML private Label EGPlayersTitleLabel;
    @FXML private Label EGPointsTitleLabel;
    @FXML private Label EGFirstFinishLabel;
    @FXML private Label EGMostPointsLabel;
    @FXML private Label EGPN0, EGPN1, EGPN2, EGPN3;
    @FXML private Label EGP1P0, EGP1P1, EGP1P2, EGP1P3;
    @FXML private Label finishPN;
    @FXML private Label highestPointsPN;
    @FXML private Label onlinePlayerNameLabel;
    @FXML private Label cantConnectToServerLabel;
    @FXML private Label playerDisconnectedLabel;

    private static final String red = "-fx-background-color: " +
            "linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%)," +
            "#9d4024,#d86e3a," +
            "radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);";
    private static final String blue = "-fx-background-color: " +
            "linear-gradient(from 0% 93% to 0% 100%, #380da3 0%, #2f2b90 100%)," +
            "#0b169d, #5b50ff," +
            "radial-gradient(center 50% 50%, radius 100%, #5b50ff, #3b4ec5);";

    // constructor
    public MenuController(){
        playersNameList = new ArrayList<>() {
            {
                for (int i = 0; i < 4; i++) add(" ");
            }
        };
        noHumanPlayers  = 2;    //there is 1 human player by default
        noVirtualPlayers = 0;    //there is no virtual player by default
    }

    // inject main controller into this
    public void injectMainController(MainController mainController){
        this.mainController = mainController;
    }

    // FXML initialize
    @FXML public void initialize(){
        setNoPlayersChoiceEventHandler();
        setMenuButtonsEventHandler();
        setBtnBindingText();
        setLabelBindingText();
    }

    // set button binding text
    private void setBtnBindingText(){
        I18NController.setUpButtonText(offlineGameBtn, "menu.play_offline");
        I18NController.setUpButtonText(changeLanguageBtn, "menu.change_language");
        I18NController.setUpButtonText(exitGameBtn, "menu.exit");
        I18NController.setUpButtonText(backBtn, "menu.back");
        I18NController.setUpButtonText(nextBtn, "menu.next");
        I18NController.setUpButtonText(exitErrorBtn, "menu.error_exit_btn");
        I18NController.setUpButtonText(exitErrorBtn1, "menu.error_exit_btn");
        I18NController.setUpButtonText(langBackBtn, "menu.back");
        I18NController.setUpButtonText(backLevelBtn, "menu.back");
        I18NController.setUpButtonText(nextPlaySceneBtn, "menu.play");
        I18NController.setUpButtonText(EGPlayAgainBtn, "menu.end_game_play_again");
        I18NController.setUpButtonText(EGNewGameBtn, "menu.new_game");
        I18NController.setUpButtonText(EGQuitBtn, "menu.exit");
        I18NController.setUpButtonText(SGPlayAgainBtn, "menu.end_game_play_again");
        I18NController.setUpButtonText(SGNewGameBtn, "menu.new_game");
        I18NController.setUpButtonText(SGQuitBtn, "menu.exit");
        I18NController.setUpButtonText(onlineGameBtn,"menu.play_online");
        I18NController.setUpButtonText(cantConnectToServerBtn,"menu.server_error_btn");
        I18NController.setUpButtonText(onlinePlayBtn,"menu.online_player_play_btn");
        I18NController.setUpButtonText(backToMainOnlinePlayBtn,"menu.online_player_back_btn");
        I18NController.setUpButtonText(playerDisconnectedBtn, "menu.error_exit_btn");
    }

    // set label binding text
    private void setLabelBindingText(){
        I18NController.setUpLabelText(noHumanPlayersLabel, "menu.no_human_players");
        I18NController.setUpLabelText(noVirtualPlayersLabel, "menu.no_virtual_players");
        I18NController.setUpLabelText(noPlayersErrLabel, "menu.noPlayers_error");
        I18NController.setUpLabelText(emptyPlayerNameLabel, "menu.empty_player_name");
        I18NController.setUpLabelText(langSelectLabel, "menu.lang_title");
        I18NController.setUpLabelText(player1NameLabel, "menu.player_1_name");
        I18NController.setUpLabelText(player2NameLabel, "menu.player_2_name");
        I18NController.setUpLabelText(player3NameLabel, "menu.player_3_name");
        I18NController.setUpLabelText(player4NameLabel, "menu.player_4_name");
        I18NController.setUpLabelText(EGTitleLabel, "menu.end_game_title");
        I18NController.setUpLabelText(SGTitleLabel, "menu.stop_game_title");
        I18NController.setUpLabelText(EGPlayersTitleLabel, "menu.end_game_players_title");
        I18NController.setUpLabelText(EGPointsTitleLabel, "menu.end_game_points_title");
        I18NController.setUpLabelText(EGFirstFinishLabel, "menu.first_player_finish");
        I18NController.setUpLabelText(EGMostPointsLabel, "menu.player_has_highest_score");
        I18NController.setUpLabelText(onlinePlayerNameLabel,"menu.online_player_name");
        I18NController.setUpLabelText(cantConnectToServerLabel,"menu.server_error_label");
        I18NController.setUpLabelText(playerDisconnectedLabel, "menu.player_disconnected");
    }

    //Set event handler for
    private void setNoPlayersChoiceEventHandler(){
        for (int i = 0; i <  5; i ++){
            StackPane noHumanPlayersChoice = (StackPane)rootMenu.lookup("#HP" + i);
            StackPane noVirtualPlayersChoice = (StackPane)rootMenu.lookup("#VP" + i);
            noHumanPlayersChoice.setOnMouseClicked(stkPaneOnMouseClickedEventHandler);
            noVirtualPlayersChoice.setOnMouseClicked(stkPaneOnMouseClickedEventHandler);
        }
    }

    //Set event handler for all menu buttons
    private void setMenuButtonsEventHandler(){
        setOfflineGameBtnEventHandler();
        setExitGameBtnEventHandler();
        setBackBtnEventHandler();
        setNextBtnEventHandler();
        setExitErrorBtnEventHandler();
        setOnlineGameBtnEventHandler();
        setOnlinePlayBtnEventHandler();
        setBackToMainMenuBtnEventHandler();
        setBackLevelBtnEventHandler();
        setNextPlaySceneBtnEventHandler();
        setChangeLanguageBtnEventHandler();
        setEnglishLangBtnEventHandler();
        setVietnameseLangBtnEventHandler();
        setLangBackBtnEventHandler();
        setEGPlayAgainBtnEventHandler();
        setEGNewGameBtn();
        setEGQuitBtn();
        setSGPlayAgainBtnEventHandler();
        setSGNewGameBtn();
        setSGQuitBtn();
        setPlayerDisconnectedBtn();
    }

    //Event handler when clicking on the circles to choose the number of human and machine players
    private EventHandler<MouseEvent> stkPaneOnMouseClickedEventHandler = event -> {
        SoundController.playButtonClickSound();
        StackPane stackPane = (StackPane)event.getSource();  //Get the stack pane object being clicked
        //If the stack pane being clicked is a choice of a number of human players
        if (stackPane.getId().charAt(0) == 'H'){
            changeNoPlayerChoiceCirColor((StackPane)rootMenu.lookup("#HP" + noHumanPlayers), false); //paint the circle of this choice to unchosen color
            noHumanPlayers = Integer.parseInt(stackPane.getId().substring(2));  //update noHumanPlayers variable
            changeNoPlayerChoiceCirColor((StackPane)rootMenu.lookup("#HP" + noHumanPlayers), true); //paint the circle of this choice to chosen color
            if ((noHumanPlayers + noVirtualPlayers) > MAX_NO_PLAYERS) {    //If the total of players exceeded 4
                changeNoPlayerChoiceCirColor( (StackPane)rootMenu.lookup("#VP" + noVirtualPlayers) , false);   //paint the circle of this choice to unchosen color
                changeNoPlayerChoiceCirColor( (StackPane)rootMenu.lookup("#VP" + (MAX_NO_PLAYERS - noHumanPlayers)), true );  //paint the circle of this choice to chosen color
                noVirtualPlayers = MAX_NO_PLAYERS - noHumanPlayers;       //Update fixed noVirtualPlayer
            }
            //If the stack pane being clicked is a choice of a number of virtual players
        } else {
            changeNoPlayerChoiceCirColor((StackPane)rootMenu.lookup("#VP" + noVirtualPlayers), false); //paint the circle of this choice to unchosen color
            noVirtualPlayers = Integer.parseInt(stackPane.getId().substring(2));
            changeNoPlayerChoiceCirColor((StackPane)rootMenu.lookup("#VP" + noVirtualPlayers), true);   //paint the circle of this choice to chosen color
            if ((noHumanPlayers + noVirtualPlayers) > MAX_NO_PLAYERS) {    //If the total of players exceeded 4
                changeNoPlayerChoiceCirColor( (StackPane)rootMenu.lookup("#HP" + noHumanPlayers) , false);       //paint the circle of this choice to unchosen color
                changeNoPlayerChoiceCirColor( (StackPane)rootMenu.lookup("#HP" + (MAX_NO_PLAYERS - noVirtualPlayers)), true );  //paint the circle of this choice to chosen color
                noHumanPlayers = MAX_NO_PLAYERS - noVirtualPlayers;       //Update fixed noHumanPlayer
            }
        }
    };

    //Change circle color
    private void changeNoPlayerChoiceCirColor(StackPane stackPane, boolean isChosen){
        String colorCode ;  //Variable storing the code of color we will use
        if (isChosen) colorCode = CHOSEN_COLOR; //if this circle is chosen
        else colorCode = UNCHOSEN_COLOR;        //If this circle is unchosen
        Circle tempCircle = (Circle)stackPane.getChildren().get(0); //Get the circle object
        tempCircle.setStyle("-fx-fill: " + colorCode);  //Fill color in the circle
    }

    // reset players name list
    public void resetPlayersNameList() {
        playersNameList.set(0, "");
        playersNameList.set(1, "");
        playersNameList.set(2, "");
        playersNameList.set(3, "");
    }

    //Set value for each object in playersNameList from textfield
    public void setPlayersNameList(){
        //Name of the human players
        for (int i = 0;  i < noHumanPlayers; i++){
            TextField tempPlayerNameTF =  (TextField)rootMenu.lookup("#TF" + i);
            playersNameList.set(i, tempPlayerNameTF.getText());
        }
        //Name of the virtual players
        for (int i = noHumanPlayers; i < noHumanPlayers + noVirtualPlayers ; i++){
            if (I18NController.isEnglish()) playersNameList.set(i, "Virtual Player " + (i - noHumanPlayers + 1));
            else playersNameList.set(i, "Người chơi ảo " + (i - noHumanPlayers + 1));
        }
    }

    // set online players name list
    private void setOnlinePlayersNameList(ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            this.playersNameList.set(i, players.get(i).getName());
            mainController.setNoOnlinePlayers(players.size());
        }
    }

    //Event handler for the "language" button
    public void setChangeLanguageBtnEventHandler(){
        changeLanguageBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            startMenu.setVisible(false);
            langMenu.setVisible(true);
        });
    }

    // vietnamese language button event handler
    public void setVietnameseLangBtnEventHandler(){
        vietnameseLangBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            vietnameseLangBtn.setStyle(red);
            englishLangBtn.setStyle(blue);
            I18NController.switchLanguage(I18NController.getLanguageLocale(I18NController.Language.VIETNAMESE));
        });
    }

    // english language button event handler
    public void setEnglishLangBtnEventHandler(){
        englishLangBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            englishLangBtn.setStyle(red);
            vietnameseLangBtn.setStyle(blue);
            I18NController.switchLanguage(I18NController.getLanguageLocale(I18NController.Language.ENGLISH));
        });
    }

    // language back button event handler
    private void setLangBackBtnEventHandler(){
        langBackBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            langMenu.setVisible(false);
            startMenu.setVisible(true);
        });
    }

    //Event handler for the newGameBtn
    private void setOfflineGameBtnEventHandler(){
        offlineGameBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            startMenu.setVisible(false);     //hide start menu
            preGameMenu.setVisible(true);    //show pregame menu
        });
    }

    //Event handler for the exitGameBtn
    private void setExitGameBtnEventHandler(){
        exitGameBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            System.exit(0);         //Exit the program
        });
    }

    //Event handler for the get back to start menu GameBtn
    private void setBackBtnEventHandler(){
        backBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            preGameMenu.setVisible(false);  //hide pregame menu
            startMenu.setVisible(true);     //show start menu
        });
    }

    //Event handler for the nextBtn
    private void setNextBtnEventHandler(){
        nextBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            if (noHumanPlayers + noVirtualPlayers < 2) {
                startMenuError.setVisible(true);
            }
            else {
                resetPlayersNameList();
                preGameMenu.setVisible(false);
                userSetNameMenu.setVisible(true);
                createSetPlayerNameMenu();
            }
        });
    }

    //Event handler for "Got it" button
    private void setExitErrorBtnEventHandler(){
        exitErrorBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            startMenuError.setVisible(false);

        });

        exitErrorBtn1.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            emptyPlayerNameError.setVisible(false);
        });

        cantConnectToServerBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            cantConnectToServerError.setVisible(false);
        });
    }

    //Event handler for "Back" button
    private void setBackLevelBtnEventHandler(){
        backLevelBtn.setOnMouseClicked(event->{
            SoundController.playButtonClickSound();
            preGameMenu.setVisible(true);
            userSetNameMenu.setVisible(false);
        });
    }

    //Event handler for "Play Again" button at end game
    private void setEGPlayAgainBtnEventHandler(){
        EGPlayAgainBtn.setOnMouseClicked(mouseEvent -> {
            endGameMenu.setVisible(false);
            rootMenu.setVisible(false);
            mainController.restartGame();
        });
    }

    //Event handler for "Play Again" button at end game
    private void setSGPlayAgainBtnEventHandler(){
        SGPlayAgainBtn.setOnMouseClicked(mouseEvent -> {
            stopGameMenu.setVisible(false);
            rootMenu.setVisible(false);
            mainController.restartGame();
        });
    }

    //Event handler for "New Game" button at end game
    private void setEGNewGameBtn(){
        EGNewGameBtn.setOnMouseClicked(e -> {
            endGameMenu.setVisible(false);
            rootMenu.setVisible(true);
            startMenu.setVisible(true);
            mainController.displayGameBoard(false, false);
            onlinePlayBtn.setMouseTransparent(false);
            onlinePlayBtn.setDisable(false);
            I18NController.setUpButtonText(onlinePlayBtn,"menu.online_player_play_btn");
            onlinePlayerTextField.setDisable(false);

        });
    }

    //Event handler for "New Game" button at stop game
    private void setSGNewGameBtn(){
        SGNewGameBtn.setOnMouseClicked(e -> {
            stopGameMenu.setVisible(false);
            rootMenu.setVisible(true);
            preGameMenu.setVisible(true);
            mainController.displayGameBoard(false, false);
        });
    }

    //Event handler for "Exit" button at end game
    private void setEGQuitBtn(){
        EGQuitBtn.setOnMouseClicked(e -> {
            SoundController.playButtonClickSound();
            System.exit(0);         //Exit the program
        });
    }

    //Event handler for "Exit" button at stop game
    private void setSGQuitBtn(){
        SGQuitBtn.setOnMouseClicked(e -> {
            SoundController.playButtonClickSound();
            System.exit(0);         //Exit the program
        });
    }

    //Event handler for "Stop Game" button
    private void setPlayerDisconnectedBtn(){
        playerDisconnectedBtn.setOnMouseClicked(e -> {
            startMenu.setVisible(true);
            playerDisconnectedMenu.setVisible(false);
            mainController.displayGameBoard(false,false);
        });
    }

    //Check if the user input nothing for the players'name
    private boolean validatePlayersNameInput(){
        for (int i = 0; i < noHumanPlayers; i++ ){
            TextField tmpPlayerNameTF = (TextField)rootMenu.lookup("#TF" + i);
            if (tmpPlayerNameTF.getText().equals("")) return false;
        }
        return true;
    }

    // set next play scene button event handler
    private void setNextPlaySceneBtnEventHandler(){
        nextPlaySceneBtn.setOnMouseClicked(event->{
            SoundController.playButtonClickSound();
            if (!validatePlayersNameInput()){
                emptyPlayerNameError.setVisible(true);
            }else {
                setPlayersNameList();
                userSetNameMenu.setVisible(false);
                rootMenu.setVisible(false);
                mainController.displayGameBoard(true, false);
            }
        });
    }

    // display end game menu
    public void displayEndGameMenu(int firstFinishId, boolean isOnlineGame){
        rootMenu.setVisible(true);
        endGameMenu.setVisible(true);
        if (isOnlineGame) EGPlayAgainBtn.setVisible(false);
        else EGPlayAgainBtn.setVisible(true);
        displayPlayersName();
        displayPlayersPoints();
        displayFirstPlayerToFinishGame(firstFinishId);
        displayPlayerWithMostPoints();
    }

    // display player name
    private void displayPlayersName(){
        EGPN0.setText(playersNameList.get(0));
        EGPN1.setText(playersNameList.get(1));
        EGPN2.setText(playersNameList.get(2));
        EGPN3.setText(playersNameList.get(3));
    }

    // display players point
    private void displayPlayersPoints(){
        if (playersNameList.get(0).length() != 0) EGP1P0.setText(String.valueOf(mainController.getScoreList()[0]));
        if (playersNameList.get(1).length() != 0) EGP1P1.setText(String.valueOf(mainController.getScoreList()[1]));
        if (playersNameList.get(2).length() != 0) EGP1P2.setText(String.valueOf(mainController.getScoreList()[2]));
        if (playersNameList.get(3).length() != 0) EGP1P3.setText(String.valueOf(mainController.getScoreList()[3]));
    }

    // display first player to finish game
    private void displayFirstPlayerToFinishGame(int firstFinishId){
        finishPN.setText(playersNameList.get(firstFinishId));
    }

    // display player with most points
    private void displayPlayerWithMostPoints(){
        int max = mainController.getScoreList()[0];
        int winnerId = 0;
        for (int i = 1; i < 4; i++){
            if (mainController.getScoreList()[i] > max){
                winnerId = i;
                max = mainController.getScoreList()[i];
            }
        }
        highestPointsPN.setText(playersNameList.get(winnerId));
    }

    // start online game
    public void startOnlineGame(MatchInformation matchInformation) {
        setOnlinePlayersNameList(matchInformation.getPlayers());
        Platform.runLater(() -> {
            onlinePlayMenu.setVisible(false);
            rootMenu.setVisible(false);
            this.mainController.displayGameBoard(true, true);
        });
    }

    // Set Name Menu added
    private void createSetPlayerNameMenu() {
        //Show text field for inputting human players' name
        for (int i = 0; i < noHumanPlayers; i++) {
            StackPane humanPlayerNamePane = (StackPane)rootMenu.lookup("#userPane" + i);
            humanPlayerNamePane.setVisible(true);
            TextField humanPlayerNameTF = (TextField)humanPlayerNamePane.getChildren().get(1);
            humanPlayerNameTF.setDisable(false);
        }

        //Show disabled text field with virtual players'name default as "Virtual Player + (i - noHumanPlayers + 1)"
        for (int i = noHumanPlayers; i < noHumanPlayers + noVirtualPlayers; i++ ){
            StackPane virtualPlayerNamePane = (StackPane)rootMenu.lookup("#userPane" + i);
            virtualPlayerNamePane.setVisible(true);
            TextField virtualPlayerNameTF = (TextField)virtualPlayerNamePane.getChildren().get(1);
            if (I18NController.isEnglish()) virtualPlayerNameTF.setText("Virtual Player " + (i - noHumanPlayers + 1));
            else virtualPlayerNameTF.setText("Người chơi ảo " + (i - noHumanPlayers + 1));
            virtualPlayerNameTF.setDisable(true);
        }

        //Hide those text field if noHumanPlayers + noVirtualPlayers < 4
        for (int i = noHumanPlayers + noVirtualPlayers; i < 4; i++){
            StackPane playerNamePane = (StackPane)rootMenu.lookup("#userPane" + i);
            playerNamePane.setVisible(false);
        }
    }

    public int getNoHumanPlayers() {
        return noHumanPlayers;
    }

    public int getNoVirtualPlayers() {
        return noVirtualPlayers;
    }

    // online game button event handler
    private void setOnlineGameBtnEventHandler(){
        onlineGameBtn.setOnMouseClicked(event -> {
            SoundController.playButtonClickSound();
            try {
                if (this.clientController == null) {
                    this.clientController = new ClientController();
                    this.clientController.injectMenuController(this);
                    this.mainController.injectClientController(clientController);
                    this.clientController.injectGameBoardController(mainController.getGameBoardController());
                    clientController.start();
                }
            } catch (IOException e) {
                this.clientController = null;
                cantConnectToServerError.setVisible(true);
                return;
            }
            startMenu.setVisible(false);           //hide start menu
            onlinePlayMenu.setVisible(true);       //online play menu
        });
    }

    // create event handler for online play button
    private void setOnlinePlayBtnEventHandler(){
        onlinePlayBtn.setOnMouseClicked(mouseEvent -> {
            SoundController.playButtonClickSound();
            // check if text field is empty -> show error
            if(onlinePlayerTextField.getText().equals("")){
                emptyPlayerNameError.setVisible(true);
            } else {
                if (onlinePlayerTextField.getText() != null) {
                    //create new player with given name
                    //update game connection field
                    Platform.runLater(() -> {
                        this.clientController.ready(onlinePlayerTextField.getText());
                        mainController.setPlayerName(onlinePlayerTextField.getText());
                        onlinePlayBtn.setMouseTransparent(true);
                        I18NController.setUpButtonText(onlinePlayBtn,"menu.online_waiting_player");
                        onlinePlayerTextField.setDisable(true);
                    });
                }
            }
        });
    }

    // back to main menu button event handler
    private void setBackToMainMenuBtnEventHandler() {
        backToMainOnlinePlayBtn.setOnMouseClicked(mouseEvent -> {
            SoundController.playButtonClickSound();
            if (this.clientController != null) this.clientController.disconnect();
            this.clientController = null;
            this.onlinePlayMenu.setVisible(false);
            this.startMenu.setVisible(true);
            onlinePlayBtn.setMouseTransparent(false);
            I18NController.setUpButtonText(onlinePlayBtn,"menu.online_player_play_btn");
            onlinePlayerTextField.setDisable(false);
        });
    }

    // get players name list
    public ArrayList<String> getPlayersNameList() {
        return playersNameList;
    }

    // display stop game menu
    public void displayStopGameMenu(boolean isDisplayed, boolean isOnlineGame){
        if (isDisplayed) {
            if (isOnlineGame) SGPlayAgainBtn.setVisible(false);
            else SGPlayAgainBtn.setVisible(true);
            rootMenu.setVisible(true);
            stopGameMenu.setVisible(true);
        } else {
            rootMenu.setVisible(false);
            stopGameMenu.setVisible(false);
        }
    }

    // display player disconnected menu
    public void displayPlayerDisconnectedMenu(){
        rootMenu.setVisible(true);
        playerDisconnectedMenu.setVisible(true);
    }

}