package it.polimi.adaptanalyzertool.gui.welcomescreen;

import it.polimi.adaptanalyzertool.gui.utility.ControlledScreen;
import it.polimi.adaptanalyzertool.gui.utility.ScreensController;

public class WelcomeScreenController implements ControlledScreen {

    private ScreensController screensController;


    @Override
    public void setScreenController(ScreensController screensController) {
        this.screensController = screensController;
    }
}
