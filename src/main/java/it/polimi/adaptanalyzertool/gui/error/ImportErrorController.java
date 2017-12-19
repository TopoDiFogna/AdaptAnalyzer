package it.polimi.adaptanalyzertool.gui.error;

import it.polimi.adaptanalyzertool.gui.NewModalWindowController;
import javafx.fxml.FXML;

public class ImportErrorController extends NewModalWindowController {

    @FXML
    private void okClicked() {
        this.getStage().close();
    }
}
