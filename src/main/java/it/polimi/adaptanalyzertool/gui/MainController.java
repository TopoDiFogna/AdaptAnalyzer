package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.architectureScreen.ArchitectureScreenController;
import it.polimi.adaptanalyzertool.gui.newarchitecturewindow.NewArchitectureWindowController;
import it.polimi.adaptanalyzertool.gui.utility.ControlledScreen;
import it.polimi.adaptanalyzertool.gui.utility.ScreenName;
import it.polimi.adaptanalyzertool.gui.utility.ScreensController;
import it.polimi.adaptanalyzertool.model.Architecture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainController implements ControlledScreen {

    private Window parent;
    private ScreensController screensController;

    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void createNewArchitecture() throws Exception {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newarchitecturewindow/newArchitectureWindow.fxml"));

        Parent root = loader.load();
        NewArchitectureWindowController controller = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initOwner(parent);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();

        Architecture architecture = controller.getArchitecture();
        if (architecture != null) {
            ArchitectureScreenController architectureScreenController = (ArchitectureScreenController) screensController.getScreen(ScreenName.ARCHITECTURE_SCREEN.getName()).getUserData();
            architectureScreenController.setArchitecture(architecture);
            architectureScreenController.setUpScreen();
            screensController.setScreen(ScreenName.ARCHITECTURE_SCREEN.getName());
        }
    }

    void setParent(Window parent) {
        this.parent = parent;
    }

    @Override
    public void setScreenController(ScreensController screensController) {
        this.screensController = screensController;
    }
}
