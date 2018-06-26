package it.polimi.adaptanalyzertool.gui.error;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * <p>This class defines a generic error window.</p>
 * <p>This window is a {@link Modality#WINDOW_MODAL}</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class ErrorWindow {

    private GenericErrorController controller;

    /**
     * <p>Shows an error message.</p>
     *
     * @param title        the title of the window.
     * @param errorMessage the error message.
     * @param parent       the parent window.
     */
    public void showErrorMessage(String title, String errorMessage, Window parent) {
        Stage stage = new Stage();
        stage.setTitle("Error");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("genericErrorWindow.fxml"));

        Parent root;
        try {
            root = loader.load();
            controller = loader.getController();
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

    /**
     * <p>Shows an error message.</p>
     *
     * @param errorMessage the error message.
     * @param parent       the parent window.
     */
    public void showErrorMessage(String errorMessage, Window parent) {
        showErrorMessage("Error", errorMessage, parent);
    }

}
