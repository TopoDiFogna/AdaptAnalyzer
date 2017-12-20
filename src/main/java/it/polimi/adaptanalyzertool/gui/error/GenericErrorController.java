package it.polimi.adaptanalyzertool.gui.error;

import it.polimi.adaptanalyzertool.gui.NewModalWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GenericErrorController extends NewModalWindowController {

    @FXML
    private Label genericErrorMessage;

    public void setErrorMessage(String errorMessage) {
        genericErrorMessage.setText(errorMessage);
    }

    @FXML
    private void okClicked() {
        this.getStage().close();
    }
}
