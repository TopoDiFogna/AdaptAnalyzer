package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.utility.ChildScreenController;
import it.polimi.adaptanalyzertool.gui.utility.ScreenController;
import it.polimi.adaptanalyzertool.metrics.AdaptabilityMetrics;
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
import javafx.scene.input.MouseButton;
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
    private final String ninetynineRegex = "^0?\\.\\d+";
    private final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    private VBox servicesVBox;
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
    private ContextMenu componentContextMenu;
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
    private CheckBox componentUsedCheckBox;
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
    private Label inActionLabel;
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
    private ContextMenu serviceContextMenu;
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
    private Label absoluteAdaptabilityLabel;
    @FXML
    private Label relativeAdaptabilityLabel;
    @FXML
    private Label serviceMetricsErrorLabel;
    /*
        Architecture Metrics
     */
    @FXML
    private Label globalAvailabilityLabel;
    @FXML
    private Label globalAvailabilitySuitabilityLabel;
    @FXML
    private Label globalCostLabel;
    @FXML
    private Label globalCostSuitabilityLabel;
    @FXML
    private Label meanAbsoluteAdaptabilityLabel;
    @FXML
    private Label meanRelativeAdaptabilityLabel;
    @FXML
    private Label levelSystemAdaptabilityLabel;
    @FXML
    private Label architectureMetricsErrorLabel;

    private Architecture architecture;
    private HashMap<String, Component> architectureComponents;
    private Component selectedComponent;
    private AbstractService selectedService;
    private ScreenController parent;

    @FXML
    private void initialize() {
        //Adds listener to the component choice box to update the services displayed
        componentChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedComponent = architectureComponents.get(newValue);
                updateServicesList();
            }
        });

        //Sets the rounding mode of the DecimalFormat used in metrics
        df.setRoundingMode(RoundingMode.DOWN);

        //Creates the context menu used to manipulate component and services
        componentContextMenu = new ContextMenu();
        MenuItem componentRemoveMenuItem = new MenuItem("Remove");
        componentRemoveMenuItem.setOnAction(event -> {
            architecture.removeComponent(selectedComponent);
            selectedComponent = null;
            updateComponentList();
            clearComponentMetrics();
            clearComponentDetail();
        });
        componentContextMenu.getItems().add(componentRemoveMenuItem);

        serviceContextMenu = new ContextMenu();
        MenuItem serviceRemoveMenuItem = new MenuItem("Remove");
        serviceRemoveMenuItem.setOnAction(event -> {
            selectedComponent.removeService(selectedService);
            clearServiceDetails();
            selectedService = null;
            updateServicesList();
        });
        serviceContextMenu.getItems().add(serviceRemoveMenuItem);
    }

    void setUpScreen() {
        architectureComponents = architecture.getComponents();
        architectureName.setText("Architecture name: " + architecture.getName());
        tabPane.getSelectionModel().select(componentsTab);
        updateComponentList();
    }

    @FXML
    private void createNewComponent() {
        try {
            showNewModifyComponent(false);
        } catch (IOException e) {
            System.err.println("Error loading internal resource: newcomponentwindow/newComponentWindow.fxml");
        }
    }

    @FXML
    private void modifyComponent() {
        try {
            showNewModifyComponent(true);
        } catch (IOException e) {
            System.err.println("Error loading internal resource: newcomponentwindow/newComponentWindow.fxml");
        }
    }

    private void showNewModifyComponent(boolean modify) throws IOException {
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


        if (modify) {
            controller.setNewComponent(selectedComponent);
            controller.initializeFields();

        }
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
                MouseButton mb = event.getButton();
                componentHBox.requestFocus();
                switch (mb) {
                    case PRIMARY:
                        showComponentDetail(component);
                        if (this.selectedComponent != component) {
                            this.selectedComponent = component;
                            clearComponentMetrics();
                        }
                        break;
                    case SECONDARY:
                        this.selectedComponent = component;
                        componentContextMenu.show(componentHBox, event.getScreenX(), event.getScreenY());
                        break;
                }
            });

            componentsVBox.getChildren().add(componentHBox);
        }
    }

    private void showComponentDetail(Component component) {
        componentNameLabel.setText(component.getName());
        componentCostLabel.setText(String.valueOf(component.getCost()));
        componentAvailabilityLabel.setText(String.valueOf(component.getAvailability()));
        componentUsedCheckBox.setSelected(component.isUsed());
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

    private void clearComponentDetail() {
        componentNameLabel.setText("");
        componentCostLabel.setText("");
        componentAvailabilityLabel.setText("");
        componentColorRectangle.setVisible(false);
        componentColorLabel.setText("");
    }

    @FXML
    private void showComponentServices() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(servicesTab);
    }

    @FXML
    private void onChangedTab() {
        componentChoiceBox.setItems(FXCollections.observableArrayList(architectureComponents.keySet()));
        if (!architectureComponents.values().isEmpty() && selectedComponent != null) {
            componentChoiceBox.setValue(selectedComponent.getName());
            this.serviceAddButton.setDisable(false);
        }
    }

    @FXML
    private void createNewService() {
        Stage newServiceStage = new Stage();
        newServiceStage.setTitle("New Service");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newservicewindow/newServiceWindow.fxml"));

        try {
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
                this.selectedService = newService;
                updateServicesList();
                showServiceDetail(selectedService);
            }
        } catch (IOException e) {
            System.err.println("Error loading internal resource: newservicewindow/newServiceWindow.fxml");
        }
    }

    @FXML
    private void calculateComponentMetrics() {
        componentMetricsErrorLabel.setText("");
        if (selectedComponent != null) {
            String sta = systemTargetAvailabilityTextField.getText().trim();
            String stc = systemTargetCostTextField.getText().trim();
            if (!sta.equals("") && sta.matches(ninetynineRegex) && !stc.equals("") && stc.matches(doubleRegex)) {
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
//                double ia = ComponentMetrics.InAction(architecture, ,selectedComponent);
//                inActionLabel.setText(df.format(ia)); TODO inAction
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
                MouseButton mb = event.getButton();
                servicesHBox.requestFocus();
                this.selectedService = service;
                switch (mb) {
                    case PRIMARY:
                        showServiceDetail(selectedService);
                        break;
                    case SECONDARY:
                        serviceContextMenu.show(servicesHBox, event.getScreenX(), event.getScreenY());
                }
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
            serviceNumberOfExecutionsLabel.setText(String.valueOf(((RequiredService) service).getNumberOfExecutionsPerCall()));
            serviceExecutionTimeLabel.setText("");
            executionTimeDetailLabel.setDisable(true);
            serviceExecutionTimeLabel.setDisable(true);
            numberOfExecutionsDetailLabel.setDisable(false);
            serviceNumberOfExecutionsLabel.setDisable(false);
            usedProbabilityDetailLabel.setDisable(false);
            serviceUsedProbabilityLabel.setDisable(false);
        }
    }

    private void clearServiceDetails() {
        serviceNameLabel.setText("");
        serviceTypeLabel.setText("");
        serviceExecutionTimeLabel.setText("");
        serviceUsedProbabilityLabel.setText("");
        serviceNumberOfExecutionsLabel.setText("");

    }

    @FXML
    private void calculateServiceMetrics() {
        serviceMetricsErrorLabel.setText("");
        if (selectedService != null) {
            double noe = ServicesMetrics.NumberOfExecutions(architecture, selectedService);
            double ptbr = ServicesMetrics.ProbabilityToBeRunning(architecture, selectedService);
            double aas = ServicesMetrics.AbsoluteAdaptability(architecture, selectedService);
            double ras = ServicesMetrics.RelativeAdaptability(architecture, selectedService);
            numberOfExecutionsLabel.setText(df.format(noe));
            probabilityToBeRunningLabel.setText(df.format(ptbr));
            absoluteAdaptabilityLabel.setText(df.format(aas));
            relativeAdaptabilityLabel.setText(df.format(ras));
        } else {
            serviceMetricsErrorLabel.setText("Select a service first");
        }
    }

    @FXML
    private void calculateArchitectureMetrics() {
        String sta = systemTargetAvailabilityTextField.getText().trim();
        String stc = systemTargetCostTextField.getText().trim();
        if (!sta.equals("") && sta.matches(ninetynineRegex) && !stc.equals("") && stc.matches(doubleRegex)) {
            architectureMetricsErrorLabel.setText("");
            double gas = ArchitectureMetrics.GlobalAvailabilitySystem(architecture, Double.valueOf(sta));
            double gcs = ArchitectureMetrics.GlobalCostSystem(architecture, Double.valueOf(stc));
            globalAvailabilityLabel.setText(df.format(gas));
            if (ArchitectureMetrics.suitableForAvailability(architecture, Double.valueOf(sta))) {
                globalAvailabilitySuitabilityLabel.setText("Suitable");
            } else {
                globalAvailabilitySuitabilityLabel.setText("Not Suitable");
            }
            globalCostLabel.setText(df.format(gcs));
            if (ArchitectureMetrics.suitableForCost(architecture, Double.valueOf(stc))) {
                globalCostSuitabilityLabel.setText("Suitable");
            } else {
                globalCostSuitabilityLabel.setText("Not Suitable");
            }
        } else {
            architectureMetricsErrorLabel.setText("Check input for mistakes");
        }
        double maas = AdaptabilityMetrics.MeanAbsoluteAdaptability(architecture);
        double raas = AdaptabilityMetrics.MeanRelativeAdaptability(architecture);
        double lsa = AdaptabilityMetrics.LevelSystemAdaptability(architecture);
        meanAbsoluteAdaptabilityLabel.setText(df.format(maas));
        meanRelativeAdaptabilityLabel.setText(df.format(raas));
        levelSystemAdaptabilityLabel.setText(df.format(lsa));
    }

    @Override
    public void setParentScreen(ScreenController screen) {
        this.parent = screen;
    }

    public Architecture getArchitecture() {
        return architecture;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }


}
