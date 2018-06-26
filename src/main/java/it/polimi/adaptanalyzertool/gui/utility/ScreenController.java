package it.polimi.adaptanalyzertool.gui.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

/**
 * <p>This class is a {@link StackPane} that contains a node, that should be shown in the center of the window.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class ScreenController extends StackPane {

    private HashMap<String, Pane> loadedScreens = new HashMap<>();

    /**
     * @see StackPane#StackPane()
     */
    public ScreenController() {
        super();
    }

    /**
     * <p>Loads a screen in this stack pane in order to be ready to be shown.</p>
     *
     * @param name   the name of the screen that has to be loaded.
     * @param layout the layout associated with the loaded screen.
     *
     * @return the controller associated with the loaded screen.
     */
    public ChildScreenController loadScreen(String name, String layout) {
        try {
            FXMLLoader screenLoader = new FXMLLoader();
            screenLoader.setLocation(getClass().getResource(layout));
            Pane childScreen = screenLoader.load();
            ChildScreenController childScreenController = screenLoader.getController();
            childScreenController.setParentScreen(this);
            loadedScreens.put(name, childScreen);
            return childScreenController;
        } catch (IOException e) {
            System.err.println("Cannot load screen " + name);
            return null;
        }
    }

    /**
     * <p>Sets the previously loaded screen in the stackpane in order to be shown.</p>
     *
     * @param name the name of the screen to be shown.
     *
     * @return {@code false} if the screen is not loaded, {@code true} otherwise.
     */
    public boolean setScreen(String name) {
        if (loadedScreens.get(name) == null) {
            System.err.println("Screen " + name + " not loaded!");
            return false;
        } else {
            if (!getChildren().isEmpty()) {
                getChildren().remove(0);
            }
            getChildren().add(loadedScreens.get(name));
            return true;
        }
    }

    /**
     * <p>Used to unload a screen from the memory.</p>
     *
     * @param name the screen to unload.
     *
     * @return {@code false} if the screen was not loaded, {@code true} otherwise.
     */
    public boolean unloadScreen(String name) {
        if (loadedScreens.remove(name) == null) {
            System.err.println("Screen " + name + " not loaded!");
            return false;
        }
        return true;
    }
}
