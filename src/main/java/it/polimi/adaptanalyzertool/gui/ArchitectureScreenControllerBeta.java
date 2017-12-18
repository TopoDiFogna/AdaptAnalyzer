package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.utility.ChildScreenController;
import it.polimi.adaptanalyzertool.gui.utility.ScreenController;
import it.polimi.adaptanalyzertool.metrics.ArchitectureMetrics;
import it.polimi.adaptanalyzertool.metrics.ComponentMetrics;
import it.polimi.adaptanalyzertool.metrics.ServicesMetrics;
import it.polimi.adaptanalyzertool.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

public class ArchitectureScreenControllerBeta implements ChildScreenController {

    private final String doubleRegex = "(?:\\d*\\.)?\\d+"; //TODO make this for double and 99 notation
    private final DecimalFormat df = new DecimalFormat("0.00");
    @FXML
    public VBox servicesVBox;
    @FXML
    private Label architectureName;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab componentsTab;
    @FXML
    private Tab servicesTab;
    /*
        Component List
     */
    @FXML
    private VBox componentsVBox;
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
    @FXML
    private Button showComponentServicesButton;
    @FXML
    private Label fitnessRatioAvailabilityLabel;
    @FXML
    private Label booleanSuitabilityAvailabilityLabel;
    @FXML
    private Label fitnessRatioCostLabel;
    @FXML
    private Label booleanSuitabilityCostLabel;
    @FXML
    private Label weightResidenceTimeLabel;
    @FXML
    private TextField systemTargetAvailabilityTextField;
    @FXML
    private TextField systemTargetCostTextField;
    @FXML
    private Label componentMetricsErrorLabel;
    /*
        Services list
     */
    @FXML
    private ChoiceBox<String> componentChoiceBox;
    @FXML
    private Button serviceAddButton;
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
    @FXML
    private Label numberOfExecutionsLabel;
    @FXML
    private Label probabilityToBeRunningLabel;
    @FXML
    private Label inActionLabel;
    @FXML
    private Label serviceMetricsErrorLabel;
    /*
        Architecture Metrics
     */
    @FXML
    private Label globalAvailabilityLabel;
    @FXML
    private Label globalCostLabel;
    @FXML
    private Label architectureMetricsErrorLabel;

    private Architecture architecture;
    private HashMap<String, Component> architectureComponents;
    private Component selectedComponent;
    private AbstractService selectedService;
    private ScreenController parent;

