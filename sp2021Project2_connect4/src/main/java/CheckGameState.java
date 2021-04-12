import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

import java.util.ArrayList;

//// GameLogic
//// GameLogic is the file that deals with all of the functions needed to play the game of keno
//// it works by waiting until the user has confirmed how many spots they want, then initializing
//// the array of user picks to be that size, there are functions to ensure that random numbers
//// do not repeat and there are functions to count how many matches there are and then calculate
//// how much the user wins. The draws are set here and accessed here but the javaFx portion actually
//// is the part that runs the game x amount of times so draws is not a concern for the GameLogic
//// File outside of storing the value. The JavaFx buttons call upon these functions to run the
//// game, and we use an instance of GameLogic to keep track of the game on the JavaFx part
//
public class CheckGameState {

    // tracks player blocks
    BlockStack fourInRow = new BlockStack(); // tracks 4 in a row

    // tracks last players move and all moves around it
    // if one move around the block is the same color
    // then add that one to the stack and move to the
    // selected color and repeat the process but in
    // the same direction as the previous block until a
    // stack of 4 is found. (a straight line)
    private BlockStack winningStack;

    // tracks all valid moves made
    private BlockStack pathStack;

//------------------------------------------------------------------------------------
    // check if b1 == .peek() of every element in blockstack list
    // pass in stack to reference the top block

    public boolean checkValidMove(ArrayList<BlockStack> list, Button b1) {
        // iterate through list of stacks
        // compare selected button with BlockStack.Top().button and if they
        // are equal, then it is a valid move.
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i)).isEmpty() == false) { // if stack is not empty
                if (list.get(i).Top().getButton() == b1) { // if b1 exist in top of valid stack
                    list.get(i).pop(); // remove peek() / Top()
                    return true;
                }
            }
        }
        return false;
    } // end of check valid move func

//-----------------------------------------------------------------------------------------

    public String switchPlayer(String color) {
        //String color = "";
        if (color == "-fx-background-color: #ff0000; "){ // if color is red, switch to blue
            return "-fx-background-color: #0000ff; "; // return blue
        }
        else { //if (color == "0000ff"){ // if color is blue, return red
            return "-fx-background-color: #ff0000; "; // red
        }
        //return color;
    } // end of switch player function

//-----------------------------------------------------------------------------------------

    // -> /
    private Boolean rightDiag(GridPane grid,
                              BlockStack playerMove,
                              BlockStack fourInRow) {


        // check for four in a row in top right
        fourInRow = _checkTopRight(grid, fourInRow, playerMove);
        // check for four in a row in bottom left
        fourInRow = _checkBottomLeft(grid, fourInRow, playerMove);

        if (fourInRow.length() > 3) { // base case
            return true;
        }
        else {
            fourInRow.clear();
            return false;
        }
    } // end of right

//------------------------------------------------------------------

    // check top right func and return updated four in row
    private BlockStack _checkTopRight(GridPane grid,
                                BlockStack fourInRow,
                                BlockStack playerMove) {

        int x = playerMove.Top().getX();
        int y = playerMove.Top().getY();
        String playerColor = playerMove.Top().getColor();

        // get current data
        int currX = x;
        int currY = y;
        String currColor = playerColor;

        while (currY != 1 && currX != 7) { // if either one is true stop checking

            // get the correct corresponding top right neighbor
            int top = currY-1;
            int right = currX+1;

            // calculate index for top right neighbor
            int TRindex = ((top-1) * 7) + (right-1);

            // get color and button for top right square
            String topRightColor = grid.getChildren().get(TRindex).getStyle();
            Button topRightB = (Button) grid.getChildren().get(TRindex);

            // check if color matches and if so, move to it
            if (currColor == topRightColor) { // if top color matches top right

                // add to fourinrow stack
                fourInRow.add(topRightB, topRightColor, right, top);

                // set to new x and y and color
                currX = fourInRow.Top().getX();
                currY = fourInRow.Top().getY();
                currColor = fourInRow.Top().getColor();
            }
            else{
                break;
            }
        }
        return fourInRow;
    }

//-----------------------------------------------------------------------

    // check bottom left func and return updated four in a row
    private BlockStack _checkBottomLeft(GridPane grid,
                                      BlockStack fourInRow,
                                      BlockStack playerMove) {

        int x = playerMove.Top().getX();
        int y = playerMove.Top().getY();
        String playerColor = playerMove.Top().getColor();

        // get current data
        int currX = x;
        int currY = y;
        String currColor = playerColor;

        while (currY != 6 && currX != 1) { // if either one is true stop checking

            // get the correct corresponding top right neighbor
            int bottom = currY+1;
            int left = currX-1;

            // calculate index for top right neighbor
            int BLindex = ((bottom-1) * 7) + (left-1);

            // get color and button for top right square
            String bottomLeftColor = grid.getChildren().get(BLindex).getStyle();
            Button bottomLeftB = (Button) grid.getChildren().get(BLindex);

            // check if color matches and if so, move to it
            if (currColor == bottomLeftColor) { // if current color matches bottom left color

                // add to fourinrow stack
                fourInRow.add(bottomLeftB, bottomLeftColor, left, bottom);

                // set to new x and y and color
                currX = fourInRow.Top().getX();
                currY = fourInRow.Top().getY();
                currColor = fourInRow.Top().getColor();
            }
            else {
                break;
            }
        }
        return fourInRow;
    }
