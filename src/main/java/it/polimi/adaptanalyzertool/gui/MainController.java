package it.polimi.adaptanalyzertool.gui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.polimi.adaptanalyzertool.gui.error.ErrorWindow;
import it.polimi.adaptanalyzertool.gui.utility.CenterScreens;
import it.polimi.adaptanalyzertool.gui.utility.ScreenController;
import it.polimi.adaptanalyzertool.model.Architecture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <p>
 * This class represents the controller for the main window of the application.
 * </p>
 * <p>
 * All methods that control the top menu should be placed here.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class MainController {

    private Window parent;
    private ScreenController screenController;
    private ArchitectureScreenControllerBeta childScreenController;
    private GraphController graphController;
    private boolean graphIsShowing = false;
    private ErrorWindow errorWindow = new ErrorWindow();

    @FXML
    private MenuItem showArchitectureGraphMenuItem;

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void createNewArchitecture() {
        Stage stage = new Stage();
        stage.setTitle("New Architecture");
        stage.getIcons().add(new Image("images/polimi_icon.png"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newarchitecturewindow/newArchitectureWindow.fxml"));

        try {
            Parent root = loader.load();
            NewArchitectureWindowController controller = loader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

            Architecture architecture = controller.getArchitecture();
            showArchitectureScreen(architecture);
        } catch (IOException e) {
            System.err.println("Error loading internal resource: newarchitecturewindow/newArchitectureWindow.fxml");
        }
    }

    @FXML
    private void generateNewArchitecture() {
        Stage stage = new Stage();
        stage.setTitle("Generate Architecture");
        stage.getIcons().add(new Image("images/polimi_icon.png"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newarchitecturewindow/generatedArchitectureWindow.fxml"));

        try {
            Parent root = loader.load();
            GeneratedArchitectureWindowController controller = loader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

            Architecture architecture = controller.getArchitecture();
            showArchitectureScreen(architecture);
        } catch (IOException e) {
            System.err.println("Error loading internal resource: newarchitecturewindow/generatedArchitectureWindow.fxml");
        }
    }

    private void showArchitectureScreen(Architecture architecture) {
        if (architecture != null) {
            showArchitectureGraphMenuItem.setDisable(false);
            screenController.setScreen(CenterScreens.ARCHITECTURE.getName());
            childScreenController = (ArchitectureScreenControllerBeta) CenterScreens.ARCHITECTURE.getController();
            childScreenController.setArchitecture(architecture);
            childScreenController.setUpScreen();
        }
    }

    @FXML
    private void exportArchitecture() {
        if (childScreenController != null) {
            Gson gson = new Gson();
            String json = gson.toJson(childScreenController.getArchitecture());
            FileChooser fc = new FileChooser();
            fc.setTitle("Export Architecture");
            fc.setInitialFileName(childScreenController.getArchitecture().getName());
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json file", "*.json"));
            File file = fc.showSaveDialog(parent);
            if (file != null) {
                saveTextFile(json, file);
            }
        } else {
            errorWindow.showErrorMessage("Nothing to export", parent);
        }
    }

    @FXML
    private void importArchitecture() {
        Architecture architecture;
        FileChooser fc = new FileChooser();
        fc.setTitle("Import Architecture");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Json file", "*.json"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fc.showOpenDialog(parent);
        if (file != null) {
            String json = openTextFile(file);
            if (json != null) {
                try {
                    Gson gson = new Gson();
                    architecture = gson.fromJson(json, Architecture.class);
                    showArchitectureScreen(architecture);
                } catch (JsonSyntaxException e) {
                    errorWindow.showErrorMessage("Json file not valid!", parent);
                }
            }
        }
    }

    @FXML
    private void showArchitectureGraph() {
        if (childScreenController != null && !graphIsShowing) {
            Stage stage = new Stage();
            stage.setTitle("Graph");
            stage.getIcons().add(new Image("images/polimi_icon.png"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("graph/graph.fxml"));
            try {
                BorderPane root = loader.load();
                graphController = loader.getController();
                graphController.setArchitecture(childScreenController.getArchitecture());
                graphController.setRoot(root);
                graphController.setUp();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initOwner(parent);
                stage.setMinHeight(root.getMinHeight());
                stage.setMinWidth(root.getMinWidth());
                graphIsShowing = true;
                stage.showAndWait();
                graphIsShowing = false;
            } catch (IOException e) {
                System.err.println("Error loading internal resource: graph/graph.fxml");
            }
        } else if (graphIsShowing) {
            graphController.setUp();
        }
    }

    void setParent(Window parent) {
        this.parent = parent;
    }

    void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    private void saveTextFile(String content, File file) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            errorWindow.showErrorMessage("Error Saving the file.", parent);
        }
    }

    private String openTextFile(File file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file.toURI())));
        } catch (IOException e) {
            errorWindow.showErrorMessage("Error opening the file", parent);
        }
        return null;
    }
}
