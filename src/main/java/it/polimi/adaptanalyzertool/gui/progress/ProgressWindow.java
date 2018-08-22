package it.polimi.adaptanalyzertool.gui.progress;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

/**
 * <p>
 * Creates a window that dispays a progress for a running task.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class ProgressWindow {

    private GenericProgressWindowController controller;

    /**
     * <p>
     * Shows a window with a given title, an informative message.
     * </p>
     *
     * @param title           the title of the window.
     * @param progressMessage the informative message displayed near the progress indicator.
     * @param parent          the window that is the parent of this.
     */
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

    /**
     * <p>
     * Shows a window with a generic title, an informative message.
     * </p>
     *
     * @param progressMessage the informative message displayed near the progress indicator.
     * @param parent          the window that is the parent of this.
     */
    public void showProgressWindow(String progressMessage, Window parent) {
        showProgressWindow("Progress", progressMessage, parent);
    }

    /**
     * <p>
     * Enables the ok button that dismisses the window.
     * </p>
     */
    public void enableOkButton() {
        controller.enableOkButton();
    }

    /**
     * <p>
     * Sets the progress of the running task.
     * </p>
     *
     * @param progress the progress as a number between 0 and 1.
     */
    public void setProgress(double progress) {
        controller.setProgressIndicator(progress);
    }

    /**
     * <p>
     * Changes the informative message diplayed near the progress indicator.
     * </p>
     *
     * @param message the new message to display.
     */
    public void setMessage(String message) {
        controller.setProgressMessage(message);
    }

    /**
     * <p>
     * Closes the window.
     * </p>
     */
    public void closeProgressWindow() {
        controller.okClicked();
    }
}
