import javafx.scene.control.Button;

import java.util.*; //importing the stack class

public class BlockStack {

    private Stack<block> blockStack = new Stack<block>(); // will hold instances of block

    class block {

        private boolean isValid;
        private String color;
        private Button button;
        private int x;
        private int y;
        private int length;
        private int width;
        private int buttonNum;

        block() {
            isValid = true;
            color = "white";
            length = 60;
            width = 60;
        }

        public String getColor() { return this.color; }
        public boolean isEmpty() { return this.isValid; }
        public int getLength() { return this.length; }
        public int getWidth() { return this.width; }
        public int getButtonNum() { return this.buttonNum; }
        public Button getButton() { return this.button; }
        public int getX() { return this.x; }
        public int getY() { return this.y; }

        public void setColor(String newColor) { this.color = newColor; }
        public void setValid(Boolean bool) { this.isValid = bool; }
        public void setLength(int newLength) { this.length = newLength; }
        public void setWidth(int newWidth) { this.width = newWidth; }

        public void clear() {
            this.isValid = true;
            //this.color = "white";
            this.length = 60;
            this.width = 60;
        }

    } // end of Block class

//----------------------------------------------------------------------------------

    // add func for loading empty valid stacks at start of game
    public void add(Button newButton, int xCor, int yCor, int counter) {
        block newBlock = new block();

        // keep track of each button (corresponding to their counter num) in the stack
        newBlock.button = newButton;

        // set x, y coordinates
        newBlock.x = xCor;
        newBlock.y = yCor;

        // unique number assigned to each button
        newBlock.buttonNum = counter;

        // true because the spaces added are empty
        newBlock.setValid(true);

        blockStack.push(newBlock); // track block in stack
    } // end of add func

//----------------------------------------------------------------------------------

    // add func for player move
    public void add(Button newButton, String newColor, int newX, int newY) {
        block newBlock = new block();

        // keep track of each button (corresponding to their counter num) in the stack
        newBlock.button = newButton;

        // assign specific color to block
        newBlock.color = newColor;

        // set x and y
        newBlock.x = newX;
        newBlock.y = newY;

        // block is no longer valid and is occupied
        newBlock.isValid = false;

        blockStack.push(newBlock);

    } // end of add func

//----------------------------------------------------------------------------------

    public void add(Button newButton, int xCor, int yCor, int counter, boolean valid) {
        block newBlock = new block();

        // keep track of each button (corresponding to their counter num) in the stack
        newBlock.button = newButton;

        // set x, y coordinates
        newBlock.x = xCor;
        newBlock.y = yCor;

        // unique number assigned to each button
        newBlock.buttonNum = counter;

        // valid = false since block is being added to player data
        newBlock.setValid(valid);

        blockStack.push(newBlock); // track block in stack
    }

//----------------------------------------------------------------------------------

    public void pop() {
        block topBlock = blockStack.peek(); // reference top block of stack
        topBlock.clear(); // call clear (set back to original state)

        blockStack.pop(); // remove block from stack

    }
//----------------------------------------------------------------------------------

    public boolean isEmpty() {
        if (this.blockStack.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

//----------------------------------------------------------------------------------

    public void clear() {
        while (this.blockStack.isEmpty() == false) {
            this.blockStack.pop(); // delete top
        }
    }

//----------------------------------------------------------------------------------

    public block Top(){
        return this.blockStack.peek();
    }

//----------------------------------------------------------------------------------

    public int length() { return this.blockStack.size(); }

//----------------------------------------------------------------------------------

} // end of block stack class
