package it.polimi.adaptanalyzertool.gui;

import javafx.stage.Stage;

/**
 * Main class for every modal window in the javaFx application.
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public abstract class NewModalWindowController {
    Stage stage;

    protected Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
