package it.polimi.adaptanalyzertool.gui.welcomescreen;

import it.polimi.adaptanalyzertool.gui.utility.ControlledScreen;
import it.polimi.adaptanalyzertool.gui.utility.ScreensController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeScreenController implements Initializable, ControlledScreen {

    private ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.myController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