    @FXML
    public void initialize() {
        componentChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedComponent = architectureComponents.get(newValue);
                updateServicesList();
            }
        });
        df.setRoundingMode(RoundingMode.DOWN);
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public void setUpScreen() {
        architectureName.setText("Architecture name: " + architecture.getName());
    }

    @FXML
    private void createNewComponent() throws IOException {
        Stage newComponentStage = new Stage();
        newComponentStage.setTitle("New Component");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newcomponentwindow/newComponentWindow.fxml"));

        Parent root = loader.load();
        NewComponentWindowController controller = loader.getController();
        controller.setStage(newComponentStage);

        Scene scene = new Scene(root);
        newComponentStage.setScene(scene);
        newComponentStage.initOwner(parent.getScene().getWindow());
        newComponentStage.setResizable(false);
        newComponentStage.initModality(Modality.WINDOW_MODAL);
        newComponentStage.showAndWait();

        Component newComponent = controller.getNewComponent();
        if (newComponent != null) {
            this.selectedComponent = newComponent;
            architecture.addComponent(newComponent);
            updateComponentList();
            showComponentDetail(selectedComponent);
        }
    }


    private void updateComponentList() {
        architectureComponents = architecture.getComponents();
        componentsVBox.getChildren().clear();
        for (Component component : architectureComponents.values()) {
            Rectangle componentColor = new Rectangle(17, 17, component.getColor());
            componentColor.setStroke(Color.BLACK);
            Label componentName = new Label(component.getName());
            HBox componentHBox = new HBox(3, componentColor, componentName);
            componentHBox.setFocusTraversable(true);
            componentHBox.setId("componentHBox");
            componentHBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                componentHBox.requestFocus();
                showComponentDetail(component);
                if (this.selectedComponent != component) {
                    this.selectedComponent = component;
                    clearComponentMetrics();
                }
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
        showComponentServicesButton.setDisable(false);
    }

    private void clearComponentMetrics() {
        fitnessRatioAvailabilityLabel.setText("NaN");
        booleanSuitabilityAvailabilityLabel.setText("NaN");
        fitnessRatioCostLabel.setText("NaN");
        booleanSuitabilityCostLabel.setText("NaN");
        weightResidenceTimeLabel.setText("NaN");
    }

    @FXML
    private void showComponentServices() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(servicesTab);
    }

    @FXML
    private void onChangedTab() {
        if (architectureComponents != null) {
            componentChoiceBox.setItems(FXCollections.observableArrayList(architectureComponents.keySet()));
            componentChoiceBox.setValue(selectedComponent.getName());

            this.serviceAddButton.setDisable(false);
        }
    }


    @FXML
    private void createNewService() throws IOException {
        Stage newServiceStage = new Stage();
        newServiceStage.setTitle("New Service");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newservicewindow/newServiceWindow.fxml"));

        Parent root = loader.load();
        NewServiceWindowController controller = loader.getController();
        controller.setStage(newServiceStage);

        Scene scene = new Scene(root);
        newServiceStage.setScene(scene);
        newServiceStage.initOwner(parent.getScene().getWindow());
        newServiceStage.setResizable(false);
        newServiceStage.initModality(Modality.WINDOW_MODAL);
        newServiceStage.showAndWait();

        AbstractService newService = controller.getNewService();
        if (newService != null) {
            if (newService instanceof ProvidedService) {
                this.selectedComponent.addProvidedService((ProvidedService) newService);
            } else if (newService instanceof RequiredService) {
                this.selectedComponent.addRequiredService((RequiredService) newService);
            }
        }
        this.selectedService = newService;
        updateServicesList();
        showServiceDetail(selectedService);
    }

    @FXML
    private void calculateComponentMetrics() {
        componentMetricsErrorLabel.setText("");
        if (selectedComponent != null) {
            String sta = systemTargetAvailabilityTextField.getText().trim();
            String stc = systemTargetCostTextField.getText().trim();
            if (!sta.equals("") && sta.matches(doubleRegex) && !stc.equals("") && stc.matches(doubleRegex)) {
                double fra = ComponentMetrics.FitnessRatioAvailability(Double.valueOf(sta), selectedComponent.getAvailability());
                double frc = ComponentMetrics.FitnessRatioCost(Double.valueOf(stc), selectedComponent.getCost());
                fitnessRatioAvailabilityLabel.setText(df.format(fra));
                fitnessRatioCostLabel.setText(df.format(frc));
                if (ComponentMetrics.BooleanSuitabilityAvailability(fra)) {
                    booleanSuitabilityAvailabilityLabel.setText("Suitable");
                } else {
                    booleanSuitabilityAvailabilityLabel.setText("Not Suitable");
                }
                if (ComponentMetrics.BooleanSuitabilityCost(frc)) {
                    booleanSuitabilityCostLabel.setText("Suitable");
                } else {
                    booleanSuitabilityCostLabel.setText("Not Suitable");
                }
                double wrt = ComponentMetrics.WeightResidenceTime(architecture, selectedComponent);
                weightResidenceTimeLabel.setText(df.format(wrt));
            } else {
                componentMetricsErrorLabel.setText("Check input for mistakes");
            }
        } else {
            componentMetricsErrorLabel.setText("Select a component first");
        }
    }

    private void updateServicesList() {
        HashMap<String, AbstractService> componentServices = new HashMap<>();
        componentServices.putAll(selectedComponent.getProvidedServices());
        componentServices.putAll(selectedComponent.getRequiredServices());
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

    @FXML
    private void calculateServiceMetrics() {
        serviceMetricsErrorLabel.setText("");
        if (selectedService != null) {
            double noe = ServicesMetrics.NumberOfExecutions(architecture, selectedService);
            double ptbr = ServicesMetrics.ProbabilityToBeRunning(architecture, selectedService);
            double ia = ServicesMetrics.InAction();
            numberOfExecutionsLabel.setText(df.format(noe));
            probabilityToBeRunningLabel.setText(df.format(ptbr));
            inActionLabel.setText(df.format(ia));
        } else {
            serviceMetricsErrorLabel.setText("Select a service first");
        }
    }

    @FXML
    private void calculateArchitectureMetrics() {
        String sta = systemTargetAvailabilityTextField.getText().trim();
        String stc = systemTargetCostTextField.getText().trim();
        if (!sta.equals("") && sta.matches(doubleRegex) && !stc.equals("") && stc.matches(doubleRegex)) {
            double gas = ArchitectureMetrics.GlobalAvailabilitySystem(architecture, Double.valueOf(sta));
            double gcs = ArchitectureMetrics.GlobalCostSystem(architecture, Double.valueOf(stc));
            globalAvailabilityLabel.setText(df.format(gas));
            globalCostLabel.setText(df.format(gcs));
        } else {
            architectureMetricsErrorLabel.setText("Check input for mistakes");
        }
    }

    @Override
    public void setParentScreen(ScreenController screen) {
        this.parent = screen;
    }
}
