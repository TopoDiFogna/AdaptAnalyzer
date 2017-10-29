package it.polimi.adaptanalyzertool.gui.newarchitecturewindow;

import it.polimi.adaptanalyzertool.logic.Architecture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewArchitectureWindowController {

    @FXML
    TextField architectureName;

    @FXML
    public void newArchitectureSubmitted(ActionEvent actionEvent) {
        Architecture architecture = new Architecture(architectureName.getText());
    }
}
