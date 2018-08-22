package it.polimi.adaptanalyzertool.gui.progress;

import it.polimi.adaptanalyzertool.gui.NewModalWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

/**
 * <p>
 * Controls a window that diplays a progress with a dotted circle nad an ok button that can be enabled with the
 * provided methods.
 * </p>
 * <p>
 * The ok button dismiss the window.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class GenericProgressWindowController extends NewModalWindowController {

    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button okButton;
    @FXML
    private Label genericProgressMessage;

    /**
     * <p>
     * Dismisses the window.
     * </p>
     */
    public void okClicked() {
        this.getStage().close();
    }

    /**
     * <p>
     * Sets a message that will be displayed next to the progres indicator.
     * </p>
     *
     * @param genericProgressMessage the message that is diplayed in the window.
     */
    public void setProgressMessage(String genericProgressMessage) {
        this.genericProgressMessage.setText(genericProgressMessage);
    }

    /**
     * <p>
     * Enables the ok button so it can be pressed by the user.
     * </p>
     */
    public void enableOkButton() {
        this.okButton.setDisable(false);
    }

    /**
     * <p>
     * Sets the progress.
     * </p>
     * <p>
     * Note that it starts from 0 and a value of 1 means that the process is completed.
     * </p>
     *
     * @param progress a number representing the progress ranging from 0 to 1.
     */
    public void setProgressIndicator(double progress) {
        progressIndicator.setProgress(progress);
    }
}
