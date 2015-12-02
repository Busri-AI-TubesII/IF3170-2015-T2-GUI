package com.zooclassifier.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Julio Savigny on 12/2/2015.
 */
public class TrainingOutputController implements Initializable {
    @FXML
    private TextArea outputTextArea;
    public void initialize(URL url, ResourceBundle rb){
        outputTextArea.setText(TrainingController.trainResult);
    }
}
