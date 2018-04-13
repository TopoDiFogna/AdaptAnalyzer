package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.model.Workflow;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class NewWorkflowWindowController extends NewModalWindowController {

    private Workflow workflow;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField workflowNameTextField;

    @FXML
    private void enterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            newWorkflowSubmitted();
        }
    }

    @FXML
    private void newWorkflowSubmitted() {
        String workflowName = workflowNameTextField.getText().trim();
        if (!workflowName.equals("")) {
            workflow = new Workflow(workflowName);
            stage.close();
        } else {
            workflowNameTextField.clear();
            errorLabel.setText("No name specified for the Workflow");
        }
    }

    Workflow getWorkflow() {
        return workflow;
    }
}
