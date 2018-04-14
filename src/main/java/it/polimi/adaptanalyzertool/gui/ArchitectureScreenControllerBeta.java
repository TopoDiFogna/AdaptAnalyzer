package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.error.ErrorWindow;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * <p>
 * This class represents the controller for the architecture controller screen. It contains all the method to
 * manipulate the architecture and show all the details.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class ArchitectureScreenControllerBeta implements ChildScreenController {

    private static final String DOUBLE_REGEX = "(?:\\d*\\.)?\\d+";
    private static final String NINETYNINE_REGEX = "^0?\\.\\d+";
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
    @FXML
    private Button modifyComponentButton;
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
        Workflow List
     */
    @FXML
    private VBox workflowsVBox;
    private ContextMenu workflowContextMenu;
    /*
        Paths
     */
    @FXML
    private Button addPathButton;
    @FXML
    private VBox pathsVBox;
    private ContextMenu pathContextMenu;
    @FXML
    private Label pathExecutionProbabilityLabel;
    @FXML
    private Button addMessageButton;
    /*
        Messages Scheme
     */
    @FXML
    private GridPane messagesGridPane;
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
    private Workflow selectedWorkflow;
    private Path selectedPath;
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
            updateServicesList();
            modifyComponentButton.setDisable(true);
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

        workflowContextMenu = new ContextMenu();
        MenuItem workflowRemoveMenuItem = new MenuItem("Remove");
        workflowRemoveMenuItem.setOnAction(event -> {
            architecture.removeWorkflow(selectedWorkflow);
            selectedWorkflow = null;
            updatePathList();
            clearPathDetails();
            addPathButton.setDisable(true);
        });
        workflowContextMenu.getItems().add(workflowRemoveMenuItem);

        pathContextMenu = new ContextMenu();
        MenuItem pathRemoveMenuItem = new MenuItem("Remove");
        pathRemoveMenuItem.setOnAction(event -> {
            selectedWorkflow.removePath(selectedPath);
            clearPathDetails();
            selectedPath = null;
            updatePathList();
            addMessageButton.setDisable(true);
        });
        pathContextMenu.getItems().add(pathRemoveMenuItem);
    }

    void setUpScreen() {
        architectureComponents = architecture.getComponents();
        architectureName.setText("Architecture name: " + architecture.getName());
        tabPane.getSelectionModel().select(componentsTab);
        selectedComponent = null;
        selectedService = null;
        selectedWorkflow = null;
        selectedPath = null;
        clearComponentDetail();
        clearServiceDetails();
        clearPathDetails();
        showComponentServicesButton.setDisable(true);
        updateComponentList();
        updateServicesList();
        updateWorkflowList();
        updatePathList();
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
                modifyComponentButton.setDisable(false);
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
            if (!sta.equals("") && sta.matches(NINETYNINE_REGEX) && !stc.equals("") && stc.matches(DOUBLE_REGEX)) {
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
                if (selectedWorkflow != null) {
                    double ia = ComponentMetrics.InAction(architecture, selectedWorkflow, selectedComponent);
                    inActionLabel.setText(df.format(ia));
                } else {
                    componentMetricsErrorLabel.setText("No workflow selected");
                }
            } else {
                componentMetricsErrorLabel.setText("Check input for mistakes");
            }
        } else {
            componentMetricsErrorLabel.setText("Select a component first");
        }
    }

    private void updateServicesList() {
        if (selectedComponent != null) {
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
                            break;
                    }
                });
            }
        } else {
            servicesVBox.getChildren().clear();
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
        if (!sta.equals("") && sta.matches(NINETYNINE_REGEX) && !stc.equals("") && stc.matches(DOUBLE_REGEX)) {
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

    @FXML
    private void createNewWorkflow() {
        Stage stage = new Stage();
        stage.setTitle("New Workflow");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newworkflowwindow/newWorkflowWindow.fxml"));

        try {
            Parent root = loader.load();
            NewWorkflowWindowController controller = loader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(parent.getScene().getWindow());
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

            Workflow newWorkflow = controller.getWorkflow();
            if (newWorkflow != null) {
                this.selectedWorkflow = newWorkflow;
                architecture.addWorkflow(selectedWorkflow);
                addPathButton.setDisable(false);
                updateWorkflowList();
                updatePathList();
            }
        } catch (IOException e) {
            System.err.println("Error loading internal resource: newworkflowwindow/newWorkflowWindow.fxml");
        }
    }

    private void updateWorkflowList() {
        workflowsVBox.getChildren().clear();
        for (Workflow workflow : architecture.getWorkflows().values()) {
            HBox workflowsHBox = new HBox(3);
            Label workflowLabel = new Label(workflow.getName());
            workflowsHBox.getChildren().add(workflowLabel);
            workflowsHBox.setFocusTraversable(true);
            workflowsHBox.setId("workflowHBox");
            workflowsVBox.getChildren().add(workflowsHBox);
            workflowsHBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                MouseButton mb = event.getButton();
                workflowsHBox.requestFocus();
                switch (mb) {
                    case PRIMARY:
                        if (this.selectedWorkflow != workflow) {
                            this.selectedWorkflow = workflow;
                            updatePathList();
                        }
                        break;
                    case SECONDARY:
                        this.selectedWorkflow = workflow;
                        workflowContextMenu.show(workflowsHBox, event.getScreenX(), event.getScreenY());
                        break;
                }
                addPathButton.setDisable(false);
            });
        }
    }

    @FXML
    private void createNewPath() {
        Stage stage = new Stage();
        stage.setTitle("New Path");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newpathwindow/newPathWindow.fxml"));

        try {
            Parent root = loader.load();
            NewPathWindowController controller = loader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(parent.getScene().getWindow());
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

            Path newPath = controller.getPath();
            if (newPath != null) {
                this.selectedPath = newPath;
                selectedWorkflow.addPath(selectedPath);
                clearPathDetails();
                updatePathList();
            }
        } catch (IOException e) {
            System.err.println("Error loading internal resource: newpathwindow/newPathWindow.fxml");
        }
    }

    private void updatePathList() {
        if (selectedWorkflow != null) {
            pathsVBox.getChildren().clear();
            for (Path path : selectedWorkflow.getPathHashMap().values()) {
                HBox pathHBox = new HBox(3);
                Label pathLabel = new Label(path.getName());
                pathHBox.setFocusTraversable(true);
                pathHBox.setId("pathHBox");
                pathHBox.getChildren().add(pathLabel);
                pathsVBox.getChildren().add(pathHBox);
                pathHBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    MouseButton mb = event.getButton();
                    pathHBox.requestFocus();
                    this.selectedPath = path;
                    switch (mb) {
                        case PRIMARY:
                            showPathDetails(selectedPath);
                            break;
                        case SECONDARY:
                            pathContextMenu.show(pathHBox, event.getScreenX(), event.getScreenY());
                            break;
                    }
                });
                addMessageButton.setDisable(false);
            }
        } else {
            pathsVBox.getChildren().clear();
        }
    }


    private void showPathDetails(Path path) {
        Tooltip removeTooltip = new Tooltip("Click to remove this message");
        pathExecutionProbabilityLabel.setText(String.valueOf(path.getExecutionProbability()));
        messagesGridPane.getChildren().clear();
        int gridPaneRows = 0;
        int gridPaneCols = 0;
        for (Message message : selectedPath.getMessagesList()) {
            Label startingComponentLabel = new Label(message.getStartingComponentName());
            startingComponentLabel.setFont(Font.font(null, FontWeight.BOLD, 18));
            Label endingComponentLabel = new Label(message.getEndingComponentName());
            endingComponentLabel.setFont(Font.font(null, FontWeight.BOLD, 18));
            Label backArrowLabel = new Label("<--");
            backArrowLabel.setTooltip(removeTooltip);
            backArrowLabel.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT)));
            backArrowLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                selectedPath.removeMessageAndFollowers((Message) backArrowLabel.getUserData());
                showPathDetails(selectedPath);
            });
            backArrowLabel.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> backArrowLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(2), null))));
            backArrowLabel.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> backArrowLabel.setBackground(new Background(new BackgroundFill(null, null, null))));
            Label arrowLabel = new Label("-->");
            arrowLabel.setTooltip(removeTooltip);
            arrowLabel.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT)));
            arrowLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                selectedPath.removeMessageAndFollowers((Message) arrowLabel.getUserData());
                showPathDetails(selectedPath);
            });
            arrowLabel.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> arrowLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(2), null))));
            arrowLabel.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> arrowLabel.setBackground(new Background(new BackgroundFill(null, null, null))));
            arrowLabel.setUserData(message);
            backArrowLabel.setUserData(message);
            if (message.isReturning()) {
                if (gridPaneCols < 2) {
                    ErrorWindow errorWindow = new ErrorWindow();
                    errorWindow.showErrorMessage("You cannot enter more return messages in this row!", parent.getScene().getWindow());
                    selectedPath.getMessagesList().removeLast();
                    return;
                }
                if (!selectedPath.getMessagesList().get(selectedPath.getMessagesList().indexOf(message) - 1).isReturning()) {
                    gridPaneRows++;
                    messagesGridPane.add(startingComponentLabel, gridPaneCols, gridPaneRows);
                    gridPaneCols--;
                    messagesGridPane.add(backArrowLabel, gridPaneCols, gridPaneRows);
                    gridPaneCols--;
                    messagesGridPane.add(endingComponentLabel, gridPaneCols, gridPaneRows);
                } else {
                    gridPaneCols--;
                    messagesGridPane.add(backArrowLabel, gridPaneCols, gridPaneRows);
                    gridPaneCols--;
                    messagesGridPane.add(endingComponentLabel, gridPaneCols, gridPaneRows);
                }
            } else if (messagesGridPane.getChildren().isEmpty()) {
                messagesGridPane.add(startingComponentLabel, gridPaneCols, gridPaneRows);
                gridPaneCols++;
                messagesGridPane.add(arrowLabel, gridPaneCols, gridPaneRows);
                gridPaneCols++;
                messagesGridPane.add(endingComponentLabel, gridPaneCols, gridPaneRows);
            } else {
                if (selectedPath.getMessagesList().get(selectedPath.getMessagesList().indexOf(message) - 1).isReturning()) {
                    gridPaneRows++;
                    messagesGridPane.add(startingComponentLabel, gridPaneCols, gridPaneRows);
                    gridPaneCols++;
                    messagesGridPane.add(arrowLabel, gridPaneCols, gridPaneRows);
                    gridPaneCols++;
                    messagesGridPane.add(endingComponentLabel, gridPaneCols, gridPaneRows);
                } else {
                    gridPaneCols++;
                    messagesGridPane.add(arrowLabel, gridPaneCols, gridPaneRows);
                    gridPaneCols++;
                    messagesGridPane.add(endingComponentLabel, gridPaneCols, gridPaneRows);
                }
            }
        }
    }

    private void clearPathDetails() {
        pathExecutionProbabilityLabel.setText("NaN");
        messagesGridPane.getChildren().clear(); // Should be enough
    }

    @FXML
    private void createAndAddNewMessage() {
        Stage stage = new Stage();
        stage.setTitle("New Message");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newmessagewindow/newMessageWindow.fxml"));

        try {
            Parent root = loader.load();
            NewMessageWindowController controller = loader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(parent.getScene().getWindow());
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            controller.setAvailableComponents(architecture.getComponents().keySet());
            stage.showAndWait();

            Message newMessage = controller.getMessage();
            if (newMessage != null) {
                Message lastMessage = selectedPath.getLastMessage();
                if (lastMessage != null) {
                    if (lastMessage.getEndingComponentName().equals(newMessage.getStartingComponentName())) {
                        selectedPath.addMessage(newMessage);
                    } else {
                        System.err.print("Error adding message " + newMessage.getStartingComponentName() + " ->" + newMessage.getEndingComponentName());
                    }

                } else {
                    selectedPath.addMessage(newMessage);
                }
                showPathDetails(selectedPath);
            }
        } catch (IOException e) {
            System.err.println("Error loading internal resource: newmessagewindow/newMessageWindow.fxml");
        }
    }


    @Override
    public void setParentScreen(ScreenController screen) {
        this.parent = screen;
    }

    /**
     * <p>Use this to retrieve the current architecture that is shown.</p>
     *
     * @return the currently shown architecture.
     */
    public Architecture getArchitecture() {
        return architecture;
    }

    /**
     * <p>Sets the architecture that needs to be shown.</p>
     *
     * @param architecture the architecture that has to be shown.
     */
    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }


}
