package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.utility.ChildScreenController;
import it.polimi.adaptanalyzertool.gui.utility.ScreenController;
import javafx.scene.layout.StackPane;

/**
 * <p>
 * This class contains the first screen shown to the user.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class WelcomeScreenController implements ChildScreenController {

    private StackPane parent;

    @Override
    public void setParentScreen(ScreenController screen) {
        this.parent = screen;
    }
}
