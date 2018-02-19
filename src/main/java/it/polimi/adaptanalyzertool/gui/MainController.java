package it.polimi.adaptanalyzertool.gui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.polimi.adaptanalyzertool.gui.error.GenericErrorController;
import it.polimi.adaptanalyzertool.gui.utility.CenterScreens;
import it.polimi.adaptanalyzertool.gui.utility.ScreenController;
import it.polimi.adaptanalyzertool.model.Architecture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
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

public class MainController {

    private Window parent;
    private ScreenController screenController;
    private ArchitectureScreenControllerBeta childScreenController;
    private GraphController controller = null;
    private boolean graphIsShowing = false;

    @FXML
    private MenuItem showArchitectureGraphMenuItem;

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void createNewArchitecture() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("New Architecture");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newarchitecturewindow/newArchitectureWindow.fxml"));

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
    }

    @FXML
    private void generateNewArchitecture() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Generate Architecture");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newarchitecturewindow/generatedArchitectureWindow.fxml"));

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
    private void exportArchitecture() throws IOException {
        if (childScreenController != null) {
            Gson gson = new Gson();
            String json = gson.toJson(childScreenController.getArchitecture());
            FileChooser fc = new FileChooser();
            fc.setTitle("Export Architecture");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json file", "*.json"));
            File file = fc.showSaveDialog(parent);
            if (file != null) {
                saveTextFile(json, file);
            }
        } else {
            showErrorMessage("Nothing to export");
        }
    }

    @FXML
    private void importArchitecture() throws IOException {
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
                    showErrorMessage("Json file not valid!");
                }
            }
        }
    }

    @FXML
    private void showArchitectureGraph() throws IOException {
        if (childScreenController != null && !graphIsShowing){
            Stage stage = new Stage();
            stage.setTitle("Graph");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("graph/graph.fxml"));
            BorderPane root = loader.load();

            controller = loader.getController();
            controller.setArchitecture(childScreenController.getArchitecture());
            controller.setRoot(root);
            controller.setUp();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(parent);
            stage.setMinHeight(root.getMinHeight());
            stage.setMinWidth(root.getMinWidth());
            graphIsShowing = true;
            stage.showAndWait();
            graphIsShowing = false;
        }
        else if(graphIsShowing){
            controller.setUp();
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
            e.printStackTrace();//TODO
        }
    }

    private String openTextFile(File file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file.toURI())));
        } catch (IOException e) {
            e.printStackTrace();//TODO
        }
        return null;
    }

    private void showErrorMessage(String errorMessage) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Error");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("error/genericErrorWindow.fxml"));

        Parent root = loader.load();
        GenericErrorController controller = loader.getController();
        controller.setErrorMessage(errorMessage);
        controller.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initOwner(parent);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }
}
