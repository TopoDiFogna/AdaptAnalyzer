package it.polimi.adaptanalyzertool.gui.progress;

import it.polimi.adaptanalyzertool.gui.NewModalWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class GenericProgressWindowController extends NewModalWindowController {

    public ProgressIndicator progressIndicator;
    @FXML
    private Button okButton;
    @FXML
    private Label genericProgressMessage;

    public void okClicked() {
        this.getStage().close();
    }

    public void setProgressMessage(String genericProgressMessage) {
        this.genericProgressMessage.setText(genericProgressMessage);
    }

    public void enableOkButton() {
        this.okButton.setDisable(false);
    }

    public void setProgressIndicator(double progress) {
        progressIndicator.setProgress(progress);
    }
}
