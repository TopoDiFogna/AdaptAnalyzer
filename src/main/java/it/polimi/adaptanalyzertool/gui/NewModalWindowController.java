package it.polimi.adaptanalyzertool.gui;

import javafx.stage.Stage;

/**
 * Main class for every modal window in the JavaFx application.
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public abstract class NewModalWindowController {
    Stage stage;

    protected Stage getStage() {
        return stage;
    }

    /**
     * <p>
     * Use this to create a {@link javafx.stage.Modality#WINDOW_MODAL} that needs to be closed before returning
     * to the parent.
     * </p>
     *
     * @param stage the stage that owns this window.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
