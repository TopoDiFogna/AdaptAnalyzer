package it.polimi.adaptanalyzertool.gui.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

public class ScreenController extends StackPane {

    private HashMap<String, Pane> loadedScreens = new HashMap<>();

    public ScreenController (){
        super();
    }

    public ChildScreenController loadScreen(String name, String layout){
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
            e.printStackTrace();
            return null;
        }
    }

    public boolean setScreen(String name){
        if (loadedScreens.get(name) == null){
            System.err.println("Screen " + name + " not loaded!");
            return false;
        }
        else {
            if (!getChildren().isEmpty()){
                getChildren().remove(0);
            }
            getChildren().add(loadedScreens.get(name));
            return true;
        }
    }

    public boolean unloadScreen(String name){
        if(loadedScreens.remove(name) == null){
            System.err.println("Screen " + name + " not loaded!");
            return false;
        }
        return true;
    }
}
