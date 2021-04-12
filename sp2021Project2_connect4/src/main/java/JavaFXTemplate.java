import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;

public class JavaFXTemplate extends Application {

	Stage window;
	Scene scene1, scene2, scene3;

	private Button playButton;
	private BorderPane border;
	private TextArea t1;




	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		// initialize primary stage
		window = primaryStage;

		// initialize primary stage button and label text
//		playButton = new Button("Play");
//		playButton.setAlignment(Pos.CENTER);
//
//
//

//
//		VBox layout1 = new VBox(20);
//		layout1.getChildren().addAll(t1, playButton);

		// create instance of game and implement class functions into scenes
		Game connect4 = new Game(); //.createGameScene();
		scene1 = connect4.startScene();
		scene2 = connect4.gameScene();
		scene3 = connect4.endScene();

		playButton = connect4.getPlayButton();

		playButton.setOnAction(e-> {
			window.setTitle("Connect Four - Game");
			window.setScene(scene2);
		});



		window.setScene(scene1);
		window.setTitle("Connect Four - Main Menu");
		window.show();
	}


}




