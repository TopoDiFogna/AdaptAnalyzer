package it.polimi.adaptanalyzertool.gui.error;

import it.polimi.adaptanalyzertool.gui.NewModalWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * <p>The controller associated with the generic error window.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class GenericErrorController extends NewModalWindowController {

    @FXML
    private Label genericErrorMessage;

    void setErrorMessage(String errorMessage) {
        genericErrorMessage.setText(errorMessage);
    }

    @FXML
    private void okClicked() {
        this.getStage().close();
    }
}
