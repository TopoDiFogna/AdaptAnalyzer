package it.polimi.adaptanalyzertool.gui.newarchitecturewindow;

import it.polimi.adaptanalyzertool.gui.utility.ControlledScreen;
import it.polimi.adaptanalyzertool.gui.utility.ScreensController;
import it.polimi.adaptanalyzertool.logic.Architecture;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewArchitectureWindowController implements Initializable, ControlledScreen {

    @FXML
    private Label errorLabel;

    @FXML
    private TextField architectureNameTextField;

    @FXML
    private Button submitButton;

    private ScreensController myController;
    private Architecture architecture;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void newArchitectureSubmitted() {
        if (!architectureNameTextField.getText().trim().equals("")) {
            architecture = new Architecture(architectureNameTextField.getText().trim());
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        } else {
            architectureNameTextField.clear();
            errorLabel.setText("No name specified for the architecture");
        }
    }

    public Architecture getArchitecture() {
        return architecture;
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.myController = screenParent;
    }
}
