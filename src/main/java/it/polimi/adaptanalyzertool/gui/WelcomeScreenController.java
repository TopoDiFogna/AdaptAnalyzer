package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.utility.ChildScreenController;
import it.polimi.adaptanalyzertool.gui.utility.ScreenController;
import javafx.scene.layout.StackPane;

public class WelcomeScreenController implements ChildScreenController{

    private StackPane parent;

    @Override
    public void setParentScreen(ScreenController screen) {
        this.parent = screen;
    }
}
