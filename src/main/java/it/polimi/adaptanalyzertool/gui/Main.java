package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.utility.CenterScreens;
import it.polimi.adaptanalyzertool.gui.utility.ChildScreenController;
import it.polimi.adaptanalyzertool.gui.utility.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * <p>
 * Entry point for the application. This needs also to be written in the build.gradle file.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class Main extends Application {

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Adaptability Analyzer Tool");
        primaryStage.getIcons().add(new Image("images/polimi_icon.png"));
        primaryStage.setMinWidth(1020);
        primaryStage.setMinHeight(766);

        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("main.fxml"));

        try {
            BorderPane root = mainLoader.load();
            MainController mainController = mainLoader.getController();
            mainController.setParent(primaryStage);

            ScreenController screenController = new ScreenController();
            ChildScreenController controller;
            controller = screenController.loadScreen(CenterScreens.WELCOME.getName(), CenterScreens.WELCOME.getLayout());
            CenterScreens.WELCOME.setController(controller);
            controller = screenController.loadScreen(CenterScreens.ARCHITECTURE.getName(), CenterScreens.ARCHITECTURE.getLayout());
            CenterScreens.ARCHITECTURE.setController(controller);
            screenController.setScreen(CenterScreens.WELCOME.getName());

            mainController.setScreenController(screenController);
            root.setCenter(screenController);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error loading internal resource: main.fxml");
        }

    }
}