//-----------------------------------------------------------------------------------------

    // -> \
    private Boolean leftDiag(GridPane grid, BlockStack playerMove, BlockStack fourInRow) {

        // check for four in a row in top left
        fourInRow = _checkTopLeft(grid, fourInRow, playerMove);

        // check bottom right
        fourInRow = _checkBottomRight(grid, fourInRow, playerMove);

        if (fourInRow.length() > 3) { // base case
            return true;
        }
        else {
            fourInRow.clear();
            return false;
        }
    } // end of left diag
//------------------------------------------------------------------

    // check top right func and return updated four in row
    private BlockStack _checkTopLeft(GridPane grid,
                                      BlockStack fourInRow,
                                      BlockStack playerMove) {

        int x = playerMove.Top().getX();
        int y = playerMove.Top().getY();
        String playerColor = playerMove.Top().getColor();

        // get current data
        int currX = x;
        int currY = y;
        String currColor = playerColor;

        while (currY != 1 && currX != 1) { // if either one is true stop checking

            // get the correct corresponding top right neighbor
            int top = currY-1;
            int left = currX-1;

            // calculate index for top right neighbor
            int TLindex = ((top-1) * 7) + (left-1);

            // get color and button for top right square
            String topLeftColor = grid.getChildren().get(TLindex).getStyle();
            Button topLeftB = (Button) grid.getChildren().get(TLindex);

            // check if color matches and if so, move to it
            if (currColor == topLeftColor) { // if top color matches top right

                // add to fourinrow stack
                fourInRow.add(topLeftB, topLeftColor, left, top);

                // set to new x and y and color
                currX = fourInRow.Top().getX();
                currY = fourInRow.Top().getY();
                currColor = fourInRow.Top().getColor();
            }
            else{
                break;
            }
        }
        return fourInRow;
    }

//-----------------------------------------------------------------------

    // check bottom left func and return updated four in a row
    private BlockStack _checkBottomRight(GridPane grid,
                                        BlockStack fourInRow,
                                        BlockStack playerMove) {

        int x = playerMove.Top().getX();
        int y = playerMove.Top().getY();
        String playerColor = playerMove.Top().getColor();

        // get current data
        int currX = x;
        int currY = y;
        String currColor = playerColor;

        while (currY != 6 && currX != 7) { // if either one is true stop checking

            // get the correct corresponding top right neighbor
            int bottom = currY+1;
            int right = currX+1;

            // calculate index for top right neighbor
            int BRindex = ((bottom-1) * 7) + (right-1);

            // get color and button for top right square
            String bottomRightColor = grid.getChildren().get(BRindex).getStyle();
            Button bottomRightB = (Button) grid.getChildren().get(BRindex);

            // check if color matches and if so, move to it
            if (currColor == bottomRightColor) { // if current color matches bottom left color

                // add to fourinrow stack
                fourInRow.add(bottomRightB, bottomRightColor, right, bottom);

                // set to new x and y and color
                currX = fourInRow.Top().getX();
                currY = fourInRow.Top().getY();
                currColor = fourInRow.Top().getColor();
            }
            else {
                break;
            }
        }
        return fourInRow;
    }

//-----------------------------------------------------------------------------------------

    // -> |
    private Boolean vertical(GridPane grid, BlockStack playerMove, BlockStack fourInRow) {

        // check for four in a row in bottom
        fourInRow = _checkBottom(grid, fourInRow, playerMove);

        if (fourInRow.length() > 3) { // base case
            return true;
        }
        else {
            fourInRow.clear();
            return false;
        }
    } // end of vertical

//-----------------------------------------------------------------------------------------

    private BlockStack _checkBottom(GridPane grid,
                                 BlockStack fourInRow,
                                 BlockStack playerMove) {

        int x = playerMove.Top().getX();
        int y = playerMove.Top().getY();
        String playerColor = playerMove.Top().getColor();


        // get current data
        int currX = x; //fourInRow.Top().getX();
        int currY = y; //fourInRow.Top().getY();
        String currColor = playerColor; //fourInRow.Top().getColor();

        while (currY != 6) { // if either one is true stop checking

            // get the correct corresponding top right neighbor
            int bottom = currY+1;

            // calculate index for top right neighbor
            // do -1 to respond to the initial counter val
            int Bindex = (((bottom-1) * 7) + (currX-1));

            // get color and button for top right square
            String bottomColor = grid.getChildren().get(Bindex).getStyle();
            Button bottomB = (Button) grid.getChildren().get(Bindex);

            // check if color matches and if so, move to it
            if (currColor == bottomColor) { // if top color matches top right

                // add to fourinrow stack
                fourInRow.add(bottomB, bottomColor, currX, bottom);

                // set to new x and y and color
                currX = fourInRow.Top().getX();
                currY = fourInRow.Top().getY();
                currColor = fourInRow.Top().getColor();
            }
            else{
                break;
            }
        }
        return fourInRow;
    }

