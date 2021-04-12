import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import sun.jvm.hotspot.opto.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


public class Game extends Application {

    // Scene 1 button
    private Button playButton;

    // instantiation for many grid buttons
    private Button b1;

    // this scene
    private Scene mainScene;

    // initialize game grid
    private GridPane grid;

    // players turn text
    private Text playerText = new Text("It is player 1's turn!");

    // Text to keep track of current game data
    private TextArea resultText = new TextArea();

    // game board array holds each empty stack containing valid spaces
    private ArrayList<BlockStack> stackList = new ArrayList<BlockStack>();

    // tracks empty blockstacks here
    private BlockStack stack1 = new BlockStack();
    private BlockStack stack2 = new BlockStack();
    private BlockStack stack3 = new BlockStack();
    private BlockStack stack4 = new BlockStack();
    private BlockStack stack5 = new BlockStack();
    private BlockStack stack6 = new BlockStack();
    private BlockStack stack7 = new BlockStack();

    // track all moves made
    private BlockStack moveStack = new BlockStack();

    // initialize checkGameState class
    private CheckGameState check = new CheckGameState();

    // set player color
    private String playerColor = "-fx-background-color: #ff0000; ";

    // create hashmap for coordinates using counter as key and x , y list as value
    private HashMap<Integer, ArrayList<Integer>> Coordinates = new HashMap<Integer, ArrayList<Integer>>();

    // create an index that player selected
    private int index = 0;


//------------------------------------------------------------------------------

    // get func for grid
    public GridPane getGrid() { return this.grid; }

    // get check
    public CheckGameState getCheck() { return this.check; }

    // get functions for each specific valid stack
    public BlockStack getStack1() { return stack1; }
    public BlockStack getStack2() { return stack2; }
    public BlockStack getStack3() { return stack3; }
    public BlockStack getStack4() { return stack4; }
    public BlockStack getStack5() { return stack5; }
    public BlockStack getStack6() { return stack6; }
    public BlockStack getStack7() { return stack7; }

    // get function for the valid block stack array
    public ArrayList<BlockStack> getStackList() { return this.stackList; }

    // override function
    public void start(Stage primaryStage) {}

    // main play button from scene 1
    public Button getPlayButton() { return this.playButton; }

//-------------------------------------------------------------------------------------------

    // create grid and game board
    public void addGameGrid() {
        // clear

        // create instantiation of grid 1 time
        this.grid = new GridPane();

        int counter = 1; // keeps track of button number

        for (int x = 1; x < 7; x++) {
            for (int y = 1; y < 8; y++) {
                b1 = new Button((y) + ", " + x); // new instance and keeps track of num
                //b1 = new Button ((Integer.toString(counter)));
                b1.setMinSize(60,60); // size
                b1.setDisable(true); // cant be clicked
                b1.setStyle("-fx-background-color: #888888; "); // og button color gray
                grid.add(b1, y, x); // col, row

                // create a tmp pairlist and only hold 2 values
                ArrayList<Integer> pairlist = new ArrayList<>();
                pairlist.add(y);
                pairlist.add(x);

                // add all x, y pairs to a unique counter starting at 1
                Coordinates.put(counter, pairlist);

                // load stacks with empty valid buttons that has a unique counter number
                // stacks keep track of buttons, x coordinate, y coordinate
                if (y == 1) {
                    //this.stack1.add((Button) grid.getChildren().get(counter), x, y);
                    this.stack1.add(b1, x, y, counter);
                }
                if (y == 2) {
                    this.stack2.add(b1, x, y, counter);
                }
                if (y == 3) {
                    this.stack3.add(b1, x, y, counter);
                }
                if (y == 4) {
                    this.stack4.add(b1, x, y, counter);
                }
                if (y == 5) {
                    this.stack5.add(b1, x, y, counter);
                }
                if (y == 6) {
                    this.stack6.add(b1, x, y, counter);
                }
                if (y == 7) {
                    this.stack7.add(b1, x, y, counter);
                }

                counter++;

            } // end of inner loop
        } // end of outer loop
    } // end of add game grid

//--------------------------------------------------------------------------------------------

    public int player(String color) {
        if (color.equals("-fx-background-color: #ff0000; ")) { // if red
            //playerText.setText("It is player 1's turn!");
            return 1;
        }
        else {
            //playerText.setText("It is player 2's turn!");
            return 2;
        }
    } // end of player func

//--------------------------------------------------------------------------------------------

