package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.newarchitecturewindow.NewArchitectureWindowController;
import it.polimi.adaptanalyzertool.gui.utility.ScreenName;
import it.polimi.adaptanalyzertool.gui.utility.ScreensController;
import it.polimi.adaptanalyzertool.logic.Architecture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Window parent;

    private Architecture architecture;
    private ScreensController myController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setMyController(ScreensController screenParent) {
        this.myController = screenParent;
    }

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
        stage.initOwner(myController.getScene().getWindow());
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();

        architecture = controller.getArchitecture();
        if (architecture != null) {
            myController.setScreen(ScreenName.ARCHITECTURESCREEN.getName());
        }
    }
}