//-----------------------------------------------------------------------------------------

    // -> --
    private Boolean horizontal(GridPane grid, BlockStack playerMove, BlockStack fourInRow) {

        // check for four in a row in left
        fourInRow = _checkLeft(grid, fourInRow, playerMove);

        // check for four in a row right
        fourInRow = _checkRight(grid, fourInRow, playerMove);

        if (fourInRow.length() > 3) { // base case
            return true;
        }
        else {
            fourInRow.clear();
            return false;
        }
    } // end of horizontal
//-----------------------------------------------------------------------------------------

    private BlockStack _checkLeft(GridPane grid,
                                    BlockStack fourInRow,
                                    BlockStack playerMove) {

        int x = playerMove.Top().getX();
        int y = playerMove.Top().getY();
        String playerColor = playerMove.Top().getColor();


        // get current data
        int currX = x; //fourInRow.Top().getX();
        int currY = y; //fourInRow.Top().getY();
        String currColor = playerColor; //fourInRow.Top().getColor();

        while (currX != 1 && currY != 1) { // if either one is true stop checking

            // get the correct corresponding top right neighbor
            int left = currX-1;

            // calculate index for left neighbor
            int Lindex = (((currY-1) * 7) + (left-1));

            // get color and button for top right square
            String leftColor = grid.getChildren().get(Lindex).getStyle();
            Button leftB = (Button) grid.getChildren().get(Lindex);

            // check if color matches and if so, move to it
            if (currColor == leftColor) { // if top color matches top right

                // add to fourinrow stack
                fourInRow.add(leftB, leftColor, left, currY);

                // set to new x and y and color
                currX = fourInRow.Top().getX();
                currY = fourInRow.Top().getY();
                currColor = fourInRow.Top().getColor();
            }
            else{
                break;
            }
        }
        return fourInRow;
    }
//-----------------------------------------------------------------------------------------

    private BlockStack _checkRight(GridPane grid,
                                  BlockStack fourInRow,
                                  BlockStack playerMove) {

        int x = playerMove.Top().getX();
        int y = playerMove.Top().getY();
        String playerColor = playerMove.Top().getColor();


        // get current data
        int currX = x; //fourInRow.Top().getX();
        int currY = y; //fourInRow.Top().getY();
        String currColor = playerColor; //fourInRow.Top().getColor();

        while (currX != 7) { // if either one is true stop checking

            // get the correct corresponding top right neighbor
            int right = currX+1;

            // calculate index for top right neighbor
            int Rindex = (((currY-1) * 7) + (right-1));

            // get color and button for top right square
            String rightColor = grid.getChildren().get(Rindex).getStyle();
            Button rightB = (Button) grid.getChildren().get(Rindex);

            // check if color matches and if so, move to it
            if (currColor == rightColor) { // if top color matches top right

                // add to fourinrow stack
                fourInRow.add(rightB, rightColor, right, currY);

                // set to new x and y and color
                currX = fourInRow.Top().getX();
                currY = fourInRow.Top().getY();
                currColor = fourInRow.Top().getColor();
            }
            else{
                break;
            }
        }
        return fourInRow;
    }

//-----------------------------------------------------------------------------------------

    private boolean _checkWinningMove(GridPane grid, BlockStack playerMove, BlockStack fourInRow) {

        if ((vertical(grid, playerMove, fourInRow)) // check below
            || (horizontal(grid, playerMove, fourInRow)) // check left and right
            || (leftDiag  (grid, playerMove, fourInRow)) // check top left, bottom right
            || ((rightDiag(grid, playerMove, fourInRow)))) { // check top right, bottom left

            return true;
        }
        else {
            fourInRow.clear();
            return false;
        }

    } // end of recursive check func
//-----------------------------------------------------------------------------------------

    public boolean checkWinningMove(GridPane grid, BlockStack playerMove) {

        //BlockStack tmpStack = new BlockStack(); // tracks 4 in a row

        fourInRow.add(playerMove.Top().getButton(), // add x, y, color and button to tmp stack
                playerMove.Top().getColor(),
                playerMove.Top().getX(),
                playerMove.Top().getY());

        // pass in 1 because the initial value is counted in the connect 4
        return _checkWinningMove(grid, playerMove, fourInRow); // length = 1 at start
    }





}
