package it.polimi.adaptanalyzertool.gui.architectureScreen;

import it.polimi.adaptanalyzertool.gui.utility.ControlledScreen;
import it.polimi.adaptanalyzertool.gui.utility.ScreensController;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ArchitectureScreenController implements Initializable, ControlledScreen {

    public Text architectureName;

    private ScreensController myController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.myController = screenParent;
    }
}
