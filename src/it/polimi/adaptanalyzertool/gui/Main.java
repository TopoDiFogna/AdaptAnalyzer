package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.utility.ScreenName;
import it.polimi.adaptanalyzertool.gui.utility.ScreensController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Adaptability Analyzer Tool");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("main.fxml"));
        BorderPane root = loader.load();
        primaryStage.setResizable(false);
        MainController mainController = loader.getController();

        ScreensController screensController = new ScreensController();
        screensController.loadScreen(ScreenName.WELCOME.getName(), ScreenName.WELCOME.getFile());
        screensController.loadScreen(ScreenName.ARCHITECTURE_SCREEN.getName(), ScreenName.ARCHITECTURE_SCREEN.getFile());

        screensController.setScreen(ScreenName.WELCOME.getName());

        root.setCenter(screensController);

        mainController.setParent(primaryStage);
        mainController.setScreenController(screensController);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
