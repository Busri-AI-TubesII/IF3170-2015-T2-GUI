/**
 * Sample Skeleton for 'Training.fxml' ClassifierController Class
 */

package com.zooclassifier.Controller;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zooclassifier.Main.Main;
import com.zooclassifier.Model.ClassifierwithStringData;
import com.zooclassifier.Model.NaiveBayes;
import com.zooclassifier.Model.ZooFileLoader;
import com.zooclassifier.Model.kNN;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private Button homeButton;

    private int algoType;
    private int nKNN;
    private int trainMethod;

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
            kNN knnVar = new kNN(1);
            NaiveBayes naiveBayesVar = new NaiveBayes();
            @Override
            public void handle(javafx.event.ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(new Stage());
                if (file != null) {
                    textboxFileInputPath.setPromptText(file.getAbsolutePath());
                    try{
                        ZooFileLoader fl = new ZooFileLoader(file.getAbsolutePath());
                        ClassifierwithStringData Classifier;
                        if (algoType == 0) { //KNN
                            if ((!radioAlgoKNN.getText().isEmpty())&&(radioAlgoKNN.getText()!=null)) {
                                knnVar.setK(Integer.parseInt(textboxAlgoKNN.getText()));
                                Classifier = new ClassifierwithStringData(knnVar);
                                if (trainMethod==1) {
                                    try {
                                        //Lakukan training metode Full training
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        //Lakukan Training metode 10 Fold
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else { //Naive
                            Classifier = new ClassifierwithStringData(naiveBayesVar);
                            if (trainMethod==1) {
                                try {
                                    //Lakukan Training metode Full training
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    //Lakukan Training metode 10 Fold
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        //Bikin Windows baru dengan stage textarea untuk menampilkan hasil training
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        homeButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myController.setScreen(Main.MAIN_SCREEN);
            }
        });

        radioAlgoKNN.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                algoType = 0;
            }
        });

        radioAlgoNB.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                algoType = 1;
            }
        });

        radioMethodTen.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainMethod = 0;
            }
        });

        radioMethodFull.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainMethod = 1;
            }
        });

    }
}
