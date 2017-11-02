package it.polimi.adaptanalyzertool.gui.architectureScreen;

import it.polimi.adaptanalyzertool.gui.utility.ControlledScreen;
import it.polimi.adaptanalyzertool.gui.utility.ScreensController;
import it.polimi.adaptanalyzertool.logic.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;


public class ArchitectureScreenController implements ControlledScreen {

    @FXML
    private Label architectureName;

    @FXML
    private TextField componentTextField;

    @FXML
    private TextField costTextField;

    @FXML
    private TextField availabilityTextField;

    @FXML
    private TextField executionsTextField;

    @FXML
    private ColorPicker componentColorPicker;

    @FXML
    private Label componentErrorLabel;

    @FXML
    private VBox componentsVBox;

    @FXML
    private Label componentNameLabel;

    @FXML
    private Label componentCostLabel;

    @FXML
    private Label componentAvailabilityLabel;

    @FXML
    private Label componentExecutionTimeLabel;

    @FXML
    private Rectangle componentColorRectangle;

    @FXML
    private Label componentColorLabel;

    @FXML
    private ComboBox serviceComboBox;

    @FXML
    private Label usedProbabilityLabel;

    @FXML
    private TextField usedProbabilityTextField;

    @FXML
    private Label numberOfExecutionLabel;

    @FXML
    private TextField numberofExecutionTextField;

    @FXML
    private TextField serviceTextField;

    @FXML
    private Label serviceErrorLabel;

    @FXML
    private Button serviceAddButton;

    @FXML
    public VBox servicesVBox;

    @FXML
    private Label serviceNameLabel;

    @FXML
    private Label serviceTypeLabel;

    @FXML
    private Label serviceUsedProbabilityLabel;

    @FXML
    private Label serviceNumberOfExecutionsLabel;

    @FXML
    private Label usedProbabilityDetailLabel;

    @FXML
    private Label numberOfExecutionsDetailLabel;


    private ScreensController myController;
    private Architecture architecture;
    private Component component;

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    @Override
    public void setScreenController(ScreensController screenParent) {
        this.myController = screenParent;
    }

    public void setUpScreen() {
        architectureName.setText("Architecture name: " + architecture.getName());
    }

    @FXML
    private void createNewComponet() {
        componentErrorLabel.setText("");
        String newComponentName = componentTextField.getText().trim();
        if (!"".equals(newComponentName)) {
            if (validateComponentInput()) {
                Component newComponent = new Component(newComponentName,
                        Double.parseDouble(costTextField.getText().trim()),
                        Double.parseDouble(availabilityTextField.getText().trim()),
                        Double.parseDouble(executionsTextField.getText().trim()),
                        componentColorPicker.getValue());
                architecture.addComponent(newComponent);
                componentErrorLabel.setText("Component Added!");
                updateComponentList();
                componentTextField.clear();
                costTextField.clear();
                availabilityTextField.clear();
                executionsTextField.clear();
            }
        } else {
            componentErrorLabel.setText("Empty name");
        }
    }

    private boolean validateComponentInput() {
        if (!costTextField.getText().trim().matches("(?:\\d*\\.)?\\d+") ||
                !availabilityTextField.getText().trim().matches("(?:\\d*\\.)?\\d+") ||
                !executionsTextField.getText().trim().matches("(?:\\d*\\.)?\\d+")) {
            componentErrorLabel.setText("Check the inputs for mistakes");
            return false;
        } else {
            componentErrorLabel.setText("");
            return true;
        }
    }

