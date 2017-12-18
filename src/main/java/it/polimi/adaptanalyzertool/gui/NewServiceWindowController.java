package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.model.AbstractService;
import it.polimi.adaptanalyzertool.model.ProvidedService;
import it.polimi.adaptanalyzertool.model.RequiredService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NewServiceWindowController extends NewModalWindowController {

    /*
    New service
    */
    @FXML
    private TextField serviceTextField;
    @FXML
    private ComboBox serviceComboBox;
    @FXML
    private Label executionTimeLabel;
    @FXML
    private TextField executionTimeTextField;
    @FXML
    private Label usedProbabilityLabel;
    @FXML
    private TextField usedProbabilityTextField;
    @FXML
    private Label numberOfExecutionLabel;
    @FXML
    private TextField numberOfExecutionTextField;
    @FXML
    private Label serviceErrorLabel;

    private AbstractService newService;
    private final String doubleRegex = "(?:\\d*\\.)?\\d+";

    @FXML
    private void saveService() {
        String newServiceName = serviceTextField.getText().trim();
        if (!"".equals(newServiceName)) {
            switch (serviceComboBox.getValue().toString()) {
                case "Provided":
                    if (validateProvidedServiceInput()) {
                        newService = new ProvidedService(newServiceName,
                                Double.parseDouble(executionTimeTextField.getText().trim()));
                        stage.close();
                    }
                    break;
                case "Required":
                    if (validateRequiredServiceInput()) {
                        newService = new RequiredService(newServiceName,
                                Double.parseDouble(usedProbabilityTextField.getText().trim()),
                                Integer.parseInt(numberOfExecutionTextField.getText().trim()));
                        stage.close();
                    }
                    break;
            }
        } else {
            serviceErrorLabel.setText("Empty name");
        }
    }


    private boolean validateProvidedServiceInput() {
        if (!executionTimeTextField.getText().trim().matches(doubleRegex)) {
            serviceErrorLabel.setText("Check the inputs for mistakes");
            return false;
        } else {
            serviceErrorLabel.setText("");
            return true;
        }
    }

    private boolean validateRequiredServiceInput() {
        if (!usedProbabilityTextField.getText().trim().matches(doubleRegex)
                || !numberOfExecutionTextField.getText().trim().matches(doubleRegex)) {
            serviceErrorLabel.setText("Check the inputs for mistakes");
            return false;
        } else {
            serviceErrorLabel.setText("");
            return true;
        }
    }

    @FXML
    private void serviceComboBoxChanged() {
        switch (serviceComboBox.getValue().toString()) {
            case "Provided":
                executionTimeLabel.setDisable(false);
                executionTimeTextField.setDisable(false);
                usedProbabilityLabel.setDisable(true);
                usedProbabilityTextField.setDisable(true);
                numberOfExecutionLabel.setDisable(true);
                numberOfExecutionTextField.setDisable(true);
                break;
            case "Required":
                executionTimeLabel.setDisable(true);
                executionTimeTextField.setDisable(true);
                usedProbabilityLabel.setDisable(false);
                usedProbabilityTextField.setDisable(false);
                numberOfExecutionLabel.setDisable(false);
                numberOfExecutionTextField.setDisable(false);
                break;
        }
    }

    public AbstractService getNewService() {
        return newService;
    }
}
