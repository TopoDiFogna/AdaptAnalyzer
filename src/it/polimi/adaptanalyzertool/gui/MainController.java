package it.polimi.adaptanalyzertool.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void createNewArchitecture(ActionEvent actionEvent) throws Exception {
        Stage mainStage = Main.getPrimaryStage();

        Stage stage = new Stage();
        BorderPane root = FXMLLoader.load(getClass().getResource("newarchitecturewindow/newArchitectureWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initOwner(mainStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
}
