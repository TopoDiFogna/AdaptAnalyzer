package it.polimi.adaptanalyzertool.gui.architectureScreen;

import it.polimi.adaptanalyzertool.gui.utility.ControlledScreen;
import it.polimi.adaptanalyzertool.gui.utility.ScreensController;
import it.polimi.adaptanalyzertool.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;


public class ArchitectureScreenController implements ControlledScreen {

    /*
            First column
         */
    @FXML
    private Label architectureName;
    /*
        New Component
     */
    @FXML
    private TextField componentTextField;
    @FXML
    private TextField componentCostTextField;
    @FXML
    private TextField componentAvailabilityTextField;
    @FXML
    private ColorPicker componentColorPicker;
    @FXML
    private Label componentErrorLabel;
    /*
        Component List
     */
    @FXML
    private VBox componentsVBox;
    /*
        Second column
     */
    /*
        Selected Component details
     */
    @FXML
    private Label componentNameLabel;
    @FXML
    private Label componentCostLabel;
    @FXML
    private Label componentAvailabilityLabel;
    @FXML
    private Rectangle componentColorRectangle;
    @FXML
    private Label componentColorLabel;
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
    @FXML
    private Button serviceAddButton;
    /*
        Services list
     */
    @FXML
    public VBox servicesVBox;
    /*
        Third column
     */
    /*
        Selected service details
     */
    @FXML
    private Label serviceNameLabel;
    @FXML
    private Label serviceTypeLabel;
    @FXML
    private Label executionTimeDetailLabel;
    @FXML
    private Label serviceUsedProbabilityLabel;
    @FXML
    private Label serviceNumberOfExecutionsLabel;
    @FXML
    private Label serviceExecutionTimeLabel;
    @FXML
    private Label usedProbabilityDetailLabel;
    @FXML
    private Label numberOfExecutionsDetailLabel;


    private ScreensController myController;
    private Architecture architecture;
    private Component component;
    private AbstractService service;

    private final String doubleRegex = "(?:\\d*\\.)?\\d+";

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
                        Double.parseDouble(componentCostTextField.getText().trim()),
                        Double.parseDouble(componentAvailabilityTextField.getText().trim()),
                        componentColorPicker.getValue());
                architecture.addComponent(newComponent);
                componentErrorLabel.setText("Component Added!");
                updateComponentList();
                if (component != null && newComponentName.equals(component.getName())) {
                    showComponentDetail(newComponent);
                }
                componentTextField.clear();
                componentCostTextField.clear();
                componentAvailabilityTextField.clear();
            }
        } else {
            componentErrorLabel.setText("Empty name");
        }
    }

    private boolean validateComponentInput() {
        if (!componentCostTextField.getText().trim().matches(doubleRegex) ||
                !componentAvailabilityTextField.getText().trim().matches(doubleRegex)) {
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
                updateServicesList();
            });
            componentsVBox.getChildren().add(componentHBox);
        }
    }

    private void showComponentDetail(Component component) {
        componentNameLabel.setText(component.getName());
        componentCostLabel.setText(String.valueOf(component.getCost()));
        componentAvailabilityLabel.setText(String.valueOf(component.getAvailability()));
        componentColorRectangle.setFill(component.getColor());
        componentColorRectangle.setVisible(true);
        componentColorLabel.setText(component.getColor().toString());
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

    @FXML
    private void createNewService() {
        AbstractService newService = null;
        String newServiceName = serviceTextField.getText().trim();
        if (!"".equals(newServiceName)) {
            switch (serviceComboBox.getValue().toString()) {
                case "Provided":
                    if (validateProvidedServiceInput()) {
                        newService = new ProvidedService(newServiceName,
                                Double.parseDouble(executionTimeTextField.getText().trim()));
                        this.component.addProvidedService((ProvidedService) newService);
                        serviceErrorLabel.setText("Provided service added to " + component.getName());
                        executionTimeTextField.clear();
                        serviceTextField.clear();
                    }
                    break;
                case "Required":
                    if (validateRequiredServiceInput()) {
                        newService = new RequiredService(newServiceName,
                                Double.parseDouble(usedProbabilityTextField.getText().trim()),
                                Integer.parseInt(numberOfExecutionTextField.getText().trim()));
                        this.component.addRequiredService((RequiredService) newService);
                        serviceErrorLabel.setText("Required service added to " + component.getName());
                        serviceTextField.clear();
                        usedProbabilityTextField.clear();
                        numberOfExecutionTextField.clear();
                    }
                    break;
            }
            updateServicesList();
            if (service != null && newServiceName.equals(service.getName())) {
                showServiceDetail(newService);
            }
        } else {
            serviceErrorLabel.setText("Empty name");
        }
    }

    private boolean validateProvidedServiceInput() {
        if (!executionTimeTextField.getText().trim().matches("(?:\\d*\\.)?\\d+")) {
            serviceErrorLabel.setText("Check the inputs for mistakes");
            return false;
        } else {
            serviceErrorLabel.setText("");
            return true;
        }
    }

    private boolean validateRequiredServiceInput() {
        if (!usedProbabilityTextField.getText().trim().matches("(?:\\d*\\.)?\\d+")
                || !numberOfExecutionTextField.getText().trim().matches("(?:\\d*\\.)?\\d+")) {
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
        for (AbstractService service : componentServices.values()) {
            HBox servicesHBox = new HBox(3);
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
        this.service = service;
        serviceNameLabel.setText(service.getName());
        if (service instanceof ProvidedService) {
            serviceTypeLabel.setText("Provided");
            serviceExecutionTimeLabel.setText(String.valueOf(((ProvidedService) service).getExecutionTime()));
            serviceUsedProbabilityLabel.setText("");
            serviceNumberOfExecutionsLabel.setText("");
            serviceExecutionTimeLabel.setDisable(false);
            executionTimeDetailLabel.setDisable(false);
            numberOfExecutionsDetailLabel.setDisable(true);
            serviceNumberOfExecutionsLabel.setDisable(true);
            usedProbabilityDetailLabel.setDisable(true);
            serviceUsedProbabilityLabel.setDisable(true);
        } else if (service instanceof RequiredService) {
            serviceTypeLabel.setText("Required");
            serviceUsedProbabilityLabel.setText(String.valueOf(((RequiredService) service).getUsedProbability()));
            serviceNumberOfExecutionsLabel.setText(String.valueOf(((RequiredService) service).getNumberOfExecutions()));
            serviceExecutionTimeLabel.setText("");
            executionTimeDetailLabel.setDisable(true);
            serviceExecutionTimeLabel.setDisable(true);
            numberOfExecutionsDetailLabel.setDisable(false);
            serviceNumberOfExecutionsLabel.setDisable(false);
            usedProbabilityDetailLabel.setDisable(false);
            serviceUsedProbabilityLabel.setDisable(false);
        }
    }
}
