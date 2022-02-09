package mines;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {


    @FXML
    private Button resetButton;

    @FXML
    private TextField heightT;

    @FXML
    private TextField widthT;

    @FXML
    private TextField minesT;

    
    public Button getReset() {
    	return resetButton;
    }
    
    public TextField height() {
    	return heightT;
    }
    
    public TextField width() {
    	return widthT;
    }
    public TextField mines() {
    	return minesT;
    }

}
