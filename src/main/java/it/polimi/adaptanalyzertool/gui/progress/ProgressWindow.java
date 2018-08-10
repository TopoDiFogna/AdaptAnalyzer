package it.polimi.adaptanalyzertool.gui.progress;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

public class ProgressWindow {

    private GenericProgressWindowController controller;

    public void showProgressWindow(String title, String progressMessage, Window parent) {
        Stage stage = new Stage();
        stage.setTitle(title);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("genericProgressWindow.fxml"));

        Parent root;
        try {
            root = loader.load();
            controller = loader.getController();
            controller.setProgressMessage(progressMessage);
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.WINDOW_MODAL);

            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading resource file");
        }
    }

    public void showProgressWindow(String progressMessage, Window parent) {
        showProgressWindow("Progress", progressMessage, parent);
    }

    public void enableOkButton() {
        controller.enableOkButton();
    }

    public void setProgress(double progress) {
        controller.setProgressIndicator(progress);
    }

    public void setMessage(String message) {
        controller.setProgressMessage(message);
    }

    public void closeProgressWindow() {
        controller.okClicked();
    }
}
