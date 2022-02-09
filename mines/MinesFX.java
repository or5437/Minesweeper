package mines;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//MinesFX class - implements the graphics of the game.
public class MinesFX extends Application implements EventHandler<MouseEvent> {
	private Mines board;
	private int width = 10, height = 10, mines = 10;
	private Stage stage;
	private HBox hbox;
	private Controller controller;
	private Button reset;
	private GridPane pane;

	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();//
			loader.setLocation(getClass().getResource("Game.fxml"));// load Game.fxml
			hbox = loader.load();
			this.stage = stage;
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller.height().setText(String.valueOf(height));
		controller.width().setText(String.valueOf(width));
		controller.mines().setText(String.valueOf(mines));
		stage.setTitle("Minesweeper Game");// set title to game
		Scene scene = new Scene(hbox);
		//scene.setFill(new Paint())
		stage.setScene(scene);
		board = new Mines(width, height, mines);// define board
		pane = getGrid();// define new grid
		hbox.getChildren().add(pane);// add the grid
		hbox.setStyle("-fx-background-color:#999999;-fx-font-size:12px;");
		reset = controller.getReset();
		reset.setOnMouseClicked(this);// define click on reset button
		stage.show();// show the stage
	}

	public static void main(String[] args) {
		launch(args);
	}

	GridPane getGrid() { // Create the grid to game.
		GridPane grid = new GridPane(); // create grid
		Button button; // define button variable
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				button = new GetButton(i, j, board.get(i, j));// Create new button with values
				button.setOnMouseClicked(this);
				button.setPrefSize(40, 40); // define the size of the buttons
				button.setAlignment(Pos.CENTER); // set in the center
				button.setText(board.get(i, j)); // set the text in the button
				ImageView v = new ImageView();
				button.setGraphic(v);
				grid.add(button, i, j); // add button to the grid
			}
		}
		grid.setAlignment(Pos.CENTER);// set in the center
		return grid; // return the grid
	}

	private class GetButton extends Button {
		private int x, y; // save the place of button.

		public GetButton(int x, int y, String supString) { // GetButton constructor.
			super(supString); // get the value of this button.
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

	}

	// return the grid of buttons
	@Override
	public void handle(MouseEvent event) {
		// ImageFX image=new ImageFX();
		if (event.getSource() instanceof GetButton) { // if the event is instance of GetButton.
			GetButton b = (GetButton) event.getSource(); // get the source of the button.
			if (event.getButton() == MouseButton.PRIMARY) { // the left button of the mouse.
				if (board.open(b.getX(), b.getY()) == false) { // if this place is a mine.
					board.setShowAll(true); // setShowAll get true value.
					sendMsg("You lost the game!"); // send message: lose.
				} else if (board.isDone()) { // if the Game finished.
					board.setShowAll(true);
					sendMsg("You win the game!"); // send message: win.
				}
			} else if (event.getButton() == MouseButton.SECONDARY)// the right button of the mouse
				System.out.println("s");
			board.toggleFlag(b.getX(), b.getY());// enter the flag
			updateBoard(); // update the board
		} else {
			resetGame(); // reset the game
		}
	}

	// send message
	void sendMsg(String s) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Minesweeper Game");
		alert.setHeaderText(null);
		alert.setContentText(s);
		alert.showAndWait();
	}

	void updateBoard() {
		pane.getChildren().clear();// clear the grid
		pane = getGrid(); // define new grid
		hbox.getChildren().add(pane);// add the grid
	}

	void resetGame() {
		width = Integer.parseInt(controller.width().getText()); // get amount of width.
		height = Integer.parseInt(controller.height().getText()); // get amount of height.
		mines = Integer.parseInt(controller.mines().getText()); // get amount of mines.
		if (mines >= width * height) {
			sendMsg("Too much mines!"); // appropriate message.
		} else {
			start(stage);
		}
	}
}
