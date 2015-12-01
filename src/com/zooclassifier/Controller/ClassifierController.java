package com.zooclassifier.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.zooclassifier.Main.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ArrayChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.zooclassifier.Model.*;


public class ClassifierController implements Initializable,ControlledScreen{
    ScreensController myController;
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToggleButton Airborne;

    @FXML
    private ToggleButton Aquatic;

    @FXML
    private ToggleButton Backbone;

    @FXML
    private ToggleButton Breathes;

    @FXML
    private ToggleButton Catsize;

    @FXML
    private ToggleButton Domestic;

    @FXML
    private ToggleButton Eggs;

    @FXML
    private ToggleButton Feathers;

    @FXML
    private ToggleButton Fins;

    @FXML
    private ToggleButton Hair;

    @FXML
    private ChoiceBox<?> Legs;

    @FXML
    private ToggleButton Milk;

    @FXML
    private ToggleButton Predator;

    @FXML
    private ToggleButton Tail;

    @FXML
    private ToggleButton Toothed;

    @FXML
    private ToggleButton Venomous;

    @FXML
    private Button Execute;

    @FXML
    private Label typeLabel;

    @FXML
    private RadioButton KNNbtn;

    @FXML
    private RadioButton NBbtn;

    @FXML
    private TextField nKNN;

    @FXML
    private Button homeButton;

    private int algoType;
    private boolean airborneValue;
    private boolean aquaticValue;
    private boolean backboneValue;
    private boolean breathesValue;
    private boolean catsizeValue;
    private boolean domesticValue;
    private boolean eggsValue;
    private boolean feathersValue;
    private boolean finsValue;
    private boolean hairValue;
    private boolean milkValue;
    private boolean predatorValue;
    private boolean tailValue;
    private boolean toothedValue;
    private boolean venomousValue;
    private int legsValue;

    private String keString(boolean b){
        if (b) {
            return "1";
        } else {
            return "0";
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        assert Airborne != null : "fx:id=\"Airborne\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Aquatic != null : "fx:id=\"Aquatic\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Backbone != null : "fx:id=\"Backbone\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Breathes != null : "fx:id=\"Breathes\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Catsize != null : "fx:id=\"Catsize\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Domestic != null : "fx:id=\"Domestic\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Eggs != null : "fx:id=\"Eggs\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Feathers != null : "fx:id=\"Feathers\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Fins != null : "fx:id=\"Fins\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Hair != null : "fx:id=\"Hair\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Legs != null : "fx:id=\"Legs\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Milk != null : "fx:id=\"Milk\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Predator != null : "fx:id=\"Predator\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Tail != null : "fx:id=\"Tail\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Toothed != null : "fx:id=\"Toothed\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        assert Venomous != null : "fx:id=\"Venomous\" was not injected: check your FXML file 'ZooClassifier.fxml'.";
        Legs.getSelectionModel().selectFirst();
        kNN knnVar = new kNN(1);
        NaiveBayes naiveBayesVar = new NaiveBayes();

        homeButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myController.setScreen(Main.MAIN_SCREEN);
            }
        });

        KNNbtn.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                algoType = 0;
            }
        });

        NBbtn.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                algoType = 1;
            }
        });

        Airborne.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                airborneValue=Airborne.isSelected();
            }
        });

        Aquatic.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                aquaticValue=Aquatic.isSelected();
            }
        });
        Backbone.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                backboneValue=Backbone.isSelected();
            }
        });
        Breathes.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                breathesValue=Breathes.isSelected();
            }
        });
        Catsize.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                catsizeValue=Catsize.isSelected();
            }
        });
        Domestic.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                domesticValue=Domestic.isSelected();
            }
        });
        Eggs.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                eggsValue=Eggs.isSelected();
            }
        });
        Feathers.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                feathersValue=Feathers.isSelected();
            }
        });
        Fins.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                finsValue=Fins.isSelected();
            }
        });
        Hair.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                hairValue=Hair.isSelected();
            }
        });
        Milk.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                milkValue=Milk.isSelected();
            }
        });
        Predator.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                predatorValue=Predator.isSelected();
            }
        });
        Tail.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                tailValue=Tail.isSelected();
            }
        });
        Toothed.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                toothedValue=Toothed.isSelected();
            }
        });
        Venomous.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                venomousValue= Venomous.isSelected();
            }
        });

        Legs.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            int[] legSelection = new int [] {0,2,4,5,6,8};
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                legsValue = legSelection[newValue.intValue()];
            }
        });
        Execute.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                String [] Masukan = {keString(hairValue),
                        keString(feathersValue),
                        keString(eggsValue),
                        keString(milkValue),
                        keString(airborneValue),
                        keString(aquaticValue),
                        keString(predatorValue),
                        keString(toothedValue),
                        keString(backboneValue),
                        keString(breathesValue),
                        keString(venomousValue),
                        keString(finsValue),
                        String.valueOf(legsValue),
                        keString(tailValue),
                        keString(domesticValue),
                        keString(catsizeValue)};

                try {
                    String type="";
                    ClassifierwithStringData Classifier;
                    ZooFileLoader fl = new ZooFileLoader("./res/zoo.data");
                    if (algoType == 0) { //KNN
                        if ((!nKNN.getText().isEmpty())&&(nKNN.getText()!=null)) {
                            try {
                                knnVar.setK(Integer.parseInt(nKNN.getText()));
                                Classifier = new ClassifierwithStringData(knnVar);
                                Classifier.setInputString(fl.getAttributesLegalValues());
                                Classifier.setOutputString(fl.getLabelsLegalValues());
                                Classifier.train(fl.getAttributes(), fl.getLabels());
                                type = Classifier.predict(Masukan);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else { //Naive
                        Classifier = new ClassifierwithStringData(naiveBayesVar);
                        try {
                            Classifier.setInputString(fl.getAttributesLegalValues());
                            Classifier.setOutputString(fl.getLabelsLegalValues());
                            Classifier.train(fl.getAttributes(), fl.getLabels());
                            type = Classifier.predict(Masukan);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    typeLabel.setText("Type = ".concat(type));
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


}