    private void updateComponentList() {
        HashMap<String, Component> components = architecture.getComponents();
        componentsVBox.getChildren().clear();
        for (Component component : components.values()) {
            Rectangle componentColor = new Rectangle(17, 17, component.getColor());
            componentColor.setStroke(Color.BLACK);
            Label componentName = new Label(component.getName());
            HBox componentHBox = new HBox(3, componentColor, componentName);
            componentHBox.setFocusTraversable(true);
            componentHBox.setId("componentHBox");
            componentHBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                componentHBox.requestFocus();
                showComponentDetail(component);
                this.component = component;
                this.serviceAddButton.setDisable(false);
            });
            componentsVBox.getChildren().add(componentHBox);
        }
    }

    private void showComponentDetail(Component component) {
        componentNameLabel.setText(component.getName());
        componentCostLabel.setText(String.valueOf(component.getCost()));
        componentAvailabilityLabel.setText(String.valueOf(component.getAvailability()));
        componentExecutionTimeLabel.setText(String.valueOf(component.getExecutionTime()));
        componentColorRectangle.setFill(component.getColor());
        componentColorRectangle.setVisible(true);
        componentColorLabel.setText(component.getColor().toString());
    }

    @FXML
    private void serviceComboBoxChanged() {
        switch (serviceComboBox.getValue().toString()) {
            case "Provided":
                usedProbabilityLabel.setDisable(true);
                usedProbabilityTextField.setDisable(true);
                numberOfExecutionLabel.setDisable(true);
                numberofExecutionTextField.setDisable(true);
                break;
            case "Required":
                usedProbabilityLabel.setDisable(false);
                usedProbabilityTextField.setDisable(false);
                numberOfExecutionLabel.setDisable(false);
                numberofExecutionTextField.setDisable(false);
                break;
        }
    }

    @FXML
    private void createNewService() {
        if (!"".equals(serviceTextField.getText().trim())) {
            switch (serviceComboBox.getValue().toString()) {
                case "Provided":
                    ProvidedService providedService = new ProvidedService(serviceTextField.getText().trim());
                    this.component.addProvidedService(providedService);
                    serviceErrorLabel.setText("Provided service added to " + component.getName());
                    break;
                case "Required":
                    if (validateServiceInput()) {
                        RequiredService requiredService = new RequiredService(serviceTextField.getText().trim(),
                                Double.parseDouble(usedProbabilityTextField.getText().trim()),
                                Integer.parseInt(numberofExecutionTextField.getText().trim()));
                        this.component.addRequiredService(requiredService);
                        serviceErrorLabel.setText("Required service added to " + component.getName());
                        usedProbabilityTextField.clear();
                        numberofExecutionTextField.clear();
                    }
                    break;
            }
            serviceTextField.clear();
            updateServicesList();
        } else {
            serviceErrorLabel.setText("Empty name");
        }
    }

    private boolean validateServiceInput() {
        if (!usedProbabilityTextField.getText().trim().matches("(?:\\d*\\.)?\\d+")
                || !numberofExecutionTextField.getText().trim().matches("(?:\\d*\\.)?\\d+")) {
            serviceErrorLabel.setText("Check the inputs for mistakes");
            return false;
        } else {
            serviceErrorLabel.setText("");
            return true;
        }
    }

    private void updateServicesList() {
        HashMap<String, AbstractService> componentServices = new HashMap<>();
        componentServices.putAll(component.getProvidedServices());
        componentServices.putAll(component.getRequiredServices());
        servicesVBox.getChildren().clear();
        HBox servicesHBox = new HBox(3);
        for (AbstractService service : componentServices.values()) {
            Label serviceLabel = new Label();
            if (service instanceof ProvidedService) {
                serviceLabel.setText("(Provided) " + service.getName());
            } else if (service instanceof RequiredService) {
                serviceLabel.setText("(Required) " + service.getName());
            }
            servicesHBox.getChildren().add(serviceLabel);
            servicesHBox.setFocusTraversable(true);
            servicesHBox.setId("serviceHBox");
            servicesVBox.getChildren().add(servicesHBox);
            servicesHBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                servicesHBox.requestFocus();
                showServiceDetail(service);
            });
        }
    }

    private void showServiceDetail(AbstractService service) {
        serviceNameLabel.setText(service.getName());
        if (service instanceof ProvidedService) {
            serviceTypeLabel.setText("Provided");
            numberOfExecutionsDetailLabel.setDisable(true);
            serviceNumberOfExecutionsLabel.setDisable(true);
            usedProbabilityDetailLabel.setDisable(true);
            serviceUsedProbabilityLabel.setDisable(true);
        } else if (service instanceof RequiredService) {
            serviceTypeLabel.setText("Required");
            serviceUsedProbabilityLabel.setText(String.valueOf(((RequiredService) service).getUsedProbability()));
            serviceNumberOfExecutionsLabel.setText(String.valueOf(((RequiredService) service).getNumberOfExecutions()));
            numberOfExecutionsDetailLabel.setDisable(false);
            serviceNumberOfExecutionsLabel.setDisable(false);
            usedProbabilityDetailLabel.setDisable(false);
            serviceUsedProbabilityLabel.setDisable(false);
        }
    }
}
