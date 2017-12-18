package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.utility.CenterScreens;
import it.polimi.adaptanalyzertool.gui.utility.ScreenController;
import it.polimi.adaptanalyzertool.model.Architecture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainController {

    private Window parent;
    private ScreenController screenController;

    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void createNewArchitecture() throws Exception {
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
        if (architecture != null) {
            screenController.setScreen(CenterScreens.ARCHITECTURE.getName());
            ArchitectureScreenControllerBeta childScreenController = (ArchitectureScreenControllerBeta) CenterScreens.ARCHITECTURE.getController();
            childScreenController.setArchitecture(architecture);
            childScreenController.setUpScreen();
        }
    }

    void setParent(Window parent) {
        this.parent = parent;
    }

    void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }
}
