package it.polimi.adaptanalyzertool.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application{

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Adaptability Analyzer Tool");
        setPrimaryStage(primaryStage);

        BorderPane root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }
}
