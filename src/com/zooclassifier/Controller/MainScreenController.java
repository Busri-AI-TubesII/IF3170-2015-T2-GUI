package com.zooclassifier.Controller;

/**
 * Created by Julio Savigny on 12/1/2015.
 */
import com.zooclassifier.Main.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable,ControlledScreen{
    ScreensController myController;
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }
    @FXML
    private Button classifierButton;

    @FXML
    private Button trainingButton;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        assert classifierButton != null : "fx:id=\"classifierButton\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert trainingButton != null : "fx:id=\"trainingButton\" was not injected: check your FXML file 'MainScreen.fxml'.";
        classifierButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                myController.setScreen(Main.CLASSIFIER_SCREEN);

            }
        });
        trainingButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                myController.setScreen(Main.TRAINING_SCREEN);

            }
        });
    }

}