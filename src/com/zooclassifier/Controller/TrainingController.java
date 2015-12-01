/**
 * Sample Skeleton for 'Training.fxml' ClassifierController Class
 */

package com.zooclassifier.Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class TrainingController implements Initializable, ControlledScreen{
    ScreensController myController;
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="radioAlgoNB"
    private RadioButton radioAlgoNB; // Value injected by FXMLLoader

    @FXML // fx:id="buttonTrain"
    private Button buttonTrain; // Value injected by FXMLLoader

    @FXML // fx:id="radioMethodTen"
    private RadioButton radioMethodTen; // Value injected by FXMLLoader

    @FXML // fx:id="textboxAlgoKNN"
    private TextField textboxAlgoKNN; // Value injected by FXMLLoader

    @FXML // fx:id="textboxFileInputPath"
    private TextField textboxFileInputPath; // Value injected by FXMLLoader

    @FXML // fx:id="radioAlgoKNN"
    private RadioButton radioAlgoKNN; // Value injected by FXMLLoader

    @FXML // fx:id="radioMethodFull"
    private RadioButton radioMethodFull; // Value injected by FXMLLoader

    @FXML // fx:id="buttonFileInputBrowse"
    private Button buttonFileInputBrowse; // Value injected by FXMLLoader

    @FXML
    void actionTrain(ActionEvent event) {
        
    }

    @FXML
    void actionBrowseFile(ActionEvent event) {
        
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL url, ResourceBundle rb) {
        assert radioAlgoNB != null : "fx:id=\"radioAlgoNB\" was not injected: check your FXML file 'Training.fxml'.";
        assert buttonTrain != null : "fx:id=\"buttonTrain\" was not injected: check your FXML file 'Training.fxml'.";
        assert radioMethodTen != null : "fx:id=\"radioMethodTen\" was not injected: check your FXML file 'Training.fxml'.";
        assert textboxAlgoKNN != null : "fx:id=\"textboxAlgoKNN\" was not injected: check your FXML file 'Training.fxml'.";
        assert textboxFileInputPath != null : "fx:id=\"textboxFileInputPath\" was not injected: check your FXML file 'Training.fxml'.";
        assert radioAlgoKNN != null : "fx:id=\"radioAlgoKNN\" was not injected: check your FXML file 'Training.fxml'.";
        assert radioMethodFull != null : "fx:id=\"radioMethodFull\" was not injected: check your FXML file 'Training.fxml'.";
        assert buttonFileInputBrowse != null : "fx:id=\"buttonFileInputBrowse\" was not injected: check your FXML file 'Training.fxml'.";
        buttonFileInputBrowse.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                System.out.println("CLICKED");
            }
        });
    }
}
