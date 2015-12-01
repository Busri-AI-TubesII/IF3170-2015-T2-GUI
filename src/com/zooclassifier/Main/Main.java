package com.zooclassifier.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.zooclassifier.Controller.*;

public class Main extends Application {


    public static final String MAIN_SCREEN = "main";
    public static final String MAIN_SCREEN_FXML = "../View/MainScreen.fxml";
    public static final String TRAINING_SCREEN = "Training";
    public static final String TRAINING_SCREEN_FXML = "../View/Training.fxml";
    public static final String CLASSIFIER_SCREEN = "Classifier";
    public static final String CLASSIFIER_SCREEN_FXML = "../View/ZooClassifier.fxml";
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.MAIN_SCREEN,
                Main.MAIN_SCREEN_FXML);
        mainContainer.loadScreen(Main.TRAINING_SCREEN,
                Main.TRAINING_SCREEN_FXML);
        mainContainer.loadScreen(Main.CLASSIFIER_SCREEN,
                Main.CLASSIFIER_SCREEN_FXML);

        mainContainer.setScreen(Main.MAIN_SCREEN);

        Scene scene = new Scene(mainContainer);
        primaryStage.setTitle("Classifier");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void resizeScreen(){
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
