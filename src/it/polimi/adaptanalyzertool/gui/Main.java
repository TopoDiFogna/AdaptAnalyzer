package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.utility.ScreenName;
import it.polimi.adaptanalyzertool.gui.utility.ScreensController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("main.fxml"));
        BorderPane root = loader.load();
        MainController mainController = loader.getController();

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(ScreenName.WELCOME.getName(), ScreenName.WELCOME.getFile());
        mainContainer.loadScreen(ScreenName.ARCHITECTURESCREEN.getName(), ScreenName.ARCHITECTURESCREEN.getFile());

        mainContainer.setScreen(ScreenName.WELCOME.getName());

        primaryStage.setTitle("Adaptability Analyzer Tool");

        Group screenGroup = new Group();
        screenGroup.getChildren().add(mainContainer);

        mainController.setMyController(mainContainer);

        root.setCenter(screenGroup);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
