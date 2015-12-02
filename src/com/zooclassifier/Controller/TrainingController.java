/**
 * Sample Skeleton for 'Training.fxml' ClassifierController Class
 */

package com.zooclassifier.Controller;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zooclassifier.Main.Main;
import com.zooclassifier.Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class TrainingController implements Initializable, ControlledScreen{
    ScreensController myController;
    TrainingOutputController trainingOutputController;
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

    @FXML
    private TextField outputTextArea;

    private int algoType;
    private int nKNN;
    private int trainMethod;
    private File file;
    public void printConfusionMatrix(String [] labels, int [][] confusionMatrix){
        System.out.print("\t");
        for (int i=0;i<labels.length;i++){
            System.out.print(labels[i]+"\t");
        }
        System.out.println("<-- classified as");
        for (int i=0;i<labels.length;i++){
            for (int j=0;j<labels.length;j++){
                System.out.print("\t"+confusionMatrix[i][j]);
            }
            System.out.println("\t"+"| "+ labels[i]);
        }
        System.out.println("");
    }
    public String confusionMatrixString(String[] labels, int[][] confusionMatrix){
        String result="\t";
        for (int i=0;i<labels.length;i++){
            result+=labels[i]+"\t";
        }
        result+="<-- classified as\n";
        for (int i=0;i<labels.length;i++){
            for (int j=0;j<labels.length;j++){
                result+="\t"+confusionMatrix[i][j];
            }
            result+="\t"+"| "+ labels[i]+"\n";
        }
        result+="\n";
        return result;
    }
    public static String trainResult;
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
                FileChooser fileChooser = new FileChooser();
                file = fileChooser.showOpenDialog(new Stage());
                if (file != null) {
                    textboxFileInputPath.setPromptText(file.getAbsolutePath());
                }
            }
        });

        buttonTrain.setOnAction(new EventHandler<ActionEvent>() {
            kNN knnVar = new kNN(1);
            NaiveBayes naiveBayesVar = new NaiveBayes();
            @Override
            public void handle(ActionEvent event) {
                try{
                    ZooFileLoader fl = new ZooFileLoader(file.getAbsolutePath());
                    ClassifierwithStringData Classifier = null;
                    if (algoType == 0) { //KNN
                        if ((!radioAlgoKNN.getText().isEmpty())&&(radioAlgoKNN.getText()!=null)) {
                            knnVar.setK(Integer.parseInt(textboxAlgoKNN.getText()));
                            if (trainMethod==1) {
                                //Lakukan training metode Full training
                                Classifier = new ClassifierwithStringData(knnVar);
                            } else {
                                //Lakukan Training metode 10 Fold
                                Classifier = new ClassifierwithStringData(new kFold(10,knnVar));
                            }
                        }
                    } else { //Naive
                        if (trainMethod==1) {
                            //Lakukan training metode Full training
                            Classifier = new ClassifierwithStringData(naiveBayesVar);

                        } else {
                            //Lakukan Training metode 10 Fold
                            Classifier = new ClassifierwithStringData(new kFold(10,naiveBayesVar));
                        }
                    }
                    try {
                        Classifier.setInputString(fl.getAttributesLegalValues());
                        Classifier.setOutputString(fl.getLabelsLegalValues());
                        Classifier.train(fl.getAttributes(),fl.getLabels());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (Classifier!=null) {
                        System.out.println(file.getName()+algoType+"-"+trainMethod);
                        if ((algoType==1)&&(trainMethod==1)&&(file.getName().equals("zoo.data"))) {
                            File file = new File("NaiveHypothesisZoo.txt");
                            OutputStream fOut = new FileOutputStream(file);
                            Classifier.writeHypothesis(fOut);
                        } else {
                            File file = new File("Hypothesis.txt");
                            OutputStream fOut = new FileOutputStream(file);
                            Classifier.writeHypothesis(fOut);
                        }
                        trainResult="Accuracy : " + Classifier.accuracy(fl.getAttributes(), fl.getLabels())+ "\n" +
                                "Confusion Matrix : \n" +
                                confusionMatrixString(Classifier.getOutputString(),Classifier.calculateConfusionMatrix(fl.getAttributes(),fl.getLabels()));

                        //Bikin Windows baru dengan stage textarea untuk menampilkan hasil training
                        Parent root;
                        try {
                            root = FXMLLoader.load(getClass().getResource("../View/TrainingOutput.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setTitle("Train Result");
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
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
