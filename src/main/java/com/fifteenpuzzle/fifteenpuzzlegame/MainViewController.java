package com.fifteenpuzzle.fifteenpuzzlegame;

import com.fifteenpuzzle.fifteenpuzzlegame.database.DBClient;
import com.fifteenpuzzle.fifteenpuzzlegame.game.PuzzleGenerator;
import com.fifteenpuzzle.fifteenpuzzlegame.model.PuzzleButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{
    private int movesCount;
    private String name = "";
    PuzzleGenerator puzzleGenerator;
    PuzzleButton[] puzzleButtons;
    DBClient client;
    boolean firstClick;
    Timeline stopwatch;
    int secondsPassed = 0;
    int currentUserId;
    LocalDateTime startDateTime;
    @FXML
    private GridPane btnsGridPane;
    @FXML
    private Pane anchor_fragment;
    @FXML
    private Label playerNameLbl;
    @FXML
    private Label stopwatchLbl;
    @FXML
    private Label gamesPlayedCountLbl;
    @FXML
    private Label gamesPlayedCountTitleLbl;

    public MainViewController()
    {
        puzzleGenerator = new PuzzleGenerator();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        client = new DBClient();
        while (name.isEmpty())
            login();
        currentUserId = client.getUserByName(name);
        if (currentUserId == -1)
        {
            currentUserId = client.insertUser(name);
        }
        gamesPlayedCountLbl.setText(Integer.toString(client.getUserPlayedGamesCount(currentUserId)));
        playerNameLbl.setText(name);
        PuzzleButton[] randArray = puzzleGenerator.createPuzzle();
        puzzleButtons = randArray;
        makeGrid(randArray);
    }

    private void login()
    {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("15 Puzzle");
        dialog.setHeaderText("Login");
        dialog.setContentText("Please enter your name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty())
        {
            Platform.exit();
            System.exit(0);
        }
        result.ifPresent(name -> this.name = name);
    }


    private void makeGrid(PuzzleButton[] randArray)
    {
        btnsGridPane.getChildren().clear();
        int sqrt = (int) Math.sqrt(randArray.length);
        for (int i = 0; i < sqrt; i++)
        {
            Node[] nodes = new Node[sqrt];
            for (int k = 0; k < sqrt; k++)
            {
                PuzzleButton puzzleButton = randArray[(i * sqrt) + k];
                puzzleButton.setOnAction(e -> buttonCliked(puzzleButton));
                nodes[k] = puzzleButton;
            }
            btnsGridPane.addRow(i, nodes);
        }
        firstClick = true;
    }

    private void buttonCliked(PuzzleButton button)
    {
        if (button.canMove())
        {
            if (firstClick)
            {
                startDateTime = LocalDateTime.now();
                firstClick = false;
                secondsPassed = 0;
                movesCount =0;
                gamesPlayedCountTitleLbl.setText("Games played: ");
                gamesPlayedCountLbl.setText(Integer.toString(client.getUserPlayedGamesCount(currentUserId)));
                stopwatch = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                event -> stopwatchLbl.setText(Integer.toString(++secondsPassed) + " sec")));
                stopwatch.setCycleCount(Timeline.INDEFINITE);
                stopwatch.play();
            }
            button.move();
            movesCount++;

            if (puzzleGenerator.checkWin(puzzleButtons))
            {
                gamesPlayedCountTitleLbl.setText("");
                gamesPlayedCountLbl.setText("You have won !!!");
                for (int i = 0; i < 15; i++)
                    puzzleButtons[i].setDisable(true);
                stopwatch.stop();
                LocalDateTime endDateTime = LocalDateTime.now();
                client.insertGame(currentUserId, startDateTime, endDateTime, movesCount);
            }
        }
    }

    @FXML
    private void startNewGame(ActionEvent e)
    {
        PuzzleButton[] randArray = puzzleGenerator.createPuzzle();
        puzzleButtons = randArray;
        makeGrid(randArray);
    }

    public void closeApplication(ActionEvent actionEvent)
    {
        Platform.exit();
        System.exit(0);
    }
}