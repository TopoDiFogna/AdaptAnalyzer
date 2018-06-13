package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.error.ErrorWindow;
import it.polimi.adaptanalyzertool.gui.progress.ProgressWindow;
import it.polimi.adaptanalyzertool.gui.utility.ChildScreenController;
import it.polimi.adaptanalyzertool.gui.utility.ScreenController;
import it.polimi.adaptanalyzertool.metrics.*;
import it.polimi.adaptanalyzertool.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
import java.util.*;

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
    private HBox componentSelectedHBox;
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
    private ComboBox<String> componentComboBox;
    @FXML
    private Button serviceAddButton;
    private ContextMenu serviceContextMenu;
    private HBox serviceSelectedHBox;
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
    private HBox workflowSelectedHBox;
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
    private HBox pathSelectedHBox;
    /*
        Messages Scheme
     */
    @FXML
    private GridPane messagesGridPane;
    /*
        Adaptability
     */
    @FXML
    private Label systemAdaptabilityLabel;
    @FXML
    private VBox adaptabilityVBox;
    @FXML
    private Label minCostLabel;
    @FXML
    private ComboBox<String> architectureMinCostComboBox;
    @FXML
    private Label maxCostLabel;
    @FXML
    private ComboBox<String> architectureMaxCostComboBox;
    @FXML
    private LineChart<Number, Number> adaptabilityChart;

    private HBox adaptabilitySelectedHBox;
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
    private Label totalAvailabilityLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label meanAbsoluteAdaptabilityLabel;
    @FXML
    private Label meanRelativeAdaptabilityLabel;
    @FXML
    private Label levelSystemAdaptabilityLabel;
    @FXML
    private Label architectureMetricsErrorLabel;

    private Architecture architecture;
    private HashMap<String, ComponentGroup> componentsGroups;
    private HashMap<Double, QualityHolder> adaptabilityQualityHashMap;
    private Component selectedComponent;
    private AbstractService selectedService;
    private Workflow selectedWorkflow;
    private Path selectedPath;
    private ScreenController parent;

    @FXML
    private void initialize() {
        //Adds listener to the component choice box to update the services displayed
        componentComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            clearServiceMetrics();
            selectedService = null;
            if (newValue != null) {
                selectedComponent = architecture.getSingleComponent(newValue);
                updateServicesList();
                clearServiceDetails();
                serviceAddButton.setDisable(false);
            }
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getText().equals("Services")) {
                componentComboBox.setItems(FXCollections.observableArrayList(architecture.getComponentsNames()).sorted());
                if (!architecture.getComponents().isEmpty() && selectedComponent != null) {
                    componentComboBox.setValue(selectedComponent.getName());
                    this.serviceAddButton.setDisable(false);
                }
            }
            if (newValue.getText().equals("Workflows")) {
                componentsGroups = ArchitectureMetrics.getComponentGroups(architecture);
            }
        });

        //Sets the rounding mode of the DecimalFormat used in metrics
        df.setRoundingMode(RoundingMode.DOWN);

        //Creates the context menu used to manipulate component, services, workflows and paths
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

        //Initializes comboboxes for component in selected architecture for a given adaptability
        initializeCombobox(architectureMaxCostComboBox);

        initializeCombobox(architectureMinCostComboBox);
    }

    private void initializeCombobox(ComboBox<String> architectureCostComboBox) {
        architectureCostComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {//TODO fire even if not changed
            if (newValue != null) {
                selectedComponent = architecture.getSingleComponent(newValue);
                tabPane.getSelectionModel().select(componentsTab);
                hightlightComponentHBox(selectedComponent);
                showComponentDetail(selectedComponent);
            }
        });
    }

    private void hightlightComponentHBox(Component component) {
        for (Node node : componentsVBox.getChildren()) {
            for (Node hBoxChildren : ((HBox) node).getChildren()) {
                if (hBoxChildren instanceof Label && ((Label) hBoxChildren).getText().equals(component.getName())) {
                    if (componentSelectedHBox != null) {
                        resetHBoxBackgroung(componentSelectedHBox);
                    }
                    componentSelectedHBox = (HBox) node;
                    setHBoxBackground(componentSelectedHBox);
                }
            }
        }
    }

    void setUpScreen() {
        architectureName.setText("Architecture name: " + architecture.getName());
        tabPane.getSelectionModel().select(componentsTab);
        selectedComponent = null;
        selectedService = null;
        selectedWorkflow = null;
        selectedPath = null;
        adaptabilityQualityHashMap = null;
        clearComponentDetail();
        clearServiceDetails();
        clearPathDetails();
        showComponentServicesButton.setDisable(true);
        updateComponentList();
        updateServicesList();
        updateWorkflowList();
        updatePathList();
        componentsGroups = ArchitectureMetrics.getComponentGroups(architecture);
        updateAdaptabilityList();
        drawChart();
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
        hightlightComponentHBox(selectedComponent);
    }

    private void updateComponentList() {
        componentsVBox.getChildren().clear();
        List<Component> architectureComponents = new ArrayList<>(architecture.getComponents());
        architectureComponents.sort(Comparator.comparing(Component::getName));
        for (Component component : architectureComponents) {
            Rectangle componentColor = new Rectangle(17, 17, component.getColor());
            componentColor.setStroke(Color.BLACK);
            Label componentName = new Label(component.getName());
            HBox componentHBox = new HBox(3, componentColor, componentName);
            componentHBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                MouseButton mb = event.getButton();
                if (componentSelectedHBox != null) {
                    resetHBoxBackgroung(componentSelectedHBox);
                }
                componentSelectedHBox = componentHBox;
                setHBoxBackground(componentSelectedHBox);
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
            HashSet<AbstractService> componentServices = new HashSet<>();
            componentServices.addAll(selectedComponent.getProvidedServices());
            componentServices.addAll(selectedComponent.getRequiredServices());
            List<AbstractService> componentServicesList = new ArrayList<>(componentServices);
            componentServicesList.sort(Comparator.comparing(AbstractService::getName));
            servicesVBox.getChildren().clear();
            for (AbstractService service : componentServicesList) {
                HBox servicesHBox = new HBox(3);
                Label serviceLabel = new Label();
                if (service instanceof ProvidedService) {
                    serviceLabel.setText("(Provided) " + service.getName());
                } else if (service instanceof RequiredService) {
                    serviceLabel.setText("(Required) " + service.getName());
                }
                servicesHBox.getChildren().add(serviceLabel);
                servicesVBox.getChildren().add(servicesHBox);
                servicesHBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    MouseButton mb = event.getButton();
                    if (serviceSelectedHBox != null) {
                        resetHBoxBackgroung(serviceSelectedHBox);
                    }
                    serviceSelectedHBox = servicesHBox;
                    setHBoxBackground(serviceSelectedHBox);
                    this.selectedService = service;
                    switch (mb) {
                        case PRIMARY:
                            showServiceDetail(selectedService);
                            clearServiceMetrics();
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

    private void clearServiceMetrics() {
        numberOfExecutionsLabel.setText("NaN");
        probabilityToBeRunningLabel.setText("NaN");
        absoluteAdaptabilityLabel.setText("NaN");
        relativeAdaptabilityLabel.setText("NaN");
    }

    @FXML
    private void calculateArchitectureMetrics() {
        componentsGroups = ArchitectureMetrics.getComponentGroups(architecture);
        String sta = systemTargetAvailabilityTextField.getText().trim();
        String stc = systemTargetCostTextField.getText().trim();
        if (!sta.equals("") && sta.matches(NINETYNINE_REGEX) && !stc.equals("") && stc.matches(DOUBLE_REGEX)) {
            architectureMetricsErrorLabel.setText("");
            double gas = ArchitectureMetrics.GlobalAvailabilitySystem(architecture, Double.valueOf(sta));
            double gcs = ArchitectureMetrics.GlobalCostSystem(architecture, Double.valueOf(stc));
            globalAvailabilityLabel.setText(df.format(gas));
            if (ArchitectureMetrics.SuitableForAvailability(architecture, Double.valueOf(sta))) {
                globalAvailabilitySuitabilityLabel.setText("Suitable");
            } else {
                globalAvailabilitySuitabilityLabel.setText("Not Suitable");
            }
            globalCostLabel.setText(df.format(gcs));
            if (ArchitectureMetrics.SuitableForCost(architecture, Double.valueOf(stc))) {
                globalCostSuitabilityLabel.setText("Suitable");
            } else {
                globalCostSuitabilityLabel.setText("Not Suitable");
            }
        } else {
            architectureMetricsErrorLabel.setText("Check input for mistakes");
        }
        double totalCost = ArchitectureMetrics.TotalCost(architecture);
        double totalAvailability = ArchitectureMetrics.TotalStaticAvailability(componentsGroups);//TODO
        double maas = AdaptabilityMetrics.MeanAbsoluteAdaptability(architecture);
        double raas = AdaptabilityMetrics.MeanRelativeAdaptability(architecture);
        double lsa = AdaptabilityMetrics.LevelSystemAdaptability(architecture);
        meanAbsoluteAdaptabilityLabel.setText(df.format(maas));
        meanRelativeAdaptabilityLabel.setText(df.format(raas));
        levelSystemAdaptabilityLabel.setText(df.format(lsa));
        totalAvailabilityLabel.setText(df.format(totalAvailability));
        totalCostLabel.setText(df.format(totalCost));
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
        List<Workflow> architectureWorkflow = new ArrayList<>(architecture.getWorkflows());
        architectureWorkflow.sort(Comparator.comparing(Workflow::getName));
        for (Workflow workflow : architectureWorkflow) {
            HBox workflowsHBox = new HBox(3);
            Label workflowLabel = new Label(workflow.getName());
            workflowsHBox.getChildren().add(workflowLabel);
            workflowsVBox.getChildren().add(workflowsHBox);
            workflowsHBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                MouseButton mb = event.getButton();
                if (workflowSelectedHBox != null) {
                    resetHBoxBackgroung(workflowSelectedHBox);
                }
                workflowSelectedHBox = workflowsHBox;
                setHBoxBackground(workflowSelectedHBox);
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
            List<Path> workflowPaths = new ArrayList<>(selectedWorkflow.getPaths());
            workflowPaths.sort(Comparator.comparing(Path::getName));
            for (Path path : workflowPaths) {
                HBox pathHBox = new HBox(3);
                Label pathLabel = new Label(path.getName());
                Label pathProbabilityLabel = new Label("(Probability: " + String.valueOf(path.getExecutionProbability()) + ")");
                pathHBox.setSpacing(5);
                pathHBox.getChildren().addAll(pathLabel, pathProbabilityLabel);
                pathsVBox.getChildren().add(pathHBox);
                pathHBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    MouseButton mb = event.getButton();
                    if (pathSelectedHBox != null) {
                        resetHBoxBackgroung(pathSelectedHBox);
                    }
                    pathSelectedHBox = pathHBox;
                    setHBoxBackground(pathSelectedHBox);
                    this.selectedPath = path;
                    switch (mb) {
                        case PRIMARY:
                            showPathDetails(selectedPath);
                            break;
                        case SECONDARY:
                            pathContextMenu.show(pathHBox, event.getScreenX(), event.getScreenY());
                            break;
                    }
                    addMessageButton.setDisable(false);
                });
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
            controller.setAvailableComponents(componentsGroups.keySet());
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

    @FXML
    private void calculateAdaptability() {
        ProgressWindow pw = new ProgressWindow();
        pw.showProgressWindow("Calculating all architectures", parent.getScene().getWindow());
        Task<HashMap<Double, QualityHolder>> task = new Task<>() {
            @Override
            protected HashMap<Double, QualityHolder> call() throws Exception {
                return ArchitectureMetrics.CheckAllArchitectures(architecture);
            }
        };
        task.setOnSucceeded(event -> {
            adaptabilityQualityHashMap = task.getValue();
            pw.enableOkButton();
            pw.setProgress(1);
            pw.setMessage("");
            updateAdaptabilityList();
            drawChart();
        });

        Thread taskThread = new Thread(task);
        taskThread.start();
    }

    private void updateAdaptabilityList() {
        if (adaptabilityQualityHashMap != null) {
            adaptabilityVBox.getChildren().clear();
            TreeMap<Double, QualityHolder> tm = new TreeMap<>(adaptabilityQualityHashMap);
            for (Double d : tm.keySet()) {
                HBox adaptabilityHBox = new HBox(3);
                Label adaptabilityLabel = new Label(String.valueOf(df.format(d)));
                adaptabilityHBox.setSpacing(5);
                adaptabilityHBox.getChildren().add(adaptabilityLabel);
                adaptabilityVBox.getChildren().add(adaptabilityHBox);
                adaptabilityHBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    MouseButton mb = event.getButton();
                    if (adaptabilitySelectedHBox != null) {
                        resetHBoxBackgroung(adaptabilitySelectedHBox);
                    }
                    adaptabilitySelectedHBox = adaptabilityHBox;
                    setHBoxBackground(adaptabilitySelectedHBox);
                    switch (mb) {
                        case PRIMARY:
                            systemAdaptabilityLabel.setText("System Adaptability: " + String.valueOf(d));
                            showAdaptabilityDetails(d);
                            break;
                    }
                });
            }

        } else {
            adaptabilityVBox.getChildren().clear();
        }
    }

    private void showAdaptabilityDetails(Double adaptability) {
        architectureMinCostComboBox.getItems().clear();
        architectureMaxCostComboBox.getItems().clear();

        QualityHolder qh = adaptabilityQualityHashMap.get(adaptability);
        minCostLabel.setText(String.valueOf(qh.getMinCost()));

        ObservableList<String> architectureMinCostComponents = FXCollections.observableArrayList();
        for (Component c : qh.getMinCostArchitecture().getComponents()) {
            if (c.isUsed()) {
                architectureMinCostComponents.add(c.getName());
            }
        }
        architectureMinCostComponents.sort(null);
        architectureMinCostComboBox.getItems().addAll(architectureMinCostComponents);

        maxCostLabel.setText(String.valueOf(qh.getMaxCost()));

        ObservableList<String> architectureMaxCostComponents = FXCollections.observableArrayList();
        for (Component c : qh.getMaxCostArchitecture().getComponents()) {
            if (c.isUsed()) {
                architectureMaxCostComponents.add(c.getName());
            }
        }
        architectureMaxCostComponents.sort(null);
        architectureMaxCostComboBox.getItems().addAll(architectureMaxCostComponents);
    }

    private void drawChart() {
        if (adaptabilityQualityHashMap != null) {
            adaptabilityChart.getData().clear();

            XYChart.Series<Number, Number> minAdaptabilitySeries = new XYChart.Series<>();
            XYChart.Series<Number, Number> maxAdaptabilitySeries = new XYChart.Series<>();

            minAdaptabilitySeries.getData().add(new XYChart.Data<>(0, 0));
            maxAdaptabilitySeries.getData().add(new XYChart.Data<>(0, 0));

            for (Map.Entry<Double, QualityHolder> entry : adaptabilityQualityHashMap.entrySet()) {
                minAdaptabilitySeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue().getMinCost()));
                maxAdaptabilitySeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue().getMaxCost()));
            }

            adaptabilityChart.getData().add(minAdaptabilitySeries);
            adaptabilityChart.getData().add(maxAdaptabilitySeries);
        } else {
            adaptabilityChart.getData().clear();
        }
    }

    private void setHBoxBackground(HBox hBox) {
        hBox.backgroundProperty().setValue(new Background(new BackgroundFill(Color.AQUAMARINE, null, null)));
    }

    private void resetHBoxBackgroung(HBox hBox) {
        hBox.backgroundProperty().setValue(new Background(new BackgroundFill(null, null, null)));
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