    // update player move data
    public void updateGrid() {

        // create an event handler that responds to a button
        // on the grid that gets clicked
        EventHandler<ActionEvent> myHandler;
        myHandler = e -> {

            // create button sources for original grid
            b1 = (Button) e.getSource();

            // keep track of x, y coordinate chosen by players
            int x = grid.getColumnIndex(b1); // == column)
            int y = grid.getRowIndex(b1); //== row &&

            // get x, y coordinates as string
            //String getPair = b1.getText();

            // check if button press is valid
            if (check.checkValidMove(stackList, b1) == false) { // not a valid move
                // update game text
                resultText.setText("Player " + player(playerColor) + " moved to " + x + ", " + y + " \n" +
                        "This is NOT a valid move \n" +
                        "Player " + player(playerColor) + " pick again!");

                return; // exit func
            }
            // add valid move to moveStack
            moveStack.add(b1, playerColor, x, y);
            // neighbor moves
//            int top = y-2;
//            int bottom = y;
//            int left = x-2;
//            int right = x;


            //else is a valid move
            resultText.setText("Player " + player(playerColor) + " moved to " + x + ", " + y + " \n" +
                    "This is a valid move");

            // set player color
            b1.setStyle(playerColor);

            // check for winning move
            if (check.checkWinningMove(grid, moveStack) == true) {
                check.fourInRow.length();
                resultText.setText("Player " + player(playerColor) + " moved to " + x + ", " + y + " \n" +
                        "Game Over, Player " + player(playerColor) + " wins! \n" +
                        "four in a row stack size = " + check.fourInRow.length() + "\n");


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText("Player " + player(playerColor) + " wins!");
                alert.setContentText("Do you want to play again,\n" +
                        "or do you want to exit the game?\n");
                alert.getButtonTypes();


                ButtonType buttonPlayAgain = new ButtonType("Play Again");
                ButtonType buttonExit = new ButtonType("Exit");

                alert.getButtonTypes().setAll(buttonPlayAgain, buttonExit);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == buttonPlayAgain){
                    // reset board and game
                }
                else if (result.get() == buttonExit) {
                    Platform.exit();
                }
                // set to game over screen
                return;
            }

            // change player
            playerColor = check.switchPlayer(playerColor);

            // set to next players turn
            playerText.setText("It is player " + player(playerColor) + "'s turn");

            // button can no longer be pressed
            //b1.setDisable(true);

        }; // end of event handler

        int counter = 0;

        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 7; y++) {

                // enable all buttons to be clickable
                grid.getChildren().get(counter).setDisable(false);

                // allow buttons to be interacted from event handler
                ((Button) grid.getChildren().get(counter)).setOnAction(myHandler);

                counter++;

            } // end of inner for loop
        } // end of outer for loop

    } // end of enable grid

//-----------------------------------------------------------------------------------------------

    public GridPane resetBoard() {
        // clear
        this.grid.getChildren().clear();

        // add new grid
        addGameGrid();

        return this.grid;
    }

//-----------------------------------------------------------------------------------------------

    public void loadStackList() {
        // load the stack list
        this.stackList.add(getStack1());
        this.stackList.add(getStack2());
        this.stackList.add(getStack3());
        this.stackList.add(getStack4());
        this.stackList.add(getStack5());
        this.stackList.add(getStack6());
        this.stackList.add(getStack7());
    }

//-----------------------------------------------------------------------------------------------

    public Scene startScene() {
        VBox root = new VBox(20);

        root.setStyle("-fx-background-color: #bb00FF;");

        playButton = new Button("Play");
        Label t1 = new Label("Welcome to the Connect Four Game!");

        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(t1, playButton);

        return new Scene(root,700,700);
    } // end of start scene

//-----------------------------------------------------------------------------------------------

    public Scene endScene() {
        VBox root = new VBox(20);

        root.setStyle("-fx-background-color: #34ab88;");

        Button playAgain = new Button("Play again");
        Label t1 = new Label("Game over!");

        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(t1, playAgain);

        return new Scene(root,700,700);
    } // end of end scene

