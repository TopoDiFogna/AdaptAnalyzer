package it.polimi.adaptanalyzertool.gui.error;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ErrorWindow {

    public void showErrorMessage(String errorMessage, Window parent) {
        Stage stage = new Stage();
        stage.setTitle("Error");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("genericErrorWindow.fxml"));

        Parent root;
        try {
            root = loader.load();
            GenericErrorController controller = loader.getController();
            controller.setErrorMessage(errorMessage);
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Error loading resource file");
        }
    }

}