//-----------------------------------------------------------------------------------------------

    public Scene gameScene() {

        // initialize borderpane in order to separate/organize data
        BorderPane border = new BorderPane();
        border.setStyle("-fx-background-color: #ff99ff;");

        // initialize vbox to store menus
        VBox menuVBox = new VBox(50); // sets spacing
        menuVBox.setStyle("-fx-background-color: #add8e6;");
        menuVBox.setAlignment(Pos.CENTER); // aligns items within

        VBox outputVBox = new VBox(50); // sets spacing
        outputVBox.setStyle("-fx-background-color: #add8e6;");
        outputVBox.setAlignment(Pos.CENTER); // aligns items within

        VBox centerVBox = new VBox(50); // sets spacing
        centerVBox.setStyle("-fx-background-color: #ff99ff;");
        centerVBox.setAlignment(Pos.CENTER); // aligns items within


        addGameGrid(); // populate the GridPane with buttons
        loadStackList(); // populate the stack list with an empty
                         // stack storing all valid moves at blockstack.peek()
        updateGrid(); // create grid buttons

        //grid.setAlignment(Pos.CENTER_RIGHT);
        grid.setAlignment(Pos.CENTER);
        grid.setMaxSize(800,800);

        // how to play text
        TextArea howToPlayText = new TextArea("This is how to play");
        howToPlayText.setMaxSize(200, 200);
        howToPlayText.isDisable();

        // game result text
//        TextArea resultText = new TextArea("Player _ moved to x,y\n" +
//                                    "This is NOT a valid move\n" +
//                                    "Player _ pick again.");
//        resultText.setMaxSize(200, 200);
//        resultText.isDisable();






        // create empty menu dropdown bars
        MenuBar menu1 = new MenuBar();
        MenuBar menu2 = new MenuBar();
        MenuBar menu3 = new MenuBar();

        // create and assign names for menu dropdown bars
        Menu mOne = new Menu("Menu1");
        Menu mTwo = new Menu("Menu2");
        Menu mThree = new Menu("Menu3");

        // create items for menu 1
        MenuItem reverseMove = new MenuItem("Reverse last move");

        reverseMove.setOnAction(e-> {
            // call player move
            int x = moveStack.Top().getX();
            int y = moveStack.Top().getY();
            Button newB = moveStack.Top().getButton();

            // calculate index
            int theIndex = (y-1)*7+(x-1);
            if (y <= 1) {
                theIndex = x-1;
            }

            ((Button) grid.getChildren().get(theIndex)).setStyle("-fx-background-color: #888888; ");

            for (int index = 0; index < stackList.size(); index++) {
                if (stackList.get(index).Top().getX() == x) {
                    stackList.get(index).add(newB, x, y, theIndex);
                }
            }

            moveStack.pop();
        });

        // create items for menu 2
        MenuItem theme1 = new MenuItem("Theme 1");
        MenuItem theme2 = new MenuItem("Theme 2");

        theme1.setOnAction(e-> {
                 //change colors of stuff
                border.setStyle("-fx-background-color: #ff99ff;");

                menuVBox.setStyle("-fx-background-color: #66ff66;");

                outputVBox.setStyle("-fx-background-color: #66ff66;");

                centerVBox.setStyle("-fx-background-color: #ffff99;");
        });

        theme2.setOnAction(e-> {
            //change colors of stuff
            border.setStyle("-fx-background-color: #ff99ff;");

            menuVBox.setStyle("-fx-background-color: #ff944d;");

            outputVBox.setStyle("-fx-background-color: #ff944d;");

            centerVBox.setStyle("-fx-background-color: #990099;");
        });

        // create items for menu 3
        MenuItem howToPlay = new MenuItem("How to play");
        MenuItem newGame = new MenuItem("New game");
        MenuItem exit = new MenuItem("Exit");

        // actions for menu 3
        howToPlay.setOnAction(e->
                howToPlayText.setText("Connect Four is a two player connection board game.\n" +
                        "It has players choose a color and then take turns dropping colored\n" +
                        "objects into seven columms vertically in a grid. The pieces fall \n" +
                        "straight down, occupying the lowest available space within the column.\n" +
                        "The objective of the game is to be the first to form a horizontal, vertical,\n" +
                        "or diagonal line of four one's in a row.\n"));

        newGame.setOnAction(e-> {
            this.grid = resetBoard();
            centerVBox.getChildren().addAll(this.playerText, this.grid, this.resultText);
                });

        exit.setOnAction(e-> Platform.exit());

        // add menu option to menu 1
        mOne.getItems().add(reverseMove);

        // add menu option to menu 2
        mTwo.getItems().add(theme1);
        mTwo.getItems().add(theme2);

        // add menu option to menu 3
        mThree.getItems().add(howToPlay);
        mThree.getItems().add(newGame);
        mThree.getItems().add(exit);

        // add updated menus to initial menu bar
        menu1.getMenus().addAll(mOne);
        menu2.getMenus().addAll(mTwo);
        menu3.getMenus().addAll(mThree);



        //event handler is attached to each button in the GridPane



        // set VBox
        menuVBox.getChildren().addAll(menu1, menu2, menu3);
        outputVBox.getChildren().addAll(howToPlayText);
        centerVBox.getChildren().addAll(playerText, grid, resultText);

        // set border
        border.setLeft(menuVBox);
        border.setRight(outputVBox);
        border.setCenter(centerVBox);

        //new scene with root node
        return new Scene(border, 800,800);

    } // end of game scene

} // end of game class
